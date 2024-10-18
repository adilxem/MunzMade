package com.adil.MunzMade.Helper;

public class ResourceNotFoundExcepiton extends RuntimeException {

    public ResourceNotFoundExcepiton (String message) {

        super(message);

    }

    public ResourceNotFoundExcepiton () {

        super("Resource Not Found");
    }

}
