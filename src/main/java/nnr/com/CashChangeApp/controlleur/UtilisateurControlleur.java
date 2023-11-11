package nnr.com.CashChangeApp.controlleur;

import lombok.AllArgsConstructor;
import nnr.com.CashChangeApp.entites.Utilisateur;
import nnr.com.CashChangeApp.services.InterfaceUtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/cashChangeApplication", consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateurControlleur {
    private final InterfaceUtilisateurService interfaceUtilisateurService;
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
}