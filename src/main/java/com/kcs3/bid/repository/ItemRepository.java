package com.kcs3.bid.repository;

import com.kcs3.bid.entity.Item;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // 물품 ID로 물품 객체 조회시 사용자 정보도 함께 조회
    @Query("""
            SELECT i FROM Item i
            JOIN FETCH i.seller
            WHERE i.itemId = :itemId
        """)
    Optional<Item> findItemWithSellerByItemId(@Param("itemId") Long itemId);
}
