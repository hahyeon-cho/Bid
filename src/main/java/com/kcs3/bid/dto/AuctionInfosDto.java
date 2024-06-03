package com.kcs3.bid.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record AuctionInfosDto(List<AuctionInfoSummeryDto> info,
                              int buyNowPrice,
                              int maxPrice,
                              AuctionPriceDto auctionPriceDto
) {
}