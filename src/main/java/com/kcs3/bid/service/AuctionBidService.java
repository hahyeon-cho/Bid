package com.kcs3.bid.service;

public interface AuctionBidService {

    // 입찰 시도
    void attemptBid(Long itemId, int bidPrice);
}
