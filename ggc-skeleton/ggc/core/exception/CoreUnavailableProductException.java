package ggc.core.exception;

public class CoreUnavailableProductException extends Exception{
    private String _id;
    private int _available;
    public CoreUnavailableProductException(String id, int available){
        super(id);
        _available = available;
        _id = id;
    }

    public String getId(){
        return _id;
    }

    public int getAvailable(){
        return _available;
    }
}
