package nnr.com.CashChangeApp.services;

import lombok.AllArgsConstructor;
import nnr.com.CashChangeApp.entites.Devise;
import nnr.com.CashChangeApp.entites.Transaction;
import nnr.com.CashChangeApp.entites.Utilisateur;
import nnr.com.CashChangeApp.exception.CashChangeAppException;
import nnr.com.CashChangeApp.repository.DeviseRepository;
import nnr.com.CashChangeApp.repository.TransactionRepository;
import nnr.com.CashChangeApp.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionService implements InterfaceTransactionService{
    private final UtilisateurRepository utilisateurRepository;
    private final DeviseRepository deviseRepository;
    private final DeviseService deviseService;
    private final TransactionRepository transactionRepository;

    /**
     * Methode permettant d'effectuer une nouvelle transaction
     * @param transaction
     * @param idUtilisateur
     * @param idDeviseSource
     * @param idDeviseCible
     * @return la Transaction effectuee
     */
    @Override
    public Transaction recordTransaction(Transaction transaction, Long idUtilisateur, Long idDeviseSource, Long idDeviseCible ) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(idUtilisateur);
        Optional<Devise> deviseSource = deviseRepository.findById(idDeviseSource);
        Optional<Devise> deviseCible = deviseRepository.findById(idDeviseCible);
        if (utilisateur.isPresent()&&deviseSource.isPresent()&&deviseCible.isPresent()){
            BigDecimal montant = deviseService.convertCurrency(transaction.getMontantSource(), deviseSource.get(), deviseCible.get());
            transaction.setUtilisateur(utilisateur.get());
            transaction.setDeviseSource(deviseSource.get());
            transaction.setDeviseCible(deviseCible.get());
            transaction.setTransactionDate(Instant.now());
            transaction.setMontantFinal(montant);

            return this.transactionRepository.save(transaction);
        }else {
            throw new CashChangeAppException("veillez entrer l'identifiant de l'utilisateur et l'identifiant de la devise ");
        }
    }

    @Override
    public List<Transaction> getAllTransaction() {
        List<Transaction> transactions =this.transactionRepository.findAll();
        if (transactions.isEmpty()){
            throw new CashChangeAppException("Aucun transaction disponible en base de donnees");
        }
        return transactions;
    }
}
