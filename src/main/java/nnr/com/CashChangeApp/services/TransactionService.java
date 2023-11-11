package nnr.com.CashChangeApp.services;

import nnr.com.CashChangeApp.entites.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements InterfaceTransactionService{
    @Override
    public Transaction recordTransaction(Transaction transaction, Long idUser, Long idCurrency, Long idExchangeRate) {
        return null;
    }
}
