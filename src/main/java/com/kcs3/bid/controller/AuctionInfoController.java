package com.kcs3.bid.controller;

import com.kcs3.bid.dto.AuctionInfoResponseDto;
import com.kcs3.bid.dto.ResponseDto;
import com.kcs3.bid.service.AuctionInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/no-auth/auction/item")
public class AuctionInfoController {

    private final AuctionInfoService auctionInfoService;

    /**
     * 경매 입찰 내역을 조회합니다.
     *
     * @param itemId 물품 ID
     * @return 물품에 대한 경매 입찰 기록, 현재 입찰 가격 정보
     */
    @GetMapping("/{itemId}/bid")
    public ResponseDto<AuctionInfoResponseDto> getAuctionHistoryByItemId(@PathVariable Long itemId) {
        return ResponseDto.ok(auctionInfoService.getAuctionInfosByItemId(itemId));
    }
}
