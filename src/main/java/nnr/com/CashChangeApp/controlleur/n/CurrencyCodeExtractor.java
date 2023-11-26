package nnr.com.CashChangeApp.controlleur.n;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nnr.com.CashChangeApp.exception.CashChangeAppException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CurrencyCodeExtractor {
    public static List<String> extractCurrencyCodes(String jsonResponse) {
        List<String> currencyCodes = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            // Accéder à la propriété "quotes" dans la réponse JSON
            JsonNode quotesNode = jsonNode.get("quotes");

            // Parcourir les paires clé-valeur dans la propriété "quotes"
            Iterator<Map.Entry<String, JsonNode>> fields = quotesNode.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();
                    String currencyCode = entry.getKey();
                    currencyCodes.add(currencyCode);
                }
            } catch (CashChangeAppException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
        }

        return currencyCodes;
        }
    }
