package com.techtator.berdie.Models.FBModel;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by user on 2018-03-28.
 */
public class CardExpiredateValidatorTest {
    @Test()
    public void testLenient_OutOfRange() throws ParseException {

        CardExpiredateValidator cardExpiredateValidator = new CardExpiredateValidator();
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM");
        Assert.assertTrue(cardExpiredateValidator.parseExact(sdf,"18/04"));
        Assert.assertTrue(cardExpiredateValidator.parseExact(sdf,"18/02"));
        Assert.assertTrue(cardExpiredateValidator.parseExact(sdf,"19/04"));
    }

}