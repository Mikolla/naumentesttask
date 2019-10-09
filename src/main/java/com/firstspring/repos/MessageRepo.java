package com.firstspring.repos;

import com.firstspring.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Integer> {

    List<Message> findMessageByTagContains(String tag);
    List<Message> findMessageByTextContains(String text);
    Message findMessageById(Integer id);

}
