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
import java.util.List;
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

    /**
     * récupération des taux de change en temps réel.
     * @return un objet Devise
     */
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

    /**
     * l'historique des taux de change permet de savoir la frequence de taux de change
     * @param date
     * @return ResponseConversionDevise
     */
    @Override
    public ResponseConversionDevise getHistoricalRates(String date) {
        String apiUrl = "http://api.currencylayer.com/historical?date=" + date;
        String url = apiUrl + "&access_key=" + apiKey;
        return restTemplate.getForObject(url, ResponseConversionDevise.class);
    }

    /**
     * methode de conversion de devise
     * @param amount
     * @param sourceCurrency
     * @param finalCurrency
     * @return la valeur converti en BigDecimal
     */
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

    @Override
    public List<Devise> getAllDevise() {
        List<Devise> devises = this.deviseRepository.findAll();
        if (devises.isEmpty()){
            throw new CashChangeAppException("Aucune devise disponible en base de donnees");
        }
        return devises;
    }

    @Override
    public void saveCurrencyCodes(List<String> currencyCodes) {
        for (String code : currencyCodes) {
            Devise devise = new Devise();
            devise.setCode(code);
            this.deviseRepository.save(devise);
        }
    }
}
