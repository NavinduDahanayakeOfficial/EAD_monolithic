package com.EAD.EAD_monolithic.Exception;


public class EmailAlreadyExistException extends RuntimeException{

    public EmailAlreadyExistException(String message) {super(message);}

    public EmailAlreadyExistException(String message, Throwable cause) {super(message,cause);}
}
