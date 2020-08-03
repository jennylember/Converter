package ru.jennylember.сonverter.dto.front;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jennylember.сonverter.repository.dao.CurrencyDao;

/**
 * Response для @GetMapping("/currencies")
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontCurrencyDto {

    private String code;
    private String name;

    public static FrontCurrencyDto fromDao(CurrencyDao dao) {
        return new FrontCurrencyDto(dao.getDateAndCode().getCode(), dao.getName());
    }


}
