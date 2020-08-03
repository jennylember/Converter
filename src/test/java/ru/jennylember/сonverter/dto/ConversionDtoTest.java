package ru.jennylember.сonverter.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.jennylember.сonverter.repository.dao.ConversionDao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ConversionDtoTest {
    @Test
    void fromDaoTest() {
        ConversionDto dto = new ConversionDto();
        dto.setDate(LocalDate.of(2020, 8, 3));
        dto.setFirstCurrency("Доллар США");
        dto.setSecondCurrency("Рубль");
        dto.setFirstCurrencyAmount(new BigDecimal("1"));
        dto.setSecondCurrencyAmount(new BigDecimal("73.4261"));

        ConversionDao dao = new ConversionDao();
        dao.setDate(LocalDate.of(2020, 8, 3));
        dao.setFirstCurrency("Доллар США");
        dao.setSecondCurrency("Рубль");
        dao.setFirstCurrencyAmount(new BigDecimal("1"));
        dao.setSecondCurrencyAmount(new BigDecimal("73.4261"));

        Assertions.assertEquals(dto, ConversionDto.fromDao(dao));
    }
}
