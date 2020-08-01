package ru.jennylember.Converter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jennylember.Converter.repository.dao.CurrencyDao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyDto {

    @XmlAttribute(name = "ID")
    private String id;

    @XmlElement(name = "NumCode")
    private String numCode;

    @XmlElement(name = "CharCode")
    private String code;

    @XmlElement(name = "Nominal")
    private Integer nominal;

    @XmlElement(name = "Name")
    private String name;

    @XmlJavaTypeAdapter(BigDecimalAdaptor.class)
    @XmlElement(name = "Value")
    private BigDecimal value;

    public static CurrencyDto fromDao(CurrencyDao dao) {

        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setNumCode(dao.getNumCode());
        currencyDto.setName(dao.getName());
        currencyDto.setNominal(dao.getNominal());
        currencyDto.setValue(dao.getValue());
        currencyDto.setCode(dao.getDateAndCode().getCode());

        return currencyDto;
    }
}
