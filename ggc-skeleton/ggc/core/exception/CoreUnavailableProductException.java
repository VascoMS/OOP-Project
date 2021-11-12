package ggc.core.exception;

public class CoreUnavailableProductException extends Exception{
    private String _id;
    private int _available;
    private int _amount;
    public CoreUnavailableProductException(String id, int available, int amount){
        super(id);
        _available = available;
        _id = id;
        _amount = amount;
    }

    public String getId(){
        return _id;
    }

    public int getAmount(){
        return _amount;
    }

    public int getAvailable(){
        return _available;
    }
}
