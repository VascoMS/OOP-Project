package ggc.core.exception;

public class CoreDuplicatePartnerKeyException extends Exception{
    public CoreDuplicatePartnerKeyException(String id){
        super("Partner with id:"+ id + "already exists");
    }
}
