package ggc.core;

import java.io.Serializable;
import java.util.*;

public class Recipe implements Serializable{
    private ArrayList<Component> _components;
    private double _alpha; //agravamento

    public Recipe(double alpha, ArrayList<Component> components){
        _alpha=alpha;
        _components = new ArrayList<>(components);
    }
    
    public double getAlpha(){
        return _alpha;
    }

    public ArrayList<Component> getComponents(){
        return _components;
    }

    @Override
    public String toString(){
        String res= "";
        for(Component c: _components){
            if(res.compareTo("")!=0)
                res += "#";
            res+=c.toString();
        }
        return res;
    }
}
