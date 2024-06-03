package com.kcs3.bid.repository;

import com.kcs3.bid.dto.AuctionBidHighestDto;
import com.kcs3.bid.dto.AuctionPriceDto;
import com.kcs3.bid.entity.AuctionProgressItem;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionProgressItemRepository extends JpaRepository<AuctionProgressItem, Long> {
    Optional<AuctionProgressItem> findByItemItemId(Long itemId);

    @Query("SELECT new com.kcs3.bid.dto.AuctionPriceDto(api.buyNowPrice, api.maxPrice) " +
            "FROM AuctionProgressItem api " +
            "WHERE api.item.itemId = :itemId")
    Optional<AuctionPriceDto> findPriceByItemItemId(Long itemId);

    @Query("SELECT new com.kcs3.bid.dto.AuctionBidHighestDto(" +
                "api.auctionProgressItemId, user.userId, user.userNickname, api.maxPrice) " +
            "FROM AuctionProgressItem api " +
            "LEFT JOIN api.user user " +
            "WHERE api.auctionProgressItemId = :auctionProgressItemId")
    Optional<AuctionBidHighestDto> findHighestBidByAuctionProgressItemId(@Param("auctionProgressItemId") Long auctionProgressItemId);

    Optional<List<AuctionProgressItem>> findAllByBidFinishTimeBefore(LocalDateTime now);
}

