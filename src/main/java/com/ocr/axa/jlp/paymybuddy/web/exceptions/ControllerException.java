package com.ocr.axa.jlp.paymybuddy.web.exceptions;

public class ControllerException extends RuntimeException {
    
    public ControllerException(String s) {
        super("ERROR : " + s);
    }
}
