package ru.jennylember.сonverter.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jennylember.сonverter.dto.ConversionDto;

import java.util.List;

/**
 * Response для @PostMapping("/conversionHistory")
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionHistoryResponse {

    private Integer resultCode;
    private String resultMessage;
    private List<ConversionDto> conversions;

}
