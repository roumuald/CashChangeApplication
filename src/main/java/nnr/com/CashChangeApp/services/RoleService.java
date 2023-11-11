package nnr.com.CashChangeApp.services;

import lombok.AllArgsConstructor;
import nnr.com.CashChangeApp.entites.Role;
import nnr.com.CashChangeApp.exception.CashChangeAppException;
import nnr.com.CashChangeApp.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService implements InterfaceRoleService{
    public RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        Role roleExist = roleRepository.findByLibelle(role.getLibelle());
        if (roleExist!=null) throw new CashChangeAppException("le Role"+" "+roleExist.getLibelle()+" "+"existe deja !!!");
        roleExist = roleRepository.save(role);
        return roleExist;
    }

    @Override
    public List<Role> getAllRole() {
        List<Role> roles = roleRepository.findAll();
        if (!roles.isEmpty()){
            return roles;
        }else {
            throw new CashChangeAppException("Aucun role disponible en base de donnees");
        }
    }

    @Override
    public Role updateRole(Long id, Role newRole) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()){
            role.get().setId(newRole.getId());
            role.get().setLibelle(newRole.getLibelle());
            return roleRepository.save(role.get());
        }
        throw new CashChangeAppException("Pas de role avec cet identifiant " + id);
    }

    @Override
    public void deleteRole(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()){
            roleRepository.delete(role.get());
        }else {
            throw new CashChangeAppException("Pas de role disponible avec l'identifiant "+ id);
        }
    }
}
