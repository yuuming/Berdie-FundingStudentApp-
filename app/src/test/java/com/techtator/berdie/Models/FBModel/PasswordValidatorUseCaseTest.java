package com.techtator.berdie.Models.FBModel;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yuminakamura on 2018-03-27.
 */
public class PasswordValidatorUseCaseTest {
    @Test
    public void testNormalPassword()
    {
        PasswordValidatorUseCase validator = PasswordValidatorUseCase.buildValidator(false, false, false, 6, 14);

        Assert.assertTrue(validator.validatePassword("howtodoinjava"));
        Assert.assertTrue(validator.validatePassword("howtodoin"));
        //Sort on length
        Assert.assertFalse(validator.validatePassword("howto"));
    }

    @Test
    public void testForceNumeric()
    {
        PasswordValidatorUseCase validator = PasswordValidatorUseCase.buildValidator(false,false, true, 6, 16);
        //Contains numeric
        Assert.assertTrue(validator.validatePassword("helloyuumi12"));
        Assert.assertTrue(validator.validatePassword("34hellosayaka"));
        Assert.assertTrue(validator.validatePassword("hellow77gami"));
        //No numeric
        Assert.assertFalse(validator.validatePassword("hellobomin"));
    }

    @Test
    public void testForceCapitalLetter()
    {
        PasswordValidatorUseCase validator = PasswordValidatorUseCase.buildValidator(false,true, false, 6, 16);
        //Contains capitals
        Assert.assertTrue(validator.validatePassword("hellocemandaliA"));
        Assert.assertTrue(validator.validatePassword("Ahellocemandali"));
        Assert.assertTrue(validator.validatePassword("hellocemAandali"));
        //No capital letter
        Assert.assertFalse(validator.validatePassword("hellocemandali"));
    }

    @Test
    public void testForceSpecialCharacter()
    {
        PasswordValidatorUseCase validator = PasswordValidatorUseCase.buildValidator(true,false, false, 6, 16);
        //Contains special char
        Assert.assertTrue(validator.validatePassword("howtodinjav!!"));
        Assert.assertTrue(validator.validatePassword("@Howtodoinjava"));
        Assert.assertTrue(validator.validatePassword("howtodOInjava@"));
        //No special char
        Assert.assertFalse(validator.validatePassword("howtodoinjava"));
    }

}