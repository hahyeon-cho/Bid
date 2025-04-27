package com.kcs3.bid.repository;

import com.kcs3.bid.dto.AuctionPriceRawDto;
import com.kcs3.bid.entity.AuctionProgressItem;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionProgressItemRepository extends JpaRepository<AuctionProgressItem, Long> {

    // 물품 ID로 경매 진행 테이블 정보 조회
    Optional<AuctionProgressItem> findByItemItemId(Long itemId);

    // 물품 ID 기준으로 진행 중인 경매의 가격 정보 조회 (즉시 구매가, 최고가, 최고 입찰자 닉네임)
    @Query("""
        SELECT new com.kcs3.bid.dto.AuctionPriceRawDto(api.buyNowPrice, api.maxPrice, api.maxBidUserNickname)
        FROM AuctionProgressItem api
        WHERE api.item.itemId = :itemId
        """)
    Optional<AuctionPriceRawDto> findAuctionPriceByItemId(@Param("itemId") Long itemId);

    @Query("SELECT new com.kcs3.bid.dto.AuctionBidHighestDto(" +
                "api.auctionProgressItemId, user.userId, user.userNickname, api.maxPrice) " +
            "FROM AuctionProgressItem api " +
            "LEFT JOIN api.user user " +
            "WHERE api.auctionProgressItemId = :auctionProgressItemId")
    Optional<AuctionBidHighestDto> findHighestBidByAuctionProgressItemId(@Param("auctionProgressItemId") Long auctionProgressItemId);

    // 현재 시간 기준으로 경매 완료 처리되어야하는 물품 목록 조회
    @Query("SELECT api FROM AuctionProgressItem api JOIN FETCH api.item WHERE api.bidFinishTime < :now")
    Optional<List<AuctionProgressItem>> findAllByBidFinishTimeBefore(LocalDateTime now);
}
