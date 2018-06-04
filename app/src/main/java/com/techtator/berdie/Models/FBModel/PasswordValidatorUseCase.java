package com.techtator.berdie.Models.FBModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuminakamura on 2018-03-27.
 */

public class PasswordValidatorUseCase {
    private static PasswordValidatorUseCase passwordValidatorUseCase = new PasswordValidatorUseCase();
    private static String pattern = null;

    private PasswordValidatorUseCase() {
    }

    /**
     *
     * (?=.*[a-z])      : This matches the presence of at least one lowercase letter.
     * (?=.*d)          : This matches the presence of at least one digit i.e. 0-9.
     * (?=.*[@#$%])     : This matches the presence of at least one special character.
     * ((?=.*[A-Z])     : This matches the presence of at least one capital letter.
     *  {6,16}          : This limits the length of password from minimum 6 letters to maximum 16 letters.
     */


    public static PasswordValidatorUseCase buildValidator( boolean specialChar,boolean capitalLetter,
                                                           boolean number,
                                                           int minLength,
                                                           int maxLength)
    {
        StringBuilder patternBuilder = new StringBuilder("((?=.*[a-z])");

        if (specialChar)
        {
            patternBuilder.append("(?=.*[@#$%!])");
        }

        if (capitalLetter)
        {
            patternBuilder.append("(?=.*[A-Z])");
        }

        if (number)
        {
            patternBuilder.append("(?=.*\\d)");
        }

        patternBuilder.append(".{" + minLength + "," + maxLength + "})");
        pattern = patternBuilder.toString();

        return passwordValidatorUseCase;
    }

    /**
     * Here we will validate the password
     * */
    public static boolean validatePassword(final String password)
    {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
