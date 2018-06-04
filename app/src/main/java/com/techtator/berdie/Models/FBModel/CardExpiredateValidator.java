package com.techtator.berdie.Models.FBModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2018-03-28.
 */

public class CardExpiredateValidator {

    public static Boolean parseExact(SimpleDateFormat format, String source) throws ParseException {
        format.setLenient(false);
        Date result = format.parse(source);
        boolean expired = result.after(new Date());
        if(format.format(result).equalsIgnoreCase(source)) return expired;
        throw new ParseException("Unparseable date: \"" + source + "\"", 0);
    }
}
