package com.deliveryManPlus.category.entity;

import com.deliveryManPlus.common.constant.Status;
import com.deliveryManPlus.common.entity.CreateAndUpdateDateEntity;
import com.deliveryManPlus.shop.entity.Shop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Category extends CreateAndUpdateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "category")
    private List<Shop> shopList;

    public Category(String name) {
        this.name = name;
        this.status = Status.USE;
    }

    public void updateName(String name) {
        this.name = name;
    }
    public void updateStatus(Status status) {
        this.status = status;
    }
}
