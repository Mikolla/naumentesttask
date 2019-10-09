package com.firstspring;

import com.firstspring.domain.Message;
import com.firstspring.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = messageRepo.findAll();
        for (Message message : messages) {
            if(message.getTag().isEmpty()) {
                message.setTag(message.getText());
            }
        }
        model.put("messages", messages);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag, @RequestParam String action,  Map<String, Object> model) {
        Message message = new Message(text, tag);
        messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "redirect:";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, @RequestParam String action, Map<String, Object> model) {
        Iterable<Message> messages;


        if (filter != null && !filter.isEmpty()) {
            if(action.equals("filterTag")){
                messages = messageRepo.findMessageByTagContains(filter);
            } else {
                messages = messageRepo.findMessageByTextContains(filter);
            }
            } else {
            messages = messageRepo.findAll();
                   }


        for (Message message : messages) {
            if(message.getTag().isEmpty()) {
                message.setTag(message.getText());
            }
        }
        model.put("messages", messages);
        return "filter";
    }

    @GetMapping("/delmessage")
    public String delmessage(@RequestParam String id, Map<String, Object> model) {
        Integer idToDel = Integer.parseInt(id);
        messageRepo.deleteById(idToDel);
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "redirect:";
    }

    @GetMapping("/editmessage")
    public String editmessage(@RequestParam String id, Map<String, Object> model) {
        Integer idToEdit = Integer.parseInt(id);
        Message message =  messageRepo.findMessageById(idToEdit);
        model.put("message", message);
        return "editmessage";
    }

    @PostMapping("/editmessage")
    public String savemessage(@RequestParam String id,
                              @RequestParam String text, @RequestParam String tag,
                              Map<String, Object> model) {
        Integer idToEdit = Integer.parseInt(id);
        Message message =  messageRepo.findMessageById(idToEdit);
        message.setTag(tag);
        message.setText(text);
        messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);
        return "redirect:";
    }
}
