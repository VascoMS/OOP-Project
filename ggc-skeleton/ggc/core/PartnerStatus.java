package ggc.core;

public interface PartnerStatus {
    public double computeFine(Date currentDate, Date deadline, int period);
    public double computeDiscount(Date currentDate, Date deadline, int period);
    public double computePoints(Date currentDate, Sale transaction, Partner partner);
}
