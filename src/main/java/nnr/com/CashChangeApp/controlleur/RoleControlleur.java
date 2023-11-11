package nnr.com.CashChangeApp.controlleur;

import lombok.AllArgsConstructor;
import nnr.com.CashChangeApp.entites.Role;
import nnr.com.CashChangeApp.services.InterfaceRoleService;
import org.springframework.http.HttpStatus;
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
    public List<Role> getAllRole(){
        return interfaceRoleService.getAllRole();
    }
    @PutMapping("/updateRole/{id}")
    //@ResponseStatus(Http)
    public Role updateRole(@PathVariable Long id, @RequestBody Role newRole){
        return interfaceRoleService.updateRole(id, newRole);
    }
    @DeleteMapping("/deleteRole/{id}")
    public void deleteRole(@PathVariable Long id){
        interfaceRoleService.deleteRole(id);
    }
}
