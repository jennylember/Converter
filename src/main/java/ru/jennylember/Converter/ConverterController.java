package ru.jennylember.Converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.jennylember.Converter.cbr.CbrCurrenciesExtractor;
import ru.jennylember.Converter.dto.CurrenciesDto;
import ru.jennylember.Converter.dto.CurrencyDto;
import ru.jennylember.Converter.dto.front.*;
import ru.jennylember.Converter.repository.ConversionRepository;
import ru.jennylember.Converter.repository.CurrencyRepository;
import ru.jennylember.Converter.repository.dao.ConversionDao;
import ru.jennylember.Converter.repository.dao.CurrencyDao;
import ru.jennylember.Converter.utils.Conversion;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ConverterController {

    @PostConstruct
    private void postConstruct() {
        log.info("Initialized");
        updateCurrencies();
    }

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    ConversionRepository conversionRepository;

    @Autowired
    CbrCurrenciesExtractor cbrCurrenciesExtractor;

    @GetMapping("/currencies")
    public List<FrontCurrencyDto> getRate() {

        log.info("/currencies");

        return currencyRepository.findAll()
                .stream()
                .map(FrontCurrencyDto::fromDao)
                .collect(Collectors.toList());
    }

    @PostMapping("/conversion")
    public @ResponseBody
    ConversionResponse doConversion(@RequestBody ConversionRequest conversionRequest, HttpServletResponse response) {

        log.info("/conversion {}", conversionRequest);

        String firstCurrencyCode = conversionRequest.getFirstCurrencyCode();
        String secondCurrencyCode = conversionRequest.getSecondCurrencyCode();
        BigDecimal firstCurrencyAmount = conversionRequest.getFirstCurrencyAmount();

        ConversionResponse conversionResponse = new ConversionResponse();

        if (firstCurrencyAmount == null) {
            conversionResponse.setResultCode(1);
            conversionResponse.setResultMessage("firstCurrencyAmount is null");
            return conversionResponse;
        }
        if (firstCurrencyAmount.compareTo(BigDecimal.ONE) < 0) {
            conversionResponse.setResultCode(1);
            conversionResponse.setResultMessage("firstCurrencyAmount is less null");
            return conversionResponse;
        }
        if (firstCurrencyCode == null) {
            conversionResponse.setResultCode(1);
            conversionResponse.setResultMessage("firstCurrencyCode is null");
            return conversionResponse;
        }
        if (secondCurrencyCode == null) {
            conversionResponse.setResultCode(1);
            conversionResponse.setResultMessage("secondCurrencyCode is null");
            return conversionResponse;
        }

        // Если какой-то из валют нет в списке
        if (currencyRepository.findByCode(firstCurrencyCode) == null || currencyRepository.findByCode(secondCurrencyCode) == null) {
            updateCurrencies();
        }

        LocalDate currentDate = LocalDate.now();
        BigDecimal secondCurrencyAmount;

        if (firstCurrencyCode.equals("RUB")) {
            CurrencyDto secondCurrencyDto = CurrencyDto.fromDao(currencyRepository.findByCode(secondCurrencyCode));
            secondCurrencyAmount = Conversion.doConversion(firstCurrencyAmount, secondCurrencyDto.getNominal(), secondCurrencyDto.getValue());
            conversionResponse.setSecondCurrencyAmount(secondCurrencyAmount);
        } else {
            if (secondCurrencyCode.equals("RUB")) {
                CurrencyDto firstCurrencyDto = CurrencyDto.fromDao(currencyRepository.findByCode(firstCurrencyCode));
                secondCurrencyAmount = Conversion.doConversion(firstCurrencyDto.getNominal(), firstCurrencyDto.getValue(), firstCurrencyAmount);
                conversionResponse.setSecondCurrencyAmount(secondCurrencyAmount);
            } else {
                CurrencyDto firstCurrencyDto = CurrencyDto.fromDao(currencyRepository.findByCode(firstCurrencyCode));
                CurrencyDto secondCurrencyDto = CurrencyDto.fromDao(currencyRepository.findByCode(secondCurrencyCode));
                secondCurrencyAmount = Conversion.doConversion(firstCurrencyDto.getNominal(), firstCurrencyDto.getValue(), firstCurrencyAmount,
                        secondCurrencyDto.getNominal(), secondCurrencyDto.getValue());
                conversionResponse.setSecondCurrencyAmount(secondCurrencyAmount);
            }
        }

        conversionResponse.setResultCode(0);

        // Сохраняем в базу транзакцию
        ConversionDao conversionDao = new ConversionDao();

        conversionDao.setFirstCurrency(firstCurrencyCode);
        conversionDao.setSecondCurrency(secondCurrencyCode);
        conversionDao.setFirstCurrencyAmount(firstCurrencyAmount);
        conversionDao.setSecondCurrencyAmount(secondCurrencyAmount);
        conversionDao.setDate(currentDate);

        conversionRepository.save(conversionDao);

        return conversionResponse;
    }

    public void updateCurrencies() {
        CurrenciesDto currenciesDto = cbrCurrenciesExtractor.getCurrencies();
        LocalDate date = currenciesDto.getDate();
        currenciesDto.getCurrencies().forEach(
                currencyDto -> currencyRepository.save(CurrencyDao.fromDto(date, currencyDto))
        );
    }


    @PostMapping("/conversionHistory")
    public @ResponseBody
    ConversionHistoryResponse getHistory(@RequestBody ConversionHistoryRequest conversionHistoryRequest) {

        log.info("/conversionHistory {}", conversionHistoryRequest);

        String firstCurrencyCode = conversionHistoryRequest.getFirstCurrencyCode();
        String secondCurrencyCode = conversionHistoryRequest.getSecondCurrencyCode();
        LocalDate date = conversionHistoryRequest.getDate();

        ConversionHistoryResponse conversionHistoryResponse = new ConversionHistoryResponse();

        if (firstCurrencyCode == null) {
            conversionHistoryResponse.setResultCode(1);
            conversionHistoryResponse.setResultMessage("firstCurrencyCode is null");
        }
        if (secondCurrencyCode == null) {
            conversionHistoryResponse.setResultCode(1);
            conversionHistoryResponse.setResultMessage("secondCurrencyCode is null");
        }
        if (date == null) {
            conversionHistoryResponse.setResultCode(1);
            conversionHistoryResponse.setResultMessage("date is null");
        }

        conversionHistoryResponse.setResultCode(0);
        conversionHistoryResponse.setConversions(conversionRepository.findAllByDateAndCode(date, firstCurrencyCode, secondCurrencyCode));

        return conversionHistoryResponse;
    }
}
