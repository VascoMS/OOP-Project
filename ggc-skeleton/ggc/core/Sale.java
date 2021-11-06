package ggc.core;

public abstract class Sale extends Transaction{
    Sale(int id, double baseValue, int quantity, Product product, Partner partner){
        super(id, baseValue, quantity, product, partner);

    }

    public abstract double calculatePayment(Date currentDate);
}
