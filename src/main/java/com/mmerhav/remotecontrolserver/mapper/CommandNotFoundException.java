package com.mmerhav.remotecontrolserver.mapper;

public class CommandNotFoundException extends RuntimeException {

    public CommandNotFoundException(String message) {
        super(message);
    }
}
