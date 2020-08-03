package ru.jennylember.сonverter.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.jennylember.сonverter.repository.dao.CurrencyDao;
import ru.jennylember.сonverter.repository.dao.CurrencyDaoId;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrencyDtoTest {
    @Test
    void fromDaoTest() {
        CurrencyDao dao = new CurrencyDao();
        CurrencyDaoId daoId = new CurrencyDaoId();
        daoId.setCode("USD");
        daoId.setDate(LocalDate.of(2020, 8, 3));
        dao.setDateAndCode(daoId);
        dao.setName("Доллар США");
        dao.setNominal(1);
        dao.setNumCode("840");
        dao.setRate(new BigDecimal("73.4261"));

        CurrencyDto dto = new CurrencyDto();
        dto.setCode("USD");
        dto.setName("Доллар США");
        dto.setNominal(1);
        dto.setNumCode("840");
        dto.setRate(new BigDecimal("73.4261"));

        Assertions.assertEquals(dto, CurrencyDto.fromDao(dao));
    }
}