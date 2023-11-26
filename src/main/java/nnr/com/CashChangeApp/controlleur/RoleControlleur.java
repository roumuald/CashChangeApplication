package nnr.com.CashChangeApp.controlleur;

import lombok.AllArgsConstructor;
import nnr.com.CashChangeApp.entites.Role;
import nnr.com.CashChangeApp.exception.CashChangeAppException;
import nnr.com.CashChangeApp.exception.ErrorEntity;
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
    public ResponseEntity saveRole(@RequestBody Role role){
        try {
            Role saveRole = interfaceRoleService.saveRole(role);
            return new ResponseEntity<>(saveRole, HttpStatus.CREATED);
        }catch (CashChangeAppException exception){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity("100", exception.getMessage()));
        }
    }
    @GetMapping("/getAllRole")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAllRole(){
        try {
            List<Role> roles=interfaceRoleService.getAllRole();
            return new ResponseEntity<>(roles, HttpStatus.OK);
        }catch (CashChangeAppException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorEntity("100", exception.getMessage()));
        }
    }

    @PutMapping("/updateRole/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateRole(@PathVariable Long id, @RequestBody Role newRole){
        try {
            Role role= interfaceRoleService.updateRole(id, newRole);
            return new ResponseEntity(role, HttpStatus.OK);
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorEntity("120", exception.getMessage()));
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity("500", e.getMessage()));
        }
    }

    @DeleteMapping("/deleteRole/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        try {
            interfaceRoleService.deleteRole(id);
            return new ResponseEntity<>("role supprimer avec succes", HttpStatus.OK);
        }catch (CashChangeAppException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Impossible de supprimer le rôle en raison de contraintes de clé étrangère", HttpStatus.BAD_REQUEST);
        }
    }
}
