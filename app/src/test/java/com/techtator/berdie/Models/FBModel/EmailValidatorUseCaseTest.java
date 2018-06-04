package com.techtator.berdie.Models.FBModel;
import com.techtator.berdie.Models.FBModel.EmailValidatorUseCase;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class EmailValidatorUseCaseTest {
    private String arg;
    private static EmailValidatorUseCase emailValidatorUseCase;
    private Boolean expectedValidation;
    public EmailValidatorUseCaseTest(String str, Boolean expectedValidation) {
        this.arg = str;
        this.expectedValidation = expectedValidation;
    }

    @BeforeClass
    public static void initialize() {
        emailValidatorUseCase = new EmailValidatorUseCase();
    }

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {

                { "javacodegeeks@gmail.com.2j",false },    // it's not allowed to have a digit in the second level tld

                { "java@java@oracle.com", false },         // you cannot have @ twice in the address

                { "java!!!@example.com", false },          // you cannot the have special character '!' in the address

                { "mysite@.com", false },                  // tld cannot start with a dot

                { "javacodegees.com", false },             // must contain a @ character and a tld

                { ".javacodegees.com@at.com", false },     // the address cannot start with a dot

                { "javacodegees..javacom@at.com", false }, // you cannot have double dots in your address

                { "javacodegeeks@gmail.com",true },

                { "nikos+mylist@gmail.com", true },

                { "abc.efg-900@gmail-list.com", true },

                { "abc123@example.com.gr", true } };

        return Arrays.asList(data);
    }

    @Test
    public void test() {
        Boolean res = emailValidatorUseCase.validate(this.arg);
        String validv = (res) ? "valid" : "invalid";
        System.out.println("Hex Color Code "+arg+ " is " + validv);
        assertEquals("Result", this.expectedValidation, res);

    }

}
