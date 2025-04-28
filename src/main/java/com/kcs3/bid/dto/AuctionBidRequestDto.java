package com.kcs3.bid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuctionBidRequestDto{
    @NotNull(message = "입찰 가격은 필수 입력 정보입니다.")
    @JsonProperty("price")
    Integer bidPrice;
}
