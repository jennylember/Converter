package ru.jennylember.Converter.cbr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.jennylember.Converter.dto.CurrenciesDto;
import ru.jennylember.Converter.utils.JaxbUtils;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class CbrCurrenciesExtractor implements CurrenciesExtractor {

    @Override
    public CurrenciesDto getCurrencies() {
        RestTemplate restTemplate = new RestTemplate();
        String url
                = "http://www.cbr.ru/scripts/XML_daily.asp";
        ResponseEntity<String> response
                = restTemplate.getForEntity(url , String.class);
        if (response.getBody() != null && response.getStatusCode() == HttpStatus.OK) {
            return JaxbUtils.xmlToObject(CurrenciesDto.class, response.getBody());
        }
        return null;
    }

    @PostConstruct
    private void postConstruct() {
        CurrenciesDto currenciesDto = getCurrencies();
        log.info(String.valueOf(currenciesDto));
    }
}
