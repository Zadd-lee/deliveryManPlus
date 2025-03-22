package com.deliveryManPlus.review.service.imp;

import com.deliveryManPlus.auth.constant.Role;
import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.ReviewErrorCode;
import com.deliveryManPlus.image.model.entity.Image;
import com.deliveryManPlus.image.model.vo.ImageTarget;
import com.deliveryManPlus.image.service.ImageService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.deliveryManPlus.common.utils.SecurityUtils.getUser;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ImageService imageService;

    @Transactional
    @Override
    public void createReview(Long orderId, ReviewCreateRequestDto dto, List<MultipartFile> imageList) {
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

        //이미지 저장
         ImageTarget imageTarget = new ImageTarget(review.getId(), this.getClass().getSimpleName());
         imageService.save(imageTarget, imageList);

    }

    @Override
    public Page<ReviewForCustomerResponseDto> getReviewForUserList(Pageable pageable) {
        Page<Review> review = reviewRepository.findByCustomer(getUser(), pageable);
        //이미지 가져오기
        List<ImageTarget> imageTargetList = review.getContent()
                .stream()
                .map(r1 -> new ImageTarget(r1.getId(), this.getClass().getSimpleName()))
                .toList();
        List<Image> imageList = imageService.findImageByTargetList(imageTargetList);

        return review
                .map(r -> new ReviewForCustomerResponseDto(r, imageList));
    }

    @Override
    public Page<ReviewForShopResponseDto> getReviewForShopList(Pageable pageable, Long shopId) {

        Page<Review> review = reviewRepository.findByShopId(shopId, pageable);


        List<ImageTarget> imageTargetList = review.getContent()
                .stream()
                .map(r1 -> new ImageTarget(r1.getId(), this.getClass().getSimpleName()))
                .toList();

        List<Image> imageList = imageService.findImageByTargetList(imageTargetList);

        return review
                .map(r ->
                        new ReviewForShopResponseDto(r, getReviewQuantity(r), getReviewAvg(r),imageList)
                );
    }

    @Override
    public ReviewForCustomerResponseDto getReview(Long reviewId) {
        Review review = reviewRepository.findByIdOrElseThrow(reviewId);
        //이미지 가져오기
        ImageTarget imageTarget = new ImageTarget(review.getId(), this.getClass().getSimpleName());
        List<Image> imageList = imageService.findImageByTarget(imageTarget);
        return new ReviewForCustomerResponseDto(review,imageList);
    }

    @Transactional
    @Override
    public void deleteReview(Long reviewId) {
        User user = getUser();
        Review review = reviewRepository.findByIdOrElseThrow(reviewId);

        //validate user
        if(user.getRole()!= Role.ADMIN &&!review.getCustomer().getId().equals(user.getId())){
            throw new ApiException(ReviewErrorCode.NOT_AUTHORIZED);
        }

        review.delete();

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
