package nnr.com.CashChangeApp.controlleur;

import lombok.AllArgsConstructor;
import nnr.com.CashChangeApp.entites.ResponseConversionDevise;
import nnr.com.CashChangeApp.services.InterfaceDeviseService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
