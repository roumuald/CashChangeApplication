package nnr.com.CashChangeApp.repository;

import nnr.com.CashChangeApp.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public void deleteById(Long roleId);
    public Role findByLibelle(String libelle);
}
