create database data_mess;
use data_mess;
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
	number_phone VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100),
    password VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE conversations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_a_id BIGINT NOT NULL,
    user_b_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_a_id) REFERENCES users(id),
    FOREIGN KEY (user_b_id) REFERENCES users(id),
    UNIQUE(user_a_id, user_b_id)
);

CREATE TABLE messages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    conversation_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (conversation_id) REFERENCES conversations(id),
    FOREIGN KEY (sender_id) REFERENCES users(id)
);

INSERT INTO users (username, number_phone, full_name, created_at,updated_at,password) 
VALUES 
('userA', '0976591256', 'Nguyễn Văn A', NOW(),now(),1234),
('userB', '0976591252', 'Trần Thị B', NOW(),now(),1234),
('chatbot','','',now());
use data_mess;
select * from users;
select * from conversations;
select * from messages;

SELECT 
    mess.conversation_id AS conversation_id, 
    mess.sender_id AS id_nguoigui, 
    CASE
        WHEN mess.sender_id = conver.user_a_id THEN conver.user_b_id
        WHEN mess.sender_id = conver.user_b_id THEN conver.user_a_id
    END AS id_nguoinhan,
    mess.content AS noi_dung_nhan,
    mess.created_at AS ngay_nhan,
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
    (conver.user_a_id = 1 AND conver.user_b_id = 3)
    OR
    (conver.user_a_id = 3 AND conver.user_b_id = 1)
ORDER BY mess.created_at;




INSERT INTO conversations (user_a_id, user_b_id, created_at) 
VALUES 
(1, 
 3, 
 NOW()),(2, 
 3,
 NOW());
 
 INSERT INTO messages (conversation_id, sender_id, content, created_at, is_read) 
VALUES 
(
    (SELECT id FROM conversations WHERE user_a_id = 1 
     AND user_b_id = 3),
    1,
    'Xin chào! Đây là tin nhắn đầu tiên 2.',
    NOW(),
    FALSE
),
(
    (SELECT id FROM conversations WHERE user_a_id = 1 
     AND user_b_id = 3),
    3,
    'Xin chào! Đây là tin nhắn đầu tiên 2.',
    NOW(),
    FALSE
),
(
    (SELECT id FROM conversations WHERE user_a_id = 1 
     AND user_b_id = 3),
    3,
    'Xin chào! Tôi khỏe, cảm ơn.',
    NOW(),
    FALSE
);



