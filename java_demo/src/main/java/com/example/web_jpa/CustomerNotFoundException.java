package com.example.web_jpa;

class CustomerNotFoundException extends RuntimeException{
    CustomerNotFoundException(long id){
        super("Couldn't find customer" + id);
    }
}
