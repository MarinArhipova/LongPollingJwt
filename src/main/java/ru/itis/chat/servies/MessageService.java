package ru.itis.chat.servies;

import ru.itis.chat.dto.MessageDto;
import ru.itis.chat.model.Message;

import java.util.List;

public interface MessageService {
    List<MessageDto> getAll();

    Message add(MessageDto form);
}
