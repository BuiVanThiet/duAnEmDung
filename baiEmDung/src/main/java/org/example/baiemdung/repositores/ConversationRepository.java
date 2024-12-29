package org.example.baiemdung.repositores;

import org.example.baiemdung.entites.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
