package ggc.core;

public interface PartnerStatus {
    double computeFine(Date currentDate, Date deadline, int period);
    double computeDiscount(Date currentDate, Date deadline, int period);
    double computePoints(Date currentDate, Sale transaction, Partner partner);
}
