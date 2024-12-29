package org.example.baiemdung.restController;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.example.baiemdung.repositores.ConversationRepository;
import org.example.baiemdung.repositores.MessageRepository;
import org.example.baiemdung.repositores.UserRepository;
import org.example.baiemdung.entites.Message;
import org.example.baiemdung.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mess-api")
public class HomeRestController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    MessageRepository messageRepository;
    LocalDate today = LocalDate.now();
    LocalDate startDate = today.minusDays(7);
    LocalDate endDate = today;

    @GetMapping("/mess-user")
    public List<Object[]> getAllMessByUser(HttpSession session) {
        System.out.println("Ngày bắt đầu: " + startDate);
        System.out.println("Ngày kết thúc: " + endDate);
        User user = (User) session.getAttribute("userLogin");
        System.out.println(user.toString());
        // Truyền LocalDate vào repository
        List<Object[]> messages = this.messageRepository.findMessagesForConversation(Integer.parseInt(String.valueOf(user.getId())), startDate, endDate);
        return messages;
    }
    @PostMapping("/fillter")
    public void getFillterMess(@RequestBody Map<String,String> getData) {
        String startDateString = getData.get("start");
        String endDateString = getData.get("end");

        // Ép kiểu String thành LocalDate
        startDate = LocalDate.parse(startDateString);
        endDate = LocalDate.parse(endDateString);

        System.out.println("Ngày bắt đầu bo loc: " + startDate);
        System.out.println("Ngày kết thúcbo loc: " + endDate);
    }

    @PostMapping("/exchange-password")
    public ResponseEntity<Map<String,String>> getExchangePassWord(@RequestBody Map<String,String> getData, HttpSession session) {
        String passWordRoot = getData.get("passWordRoot");
        String passWordNew = getData.get("passWordNew");
        String passWordNewConfirm = getData.get("passWordNewConfirm");
        Map<String,String> thongBao = new HashMap<>();

        User user = (User) session.getAttribute("userLogin");
        if (user == null) {
            thongBao.put("message","Chưa đăng nhập!");
            thongBao.put("check","3");
            return ResponseEntity.ok(thongBao);
        }

        if(!passWordRoot.equals(user.getPassword())) {
            thongBao.put("message","Mật khẩu cũ không đúng!");
            thongBao.put("check","3");
            return ResponseEntity.ok(thongBao);
        }

        if(!passWordNewConfirm.equals(passWordNew)) {
            thongBao.put("message","Mật khẩu mới và xác nhận mật khẩu phải giống nhau!");
            thongBao.put("check","3");
            return ResponseEntity.ok(thongBao);
        }

        user.setPassword(passWordNew);
        this.userRepository.save(user);

        thongBao.put("message","Thay đổi mật khẩu thành công!");
        thongBao.put("check","1");
        return ResponseEntity.ok(thongBao);
    }

    @GetMapping("/all-user")
    public List<User> getAllUser(ModelMap modelMap) {
        List<User> user = this.userRepository.findAll();
        return user;
    }
}
