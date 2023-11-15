package nnr.com.CashChangeApp.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nnr.com.CashChangeApp.entites.Role;
import nnr.com.CashChangeApp.entites.Utilisateur;
import nnr.com.CashChangeApp.entites.Validation;
import nnr.com.CashChangeApp.exception.CashChangeAppException;
import nnr.com.CashChangeApp.repository.RoleRepository;
import nnr.com.CashChangeApp.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurService implements InterfaceUtilisateurService, UserDetailsService {
    private  UtilisateurRepository utilisateurRepository;
    private  RoleRepository roleRepository;
    private  InterfaceValidationService interfaceValidationService;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void inscription(Utilisateur utilisateur, Long idRole) {
        Optional<Role> role = roleRepository.findById(idRole);
        if (role.isPresent()) {
            Optional<Utilisateur> utilisateurexiste = utilisateurRepository.findByEmail(utilisateur.getEmail());
            if (utilisateurexiste.isEmpty())
                throw new CashChangeAppException("l'adresse email " + utilisateur.getEmail() + " existe deja veillez renseigner une autre adresse email svp");
            if (!utilisateur.getEmail().contains("@")) throw new CashChangeAppException("Adresse email invalide");
            String motDePassCripte = passwordEncoder.encode(utilisateur.getPassword());
            utilisateur.setPassword(motDePassCripte);
            utilisateur.getRoles().add(role.get());
            utilisateurexiste = Optional.of(utilisateurRepository.save(utilisateur));
            this.interfaceValidationService.enregistrer(utilisateurexiste.get());
        } else {
            throw new CashChangeAppException("La creation d'un utilisateur necessite un role");
        }
    }

    @Override
    public void activation(Map<String, String> activation) {
        Validation validation = this.interfaceValidationService.getValidationByCode(activation.get("code"));
        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new CashChangeAppException("Votre code est expire");
        }
        Utilisateur utilisateur = this.utilisateurRepository.findById(validation.getUtilisateur().getId()).orElseThrow(() -> new RuntimeException("L'utilisateur n'existe pas"));
        utilisateur.setActif(true);
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public void deteleUser(Long id) {
        Optional<Utilisateur> user = utilisateurRepository.findById(id);
        if (user.isPresent()) {
            utilisateurRepository.delete(user.get());
        } else {
            throw new CashChangeAppException("pas d'utilisateur avec l'identifiant " + id);
        }
    }
    @Override
    public Utilisateur updateUser(Long id, Utilisateur newUser) {
        Optional<Utilisateur> user = utilisateurRepository.findById(id);
        if (user.isPresent()) {
            user.get().setId(newUser.getId());
            user.get().setNom(newUser.getNom());
            user.get().setEmail(newUser.getEmail());
            user.get().setPassword(newUser.getPassword());
            return utilisateurRepository.save(user.get());
        } else {
            throw new CashChangeAppException("Aucun utilisateur disponible avec l'identifiant " + id);
        }
    }
    @Override
    public List<Utilisateur> getAllUser() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        if (utilisateurs.isEmpty()) {
            throw new CashChangeAppException("Aucun utilisateur en base de donnees");
        }
        return utilisateurs;
    }

    @Override
    public Utilisateur getOneUser(Long id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if (utilisateur.isEmpty()) {
            throw new CashChangeAppException("Aucun utilisateur avec l'identifiant " + id);
        }
        return utilisateur.get();
    }

    @Override
    public void addRoleToUser(Long idUser, Long idRole) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(idUser);
        Optional<Role> role = roleRepository.findById(idRole);
        if (utilisateur.isPresent() && role.isPresent()) {
            utilisateur.get().getRoles().add(role.get());
        } else {
            throw new CashChangeAppException("Impossible d'ajouter un role a l'utilisateur");
        }
    }
    @Override
    public void moveRoleToUser(Long idUser, Long idRole) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(idUser);
        Optional<Role> role = roleRepository.findById(idRole);
        if (utilisateur.isPresent() && role.isPresent()) {
            utilisateur.get().getRoles().remove(role.get());
        } else {
            throw new CashChangeAppException("Impossible de retirer un role a l'utilisateur");
        }
    }
    @Override
    public Utilisateur loaderUserByEmailUser(String email) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur.isEmpty()) {
            throw new CashChangeAppException("Aucun utilisateur avec l'adress " + email);
        }
        return utilisateur.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateur = this.utilisateurRepository.findByEmail(username);
        if (utilisateur.isEmpty()) throw new CashChangeAppException("Aucun utilisateur ne correspond a cet identifiant");
        return utilisateur.get();
    }
}

