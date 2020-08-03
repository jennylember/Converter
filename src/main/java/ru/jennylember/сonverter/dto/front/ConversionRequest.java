package ru.jennylember.сonverter.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request для @PostMapping("/conversion")
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionRequest {

    private String firstCurrencyCode;
    private BigDecimal firstCurrencyAmount;
    private String secondCurrencyCode;

}
