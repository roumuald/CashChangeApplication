package nnr.com.CashChangeApp.controlleur;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nnr.com.CashChangeApp.dto.AuthenticationDto;
import nnr.com.CashChangeApp.entites.Utilisateur;
import nnr.com.CashChangeApp.exception.CashChangeAppException;
import nnr.com.CashChangeApp.exception.ErrorEntity;
import nnr.com.CashChangeApp.services.InterfaceUtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/cashChangeApplication", consumes = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UtilisateurControlleur {

    private  InterfaceUtilisateurService interfaceUtilisateurService;
    private AuthenticationManager authenticationManager;

    @PostMapping("/inscription/{idRole}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity inscription(@RequestBody Utilisateur utilisateur, @PathVariable Long idRole){
        try {
            interfaceUtilisateurService.inscription(utilisateur, idRole);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CashChangeAppException("Utilisateur cre avec success"));
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity("120", exception.getMessage()));
        }
    }
    @PostMapping("/activation")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity activation(@RequestBody Map<String, String> activation){
        try {
            interfaceUtilisateurService.activation(activation);
            return ResponseEntity.status(HttpStatus.OK).body(new CashChangeAppException("code d'activation envoye"));
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity("502", exception.getMessage()));
        }
    }
    @PostMapping("/connexion")
    public Map<String, String> connexion(@RequestBody AuthenticationDto authenticationDto){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.email(), authenticationDto.password())
        );
        log.info("le resultat est ", auth.isAuthenticated());
        return null;
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deteleUser(@PathVariable Long id){
        try {
            this.interfaceUtilisateurService.deteleUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorEntity("100", "Utilisateur supprime avec success"));
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity("502", exception.getMessage()));
        }
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody Utilisateur newUser){
        try {
            Utilisateur utilisateur=this.interfaceUtilisateurService.updateUser(id, newUser);
            return ResponseEntity.ok(utilisateur);
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorEntity("500", exception.getMessage()));
        }
    }
    @GetMapping("/getAllUser")
    public ResponseEntity getAllUser(){
        try {
            List<Utilisateur> utilisateurs = this.interfaceUtilisateurService.getAllUser();
            return ResponseEntity.ok(utilisateurs);
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorEntity("508", exception.getMessage()));
        }
    }
    @GetMapping("/getOneUser/{id}")
    public ResponseEntity getOneUser(@PathVariable Long id){
        try {
            Utilisateur utilisateur = this.interfaceUtilisateurService.getOneUser(id);
            return ResponseEntity.ok(utilisateur);
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorEntity("510", exception.getMessage()));
        }
    }
    @PostMapping("/addRoleToUser/{idUser}/{idRole}")
    public ResponseEntity addRoleToUser(@PathVariable Long idUser, @PathVariable Long idRole){
        try {
            this.interfaceUtilisateurService.addRoleToUser(idUser, idRole);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorEntity("201", "Role ajoute avec success"));
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity("510", exception.getMessage()));
        }
    }
    @PostMapping("/moveRoleToUserr/{idUser}/{idRole}")
    public ResponseEntity moveRoleToUser(@PathVariable Long idUser, @PathVariable Long idRole){
        try {
            this.interfaceUtilisateurService.moveRoleToUser(idUser, idRole);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorEntity("201", "Role retire avec success"));
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity("510", exception.getMessage()));
        }
    }
    @GetMapping("/loaderUserByEmailUser/{email}")
    public ResponseEntity loaderUserByEmailUser(@PathVariable String email){
        try {
            Utilisateur utilisateur=this.interfaceUtilisateurService.loaderUserByEmailUser(email);
            return ResponseEntity.ok(utilisateur);
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorEntity("510", exception.getMessage()));
        }
    }
}
