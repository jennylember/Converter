package ru.jennylember.Converter.dto.front;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jennylember.Converter.repository.dao.CurrencyDao;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontCurrencyDto {

    private String numCode;
    private String name;

    public static FrontCurrencyDto fromDao(CurrencyDao dao) {
        return new FrontCurrencyDto(dao.getDateAndCode().getCode(), dao.getName());
    }
}
