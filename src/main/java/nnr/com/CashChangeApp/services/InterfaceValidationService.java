package nnr.com.CashChangeApp.services;

import nnr.com.CashChangeApp.entites.Utilisateur;
import nnr.com.CashChangeApp.entites.Validation;

public interface InterfaceValidationService {
    public void enregistrer(Utilisateur utilisateur);
    public Validation getValidationByCode(String code);
}
