package ggc.core;

import java.util.*;

public abstract class Product{
    private double _maxPrice;
    private String _id;
    private ArrayList<Batch> _batches;
    private long _totalStock;

    Product(String id){
        _id=id;
        _batches = new ArrayList<Batch>();
    }
    
    public double getMaxPrice(){
        return _maxPrice;
    }

    public void updateMaxPrice(){
        for(Batch batch: _batches){
            if(batch.getPrice() > _maxPrice)
                _maxPrice = batch.getPrice();
        }
    }

    public void addBatch(Batch batch) {
        _batches.add(batch);
        _totalStock += batch.getQuantity();
        updateMaxPrice();
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
