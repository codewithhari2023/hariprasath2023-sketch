package com.bookshopping.bookshopping.Exception;

public class InvalidUserException extends  RuntimeException{
    public  InvalidUserException(String message){
        super(message);
    }
}
