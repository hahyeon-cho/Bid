package com.kcs3.bid.dto;

import lombok.Builder;

@Builder
public record AuctionPriceRawDto(
    Integer buyNowPrice,
    Integer maxPrice,
    String maxBidUserNickname
) {

}
