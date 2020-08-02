package ru.jennylember.Converter.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.jennylember.Converter.repository.dao.CurrencyDao;
import ru.jennylember.Converter.repository.dao.CurrencyDaoId;


@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyDao, CurrencyDaoId> {

        @Query("SELECT cur FROM CurrencyDao cur where cur.dateAndCode.code = ?1")
        CurrencyDao findByCode(String code);
}
