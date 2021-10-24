package ggc.core;

import java.util.Comparator;

public class PartnerComparator implements Comparator<Partner>{
    public int compare(Partner p1,Partner p2){
        return p1.getId().toUpperCase().compareTo(p2.getId().toUpperCase());
    }
}
