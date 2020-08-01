package ru.jennylember.Converter.dto;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.from(dateFormat.parse(v));
    }

    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}

