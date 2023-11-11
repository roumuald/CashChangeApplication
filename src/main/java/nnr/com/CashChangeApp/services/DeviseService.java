package nnr.com.CashChangeApp.services;

import nnr.com.CashChangeApp.entites.Devise;
import nnr.com.CashChangeApp.entites.ResponseConversionDevise;
import nnr.com.CashChangeApp.exception.CashChangeAppException;
import nnr.com.CashChangeApp.repository.DeviseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

/**
 * @Author Roumuald
 * Pour récupérer les taux de change en temps réel depuis une source externe, comme l'API CurrencyLayer
 * @Value("${currencylayer.apiKey}") permet de recuperer la cle de currencyLayer depuis le fichier .properties
 * @Cacheable("exchangeRates") pour la mise en cache du taux de change recupere. cela permet de reduire les appels
 * a l'API currencyLayer
 */
@Service
public class DeviseService implements InterfaceDeviseService{
    @Value("${currencylayer.apiKey}")
    private String apiKey;
    private final String apiUrl = "http://api.currencylayer.com/live";
    private final RestTemplate restTemplate;
    private final DeviseRepository deviseRepository;

    public DeviseService(RestTemplate restTemplate, DeviseRepository deviseRepository) {
        this.restTemplate = restTemplate;
        this.deviseRepository = deviseRepository;
    }
    @Override
    @Cacheable("exchangeRates")
    public ResponseConversionDevise getLatestRates() {
        String url = apiUrl + "?access_key=" + apiKey;
        ResponseEntity<ResponseConversionDevise> responseEntity = restTemplate.getForEntity(url, ResponseConversionDevise.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new CashChangeAppException("Échec de la récupération des taux de change en temps réel.");
        }
    }
    @Override
    @CacheEvict("exchangeRates")
    public void refreshExchangeRates() {
        // todo Logique pour actualiser les taux de change depuis l'API
        // ...
    }
    @Override
    public ResponseConversionDevise getHistoricalRates(String date) {
        String apiUrl = "http://api.currencylayer.com/historical?date=" + date;
        String url = apiUrl + "&access_key=" + apiKey;
        return restTemplate.getForObject(url, ResponseConversionDevise.class);
    }
    @Override
    public Devise saveCurrency(Devise devise) {
        if (devise==null){
            throw new CashChangeAppException("les champs sont null");
        }else {
            return deviseRepository.save(devise);
        }
    }
    @Override
    public Devise updateCurrency(Long id, Devise newdevise) {
        Optional<Devise> devise = deviseRepository.findById(id);
        if (devise.isPresent()){
            devise.get().setId(newdevise.getId());
            devise.get().setCode(newdevise.getCode());
            devise.get().setName(newdevise.getName());
            return deviseRepository.save(devise.get());
        }else {
            throw new CashChangeAppException("pas de devise avec l'identifiant "+ id);
        }
    }
    @Override
    public void deleteCurrency(Long id) {
        Optional<Devise> currency=deviseRepository.findById(id);
        if (currency.isPresent()){
            deviseRepository.delete(currency.get());
        }else {
            throw new CashChangeAppException("pas de devise avec l'identifiant "+ id);
        }
    }
    @Override
    public BigDecimal convertCurrency(BigDecimal amount, Devise sourceCurrency, Devise finalCurrency) {
        ResponseConversionDevise ratesResponse = getLatestRates();
        Map<String, BigDecimal> quotes = ratesResponse.getQuotes();
        if (!quotes.containsKey(sourceCurrency.getCode())||!quotes.containsKey(finalCurrency.getCode())){
            throw new CashChangeAppException("Taux de change manquants pour certaines devises.");
        }else {
            BigDecimal sourceCurrencyRate = quotes.get(sourceCurrency.getCode());
            BigDecimal finalCurrencyRate = quotes.get(finalCurrency.getCode());
            BigDecimal convertedAmount = amount.multiply(finalCurrencyRate).divide(sourceCurrencyRate, 2, RoundingMode.HALF_UP);
            return convertedAmount;
        }
    }

}
