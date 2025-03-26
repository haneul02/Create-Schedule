package com.example.demo.Iayered.exception;

public class PasswordIncorrectException extends RuntimeException {
    public PasswordIncorrectException() {
        super("비밀번호 불일치");
    }
}
