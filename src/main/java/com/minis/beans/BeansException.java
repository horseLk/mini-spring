package com.minis.beans;

public class BeansException extends Exception {
    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(Exception e) {
        super(e);
    }
}
