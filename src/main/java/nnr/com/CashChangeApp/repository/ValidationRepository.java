package nnr.com.CashChangeApp.repository;

import nnr.com.CashChangeApp.entites.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationRepository extends JpaRepository<Validation, Long> {
    public Optional<Validation> findByCode(String code);
}
