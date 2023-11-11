package nnr.com.CashChangeApp.services;

import nnr.com.CashChangeApp.entites.Utilisateur;

import java.util.List;
import java.util.Map;

public interface InterfaceUtilisateurService {
    public void inscription(Utilisateur utilisateur, Long idRole);
    public void deteleUser(Long id);
    public Utilisateur updateUser(Long id, Utilisateur newUser);
    public List<Utilisateur> getAllUser();
    public Utilisateur getOneUser(Long id);
    public void addRoleToUser(Long idUser, Long idRole);
    public void moveRoleToUser(Long idUser, Long idRole);
    public Utilisateur loaderUserByEmailUser(String email);
    public void activation(Map<String, String> activation);
}
