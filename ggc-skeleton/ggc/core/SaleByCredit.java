package ggc.core;

public class SaleByCredit extends Sale {

    SaleByCredit(int id, double baseValue, int quantity, Product product, Partner partner, int deadline) {
        super(id, baseValue, quantity, product, partner, deadline);
        super.setAmountPaid(0);
    }

    boolean isPaid() {
        return super.getAmountPaid() != 0;
    }


    double calculatePayment(Date currentDate) {
        double discount = super.getPartner().computeDiscount(currentDate, getDeadline(), super.getProduct().getPeriodN());
        double fine = super.getPartner().computeFine(currentDate, getDeadline(), super.getProduct().getPeriodN());
        return super.getBaseValue() - (super.getBaseValue() * discount) + (super.getBaseValue() * fine);
    }

    void paySale(double payment, Date date) {
        setAmountPaid(payment);
        setPaymentDate(date.getDays());
        getPartner().updatePayedSales(payment);
        getPartner().updatePoints(date, this);
    }

    public String toString(Date date) {
        if (isPaid())
            return "VENDA" + "|" + super.getId() + "|" + super.getPartner().getId() + "|" + super.getProduct().getId()
                    + "|" + super.getQuantity() + "|" + Math.round(super.getBaseValue()) + "|"
                    + Math.round(super.getAmountPaid()) + "|" + getDeadline() + "|" + super.getPaymentDate();
        else
            return "VENDA" + "|" + super.getId() + "|" + super.getPartner().getId() + "|" + super.getProduct().getId()
                    + "|" + super.getQuantity() + "|" + Math.round(super.getBaseValue()) + "|" + Math.round(calculatePayment(date))
                    + "|" + getDeadline();
    }

}