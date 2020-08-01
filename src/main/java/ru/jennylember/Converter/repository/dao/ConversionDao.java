package ru.jennylember.Converter.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.jennylember.Converter.dto.ConversionDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "conversions")
@AllArgsConstructor
@NoArgsConstructor
public class ConversionDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "firstCurrency")
    private String firstCurrency;

    @Column(name = "secondCurrency")
    private String secondCurrency;

    @Column(name = "firstCurrencyAmount")
    private BigDecimal firstCurrencyAmount;

    @Column(name = "secondCurrencyAmount")
    private BigDecimal secondCurrencyAmount;

    @Column(name = "date")
    private LocalDate date;


}
