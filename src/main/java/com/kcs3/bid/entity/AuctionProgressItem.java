package com.kcs3.bid.entity;

import com.kcs3.bid.model.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "AuctionProgressItem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
public class AuctionProgressItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="auctionProgressItemId", nullable = false)
    private Long auctionProgressItemId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", nullable = false)
    private Item item;

    @Column(nullable = false)
    private String itemTitle;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private Integer startPrice;
    private Integer buyNowPrice;

    @Column(nullable = false)
    private LocalDateTime bidFinishTime;

    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private String maxPersonNickName;

    @Column(nullable = false)
    private Integer maxPrice;

    @Version
    private Integer version;


    public static class AuctionProgressItemBuilder {
        public AuctionProgressItemBuilder startPrice(int startPrice) {
            this.startPrice = startPrice;
            this.maxPrice = startPrice;
            return this;
        }
    }

    public void updateAuctionMaxBid(User user, String nickname, int price) {
        this.user = user;
        this.maxPersonNickName = nickname;
        this.maxPrice = price;
    }
}
