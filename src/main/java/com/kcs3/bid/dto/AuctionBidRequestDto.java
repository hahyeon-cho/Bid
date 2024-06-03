package com.kcs3.bid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuctionBidRequestDto(Long itemId,
                                   @JsonProperty("price") int bidPrice,
                                   Long userId,
                                   String nickname
) {
}
