package nnr.com.CashChangeApp.controlleur;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import nnr.com.CashChangeApp.entites.Role;
import nnr.com.CashChangeApp.exception.CashChangeAppException;
import nnr.com.CashChangeApp.services.InterfaceRoleService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cashChangeApplication")
@AllArgsConstructor
public class RoleControlleur {
    private final InterfaceRoleService interfaceRoleService;

    @PostMapping("/saveRole")
    @ResponseStatus(HttpStatus.CREATED)
    public Role saveRole(@RequestBody Role role){

        return interfaceRoleService.saveRole(role);
    }

    @GetMapping("/getAllRole")
    @ResponseStatus(HttpStatus.OK)
    public List<Role> getAllRole(){
        return interfaceRoleService.getAllRole();
    }

    @PutMapping("/updateRole/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Role updateRole(@PathVariable Long id, @RequestBody Role newRole){
        return interfaceRoleService.updateRole(id, newRole);
    }

    @DeleteMapping("/deleteRole/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        try {
            interfaceRoleService.deleteRole(id);
            return new ResponseEntity<>("role supprimer avec succes", HttpStatus.OK);
        }catch (CashChangeAppException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e){
            return new ResponseEntity<>("Impossible de supprimer le rôle en raison de contraintes de clé étrangère", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("une erreur est survenu", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
