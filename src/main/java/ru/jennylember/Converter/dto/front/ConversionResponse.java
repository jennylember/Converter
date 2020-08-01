package ru.jennylember.Converter.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponse {
    private Integer resultCode;
    private String resultMessage;
    private BigDecimal secondCurrencyAmount;
}
