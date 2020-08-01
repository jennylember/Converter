package ru.jennylember.Converter.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jennylember.Converter.dto.CurrencyDto;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Data
@Table(name = "currencies")
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDao implements Serializable {

    @EmbeddedId
    private CurrencyDaoId dateAndCode;

    @Column(name = "numCode")
    private String numCode;

    @Column(name = "nominal")
    private Integer nominal;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private BigDecimal value;

    public static CurrencyDao fromDto(LocalDate date, CurrencyDto dto) {

        return new CurrencyDao((new CurrencyDaoId(date, dto.getCode())),
                dto.getNumCode(), dto.getNominal(), dto.getName(), dto.getValue());
    }
}
