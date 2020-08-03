package ru.jennylember.сonverter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

/**
 * Класс нужен для парсинга XML актуальных валют и их курсов с сайта ЦБРФ
 */
@Data
@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
public class CurrenciesDto {


    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @XmlAttribute(name = "Date")
    private LocalDate date;

    @XmlElement(name = "Valute")
    private List<CurrencyDto> currencies;

}
