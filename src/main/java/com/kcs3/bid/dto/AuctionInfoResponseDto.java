package com.kcs3.bid.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionInfoResponseDto {

    List<AuctionInfoDto> infos;
    AuctionPriceDto price;

    @Builder
    public AuctionInfoResponseDto(List<AuctionInfoDto> infos, AuctionPriceDto price) {
        this.infos = infos;
        this.price = price;
    }
}
