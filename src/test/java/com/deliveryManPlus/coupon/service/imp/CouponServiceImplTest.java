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
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

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

    @DisplayName("쿠폰 다수 발급")
    @WithAnonymousUser
    void useMultipleCoupon() {
        //given
        int quantity = 3;
        Coupon coupon = Coupon.builder()
                .quantity(quantity)
                .build();
        CouponUser couponUser = CouponUser.builder().build();

        when(couponRepository.findByCodeOrElseThrows(any())).thenReturn(coupon);
        when(couponUserRepository.save(any())).thenReturn(couponUser);

        //when
        couponService.useCoupon(coupon.getCode());

        //then
        assertThat(coupon.getQuantity()).isEqualTo(quantity - 2);
    }

    @Test
    void testSecurityContextInMultiThread() {
        AtomicReference<String> threadUsername = new AtomicReference<>();

        userService.processInNewThread(() -> {
            String username = userService.getAuthenticatedUsername();
            threadUsername.set(username);
        });

        assertThat(threadUsername.get()).isEqualTo("mainUser");
    }

    @Test
    void testSecurityContextIsolationBetweenThreads() {
        AtomicReference<String> mainThreadUsername = new AtomicReference<>();
        AtomicReference<String> childThreadUsername = new AtomicReference<>();

        userService.processInNewThread(() -> {
            // 자식 스레드의 SecurityContext 재설정
            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Authentication authentication = Mockito.mock(Authentication.class);

            Mockito.when(authentication.getName()).thenReturn("childUser");
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

            SecurityContextHolder.setContext(securityContext);
            childThreadUsername.set(userService.getAuthenticatedUsername());
        });

        mainThreadUsername.set(userService.getAuthenticatedUsername());

        assertThat(mainThreadUsername.get()).isEqualTo("mainUser");
        assertThat(childThreadUsername.get()).isEqualTo("childUser");
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