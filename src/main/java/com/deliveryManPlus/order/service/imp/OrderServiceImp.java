package com.deliveryManPlus.order.service.imp;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.auth.repository.UserRepository;
import com.deliveryManPlus.common.exception.constant.errorcode.MenuErrorCode;
import com.deliveryManPlus.common.exception.constant.errorcode.OrderErrorCode;
import com.deliveryManPlus.common.exception.constant.errorcode.ShopErrorCode;
import com.deliveryManPlus.menu.entity.Menu;
import com.deliveryManPlus.menu.entity.MenuHistory;
import com.deliveryManPlus.menu.repository.MenuHistoryRepository;
import com.deliveryManPlus.menu.repository.MenuRepository;
import com.deliveryManPlus.order.dto.OrderCreateRequestDto;
import com.deliveryManPlus.order.dto.OrderResponseDto;
import com.deliveryManPlus.order.dto.OrderStatusRejectDto;
import com.deliveryManPlus.order.dto.OrderStatusUpdateDto;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.order.entity.Order;
import com.deliveryManPlus.order.entity.OrderMenu;
import com.deliveryManPlus.order.repository.OrderMenuRepository;
import com.deliveryManPlus.order.repository.OrderRepository;
import com.deliveryManPlus.order.service.OrderService;
import com.deliveryManPlus.shop.entity.Shop;
import com.deliveryManPlus.shop.repository.ShopRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.deliveryManPlus.common.utils.EntityValidator.validate;

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