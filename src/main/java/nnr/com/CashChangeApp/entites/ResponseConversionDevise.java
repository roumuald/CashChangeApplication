package nnr.com.CashChangeApp.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResponseConversionDevise {
    private boolean success;
    private long timestamp;
    private String source;
    private Map<String, BigDecimal> quotes;
}
