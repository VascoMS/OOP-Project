package ggc.core;

import java.util.Comparator;


public class BatchPriceComparator implements Comparator<Batch> {
    public int compare(Batch b1, Batch b2){
        if(b1.getPrice()-b2.getPrice()==0){
            return b1.getQuantity()-b2.getQuantity();
        }
        return (int)b1.getPrice() - (int)b2.getPrice();
    }
}