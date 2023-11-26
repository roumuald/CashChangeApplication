package nnr.com.CashChangeApp.controlleur;

import lombok.AllArgsConstructor;
import nnr.com.CashChangeApp.entites.Devise;
import nnr.com.CashChangeApp.entites.ResponseConversionDevise;
import nnr.com.CashChangeApp.exception.CashChangeAppException;
import nnr.com.CashChangeApp.exception.ErrorEntity;
import nnr.com.CashChangeApp.services.InterfaceDeviseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/cashChangeApplication")
@AllArgsConstructor
public class DeviseControlleur {
    private final InterfaceDeviseService interfaceDeviseService;
    @GetMapping("/latestCurrency")
    @Async
    public CompletableFuture<ResponseConversionDevise> getLatestRates() {
        return CompletableFuture.completedFuture(interfaceDeviseService.getLatestRates());
    }
    @PostMapping("/saveCurrency")
    public ResponseEntity saveCurrency(@RequestBody Devise devise){
        try {
            Devise deviseSave = this.interfaceDeviseService.saveCurrency(devise);
            return ResponseEntity.ok(deviseSave);
        }catch (CashChangeAppException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorEntity("500", exception.getMessage()));
        }
    }
    @PutMapping("/updateCurrency/{id}")
    public ResponseEntity updateCurrency(@PathVariable Long id, @RequestBody Devise newdevise){
        try {
            Devise devise = this.interfaceDeviseService.updateCurrency(id, newdevise);
            return ResponseEntity.ok(devise);
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorEntity("501", exception.getMessage()));
        }
    }
    @DeleteMapping("deleteCurrency/{id}")
    public ResponseEntity<String> deleteCurrency(@PathVariable Long id){
        try {
            this.interfaceDeviseService.deleteCurrency(id);
            return new ResponseEntity<>("Devise supprime avec success", HttpStatus.NO_CONTENT);
        }catch (CashChangeAppException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllDevise")
    public ResponseEntity getAllDevise(){
        try {
            List<Devise> devises = this.interfaceDeviseService.getAllDevise();
            return ResponseEntity.ok(devises);
        }catch (CashChangeAppException exception){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorEntity("500", exception.getMessage()));
        }
    }

    @GetMapping("/convertCurrency")
    public ResponseEntity<BigDecimal> convertCurrency(
            @RequestParam BigDecimal amount,
            @RequestParam String sourceCurrencyCode,
            @RequestParam String finalCurrencyCode
    ) {

        Devise sourceCurrency = new Devise();
        sourceCurrency.setCode(sourceCurrencyCode);
        Devise finalCurrency = new Devise();
        finalCurrency.setCode(finalCurrencyCode);

        try {
            BigDecimal convertedAmount = this.interfaceDeviseService.convertCurrency(amount, sourceCurrency, finalCurrency);
            return new ResponseEntity<>(convertedAmount, HttpStatus.OK);
        } catch (CashChangeAppException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
