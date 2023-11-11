package nnr.com.CashChangeApp.services;

import nnr.com.CashChangeApp.entites.Transaction;

public interface InterfaceTransactionService {
    public Transaction recordTransaction(Transaction transaction, Long idUser, Long idCurrency, Long idExchangeRate);
}
