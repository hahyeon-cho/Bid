package com.kcs3.bid.service;

import com.kcs3.bid.entity.AuctionProgressItem;
import com.kcs3.bid.repository.AuctionProgressItemRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionAutoExpireService {

    private final AuctionCompleteService auctionCompleteService;
    private final AuctionProgressItemRepository progressItemRepository;

    @Scheduled(cron = "0 0 * * * *")  // 매 시간 정각에 실행
    public void finishAuctionsByTime() {
        LocalDateTime now = LocalDateTime.now();
        Optional<List<AuctionProgressItem>> completedItemsOptional
            = progressItemRepository.findAllByBidFinishTimeBefore(now);

        log.info("스케쥴러 test scheduler" + completedItemsOptional.isPresent() + ": 현재시간" + now);
        if (completedItemsOptional.isPresent()) {
            List<AuctionProgressItem> completedItems = completedItemsOptional.get();
            completedItems.forEach(item -> auctionCompleteService.completeAuction(item));
        } else {
            log.info("현재 경매 완료된 물품이 존재하지 않습니다. - {} ", now);
        }
    }
}
