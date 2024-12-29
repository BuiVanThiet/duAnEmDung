package org.example.baiemdung.implement;

import org.example.baiemdung.repositores.MessageRepository;
import org.example.baiemdung.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MessageImplement implements MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Override
    public List<Object[]> findMessagesForConversation(@Param("userId") Integer userId,
                                                      @Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate) {
        return messageRepository.findMessagesForConversation(userId, startDate, endDate);
    }
}
