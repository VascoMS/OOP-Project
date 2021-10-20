package ggc.core;

import java.util.*;

public abstract class Product{
    private double _maxPrice;
    private String _id;
    private ArrayList<Batch> _batches;

    Product(String id){
        _id=id;
    }
    
    public double getMaxPrice(){
        return _maxPrice;
    }

    public String getId(){
        return _id;
    }

    public ArrayList<Batch> getBatches(){
        return _batches;
    }

    @Override
    public String toString(){
        return ""; //FIXME 
    }

    abstract boolean checkQuantity(int quantity, Partner partner);



}
