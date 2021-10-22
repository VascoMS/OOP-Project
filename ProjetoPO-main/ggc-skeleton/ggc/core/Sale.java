package ggc.core;

public abstract class Sale extends Transaction{
    Sale(int id, Date paymentDate, double baseValue, int quantity, Product product, Partner partner){
        super(id, paymentDate, baseValue, quantity, product, partner);

    }
}
