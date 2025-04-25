package com.kcs3.bid.controller;

import com.kcs3.bid.dto.AuctionBidRequestDto;
import com.kcs3.bid.dto.AuctionInfosDto;
import com.kcs3.bid.service.AuctionBidService;
import com.kcs3.bid.service.AuctionInfoService;
import com.kcs3.bid.dto.ResponseDto;
import com.kcs3.bid.exception.CommonException;
import com.kcs3.bid.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/auction/item")
public class AuctionBidController {

    @Autowired
    private AuctionInfoService auctionInfoService;
    @Autowired
    private AuctionBidService auctionBidService;

    /**
    /**
     * 경매 입찰 참여 API
     * @return ResponseDto<String> 입찰 결과
     */
    @PostMapping("/{itemId}/bid")
    public ResponseDto<?> submitBid(@PathVariable Long itemId,
                                    @RequestBody AuctionBidRequestDto auctionBidRequestDto) {
        try {
            auctionBidService.attemptBid(itemId,
                                        auctionBidRequestDto.userId(),
                                        auctionBidRequestDto.nickname(),
                                        auctionBidRequestDto.bidPrice());
            return ResponseDto.ok("입찰에 성공하였습니다.");
        } catch (CommonException e) {
            return ResponseDto.fail(e);
        } catch (Exception e) {
            log.error("컨트롤러 에러 {}", e.getMessage());
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}