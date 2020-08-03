package ru.jennylember.сonverter.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ConversionTest {

    @Test
    void conversionTest() {

        // If firstCurrencyCode "RUB"
        Assertions.assertEquals(new BigDecimal("300.3634"), Conversion.doConversion(new BigDecimal("1000"),
                10, new BigDecimal("33.2930")));

        // If secondCurrencyCode "RUB"
        Assertions.assertEquals(new BigDecimal("7342.6900"), Conversion.doConversion(1,
                new BigDecimal("73.4269"), new BigDecimal("100")));

        // If secondCurrencyCode "RUB"
        Assertions.assertEquals(new BigDecimal("947.4210"), Conversion.doConversion(10,
                new BigDecimal("94.7421"), new BigDecimal("100")));

        // No RUB
        Assertions.assertEquals(new BigDecimal("412.4995"), Conversion.doConversion(100,
                new BigDecimal("25.2823"), new BigDecimal("5432"), 10, new BigDecimal("33.2930")));

    }
}
