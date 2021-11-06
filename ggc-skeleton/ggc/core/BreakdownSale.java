package ggc.core;

public class BreakdownSale extends Sale{

    BreakdownSale(int id, double baseValue, int quantity, Product product, Partner partner){
        super(id, baseValue, quantity, product, partner);
    }

    public boolean isPaid() {
        return true;
    }

    public double calculatePayment(Date date){
        return super.getBaseValue();
    }

}