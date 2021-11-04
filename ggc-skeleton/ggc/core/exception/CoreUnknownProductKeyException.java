package ggc.core.exception;

public class CoreUnknownProductKeyException extends Exception {
    private String _id;
    public CoreUnknownProductKeyException(String id){
        super("No Product with id:"+id+"found");
        _id = id;
    }

    public String getId(){
        return _id;
    }
}
