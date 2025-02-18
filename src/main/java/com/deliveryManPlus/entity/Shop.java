package com.deliveryManPlus.entity;

import com.deliveryManPlus.utils.StringUtils;
import com.deliveryManPlus.constant.ShopStatus;
import com.deliveryManPlus.dto.shop.ShopUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Builder
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

    public Shop() {
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
}
