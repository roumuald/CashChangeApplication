package nnr.com.CashChangeApp.controlleur;

import lombok.AllArgsConstructor;
import nnr.com.CashChangeApp.entites.Transaction;
import nnr.com.CashChangeApp.exception.CashChangeAppException;
import nnr.com.CashChangeApp.exception.ErrorEntity;
import nnr.com.CashChangeApp.services.TransactionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cashChangeApplication")
@AllArgsConstructor
public class TransactionControlleur {
    private TransactionService transactionService;
    @PostMapping("/recordTransaction/{idUtilisateur}/{idDeviseSource}/{idDeviseCible}")
    public ResponseEntity recordTransaction(@RequestBody Transaction transaction, @PathVariable Long idUtilisateur, @PathVariable Long idDeviseSource, @PathVariable Long idDeviseCible ){
        try {
            Transaction transactionSave = this.transactionService.recordTransaction(transaction, idUtilisateur,idDeviseSource, idDeviseCible);
            return ResponseEntity.ok(transactionSave);
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity("600", exception.getMessage()));
        }
    }
    @GetMapping("/getAllTransaction")
    public ResponseEntity getAllTransaction(){
        try {
            List<Transaction> transactions=this.transactionService.getAllTransaction();
            return ResponseEntity.ok(transactions);
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorEntity("500", exception.getMessage()));
        }

    }
}
