package de.promove.autokss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

@Service
public class EmailService {

    @Value("${mail.from}")
    private String from;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    @Qualifier("fixedThreadPool")
    private ExecutorService executorService;

    public FutureTask<SimpleMailMessage> sendSimpleMessage(String to, String subject, String text) {
        FutureTask<SimpleMailMessage> futureTask = new FutureTask<>(new Callable<SimpleMailMessage>() {
            @Override
            public SimpleMailMessage call() throws Exception {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(from);
                message.setTo(to);
                message.setSubject(subject);
                message.setText(text);
                emailSender.send(message);
                return message;
            }
        });

        executorService.execute(futureTask);
        return futureTask;
    }
}
