package com.kcs3.bid.dto;
import lombok.Builder;

@Builder
public record AuctionBidHighestDto(
        Long auctionProgressItemId,
        Long userId,
        String maxPersonNickName,
        Integer maxPrice
) {
}