package com.deliveryManPlus.review.service.imp;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.ReviewErrorCode;
import com.deliveryManPlus.order.entity.Order;
import com.deliveryManPlus.order.repository.OrderRepository;
import com.deliveryManPlus.review.dto.ReviewCreateRequestDto;
import com.deliveryManPlus.review.dto.ReviewForCustomerResponseDto;
import com.deliveryManPlus.review.dto.ReviewForShopResponseDto;
import com.deliveryManPlus.review.entity.Review;
import com.deliveryManPlus.review.repository.ReviewRepository;
import com.deliveryManPlus.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.deliveryManPlus.common.utils.SecurityUtils.getUser;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public void createReview(Long orderId, ReviewCreateRequestDto dto) {
        //order 검증
        Order order = orderRepository.findByIdOrElseThrow(orderId);
        User customer = getUser();
        if (!order.getCustomer().getId().equals(customer.getId())) {
            throw new IllegalArgumentException("주문자만 리뷰를 작성할 수 있습니다.");
        }

        //review 검증
        if (reviewRepository.existsByOrder(order)) {
            throw new ApiException(ReviewErrorCode.ALREADY_REVIEWED);
        }

        Review review = dto.toEntity(order, customer);

        reviewRepository.save(review);

    }

    @Override
    public Page<ReviewForCustomerResponseDto> getReviewForUserList(Pageable pageable) {
        return reviewRepository.findByCustomer(getUser(), pageable)
                .map(ReviewForCustomerResponseDto::new);
    }

    @Override
    public Page<ReviewForShopResponseDto> getReviewForShopList(Pageable pageable, Long shopId) {

        return reviewRepository.findByShopId(shopId, pageable)
                .map(r ->
                        new ReviewForShopResponseDto(r, getReviewQuantity(r), getReviewAvg(r))
        );
    }

    @Override
    public ReviewForCustomerResponseDto getReview(Long reviewId) {
        return new ReviewForCustomerResponseDto(reviewRepository.findByIdOrElseThrow(reviewId));
    }

    private static double getReviewAvg(Review r) {
        return r.getCustomer().getReviewList()
                .stream()
                .mapToInt(Review::getScore)
                .average()
                .orElse(0);
    }

    private static Integer getReviewQuantity(Review r) {
        return r.getCustomer().getReviewList()
                .size();
    }


}
