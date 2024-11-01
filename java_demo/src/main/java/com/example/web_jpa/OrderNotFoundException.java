package com.example.web_jpa;

class OrderNotFoundException extends RuntimeException {
    OrderNotFoundException(Long id) {
        super("Order with id " + id + " not found");
    }
}
