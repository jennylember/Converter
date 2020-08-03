package ru.jennylember.сonverter.dto.front;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.jennylember.сonverter.repository.dao.CurrencyDao;
import ru.jennylember.сonverter.repository.dao.CurrencyDaoId;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FrontCurrencyDtoTest {

    @Test
    void fromDaoTest() {
        FrontCurrencyDto dto = new FrontCurrencyDto();
        dto.setName("Доллар США");
        dto.setCode("USD");

        CurrencyDao dao = new CurrencyDao();
        CurrencyDaoId daoId = new CurrencyDaoId();
        daoId.setCode("USD");
        daoId.setDate(LocalDate.of(2020,8,3));
        dao.setDateAndCode(daoId);
        dao.setName("Доллар США");
        dao.setNominal(1);
        dao.setNumCode("840");
        dao.setRate(new BigDecimal("73.4261"));

        Assertions.assertEquals(dto, FrontCurrencyDto.fromDao(dao));

    }
}
