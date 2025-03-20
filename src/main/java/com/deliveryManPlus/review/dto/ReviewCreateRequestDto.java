package com.deliveryManPlus.review.dto;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.order.entity.Order;
import com.deliveryManPlus.review.entity.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewCreateRequestDto {
    @NotBlank
    private String content;
    @PositiveOrZero
    private Integer score;


    public Review toEntity(Order order, User customer) {
        return Review.builder()
                .content(content)
                .score(score)
                .customer(customer)
                .shop(order.getShop())
                .order(order)
                .build();


    }
}
