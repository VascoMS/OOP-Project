package ggc.core.exception;

public class CoreUnknownTransactionKeyException extends Exception {
    public CoreUnknownTransactionKeyException(int id){
        super("No Transaction with id:"+id+"found");
    }
}