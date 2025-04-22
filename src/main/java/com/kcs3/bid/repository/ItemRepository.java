package com.kcs3.bid.repository;

import com.kcs3.bid.entity.AuctionCompleteItem;
import com.kcs3.bid.entity.AuctionProgressItem;
import com.kcs3.bid.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface  ItemRepository extends JpaRepository<Item, Long> {
        @Query("SELECT i.seller.userId FROM Item i WHERE i.itemId = :itemId")
        Long findSellerIdByItemId(@Param("itemId") Long itemId);

}
