package org.example.baiemdung.service;

import org.example.baiemdung.entites.Conversation;

import java.util.List;

public interface ConversationService {
    List<Conversation> findAll();

    List<Conversation> findAllById(Iterable<Long> longs);
}
