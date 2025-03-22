package com.deliveryManPlus.shop.entity;

import com.deliveryManPlus.category.entity.Category;
import com.deliveryManPlus.review.entity.Review;
import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.common.entity.CreateAndUpdateDateEntity;
import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.menu.entity.Menu;
import com.deliveryManPlus.shop.dto.ShopUpdateRequestDto;
import com.deliveryManPlus.common.utils.StringUtils;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
public class Shop extends CreateAndUpdateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String registNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private BigDecimal minimumOrderAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShopStatus status;

    @Column(nullable = false)
    private String openAt;

    @Column(nullable = false)
    private String closedAt;

    @Column(nullable = false)
    private String closedDay;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "shop")
    private List<Menu> menuList;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "shop")
    private List<Review> reviewList;

    @Builder
    public Shop(String registNumber, String name, String address, BigDecimal minimumOrderAmount, ShopStatus status, String openAt, String closedAt, String closedDay) {
        this.registNumber = registNumber;
        this.name = name;
        this.address = address;
        this.minimumOrderAmount = minimumOrderAmount;
        this.status = status;
        this.openAt = openAt;
        this.closedAt = closedAt;
        this.closedDay = closedDay;
    }

    public void updateOwner(User user) {
        this.owner= user;
    }

    public void updateByDto(ShopUpdateRequestDto dto) {
        this.name = dto.getName();
        this.address = dto.getAddress();
        this.openAt = dto.getOpenAt();
        this.closedAt = dto.getClosedAt();
        this.closedDay = StringUtils.toStringWithComma(dto.getClosedDay());
        this.minimumOrderAmount = dto.getMinimumOrderAmount();
    }

    public void updateStatus(ShopStatus status) {
        this.status = status;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }
}
