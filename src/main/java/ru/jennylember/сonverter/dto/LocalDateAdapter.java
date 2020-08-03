package ru.jennylember.сonverter.dto;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final DateTimeFormatter CBR_XML_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public LocalDate unmarshal(String v) {
        return LocalDate.from(CBR_XML_DATE_FORMAT.parse(v));
    }

    public String marshal(LocalDate v) {
        return v.toString();
    }
}

