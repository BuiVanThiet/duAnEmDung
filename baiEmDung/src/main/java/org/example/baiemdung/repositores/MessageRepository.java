package org.example.baiemdung.repositores;

import org.example.baiemdung.entites.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT mess FROM Message mess WHERE mess.conversation.id = (select conver.id from Conversation conver where conver.userA.id = :userId and conver.userB.id = 3) " +
            "AND DATE(mess.createdAt) BETWEEN :startDate AND :endDate")
    List<Message> getMessagesByUserIdAndDateRange(@Param("userId") Long userId,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);
    @Query(value = """
    SELECT
        mess.conversation_id AS conversation_id,
        mess.sender_id AS id_nguoigui,
        CASE
            WHEN mess.sender_id = conver.user_a_id THEN conver.user_b_id
            WHEN mess.sender_id = conver.user_b_id THEN conver.user_a_id
        END AS id_nguoinhan,
        mess.content AS noi_dung_nhan,
        CASE
               WHEN DATE(mess.created_at) = CURDATE() THEN
                   DATE_FORMAT(mess.created_at, '%H:%i') -- Giờ:phút nếu hôm nay
               ELSE
                   DATE_FORMAT(mess.created_at, '%H:%i %d-%m-%Y') -- Giờ:phút ngày-tháng-năm nếu không phải hôm nay
           END AS ngay_nhan,
        CASE
            WHEN mess.sender_id = conver.user_a_id THEN 'sent'
            WHEN mess.sender_id = conver.user_b_id THEN 'received'
        END AS message_type,
        CASE
            WHEN mess.sender_id = 1 THEN 'sent'
            WHEN CASE
                    WHEN mess.sender_id = conver.user_a_id THEN conver.user_b_id
                    WHEN mess.sender_id = conver.user_b_id THEN conver.user_a_id
                 END = 1 THEN 'received'
            ELSE 'unknown'
        END AS is_sender
    FROM
        messages mess
    JOIN
        conversations conver ON mess.conversation_id = conver.id
    WHERE
        ((conver.user_a_id = :userId AND conver.user_b_id = 3)
        OR
        (conver.user_a_id = 3 AND conver.user_b_id = :userId))
        and DATE(mess.created_at) BETWEEN :startDate AND :endDate
    ORDER BY mess.created_at;
""",
            nativeQuery = true)
    List<Object[]> findMessagesForConversation(@Param("userId") Integer userId,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);


}
