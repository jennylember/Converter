package ru.jennylember.Converter.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jennylember.Converter.repository.dao.ConversionDao;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionDto {

    private String firstCurrency;
    private String secondCurrency;
    private BigDecimal firstCurrencyAmount;
    private BigDecimal secondCurrencyAmount;
    private LocalDate date;

    public static ConversionDto fromDao(ConversionDao dao) {
        return new ConversionDto(dao.getFirstCurrency(), dao.getSecondCurrency(),
                dao.getFirstCurrencyAmount(), dao.getSecondCurrencyAmount(), dao.getDate());
    }

}
