package ggc.core;

import java.util.Comparator;


public class BatchComparator implements Comparator<Batch> {
    public int compare(Batch b1, Batch b2){
        if(b1.getProduct().getId().toUpperCase().equals(b2.getProduct().getId().toUpperCase())){
            if(b1.getPartner().getId().toUpperCase().equals((b2.getPartner().getId().toUpperCase()))){
                if(b1.getPrice()-b2.getPrice()==0){
                    return b1.getQuantity()-b2.getQuantity();
                }
                return (int)b1.getPrice() - (int)b2.getPrice();
            }
            return b1.getPartner().getId().toUpperCase().compareTo(b2.getPartner().getId().toUpperCase());
        }
        return b1.getProduct().getId().toUpperCase().compareTo(b2.getProduct().getId().toUpperCase());
    }
    
}


