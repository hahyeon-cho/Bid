package com.kcs3.bid.service;

import com.kcs3.bid.dto.AuctionInfosDto;
import java.util.Optional;

public interface AuctionInfoService {
    /**
     * 물품의 경매 내역 조회
     */
    Optional<AuctionInfosDto> getAuctionInfosDto(Long itemId);

}
