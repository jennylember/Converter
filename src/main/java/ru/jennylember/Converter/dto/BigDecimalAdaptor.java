package ru.jennylember.Converter.dto;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

public class BigDecimalAdaptor extends XmlAdapter<String, BigDecimal> {
    @Override
    public String marshal(BigDecimal value) throws Exception
    {
        if (value!= null)
        {
            return value.toString();
        }
        return null;
    }

    @Override
    public BigDecimal unmarshal(String s) throws Exception
    {
        // TODO
        if (s.contains(",")) {
            return new BigDecimal(s.replaceAll(",", "."));
        }
        return new BigDecimal(s);
    }
}
