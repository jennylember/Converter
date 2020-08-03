package ru.jennylember.сonverter.utils;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Conversion {

    /**
     * Число цифр после запятой в BigDecimal для объема.
     */
    private static final int AMOUNT_SCALE = 4;

    /**
     * Если firstCurrencyCode это "RUB"
     *
     * @param firstCurrencyAmount - объем в первой валюте
     * @param secondCurrencyNominal - число единиц второй валюты
     * @param secondCurrencyRate - курс второй валюты
     * @return - полученный объем во второй валюте
     */
    public static @NotNull BigDecimal doConversion(@NotNull BigDecimal firstCurrencyAmount, @NotNull Integer secondCurrencyNominal, @NotNull BigDecimal secondCurrencyRate) {

        return new BigDecimal(secondCurrencyNominal)
                .multiply(firstCurrencyAmount)
                .divide(secondCurrencyRate, AMOUNT_SCALE, RoundingMode.HALF_UP);

    }

    /**
     * Если secondCurrencyCode это "RUB"
     *
     * @param firstCurrencyNominal - число единиц первой валюты
     * @param firstCurrencyRate    - курс первой валюты
     * @param firstCurrencyAmount  - объем в первой валюте
     * @return полученный объем во второй валюте
     *
     */
    public static @NotNull BigDecimal doConversion(@NotNull Integer firstCurrencyNominal, @NotNull BigDecimal firstCurrencyRate, @NotNull BigDecimal firstCurrencyAmount) {
        return firstCurrencyAmount
                .multiply(firstCurrencyRate)
                .divide(new BigDecimal(firstCurrencyNominal), AMOUNT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Если валюты не "RUB"
     *
     * @param firstCurrencyNominal - число единиц первой валюты
     * @param firstCurrencyRate - курс первой валюты
     * @param firstCurrencyAmount - объем в первой валюте
     * @param secondCurrencyNominal - число единиц второй валюты
     * @param secondCurrencyRate - курс второй валюты
     * @return - полученный объем во второй валюте
     */
    public static @NotNull BigDecimal doConversion(@NotNull Integer firstCurrencyNominal, @NotNull BigDecimal firstCurrencyRate, @NotNull BigDecimal firstCurrencyAmount,
                                          @NotNull Integer secondCurrencyNominal, @NotNull BigDecimal secondCurrencyRate) {

        return firstCurrencyAmount
                .multiply(firstCurrencyRate)
                .multiply(new BigDecimal(secondCurrencyNominal))
                .divide(new BigDecimal(firstCurrencyNominal), AMOUNT_SCALE, RoundingMode.HALF_UP)
                .divide(secondCurrencyRate, AMOUNT_SCALE, RoundingMode.HALF_UP);

    }

}
