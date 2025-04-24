package com.kcs3.bid.service;

import com.kcs3.bid.dto.AuctionInfoDto;
import com.kcs3.bid.dto.AuctionInfoResponseDto;
import com.kcs3.bid.dto.AuctionPriceDto;
import com.kcs3.bid.dto.AuctionPriceRawDto;
import com.kcs3.bid.entity.Item;
import com.kcs3.bid.exception.CommonException;
import com.kcs3.bid.exception.ErrorCode;
import com.kcs3.bid.repository.AuctionCompleteItemRepository;
import com.kcs3.bid.repository.AuctionInfoRepository;
import com.kcs3.bid.repository.AuctionProgressItemRepository;
import com.kcs3.bid.repository.ItemRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionInfoServiceImpl implements AuctionInfoService {

    private final ItemRepository itemRepository;
    private final AuctionProgressItemRepository auctionProgressItemRepo;
    private final AuctionCompleteItemRepository auctionCompleteItemRepo;
    private final AuctionInfoRepository auctionInfoRepo;

    @Override
    @Transactional(readOnly = true)
    public AuctionInfoResponseDto getAuctionInfosByItemId(Long itemId) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new CommonException(ErrorCode.ITEM_NOT_FOUND));

        AuctionPriceRawDto rawPriceDto;
        if (!item.isAuctionComplete()) {
            rawPriceDto = auctionProgressItemRepo.findAuctionPriceByItemId(itemId)
                .orElseThrow(() -> new CommonException(ErrorCode.AUCTION_PRICE_NOT_FOUND));
        } else {
            rawPriceDto = auctionCompleteItemRepo.findAuctionPriceByItemId(itemId)
                .orElseThrow(() -> new CommonException(ErrorCode.AUCTION_PRICE_NOT_FOUND));
        }

        // 현재 최대 입찰자가 존재하는 경우에만 경매 내역 조회 (존재하지 않을 경우 경매 내역이 없음)
        List<AuctionInfoDto> auctionInfos = Collections.emptyList();
         if (rawPriceDto.maxBidUserNickname() != null) {
            auctionInfos = auctionInfoRepo.findInfosByItemId(itemId);
            if (auctionInfos.isEmpty()) {
                throw new CommonException(ErrorCode.AUCTION_HISTORY_NOT_FOUND);
            }
        }

        return AuctionInfoResponseDto.builder()
            .infos(auctionInfos)
            .price(AuctionPriceDto.from(rawPriceDto)) // 가격 정보만 응답에 포함
            .build();
    }
}
