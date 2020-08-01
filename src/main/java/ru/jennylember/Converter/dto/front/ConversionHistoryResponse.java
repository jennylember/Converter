package ru.jennylember.Converter.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jennylember.Converter.repository.dao.ConversionDao;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionHistoryResponse {

    private Integer resultCode;
    private String resultMessage;
    private List<ConversionDao> conversions;

}
