package com.kcs3.bid.service;

import com.kcs3.bid.entity.AuctionInfo;
import com.kcs3.bid.entity.AuctionProgressItem;
import com.kcs3.bid.entity.Item;
import com.kcs3.bid.entity.User;
import com.kcs3.bid.exception.CommonException;
import com.kcs3.bid.exception.ErrorCode;
import com.kcs3.bid.repository.AuctionInfoRepository;
import com.kcs3.bid.repository.AuctionProgressItemRepository;
import com.kcs3.bid.repository.ItemRepository;
import com.kcs3.bid.utils.AuthUserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionBidServiceImpl implements AuctionBidService {

    private final AuthUserProvider authUserProvider;
    private final AuctionCompleteService auctionCompleteService;

    private final ItemRepository itemRepository;
    private final AuctionProgressItemRepository progressItemRepository;
    private final AuctionInfoRepository auctionInfoRepository;

    // 입찰 참여
    @Override
    @Transactional
    public void attemptBid(Long itemId, int bidPrice) {
        User user = authUserProvider.getCurrentUser();

        Item item = itemRepository.findItemWithSellerByItemId(itemId)
            .orElseThrow(() -> new CommonException(ErrorCode.ITEM_NOT_FOUND));

        if (user.getUserId().equals(item.getSeller().getUserId())) {
            throw new CommonException(ErrorCode.BIDDER_IS_SELLER);
        }

        AuctionProgressItem progressItem = progressItemRepository.findProgressItemWithMaxBidUserByItemId(itemId)
            .orElseThrow(() -> new CommonException(ErrorCode.ITEM_NOT_FOUND));

        // 즉시 구매
        if (progressItem.getBuyNowPrice() != null && bidPrice >= progressItem.getBuyNowPrice()) {
            log.debug("User {}가 Item {}을 즉시 구매 - 가격: {}", user.getUserId(), itemId, bidPrice);

            registerBid(item, user, progressItem, bidPrice);
            auctionCompleteService.completeAuction(progressItem);
            return;
        }

        // 현재 최고 입찰자 & 최고 가격 비교
        Long maxBidUserId = progressItem.getMaxBidUser().getUserId();

        if (maxBidUserId != null) {
            if (user.getUserId().equals(maxBidUserId)) {
                throw new CommonException(ErrorCode.BIDDER_IS_CURRENT_HIGHEST);
            }
            if (bidPrice <= progressItem.getMaxPrice()) {
                throw new CommonException(ErrorCode.BID_PRICE_TOO_LOW);
            }
        } else if (bidPrice < progressItem.getMaxPrice()) {
            throw new CommonException(ErrorCode.BID_PRICE_TOO_LOW);
        }

        registerBid(item, user, progressItem, bidPrice);
    }

    private void registerBid(Item item, User user, AuctionProgressItem progressItem, int bidPrice) {
        AuctionInfo auctionInfo = AuctionInfo.builder()
            .item(item)
            .user(user)
            .bidPrice(bidPrice)
            .build();
        auctionInfoRepository.save(auctionInfo);

        progressItem.updateAuctionMaxBid(user, user.getNickname(), bidPrice);
        progressItemRepository.save(progressItem);
    }
}