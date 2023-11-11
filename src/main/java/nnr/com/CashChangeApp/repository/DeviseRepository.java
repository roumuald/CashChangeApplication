package nnr.com.CashChangeApp.repository;

import nnr.com.CashChangeApp.entites.Devise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviseRepository extends JpaRepository<Devise, Long> {
}
