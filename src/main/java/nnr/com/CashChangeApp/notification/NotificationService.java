package nnr.com.CashChangeApp.notification;

import lombok.AllArgsConstructor;
import nnr.com.CashChangeApp.entites.Validation;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService implements InterfaceNotificationService {
    private JavaMailSender javaMailSender;
    @Override
    public void notification(Validation validation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("roumualdnoubiap@gmail.com");
        message.setTo(validation.getUtilisateur().getEmail());
        message.setSubject("Votre code d'activation");
        String text = String.format("Bonjour %s, votre code d'activation est %s, A bientot",
                                        validation.getUtilisateur().getNom(), validation.getCode());
        message.setText(text);
        javaMailSender.send(message);
    }
}
