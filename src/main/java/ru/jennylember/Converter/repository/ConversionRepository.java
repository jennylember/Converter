package ru.jennylember.Converter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jennylember.Converter.repository.dao.ConversionDao;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ConversionRepository extends JpaRepository<ConversionDao, String> {

    public List<ConversionDao> findAllByDate(LocalDate date);

}
