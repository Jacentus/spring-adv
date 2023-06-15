package pl.training.chat.integration.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.training.chat.ChatEventEmitter;
import pl.training.chat.ChatMessageDto;

import java.util.Locale;
import java.util.Map;

@Service
@Log
@RequiredArgsConstructor
public class ChatMailEventEmitter implements ChatEventEmitter {

    private static final String SENDER = "chat@training.pl";
    private static final String SUBJECT_KEY = "notification";
    private static final String LANGUAGE = "pl";
    private static final String TEMPLATE_NAME = "ChatNotification";
    private static final String[] RECIPIENTS = new String[] {"admin@training.pl"};
    private static final Object[] NO_ARGUMENTS = new Object[] {};

    private final TemplateService templateService;
    private final JavaMailSender mailSender;
    private final MessageSource messageSource;

    @Async("mailExecutor")
    @Override
    public void emit(ChatMessageDto chatMessageDto) {
        log.info("Current sender thread: " + Thread.currentThread().getName());
        Map<String, Object> parameters = Map.of("value", chatMessageDto.toString());
        var text = templateService.evaluate(TEMPLATE_NAME, parameters, LANGUAGE);
        var subject = messageSource.getMessage(SUBJECT_KEY, NO_ARGUMENTS, new Locale(LANGUAGE));
        var mailMessage = createMessage(SENDER, RECIPIENTS, subject, text);
        mailSender.send(mailMessage);
    }

    private MimeMessagePreparator createMessage(String sender, String[] recipients, String subject, String text) {
        return mimeMessage -> {
            var mailMessage = new MimeMessageHelper(mimeMessage);
            mailMessage.setFrom(sender);
            mailMessage.setTo(recipients);
            mailMessage.setSubject(subject);
            mailMessage.setText(text, true);
        };
    }

}
