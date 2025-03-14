package com.deliveryManPlus.coupon.service.imp;

import com.deliveryManPlus.auth.config.UserDetailsImp;
import com.deliveryManPlus.auth.constant.Role;
import com.deliveryManPlus.auth.entity.BasicAuth;
import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.auth.repository.UserRepository;
import com.deliveryManPlus.coupon.entity.Coupon;
import com.deliveryManPlus.coupon.entity.CouponUser;
import com.deliveryManPlus.coupon.repository.CouponRepository;
import com.deliveryManPlus.coupon.repository.CouponUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CouponServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CouponRepository couponRepository;
    @Mock
    private CouponUserRepository couponUserRepository;

    @InjectMocks
    private CouponServiceImpl couponService;


    @Test
    @DisplayName("쿠폰 단일 발급")
    void useOneCoupon() {
        //given
        int quantity = 5;
        Coupon coupon = Coupon.builder()
                .quantity(quantity)
                .build();
        CouponUser couponUser = CouponUser.builder().build();

        when(couponRepository.findByCodeOrElseThrows(any())).thenReturn(coupon);
        when(couponUserRepository.save(any())).thenReturn(couponUser);

        //when
        couponService.useCoupon(coupon.getCode());

        //then
        assertThat(coupon.getQuantity()).isEqualTo(quantity - 1);
    }

    @BeforeEach
    @DisplayName("SecurityContext 설정")
    void setUp() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        User mockUser = new User("test", LocalDate.now(), Role.ADMIN);

        BasicAuth basicAuth = mock(BasicAuth.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new UserDetailsImp(basicAuth));
        when(basicAuth.getUser()).thenReturn(mockUser);

        SecurityContextHolder.setContext(securityContext);
    }

}