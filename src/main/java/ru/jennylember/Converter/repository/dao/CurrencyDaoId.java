package ru.jennylember.Converter.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDaoId implements Serializable {

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "code")
    private String code;
}
