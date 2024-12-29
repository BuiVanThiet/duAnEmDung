package org.example.baiemdung.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.baiemdung.entites.User;
import org.example.baiemdung.repositores.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.getUserByNumberPhone(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + user);
        }

        // Lấy HttpServletRequest từ RequestContextHolder
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        HttpSession session = request.getSession();  // Lấy session

        // Lưu đối tượng staff vào session
        session.setAttribute("userLogin", user);
        session.setMaxInactiveInterval(24 * 60 * 60);
        username = user.getNumberPhone();
        String role = "USER";
        System.out.println(username);
        PasswordEncoder passwordEncoder = this.passwordEncoder();
        return org.springframework.security.core.userdetails.User.withUsername(username)
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(role)
                .build();
    }
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Hoặc NoOpPasswordEncoder nếu không muốn mã hóa
    }

}
