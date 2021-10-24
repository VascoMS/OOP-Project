package ggc.core;

import java.util.*;

public abstract class Product{
    private double _maxPrice;
    private String _id;
    private ArrayList<Batch> _batches;
    private long _totalStock;

    Product(String id){
        _id=id;
    }
    
    public double getMaxPrice(){
        return _maxPrice;
    }

    public void updateMaxPrice(double maxPrice){
        _maxPrice=maxPrice;
    }

    public void addBatch(Batch batch) {
        _batches.add(batch);
        _totalStock += batch.getQuantity();
    }

    public String getId(){
        return _id;
    }

    public ArrayList<Batch> getBatches(){
        return _batches;
    }

    public long getTotalStock(){
        return _totalStock;
    }

    @Override
    public boolean equals(Object obj){
        return obj instanceof Product && _id.equals(((Product)obj).getId());
    }

    abstract int checkQuantity(int quantity, Partner partner);



}
