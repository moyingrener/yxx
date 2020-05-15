package com.yxx.conversion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConversion implements org.springframework.core.convert.converter.Converter<String, Date> {

    @Override
    public Date convert(String arg0) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(arg0);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }


}
