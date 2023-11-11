package nnr.com.CashChangeApp.services;

import nnr.com.CashChangeApp.entites.Transaction;
import nnr.com.CashChangeApp.entites.Utilisateur;

import java.util.Optional;

public interface InterfaceTransactionService {
    public Transaction recordTransaction(Transaction transaction,  Long idUtilisateur, Long idDeviseSource, Long idDeviseCible);

}
