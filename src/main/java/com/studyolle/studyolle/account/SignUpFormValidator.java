package com.studyolle.studyolle.account;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository;
/*
    public SignUpFormValidator(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

 */
    @Override
    public boolean supports(Class<?> aClass){
        return aClass.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object object, Errors errors){
        SignUpForm signUpForm = (SignUpForm)object;
        if(accountRepository.existsByEmail(signUpForm.getEmail())){
            errors.rejectValue("email","invalid.email",new Object[]{signUpForm.getEmail()}, "already using email");
        }
        if(accountRepository.existsByNickname(signUpForm.getNickname())){
            errors.rejectValue("nickname","invalid.nickname",new Object[]{signUpForm.getNickname()}, "already using nickname");
        }
    }

}
