package com.techtator.berdie.Models.FBModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidatorUseCase {
    private Pattern pattern;

    private  static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidatorUseCase(){
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public boolean validate(final String email){
        Matcher m = pattern.matcher(email);
        return m.matches();
    }
}
