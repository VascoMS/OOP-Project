package ggc.core;

import java.util.*;

public class Recipe {
    private ArrayList<Component> _components;
    private double _alpha; //agravamento

    public Recipe(double alpha, ArrayList<Component> components){
        _alpha=alpha;
        _components = new ArrayList<>(components);
    }
    
    public String toString(){
        return ""; //FIXME
    }
}
