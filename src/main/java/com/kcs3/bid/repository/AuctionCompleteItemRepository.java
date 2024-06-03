package com.kcs3.bid.repository;

import com.kcs3.bid.dto.AuctionPriceDto;
import com.kcs3.bid.entity.AuctionCompleteItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuctionCompleteItemRepository extends JpaRepository<AuctionCompleteItem, Long> {
    @Query("SELECT new com.kcs3.bid.dto.AuctionPriceDto(aci.buyNowPrice, aci.maxPrice) " +
            "FROM AuctionCompleteItem aci " +
            "WHERE aci.item.itemId = :itemId")
    Optional<AuctionPriceDto> findPriceByItemItemId(Long itemId);

    Optional<AuctionCompleteItem> findByItemItemId(Long itemId);
}
