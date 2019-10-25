package ru.itis.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.chat.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
