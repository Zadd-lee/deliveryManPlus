package com.deliveryManPlus.order.service.imp;

import com.deliveryManPlus.cart.entity.Cart;
import com.deliveryManPlus.cart.entity.CartMenu;
import com.deliveryManPlus.cart.repository.CartMenuOptionDetailRepository;
import com.deliveryManPlus.cart.repository.CartMenuRepository;
import com.deliveryManPlus.cart.repository.CartRepository;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.OrderErrorCode;
import com.deliveryManPlus.common.exception.constant.errorcode.OrderStatus;
import com.deliveryManPlus.common.exception.constant.errorcode.ShopErrorCode;
import com.deliveryManPlus.menu.repository.MenuRepository;
import com.deliveryManPlus.order.dto.OrderDetailResponseDto;
import com.deliveryManPlus.order.dto.OrderSimpleResponseDto;
import com.deliveryManPlus.order.dto.OrderStatusRejectDto;
import com.deliveryManPlus.order.entity.Order;
import com.deliveryManPlus.order.entity.OrderMenu;
import com.deliveryManPlus.order.entity.OrderMenuOptionDetail;
import com.deliveryManPlus.order.repository.OrderMenuOptionDetailRepository;
import com.deliveryManPlus.order.repository.OrderMenuRepository;
import com.deliveryManPlus.order.repository.OrderRepository;
import com.deliveryManPlus.order.service.OrderService;
import com.deliveryManPlus.shop.entity.Shop;
import com.deliveryManPlus.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.deliveryManPlus.common.utils.EntityValidator.validate;
import static com.deliveryManPlus.common.utils.SecurityUtils.getUser;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final OrderMenuOptionDetailRepository orderMenuOptionDetailRepository;
    private final ShopRepository shopRepository;
    private final CartRepository cartRepository;
    private final CartMenuRepository cartMenuRepository;
    private final CartMenuOptionDetailRepository cartMenuOptionDetailRepository;


    @Transactional
    @Override
    public void createOrder() {
        Cart cart = cartRepository.findByCustomerIdDesc(getUser().getId())
                .orElseThrow(() -> new ApiException(OrderErrorCode.NOT_FOUND));


        //주문 생성
        Order order = new Order(cart);

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
    public List<OrderSimpleResponseDto> findAllOrderForUser() {
        return orderRepository.findByCustomerId(getUser().getId())
                .stream()
                .map(OrderSimpleResponseDto::new)
                .toList();
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
    public List<OrderDetailResponseDto> findOrderForOwner(Long shopId) {
        //가게 검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));
        validate(shop);

        List<Order> orderList = orderRepository.findByShopId(shopId);

        //주문 검증
        if(orderList.isEmpty()){
            throw new ApiException(OrderErrorCode.NOT_FOUND);
        }
        return orderList.stream()
                .map(OrderDetailResponseDto::new)
                .toList();
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
    public void reject(Long shopId, Long orderId, OrderStatusRejectDto dto) {
        //가게 검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));
        validate(shop);

        //주문검증
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiException(OrderErrorCode.NOT_FOUND));
        order.reject(dto.getRejectReason());
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