package org.example.baiemdung.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.baiemdung.entites.User;
import org.example.baiemdung.repositores.UserRepository;
import org.example.baiemdung.service.UserSecurityService;
import org.example.baiemdung.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserSecurityService userSecurityService;
    @Autowired
    UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/ajax/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/home/**").hasAnyRole("USER")
                        .requestMatchers("/mess-api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .userDetailsService(userSecurityService)
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home/char", true)
                        .failureHandler(authenticationFailureHandler())  // Xử lý khi đăng nhập thất bại
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                )
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Hoặc NoOpPasswordEncoder nếu không muốn mã hóa
    }
    // Xử lý khi đăng nhập thất bại
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException exception) throws IOException, ServletException {
                String username = request.getParameter("username");
                request.getSession().setAttribute("usernameFalse", username);

                // Kiểm tra tài khoản có tồn tại không
                User user = userRepository.getUserByNumberPhone(username);
                if (user != null) {
                    // Tài khoản tồn tại nhưng mật khẩu không đúng
                    request.getSession().setAttribute("usernameError", null);
                    request.getSession().setAttribute("passwordError", "Mật khẩu không chính xác");
                } else {
                    // Tài khoản không tồn tại
                    request.getSession().setAttribute("usernameError", "Tài khoản không tồn tại");
                    request.getSession().setAttribute("passwordError", "Mật khẩu không chính xác");
                }

                // Chuyển hướng lại trang login với thông báo lỗi
                super.setDefaultFailureUrl("/login?error=true");
                super.onAuthenticationFailure(request, response, exception);
            }
        };
    }
}
