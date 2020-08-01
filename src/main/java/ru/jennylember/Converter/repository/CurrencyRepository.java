package ru.jennylember.Converter.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jennylember.Converter.repository.dao.CurrencyDao;
import ru.jennylember.Converter.repository.dao.CurrencyDaoId;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyDao, CurrencyDaoId> {
        public CurrencyDao findByCode(String code);
}
