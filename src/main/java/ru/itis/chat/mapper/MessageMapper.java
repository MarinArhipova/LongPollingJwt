package ru.itis.chat.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.chat.dto.MessageDto;
import ru.itis.chat.model.Message;
import ru.itis.chat.repositories.UsersRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MessageMapper {
    @Autowired
    UsersRepository usersRepository;

    public Message MessageDtomappedMessage(MessageDto messageDto){
        Message message = Message.builder()
                .text(messageDto.getText())
                .userId(usersRepository.findByUsername(messageDto.getAuthor()).orElseThrow(EntityNotFoundException::new))
                .build();
        return message;
    }

    public MessageDto convertModelToForm(Message message) {
        return MessageDto.builder()
                .text(message.getText())
                .author(message.getUserId().getUsername())
                .build();
    }

    private Stream<MessageDto> modelsToForm(List<Message> messages) {
        return messages.stream().map(this::convertModelToForm);
    }

    public List<MessageDto> convertModelsToDtos(List<Message> messages){
        return modelsToForm(messages).collect(Collectors.toList());
    }
}
