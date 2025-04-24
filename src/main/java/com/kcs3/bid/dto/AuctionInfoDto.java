package com.kcs3.bid.dto;

import lombok.Builder;

@Builder
public record AuctionInfoDto(
    String nickname,
    Integer bidPrice
) {

}
