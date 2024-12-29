package org.example.baiemdung.conntroller;

import jakarta.servlet.http.HttpSession;
import org.example.baiemdung.entites.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @GetMapping("/home/char")
    public String getHomeChar() {
        return "template/char";
    }
    @GetMapping("/home/mess")
    public String getHomeMess() {
        return "template/mess";
    }
    @GetMapping("/home/account")
    public String getHomeAccount(HttpSession session, ModelMap modelMap) {
        modelMap.addAttribute("userLogin",(User)session.getAttribute("userLogin"));
        return "template/account";
    }
    @GetMapping("/login")
    public String login() {
        return "template/login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Xóa thông tin khỏi session khi đăng xuất
        session.removeAttribute("userLogin");
        return "redirect:/login";
    }
}
