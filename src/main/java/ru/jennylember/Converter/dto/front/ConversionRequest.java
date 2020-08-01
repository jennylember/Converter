package ru.jennylember.Converter.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionRequest {

    private String firstCurrencyCode;
    private BigDecimal firstCurrencyAmount;
    private String secondCurrencyCode;

}
