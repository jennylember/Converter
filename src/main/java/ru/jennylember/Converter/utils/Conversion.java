package ru.jennylember.Converter.utils;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Conversion {

    public static BigDecimal doConversion(@NotNull Integer firstCurrencyNominal, @NotNull BigDecimal firstCurrencyValue, BigDecimal firstCurrencyAmount,
                                          @NotNull Integer secondCurrencyNominal, @NotNull BigDecimal secondCurrencyValue) {

        return firstCurrencyAmount
                .multiply(firstCurrencyValue)
                .multiply(new BigDecimal(secondCurrencyNominal))
                .divide(new BigDecimal(firstCurrencyNominal), 4, RoundingMode.HALF_UP)
                .divide(secondCurrencyValue, 4, RoundingMode.HALF_UP);

    }

    // If firstCurrencyCode "RUB"
    public static BigDecimal doConversion(BigDecimal firstCurrencyAmount, Integer secondCurrencyNominal, BigDecimal secondCurrencyValue) {

        return new BigDecimal(secondCurrencyNominal)
                .multiply(firstCurrencyAmount)
                .divide(secondCurrencyValue, 4, RoundingMode.HALF_UP);

    }

    // If secondCurrencyCode "RUB"
    public static BigDecimal doConversion(Integer firstCurrencyNominal, BigDecimal firstCurrencyValue, BigDecimal firstCurrencyAmount) {
        return firstCurrencyAmount
                .multiply(firstCurrencyValue)
                .divide(new BigDecimal(firstCurrencyNominal), 4, RoundingMode.HALF_UP);
    }

}
