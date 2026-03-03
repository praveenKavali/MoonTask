package com.MoonTask.Backend.aspect;

import com.MoonTask.Backend.user.dto.CreateUserDTO;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MailSender {
    //takes the sender mail from the application properties file
    @Value("spring.mail.username")
    private String sender;
    @Autowired
    private JavaMailSender mailSender;

    /**
     * This method is used to send mail to the registered user that saying welcome to moon task
     * <p>
     *     When the user is registered successfully this method will be called and send mail to the user.
     * </p>
     * @param user contains the mail details*/
    @After("execution(* com.MoonTask.Backend.user.service.UserService.register(..)) && args(user)")
    public void sendMail(CreateUserDTO user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(user.getEmail());
        message.setSubject("Welcome to moon task");
        message.setText("Moon task is a task managing application. Used to store your tasks.");
        try {
            mailSender.send(message);
        } catch (MailException ex){
            System.err.println(ex.getMessage());
        }
    }
}
