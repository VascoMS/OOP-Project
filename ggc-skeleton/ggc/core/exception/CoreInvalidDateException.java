package ggc.core.exception;

public class CoreInvalidDateException extends Exception{
    public CoreInvalidDateException(int days){
        super("Invalid date:"+days);
    
    }
}
