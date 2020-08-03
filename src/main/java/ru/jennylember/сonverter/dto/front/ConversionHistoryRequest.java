package ru.jennylember.сonverter.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Request для @PostMapping("/conversionHistory")
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionHistoryRequest {
    private String firstCurrencyCode;
    private String secondCurrencyCode;
    private LocalDate date;
}
