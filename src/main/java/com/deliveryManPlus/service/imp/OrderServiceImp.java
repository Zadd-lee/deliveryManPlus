package com.deliveryManPlus.service.imp;

import com.deliveryManPlus.constant.error.MenuErrorCode;
import com.deliveryManPlus.constant.error.OrderErrorCode;
import com.deliveryManPlus.constant.error.ShopErrorCode;
import com.deliveryManPlus.dto.order.OrderCreateRequestDto;
import com.deliveryManPlus.dto.order.OrderResponseDto;
import com.deliveryManPlus.dto.order.OrderStatusRejectDto;
import com.deliveryManPlus.dto.order.OrderStatusUpdateDto;
import com.deliveryManPlus.entity.*;
import com.deliveryManPlus.exception.ApiException;
import com.deliveryManPlus.repository.*;
import com.deliveryManPlus.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.deliveryManPlus.utils.EntityValidator.validate;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final MenuHistoryRepository menuHistoryRepository;
    private final UserRepository userRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final ShopRepository shopRepository;


    @Override
    public void createOrder(User user, @Valid OrderCreateRequestDto dto) {
        //주문 검증
        Menu menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new ApiException(MenuErrorCode.NOT_FOUND));

        MenuHistory menuHistory = new MenuHistory(menu);
        menuHistoryRepository.save(menuHistory);

        //주문 내역 저장
        OrderMenu orderMenu = new OrderMenu(menuHistory);
        Order order = new Order(menuHistory, user);

        orderMenu.updateOrder(order);
        order.updateOrderMenu(List.of(orderMenu));

        orderMenuRepository.saveAll(order.getOrderMenu());
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponseDto> findOrderForUser(User user) {
        List<Order> orderList = orderRepository.findByCustomerId(user.getId());
        return orderList.stream()
                .map(OrderResponseDto::new)
                .toList();
    }

    @Override
    public List<OrderResponseDto> findOrderForOwner(Long shopId) {
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
                .map(OrderResponseDto::new)
                .toList();
    }

    @Override
    public OrderResponseDto updateStatus(Long shopId, Long orderId, OrderStatusUpdateDto dto) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));
        validate(shop);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiException(OrderErrorCode.NOT_FOUND));

        order.updateStatus(dto.getStatus());

        return new OrderResponseDto(order);
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

}