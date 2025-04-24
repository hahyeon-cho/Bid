package com.kcs3.bid.service;

import com.kcs3.bid.dto.AuctionInfoResponseDto;

public interface AuctionInfoService {

    // 물품의 경매 내역 조회
    AuctionInfoResponseDto getAuctionInfosByItemId(Long itemId);
}
