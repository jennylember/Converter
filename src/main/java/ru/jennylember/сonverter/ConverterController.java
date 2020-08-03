package ru.jennylember.сonverter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.jennylember.сonverter.cbr.CbrCurrenciesExtractor;
import ru.jennylember.сonverter.dto.ConversionDto;
import ru.jennylember.сonverter.dto.CurrenciesDto;
import ru.jennylember.сonverter.dto.CurrencyDto;
import ru.jennylember.сonverter.dto.front.*;
import ru.jennylember.сonverter.repository.ConversionRepository;
import ru.jennylember.сonverter.repository.CurrencyRepository;
import ru.jennylember.сonverter.repository.dao.ConversionDao;
import ru.jennylember.сonverter.repository.dao.CurrencyDao;
import ru.jennylember.сonverter.utils.Conversion;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ConverterController {

    private static final String RUB_CURRENCY = "RUB";
    private static final String REQUEST_IS_NULL = "Request is null";
    private static final String FIRST_CURRENCY_AMOUNT_IS_NULL = "firstCurrencyAmount is null";
    private static final String FIRST_CURRENCY_CODE_IS_NULL = "firstCurrencyCode is null";
    private static final String SECOND_CURRENCY_CODE_IS_NULL = "secondCurrencyCode is null";
    private static final String DATE_IS_NULL = "date is null";
    private static final String FIRST_CURRENCY_AMOUNT_IS_LESS_NULL = "firstCurrencyAmount is less null";

    private final CurrencyRepository currencyRepository;

    private final ConversionRepository conversionRepository;

    private final CbrCurrenciesExtractor cbrCurrenciesExtractor;

    @Autowired
    public ConverterController(CurrencyRepository currencyRepository, ConversionRepository conversionRepository, CbrCurrenciesExtractor cbrCurrenciesExtractor) {
        this.currencyRepository = currencyRepository;
        this.conversionRepository = conversionRepository;
        this.cbrCurrenciesExtractor = cbrCurrenciesExtractor;
    }

    @PostConstruct
    private void postConstruct() {
        log.info("Initialized");
        updateCurrencies();
    }

    @GetMapping("/currencies")
    public List<FrontCurrencyDto> getRate() {

        log.info("/currencies");

        return currencyRepository.findAll()
                .stream()
                .map(FrontCurrencyDto::fromDao)
                .collect(Collectors.toList());
    }

    @PostMapping("/conversion")
    public ConversionResponse doConversion(@RequestBody ConversionRequest conversionRequest) {

        log.info("/conversion {}", conversionRequest);

        ConversionResponse conversionResponse = new ConversionResponse();

        if (conversionRequest == null) {
            conversionResponse.setResultCode(1);
            conversionResponse.setResultMessage(REQUEST_IS_NULL);
            return conversionResponse;
        }

        String firstCurrencyCode = conversionRequest.getFirstCurrencyCode();
        String secondCurrencyCode = conversionRequest.getSecondCurrencyCode();
        BigDecimal firstCurrencyAmount = conversionRequest.getFirstCurrencyAmount();


        if (firstCurrencyAmount == null) {
            conversionResponse.setResultCode(1);
            conversionResponse.setResultMessage(FIRST_CURRENCY_AMOUNT_IS_NULL);
            return conversionResponse;
        }
        if (firstCurrencyAmount.compareTo(BigDecimal.ONE) < 0) {
            conversionResponse.setResultCode(1);
            conversionResponse.setResultMessage(FIRST_CURRENCY_AMOUNT_IS_LESS_NULL);
            return conversionResponse;
        }
        if (firstCurrencyCode == null) {
            conversionResponse.setResultCode(1);
            conversionResponse.setResultMessage(FIRST_CURRENCY_CODE_IS_NULL);
            return conversionResponse;
        }
        if (secondCurrencyCode == null) {
            conversionResponse.setResultCode(1);
            conversionResponse.setResultMessage(SECOND_CURRENCY_CODE_IS_NULL);
            return conversionResponse;
        }

        // Если какой-то из валют нет в списке
        if (currencyRepository.findByCode(firstCurrencyCode) == null || currencyRepository.findByCode(secondCurrencyCode) == null) {
            updateCurrencies();
        }

        LocalDate currentDate = LocalDate.now();
        BigDecimal secondCurrencyAmount;

        if (RUB_CURRENCY.equals(firstCurrencyCode)) {
            CurrencyDto secondCurrencyDto = CurrencyDto.fromDao(currencyRepository.findByCode(secondCurrencyCode));
            secondCurrencyAmount = Conversion.doConversion(firstCurrencyAmount, secondCurrencyDto.getNominal(), secondCurrencyDto.getRate());
        } else {
            if (RUB_CURRENCY.equals(secondCurrencyCode)) {
                CurrencyDto firstCurrencyDto = CurrencyDto.fromDao(currencyRepository.findByCode(firstCurrencyCode));
                secondCurrencyAmount = Conversion.doConversion(firstCurrencyDto.getNominal(), firstCurrencyDto.getRate(), firstCurrencyAmount);
            } else {
                CurrencyDto firstCurrencyDto = CurrencyDto.fromDao(currencyRepository.findByCode(firstCurrencyCode));
                CurrencyDto secondCurrencyDto = CurrencyDto.fromDao(currencyRepository.findByCode(secondCurrencyCode));
                secondCurrencyAmount = Conversion.doConversion(firstCurrencyDto.getNominal(), firstCurrencyDto.getRate(), firstCurrencyAmount,
                        secondCurrencyDto.getNominal(), secondCurrencyDto.getRate());
            }
        }

        conversionResponse.setResultCode(0);
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

    private void updateCurrencies() {
        CurrenciesDto currenciesDto = cbrCurrenciesExtractor.getCurrencies();
        LocalDate date = currenciesDto.getDate();
        currenciesDto.getCurrencies().forEach(
                currencyDto -> currencyRepository.save(CurrencyDao.fromDto(date, currencyDto))
        );
    }

    @PostMapping("/conversionHistory")
    public ConversionHistoryResponse getHistory(@RequestBody ConversionHistoryRequest request) {

        log.info("/conversionHistory {}", request);

        ConversionHistoryResponse response = new ConversionHistoryResponse();

        if (request == null) {
            response.setResultCode(1);
            response.setResultMessage(REQUEST_IS_NULL);
            return response;
        }

        String firstCurrencyCode = request.getFirstCurrencyCode();
        String secondCurrencyCode = request.getSecondCurrencyCode();
        LocalDate date = request.getDate();


        if (firstCurrencyCode == null) {
            response.setResultCode(1);
            response.setResultMessage(FIRST_CURRENCY_CODE_IS_NULL);
            return response;
        }
        if (secondCurrencyCode == null) {
            response.setResultCode(1);
            response.setResultMessage(SECOND_CURRENCY_CODE_IS_NULL);
            return response;
        }
        if (date == null) {
            response.setResultCode(1);
            response.setResultMessage(DATE_IS_NULL);
            return response;
        }

        response.setResultCode(0);

        response.setConversions(conversionRepository.findAllByDateAndCode(date, firstCurrencyCode, secondCurrencyCode).stream()
                .map(ConversionDto::fromDao)
                .collect(Collectors.toList()));

        return response;
    }
}
