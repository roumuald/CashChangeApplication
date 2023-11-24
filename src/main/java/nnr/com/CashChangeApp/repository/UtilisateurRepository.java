package nnr.com.CashChangeApp.repository;

import nnr.com.CashChangeApp.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    public Utilisateur findByEmail(String email);
}
