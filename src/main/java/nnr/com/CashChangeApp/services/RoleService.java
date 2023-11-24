package nnr.com.CashChangeApp.services;

import lombok.AllArgsConstructor;
import nnr.com.CashChangeApp.entites.Role;
import nnr.com.CashChangeApp.exception.CashChangeAppException;
import nnr.com.CashChangeApp.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService implements InterfaceRoleService{
    public RoleRepository roleRepository;

    /**
     * methode permettant d'enregistrer un nouveau role
     * @param role
     * @return Role enregistre
     */
    @Override
    public Role saveRole(Role role) {
        Role roleExist = roleRepository.findByLibelle(role.getLibelle());
        if (roleExist!=null) throw new CashChangeAppException("le Role"+" "+roleExist.getLibelle()+" "+"existe deja !!!");
        roleExist = roleRepository.save(role);
        return roleExist;
    }

    /**
     * methode permet de recuperer tous les roles enregistres en base de donnees
     * @return la liste de role
     */
    @Override
    public List<Role> getAllRole() {
        List<Role> roles = roleRepository.findAll();
        if (!roles.isEmpty()){
            return roles;
        }else {
            throw new CashChangeAppException("Aucun role disponible en base de donnees");
        }
    }

    /**
     * methode de mise a jour d'un role
     * @param id
     * @param newRole
     * @return le Role mis a jour
     */

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

    /**
     * Supression d'un role
     * @param id
     */
    @Override
    @Transactional
    public void deleteRole(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()){
            roleRepository.deleteById(id);
            roleRepository.delete(role.get());
        }else {
            throw new CashChangeAppException("Pas de role disponible avec l'identifiant "+ id);
        }
    }
}
