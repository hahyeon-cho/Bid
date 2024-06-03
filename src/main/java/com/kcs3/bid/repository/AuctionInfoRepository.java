package com.kcs3.bid.repository;

import com.kcs3.bid.dto.AuctionInfoSummeryDto;
import com.kcs3.bid.entity.AuctionInfo;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionInfoRepository extends JpaRepository<AuctionInfo, Long> {
    @Query("SELECT new com.kcs3.bid.dto.AuctionInfoSummeryDto(user.userNickname, ai.bidPrice) " +
            "FROM AuctionInfo ai " +
            "JOIN ai.user user " +
            "WHERE ai.item.itemId = :itemId")
    List<AuctionInfoSummeryDto> findInfoSummariesByItemId(Long itemId);


}
