package com.kcs3.bid.service;

import com.kcs3.bid.dto.AuctionInfoSummeryDto;
import com.kcs3.bid.dto.AuctionInfosDto;
import com.kcs3.bid.dto.AuctionPriceDto;
import com.kcs3.bid.entity.Item;
import com.kcs3.bid.repository.AuctionCompleteItemRepository;
import com.kcs3.bid.repository.AuctionInfoRepository;
import com.kcs3.bid.repository.AuctionProgressItemRepository;
import com.kcs3.bid.repository.ItemRepository;
import com.kcs3.bid.exception.CommonException;
import com.kcs3.bid.exception.ErrorCode;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class AuctionInfoServiceImpl implements AuctionInfoService{
    @Autowired
    private AuctionProgressItemRepository auctionProgressItemRepo;
    @Autowired
    private AuctionCompleteItemRepository auctionCompleteItemRepo;
    @Autowired
    private AuctionInfoRepository auctionInfoRepo;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<AuctionInfosDto> getAuctionInfosDto(Long itemId) {
        log.debug("경매 내역 조회 - 물품 ID : {}", itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CommonException(ErrorCode.ITEM_NOT_FOUND));

        Optional<AuctionPriceDto> optionalAuctionPriceDto;
        if (item.isAuctionComplete()) {
            optionalAuctionPriceDto = auctionCompleteItemRepo.findPriceByItemItemId(itemId);
        } else {
            optionalAuctionPriceDto = auctionProgressItemRepo.findPriceByItemItemId(itemId);
        }

        if (optionalAuctionPriceDto.isEmpty()) {
            throw new CommonException(ErrorCode.AUCTION_PRICE_NOT_FOUND);
        }
        AuctionPriceDto auctionPriceDto = optionalAuctionPriceDto.get();

        List<AuctionInfoSummeryDto> auctionInfoSummaries = auctionInfoRepo
                .findInfoSummariesByItemId(itemId);

        AuctionInfosDto auctionInfosDto = AuctionInfosDto.builder()
                .info(auctionInfoSummaries)
                .buyNowPrice(auctionPriceDto.buyNowPrice())
                .maxPrice(auctionPriceDto.maxPrice())
                .auctionPriceDto(auctionPriceDto)
                .build();

        return Optional.of(auctionInfosDto);
    }//getAuctionInfos()
}//end class
