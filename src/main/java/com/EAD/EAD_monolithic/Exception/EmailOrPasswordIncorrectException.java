package com.EAD.EAD_monolithic.Exception;

public class EmailOrPasswordIncorrectException extends RuntimeException{

    public EmailOrPasswordIncorrectException(String message) {super(message);}

    public EmailOrPasswordIncorrectException(String message, Throwable cause) {super(message,cause);}
}
