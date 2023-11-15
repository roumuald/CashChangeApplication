package nnr.com.CashChangeApp.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nnr.com.CashChangeApp.enumeration.StatutTransaction;
import nnr.com.CashChangeApp.enumeration.TypeTransaction;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal montantSource;
    private BigDecimal montantFinal;
    private Instant transactionDate;
    //private Map<String, BigDecimal> exchangeRate;
    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;
    @Enumerated(EnumType.STRING)
    private StatutTransaction statusTransaction;
    @ManyToOne(fetch = FetchType.EAGER)
    private Utilisateur utilisateur;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "devise_source_id")
    private Devise deviseSource;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "devise_cible_id")
    private Devise deviseCible;
}
