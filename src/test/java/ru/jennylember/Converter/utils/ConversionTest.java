package ru.jennylember.Converter.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ConversionTest {

    @Test
    public void conversionTest() {

        Assertions.assertEquals(BigDecimal.valueOf(7342.69), Conversion.doConversion("USD",1,
                BigDecimal.valueOf(73.4269),BigDecimal.valueOf(100), "RUB"));

        Assertions.assertEquals(BigDecimal.valueOf(947.421), Conversion.doConversion("HKD",10,
                BigDecimal.valueOf(94.7421),BigDecimal.valueOf(100), "RUB"));

        Assertions.assertEquals(BigDecimal.valueOf(7342.69), Conversion.doConversion("RUB",1,
                BigDecimal.valueOf(73.4269),BigDecimal.valueOf(100), "RUB"));


    }
}
