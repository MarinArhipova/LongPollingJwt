package ru.itis.chat.servies;

import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.chat.dto.MessageDto;
import ru.itis.chat.mapper.MessageMapper;
import ru.itis.chat.model.Message;
import ru.itis.chat.repositories.MessageRepository;
import ru.itis.chat.repositories.UsersRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    MessageMapper messageMapper;

    @Override
    public List<MessageDto> getAll() {
        return messageMapper.convertModelsToDtos(messageRepository.findAll());
    }

    @Override
    public Message add(MessageDto form) {
        Message message = Message.builder()
                .text(form.getText())
                .userId(usersRepository.findByUsername(form.getAuthor()).orElseThrow(EntityNotFoundException::new))
                .build();
        return messageRepository.save(message);
    }


//    @Override
//    public void clear(MessageDto form) {
//        Message message = messageMapper.MessageDtomappedMessage(form);
//
//        messageRepository.delete(message);
//    }
}
