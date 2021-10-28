package ggc.core.exception;

public class CoreUnknownPartnerKeyException extends Exception{
    public CoreUnknownPartnerKeyException(String id){
        super("No Partner with id:"+id+"found");
    }
}
