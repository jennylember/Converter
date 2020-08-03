package ru.jennylember.сonverter.utils;

import org.jetbrains.annotations.NotNull;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class JaxbUtils {

    @NotNull
    public static <T> T xmlToObject(@NotNull Class<T> type, @NotNull String xml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(type);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return type.cast(unmarshaller.unmarshal(new StringReader(xml)));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
