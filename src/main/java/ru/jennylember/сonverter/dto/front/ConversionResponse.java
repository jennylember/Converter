package ru.jennylember.сonverter.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Response для @PostMapping("/conversion")
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponse {
    private Integer resultCode;
    private String resultMessage;
    private BigDecimal secondCurrencyAmount;
}
