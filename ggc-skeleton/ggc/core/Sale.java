package ggc.core;

public abstract class Sale extends Transaction{   

    Sale(int id, double baseValue, int quantity, Product product, Partner partner, int deadline){
        super(id, baseValue, quantity, product, partner, deadline);
    }
}
