package com.deliveryManPlus.order.service.imp;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.cart.entity.Cart;
import com.deliveryManPlus.cart.entity.CartMenu;
import com.deliveryManPlus.cart.repository.CartMenuOptionDetailRepository;
import com.deliveryManPlus.cart.repository.CartMenuRepository;
import com.deliveryManPlus.cart.repository.CartRepository;
import com.deliveryManPlus.common.constant.OrderBy;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.CouponErrorCode;
import com.deliveryManPlus.common.exception.constant.errorcode.OrderErrorCode;
import com.deliveryManPlus.common.exception.constant.errorcode.OrderStatus;
import com.deliveryManPlus.common.exception.constant.errorcode.ShopErrorCode;
import com.deliveryManPlus.coupon.entity.CouponUser;
import com.deliveryManPlus.coupon.repository.CouponRepository;
import com.deliveryManPlus.coupon.repository.CouponUserRepository;
import com.deliveryManPlus.order.dto.OrderDetailResponseDto;
import com.deliveryManPlus.order.dto.OrderSimpleResponseDto;
import com.deliveryManPlus.order.entity.Order;
import com.deliveryManPlus.order.entity.OrderMenu;
import com.deliveryManPlus.order.entity.OrderMenuOptionDetail;
import com.deliveryManPlus.order.repository.OrderMenuOptionDetailRepository;
import com.deliveryManPlus.order.repository.OrderRepository;
import com.deliveryManPlus.order.service.OrderService;
import com.deliveryManPlus.shop.entity.Shop;
import com.deliveryManPlus.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.deliveryManPlus.common.utils.EntityValidator.validate;
import static com.deliveryManPlus.common.utils.SecurityUtils.getUser;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMenuOptionDetailRepository orderMenuOptionDetailRepository;
    private final ShopRepository shopRepository;
    private final CartRepository cartRepository;
    private final CartMenuRepository cartMenuRepository;
    private final CartMenuOptionDetailRepository cartMenuOptionDetailRepository;
    private final CouponRepository couponRepository;
    private final CouponUserRepository couponUserRepository;


    @Transactional
    @Override
    public void createOrder(Long couponId) {
        User user = getUser();
        Cart cart = cartRepository.findByCustomerIdDesc(user.getId())
                .orElseThrow(() -> new ApiException(OrderErrorCode.CART_EMPTY));


        //주문 생성
        Order order = new Order(cart);

        //coupon 적용
        if (couponId != null) {
            CouponUser couponUser = couponUserRepository.findByCustomerAndCouponId(user, couponId)
                    .orElseThrow(() -> new ApiException(CouponErrorCode.NOT_FOUND));

            if (order.getTotalPrice().compareTo(couponUser.getCoupon().getDiscountPrice())<0) {
                throw new ApiException(OrderErrorCode.INVALID_COUPON);
            }

            order.updateCouponUser(couponUser);
            couponUser.useCoupon(order);
        }

        //order menu 생성
        List<OrderMenu> orderMenuList = cart.getCartMenuList()
                .stream()
                .map(OrderMenu::new)
                .toList();

        order.updateOrderMenu(orderMenuList);
        orderMenuList.forEach(orderMenu -> orderMenu.updateOrder(order));

        //order menu option 저장
        List<CartMenu> cartMenuList = cart.getCartMenuList();

        cartMenuList.forEach(cartMenu -> {
            // cart menu -> order menu 변환
            OrderMenu orderMenu = convertCartMenuToOrderMenu(cartMenu, order);

            // cart menu option -> order menu option 변환
            List<OrderMenuOptionDetail> orderMenuOptionDetailList = convertCartMenuOptionsToOrderMenuOptions(cartMenu);

            // order menu 에 옵션 추가
            orderMenu.updateOrderMenuOptionDetail(orderMenuOptionDetailList);
            orderMenuOptionDetailList.forEach(orderMenuOptionDetail -> orderMenuOptionDetail.updateOrderMenu(orderMenu));

            // order menu option detail 저장
            orderMenuOptionDetailRepository.saveAll(orderMenuOptionDetailList);
        });

        //저장
        orderRepository.save(order);

        //cart 삭제
        cart.getCartMenuList().stream()
                .map(CartMenu::getCartMenuOptionDetailList)
                .forEach(cartMenuOptionDetailRepository::deleteAll);
        cartMenuRepository.deleteAll(cartMenuList);
        cartRepository.delete(cart);
    }

    @Override
    public Page<OrderSimpleResponseDto> findAllOrderForUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(OrderBy.UPDATED_DATE.getName()).descending());
        return orderRepository.findByCustomer_Id(getUser().getId(), pageable)
                .map(OrderSimpleResponseDto::new);
    }

    @Override
    public OrderDetailResponseDto findOrderForUser(Long orderId) {
        Order order = orderRepository.findByIdOrElseThrow(orderId);
        if(!order.getCustomer().getId().equals(getUser().getId())){
            throw new ApiException(OrderErrorCode.UNAUTHORIZED);
        }
        return new OrderDetailResponseDto(order);
    }

    @Override
    public Page<OrderDetailResponseDto> findOrderForOwner(Long shopId, int page, int size) {
        //가게 검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));
        validate(shop);

        Pageable pageable = PageRequest.of(page, size, Sort.by(OrderBy.UPDATED_DATE.getName()).descending());

        //주문 검증
        return orderRepository.findByShopId(shopId, pageable)
                .map(OrderDetailResponseDto::new);
    }

    @Transactional
    @Override
    public void updateStatus(Long shopId, Long orderId, OrderStatus orderStatus) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));
        validate(shop);

        Order order = orderRepository.findByIdOrElseThrow(orderId);

        order.updateStatus(orderStatus);
    }

    @Override
    public void reject(Long shopId, Long orderId, String rejectReason) {
        //가게 검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));
        validate(shop);

        //주문검증
        Order order = orderRepository.findByIdOrElseThrow(orderId);
        order.reject(rejectReason);
    }

    private OrderMenu convertCartMenuToOrderMenu(CartMenu cartMenu, Order order) {
        OrderMenu orderMenu = new OrderMenu(cartMenu);
        orderMenu.updateOrder(order);
        return orderMenu;
    }

    private List<OrderMenuOptionDetail> convertCartMenuOptionsToOrderMenuOptions(CartMenu cartMenu) {
        return cartMenu.getCartMenuOptionDetailList()
                .stream()
                .map(OrderMenuOptionDetail::new)
                .toList();
    }


}