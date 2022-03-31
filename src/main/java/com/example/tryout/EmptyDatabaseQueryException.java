package com.example.tryout;

public class EmptyDatabaseQueryException extends Exception{
    public EmptyDatabaseQueryException(String errorMessage){
        super(errorMessage);
    }
}
