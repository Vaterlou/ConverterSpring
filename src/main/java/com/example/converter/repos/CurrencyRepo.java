package com.example.converter.repos;

import com.example.converter.domain.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepo extends CrudRepository<Currency, Long> {
    Currency findByCharCode(String charCode);
}
