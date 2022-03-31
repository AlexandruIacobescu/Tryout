package com.example.tryout;

public class EmptyDatabaseCommandException extends Exception{
    EmptyDatabaseCommandException(String errorMessage){
        super(errorMessage);
    }
}
