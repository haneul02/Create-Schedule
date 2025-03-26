package com.example.demo.Iayered.exception;

public class IdException extends RuntimeException{
    public IdException() {
        super("해당 id가 없습니다.");
    }
}
