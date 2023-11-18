package nnr.com.CashChangeApp.services;

import lombok.AllArgsConstructor;
import nnr.com.CashChangeApp.entites.Utilisateur;
import nnr.com.CashChangeApp.entites.Validation;
import nnr.com.CashChangeApp.notification.InterfaceNotificationService;
import nnr.com.CashChangeApp.repository.ValidationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@AllArgsConstructor
public class ValidationService implements InterfaceValidationService{
    private final ValidationRepository validationRepository;
    private final InterfaceNotificationService interfaceNotificationService;

    /**
     * Methode permet d'enregister un utilisateur avec validation
     * @param utilisateur
     */
    @Override
    public void enregistrer(Utilisateur utilisateur) {
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        Instant creation = Instant.now();
        validation.setCreation(creation);
        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpiration(expiration);
        Random random=new Random();
        int number=random.nextInt(999999);
        String code = String.format("%06d", number);
        validation.setCode(code);
        validationRepository.save(validation);
        this.interfaceNotificationService.notification(validation);
    }

    /**
     * Methode permet d'enregister la validation
     * @param code
     * @return Validation
     */
    @Override
    public Validation getValidationByCode(String code) {
      Validation validation=validationRepository.findByCode(code).orElseThrow(()->new RuntimeException("votre code est invalide"));
        return validation;
    }
}
