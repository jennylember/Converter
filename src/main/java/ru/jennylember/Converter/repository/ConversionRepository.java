package ru.jennylember.Converter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.jennylember.Converter.repository.dao.ConversionDao;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ConversionRepository extends JpaRepository<ConversionDao, String> {

    @Query("SELECT con FROM ConversionDao con where con.date = ?1")
    List<ConversionDao> findAllByDate(LocalDate date);

    @Query("SELECT con FROM ConversionDao con where con.date = ?1 and con.firstCurrency = ?2 and con.secondCurrency = ?3")
    List<ConversionDao> findAllByDateAndCode(LocalDate date, String firstCurrencyCode, String secondCurrencyCode);


}
