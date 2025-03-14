package com.deliveryManPlus.review.repository;

import com.deliveryManPlus.order.entity.Order;
import com.deliveryManPlus.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByOrder(Order order);
}
