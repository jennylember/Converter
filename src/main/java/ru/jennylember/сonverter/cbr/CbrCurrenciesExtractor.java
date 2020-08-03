package ru.jennylember.сonverter.cbr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import ru.jennylember.сonverter.dto.CurrenciesDto;
import ru.jennylember.сonverter.utils.JaxbUtils;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class CbrCurrenciesExtractor implements CurrenciesExtractor {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private String cbrUrl;

    @Value("${cbr.url}")
    public void setCbrUrl(String cbrUrl) {
        this.cbrUrl = cbrUrl;
    }

    @Override
    public CurrenciesDto getCurrencies() {
        ResponseEntity<String> response
                = REST_TEMPLATE.getForEntity(cbrUrl , String.class);
        if (response.getBody() != null && response.getStatusCode() == HttpStatus.OK) {
            return JaxbUtils.xmlToObject(CurrenciesDto.class, response.getBody());
        }
        return null;
    }

    @PostConstruct
    private void postConstruct() {
        log.info("Initialized");
    }
}
