package com.kcs3.bid.service;

import com.kcs3.bid.entity.Alarm;
import com.kcs3.bid.entity.AuctionCompleteItem;
import com.kcs3.bid.entity.AuctionProgressItem;
import com.kcs3.bid.entity.ChattingRoom;
import com.kcs3.bid.entity.Item;
import com.kcs3.bid.exception.CommonException;
import com.kcs3.bid.exception.ErrorCode;
import com.kcs3.bid.repository.AlarmRepository;
import com.kcs3.bid.repository.AuctionCompleteItemRepository;
import com.kcs3.bid.repository.AuctionProgressItemRepository;
import com.kcs3.bid.repository.ChattingRoomRepository;
import com.kcs3.bid.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class AuctionCompleteService {

    private final ItemRepository itemRepository;
    private final AuctionProgressItemRepository progressItemRepository;
    private final AuctionCompleteItemRepository completeItemRepository;

    private final AlarmRepository alarmRepository;
    private final ChattingRoomRepository chattingRoomRepository;

    @Transactional
    void completeAuction(AuctionProgressItem progressItem) {
        boolean isBidComplete = checkBidCompletionStatus(progressItem);
        AuctionCompleteItem completeItem = convertAuctionCompleteItem(progressItem, isBidComplete);

        // 경매 완료 여부 데이터를 업데이트하고, 테이블 변경
        Item item = progressItem.getItem();
        item.updateAuctionStatus();

        itemRepository.save(item);
        completeItemRepository.save(completeItem);
        progressItemRepository.delete(progressItem);

        if (isBidComplete) {
            notifyAuctionSuccess(completeItem);
        } else {
            notifyAuctionFinish(completeItem);
        }
    }

    private boolean checkBidCompletionStatus(AuctionProgressItem progressItem) {
        boolean userIsNull = progressItem.getMaxBidUser() == null;
        boolean maxBidUserNicknameIsNull = progressItem.getMaxBidUserNickname() == null;

        if (userIsNull && maxBidUserNicknameIsNull) {
            return false;
        } else if (userIsNull || maxBidUserNicknameIsNull) {
            log.error("{}: 해당 경매 물품 입찰 정보가 유효하지 않습니다.", progressItem.getAuctionProgressItemId());
            throw new CommonException(ErrorCode.ITEM_BID_FIELD_MISMATCH);
        }

        return true;
    }

    private void notifyAuctionSuccess(AuctionCompleteItem progressItem) {
        chattingRoomRepository.save(ChattingRoom.builder()
            .buyer(progressItem.getMaxBidUser())
            .seller(progressItem.getItem().getSeller())
            .completeItem(progressItem)
            .build());

        alarmRepository.save(Alarm.builder()
            .alarmContent(progressItem.getItemTitle() + "이(가) 낙찰되었습니다.")
            .user(progressItem.getItem().getSeller())
            .build());

        alarmRepository.save(Alarm.builder()
            .alarmContent(progressItem.getItemTitle() + "을(를) 낙찰하였습니다.")
            .user(progressItem.getMaxBidUser())
            .build());
    }

    private void notifyAuctionFinish(AuctionCompleteItem progressItem) {
        alarmRepository.save(Alarm.builder()
            .alarmContent(progressItem.getItemTitle() + "의 경매가 완료되었습니다.")
            .user(progressItem.getItem().getSeller())
            .build());
    }

    private AuctionCompleteItem convertAuctionCompleteItem(AuctionProgressItem item, boolean isComplete) {
        return AuctionCompleteItem.builder()
            .item(item.getItem())
            .itemTitle(item.getItemTitle())
            .thumbnail(item.getThumbnail())
            .location(item.getLocation())
            .startPrice(item.getStartPrice())
            .buyNowPrice(item.getBuyNowPrice())
            .bidFinishTime(item.getBidFinishTime())
            .maxBidUser(item.getMaxBidUser())
            .maxBidUserNickname(item.getMaxBidUserNickname())
            .maxPrice(item.getMaxPrice())
            .isBidComplete(isComplete)
            .build();
    }
}
