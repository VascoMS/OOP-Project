package ggc.core;

public class SaleByCredit extends Sale {
    private double _amountOwed;

    SaleByCredit(int id, double baseValue, int quantity, Product product, Partner partner, int deadline) {
        super(id, baseValue, quantity, product, partner, deadline);
        _amountOwed = baseValue;
        super.setAmountPaid(0);
    }

    public boolean isPaid() {
        return super.getAmountPaid() != 0;
    }

    public void updateAmountOwed(Date currentDate) {
        _amountOwed = calculatePayment(currentDate);
    }

    public void setAmountOwed(double amount) {
        _amountOwed = amount;
    }

    public double getAmountOwed() {
        return _amountOwed;
    }

    public double calculatePayment(Date currentDate) {
        double discount = super.getPartner().computeDiscount(currentDate, getDeadline(), super.getProduct().getPeriodN());
        double fine = super.getPartner().computeFine(currentDate, getDeadline(), super.getProduct().getPeriodN());
        double payment = super.getBaseValue() + super.getBaseValue() * discount + super.getBaseValue() * fine;
        return payment;
    }

    public void paySale(double payment, Date date) {
        setAmountOwed(0);
        setAmountPaid(payment);
        setPaymentDate(date);
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