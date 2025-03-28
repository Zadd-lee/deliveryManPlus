package com.deliveryManPlus.review.repository;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.ReviewErrorCode;
import com.deliveryManPlus.order.entity.Order;
import com.deliveryManPlus.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByOrder(Order order);

    Page<Review> findByCustomer(User customer, Pageable pageable);

    Page<Review> findByShopId(Long shopId, Pageable pageable);

    default Review findByIdOrElseThrow(Long reviewId) {
        return findById(reviewId).orElseThrow(() -> new ApiException(ReviewErrorCode.NOT_FOUND));
    }

}
