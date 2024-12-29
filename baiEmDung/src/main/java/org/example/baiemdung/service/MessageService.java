package org.example.baiemdung.service;

import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MessageService {
    List<Object[]> findMessagesForConversation(@Param("userId") Integer userId,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);
}
