package ru.jennylember.Converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.jennylember.Converter.cbr.CbrCurrenciesExtractor;
import ru.jennylember.Converter.dto.ConversionDto;
import ru.jennylember.Converter.dto.CurrencyDto;
import ru.jennylember.Converter.dto.front.*;
import ru.jennylember.Converter.repository.ConversionRepository;
import ru.jennylember.Converter.repository.CurrencyRepository;
import ru.jennylember.Converter.repository.dao.ConversionDao;
import ru.jennylember.Converter.repository.dao.CurrencyDao;
import ru.jennylember.Converter.repository.dao.CurrencyDaoId;
import ru.jennylember.Converter.utils.Conversion;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ConverterController {

    @PostConstruct
    private void postConstruct() {
        log.info("Initialized");
        test();
    }

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    ConversionRepository conversionRepository;

    @Autowired
    CbrCurrenciesExtractor cbrCurrenciesExtractor;

    public void test() {

        CurrencyDaoId currencyDaoId = new CurrencyDaoId(LocalDate.of(2022, 7, 30), "AZN");
        CurrencyDao currencyDao = new CurrencyDao();
        currencyDaoId.setDate(LocalDate.of(2022, 7, 30));
        currencyDaoId.setCode("AZN");
        currencyDao.setDateAndCode(currencyDaoId);
        currencyDao.setName("Азербайджанский манат");
        currencyDao.setNominal(1);
        currencyDao.setValue(new BigDecimal("43.1803"));
        currencyDao.setNumCode("944");

        currencyRepository.save(currencyDao);

        List<CurrencyDao> currencyDao1 = currencyRepository.findAll();
        currencyDao.setName("Жопа");
    }


    @GetMapping("/currencies")
    public List<FrontCurrencyDto> getRate() {

        log.info("/currencies {}", currencyRepository.findAll());

        return currencyRepository.findAll()
                .stream()
                .map(FrontCurrencyDto::fromDao)
                .collect(Collectors.toList());
    }

    @PostMapping("/conversion")
    public @ResponseBody
    ConversionResponse doConversion(@RequestBody ConversionRequest conversionRequest, HttpServletResponse response) throws IOException {

        log.info("/conversion {}", conversionRequest);

        String firstCurrencyCode = conversionRequest.getFirstCurrencyCode();
        String secondCurrencyCode = conversionRequest.getSecondCurrencyCode();
        BigDecimal firstCurrencyAmount = conversionRequest.getFirstCurrencyAmount();

        ConversionResponse conversionResponse = new ConversionResponse();

        if (firstCurrencyAmount == null) {
            conversionResponse.setResultCode(1);
            conversionResponse.setResultMessage("firstCurrencyAmount is null");
        }
        if (firstCurrencyAmount.compareTo(BigDecimal.ONE) < 0) {
            conversionResponse.setResultCode(1);
            conversionResponse.setResultMessage("firstCurrencyAmount is less null");
        }
        if (firstCurrencyCode == null) {
            conversionResponse.setResultCode(1);
            conversionResponse.setResultMessage("firstCurrencyCode is null");
        }
        if (secondCurrencyCode == null) {
            conversionResponse.setResultCode(1);
            conversionResponse.setResultMessage("secondCurrencyCode is null");
        }

        CurrencyDto currencyDto = CurrencyDto.fromDao(currencyRepository.findByCode(firstCurrencyCode));

        // Если какой-то из валют нет в списке
        if (currencyRepository.findByCode(firstCurrencyCode) == null || currencyRepository.findByCode(secondCurrencyCode) == null) {
            updateCurrencies();
        }

        LocalDate currentDate = LocalDate.now();
        conversionResponse.setResultCode(0);
        BigDecimal secondCurrencyAmount = Conversion.doConversion(firstCurrencyCode, currencyDto.getNominal(),
                currencyDto.getValue(), firstCurrencyAmount, secondCurrencyCode);

        conversionResponse.setSecondCurrencyAmount(secondCurrencyAmount);

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
        // TODO получение курсов и сохранение в БД
        cbrCurrenciesExtractor.getCurrencies();
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
        conversionHistoryResponse.setConversions(conversionRepository.findAllByDate(date));

        return conversionHistoryResponse;
    }
}
