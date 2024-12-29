package org.example.baiemdung.implement;

import org.example.baiemdung.entites.Conversation;
import org.example.baiemdung.repositores.ConversationRepository;
import org.example.baiemdung.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationImplement implements ConversationService {
    @Autowired
    ConversationRepository conversationRepository;

    @Override
    public List<Conversation> findAll() {
        return conversationRepository.findAll();
    }

    @Override
    public List<Conversation> findAllById(Iterable<Long> longs) {
        return conversationRepository.findAllById(longs);
    }
}
