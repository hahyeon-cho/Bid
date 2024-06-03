package com.kcs3.bid.dto;

import lombok.Builder;

@Builder
public record AuctionInfoSummeryDto(String name,
                                        int price
) {
}
