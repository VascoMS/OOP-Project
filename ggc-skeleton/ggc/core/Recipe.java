package ggc.core;

import java.io.Serializable;
import java.util.*;

public class Recipe implements Serializable{
    private List<Component> _components;
    private double _alpha;

    public Recipe(double alpha, List<Component> components){
        _alpha=alpha;
        _components = new ArrayList<>(components);
    }
    
    public double getAlpha(){
        return _alpha;
    }

    public List<Component> getComponents(){
        return _components;
    }

    @Override
    public String toString(){
        String res = "";
        for(Component c: _components){
            if("".compareTo(res)!=0)
                res += "#";
            res+=c.toString();
        }
        return res;
    }
}
