/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvlima.javafx.doit;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author danilolima
 */
public class BindingTaskTest {
    
    public static void main(String[] args) {
        StringProperty lastNameProp = new SimpleStringProperty();
        StringProperty fistNameProp = new SimpleStringProperty();
        
        lastNameProp.set("dvlima");
        fistNameProp.set(" ;)");
        
        System.out.println(fistNameProp.get());
        System.out.println(lastNameProp.get());
        
        StringProperty fullNameProp = new SimpleStringProperty();
        fullNameProp.bind(Bindings.concat(fistNameProp, " ", lastNameProp));
        System.out.println(fullNameProp.get());
        
        IntegerProperty length = new SimpleIntegerProperty(18);
        IntegerProperty width = new SimpleIntegerProperty(18);
        
        IntegerProperty area = new SimpleIntegerProperty();
        area.bind(length.multiply(width));
        
        NumberBinding perimeter = length.add(width).multiply(2);
        
        System.out.println(area.get());
        System.out.println(perimeter.getValue());
    }
    
}
