package com.kcs3.bid.dto;

import lombok.Builder;

@Builder
public record AuctionPriceDto(int buyNowPrice,
                              int maxPrice
) {
}
