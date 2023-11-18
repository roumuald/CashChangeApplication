package nnr.com.CashChangeApp.controlleur;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nnr.com.CashChangeApp.dto.AuthenticationDto;
import nnr.com.CashChangeApp.entites.Utilisateur;
import nnr.com.CashChangeApp.services.InterfaceUtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public void inscription(@RequestBody Utilisateur utilisateur, @PathVariable Long idRole){
        interfaceUtilisateurService.inscription(utilisateur, idRole);
    }
    @PostMapping("/activation")
    @ResponseStatus(HttpStatus.CREATED)
    public void activation(@RequestBody Map<String, String> activation){
        interfaceUtilisateurService.activation(activation);
    }
    @PostMapping("/connexion")
    public Map<String, String> connexion(@RequestBody AuthenticationDto authenticationDto){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.email(), authenticationDto.password())
        );
        log.info("le resultat est ", auth.isAuthenticated());
        return null;
    }

}
