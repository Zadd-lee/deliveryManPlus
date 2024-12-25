package com.deliveryManPlus.order.service.impl;

import com.deliveryManPlus.common.exception.constant.MenuErrorCode;
import com.deliveryManPlus.common.exception.constant.OrderErrorCode;
import com.deliveryManPlus.common.exception.constant.SessionErrorCode;
import com.deliveryManPlus.common.exception.exception.ApiException;
import com.deliveryManPlus.menu.model.entity.Menu;
import com.deliveryManPlus.menu.repository.MenuRepository;
import com.deliveryManPlus.order.model.dto.CreateOrderRequestDto;
import com.deliveryManPlus.order.model.dto.OrderResponseDto;
import com.deliveryManPlus.order.model.dto.OrderStatusRejectDto;
import com.deliveryManPlus.order.model.dto.OrderStatusUpdateDto;
import com.deliveryManPlus.order.model.entity.MenuHistory;
import com.deliveryManPlus.order.model.entity.Order;
import com.deliveryManPlus.order.model.entity.OrderMenu;
import com.deliveryManPlus.order.repository.MenuHistoryRepository;
import com.deliveryManPlus.order.repository.OrderMenuRepository;
import com.deliveryManPlus.order.repository.OrderRepository;
import com.deliveryManPlus.order.service.OrderService;
import com.deliveryManPlus.user.model.entity.User;
import com.deliveryManPlus.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.deliveryManPlus.common.utils.EntityValidator.isValid;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final MenuHistoryRepository menuHistoryRepository;
    private final UserRepository userRepository;
    private final OrderMenuRepository orderMenuRepository;


    @Override
    public void createOrder(Long id, @Valid CreateOrderRequestDto dto) {
        //검증
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(SessionErrorCode.NO_SESSION));
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
    public List<OrderResponseDto> findOrderForUser(Long userId) {
        List<Order> orderList = orderRepository.findByCustomerId(userId);
        return orderList.stream()
                .map(OrderResponseDto::new)
                .toList();
    }

    @Override
    public OrderResponseDto updateStatus(Long userId, Long shopId, Long orderId, OrderStatusUpdateDto dto) {
        //검증
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiException(OrderErrorCode.NOT_FOUND));
        if(order.getCustomer().getId()!=userId || order.getShop().getId()!=shopId){
            throw new ApiException(OrderErrorCode.UNAUTHORIZED);
        }
        validateOrder(userId, shopId, order);
        order.updateStatus(dto.getStatus());

        return new OrderResponseDto(order);
    }

    @Override
    public void reject(Long userId, Long shopId, Long orderId, OrderStatusRejectDto dto) {
        //검증
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiException(OrderErrorCode.NOT_FOUND));
        validateOrder(userId, shopId, order);
        order.reject(dto.getRejectReason());
    }

    /**
     * 주문 검증
     * user - shop
     * shop - order
     * @param userId
     * @param shopId
     * @param order
     */
    private void validateOrder(Long userId, Long shopId, Order order) {
        if(!(isValid(userId, order.getShop())&& isValid(shopId,order))){
            throw new ApiException(OrderErrorCode.UNAUTHORIZED);
        }
    }
}