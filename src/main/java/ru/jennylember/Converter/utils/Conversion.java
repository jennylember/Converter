package ru.jennylember.Converter.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.jennylember.Converter.repository.CurrencyRepository;
import ru.jennylember.Converter.repository.dao.CurrencyDao;

import java.math.BigDecimal;

public class Conversion {

    // TODO Реализовать конвертацию
    public static BigDecimal doConversion(String firstCurrencyCode, Integer firstCurrencyNominal, BigDecimal firstCurrencyValue,
                                          BigDecimal firstCurrencyAmount, String secondCurrencyCode) {

        BigDecimal secondCurrencyAmount = null;


        if (secondCurrencyCode.equals("RUB")) {
            secondCurrencyAmount = firstCurrencyAmount
                    .multiply(firstCurrencyValue)
                    .divide(BigDecimal.valueOf(firstCurrencyNominal));
        }

        if (firstCurrencyCode.equals("RUB")) {
            secondCurrencyAmount = BigDecimal.valueOf(firstCurrencyNominal)
                    .divide(firstCurrencyValue)
                    .multiply(firstCurrencyAmount);
        }

        return secondCurrencyAmount;
    }

}
