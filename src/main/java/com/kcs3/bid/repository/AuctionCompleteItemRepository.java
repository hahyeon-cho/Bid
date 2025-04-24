package com.kcs3.bid.repository;

import com.kcs3.bid.dto.AuctionPriceRawDto;
import com.kcs3.bid.entity.AuctionCompleteItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionCompleteItemRepository extends JpaRepository<AuctionCompleteItem, Long> {
    @Query("SELECT new com.kcs3.bid.dto.AuctionPriceDto(aci.buyNowPrice, aci.maxPrice) " +
            "FROM AuctionCompleteItem aci " +
            "WHERE aci.item.itemId = :itemId")
    Optional<AuctionPriceDto> findPriceByItemItemId(Long itemId);

    // 물품 ID로 경매 완료 테이블 정보 조회
    Optional<AuctionCompleteItem> findByItemItemId(Long itemId);

    // 물품 ID로 완료된 경매의 가격 정보 조회 (즉시 구매가, 최고가, 최고 입찰자 닉네임)
    @Query("""
        SELECT new com.kcs3.bid.dto.AuctionPriceRawDto(aci.buyNowPrice, aci.maxPrice, aci.maxBidUserNickname)
        "FROM AuctionCompleteItem aci
        "WHERE aci.item.itemId = :itemId
        """)
    Optional<AuctionPriceRawDto> findAuctionPriceByItemId(@Param("itemId") Long itemId);
}
