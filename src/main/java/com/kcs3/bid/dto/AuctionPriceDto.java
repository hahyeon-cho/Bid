package com.kcs3.bid.dto;

import lombok.Builder;

@Builder
public record AuctionPriceDto(
    Integer buyNowPrice,
    Integer maxPrice
) {

    public static AuctionPriceDto from(AuctionPriceRawDto raw) {
        return AuctionPriceDto.builder()
            .buyNowPrice(raw.buyNowPrice())
            .maxPrice(raw.maxPrice())
            .build();
    }
}
