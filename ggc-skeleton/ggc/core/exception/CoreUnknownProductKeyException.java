package ggc.core.exception;

public class CoreUnknownProductKeyException extends Exception {
    public CoreUnknownProductKeyException(String id){
        super("No Product with id:"+id+"found");
    }
}
