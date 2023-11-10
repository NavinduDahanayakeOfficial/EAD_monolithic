package com.EAD.EAD_monolithic.Exception;

public class InventoryItemNotFoundException extends RuntimeException{
    public InventoryItemNotFoundException(String message) {
        super(message);
    }
}
