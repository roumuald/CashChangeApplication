package nnr.com.CashChangeApp.services;

import nnr.com.CashChangeApp.entites.Devise;
import nnr.com.CashChangeApp.entites.ResponseConversionDevise;

import java.math.BigDecimal;
import java.util.List;

public interface InterfaceDeviseService {

    public ResponseConversionDevise getLatestRates();
    public void refreshExchangeRates();

    public ResponseConversionDevise getHistoricalRates(String date);
    public Devise saveCurrency(Devise devise);
//    public Devise updateCurrency(Long id, Devise newdevise);
    public void deleteCurrency(Long id);
    public BigDecimal convertCurrency(BigDecimal amount, Devise sourceCurrency, Devise finalCurrency);
    public List<Devise> getAllDevise();
    public void saveCurrencyCodes(List<String> currencyCodes);
}
