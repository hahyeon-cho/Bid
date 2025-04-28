package com.kcs3.bid.controller;

import com.kcs3.bid.dto.AuctionBidRequestDto;
import com.kcs3.bid.dto.ResponseDto;
import com.kcs3.bid.service.AuctionBidService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/auction/item")
public class AuctionBidController {

    private final AuctionBidService auctionBidService;

    /**
     * 경매 물품에 관한 입찰 진행
     *
     * @param itemId               입찰할 경매 물품 ID
     * @param auctionBidRequestDto 입찰 가격 요청 DTO
     * @return 입찰 성공 여부
     */
    @PostMapping("/{itemId}/bid")
    public ResponseDto<String> createBid(
        @PathVariable Long itemId,
        @RequestBody AuctionBidRequestDto auctionBidRequestDto
    ) {
        auctionBidService.attemptBid(itemId, auctionBidRequestDto.getBidPrice());
        return ResponseDto.ok("입찰에 성공하였습니다.");
    }
}