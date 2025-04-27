package com.kcs3.bid.repository;

import com.kcs3.bid.dto.AuctionInfoDto;
import com.kcs3.bid.entity.AuctionInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionInfoRepository extends JpaRepository<AuctionInfo, Long> {

    // 물품 ID로 경매 내역 조회
    @Query("""
        SELECT new com.kcs3.bid.dto.AuctionInfoDto(user.nickname, ai.bidPrice)
        FROM AuctionInfo ai
        JOIN ai.user user
        WHERE ai.item.itemId = :itemId
        ORDER BY ai.createdAt ASC
        """)
    List<AuctionInfoDto> findInfosByItemId(@Param("itemId") Long itemId);
}
