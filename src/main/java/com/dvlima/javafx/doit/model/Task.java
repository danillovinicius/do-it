/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvlima.javafx.doit.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author danilolima
 */
public class Task {
    
    private ObjectProperty<Integer> id = new SimpleObjectProperty<>(null) ;
    private StringProperty priority = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private ObjectProperty<Integer> progress = new SimpleObjectProperty<>(0) ;

    public Task() {
        
    }

    public Task(Integer id, String priority, String description, Integer progress) {
        setId(id);
        setDescription(description);
        setPriority(priority);
        setProgress(progress);
    }

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public String  getPriority() {
        return priority.get();
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Integer getProgress() {
        return progress.get();
    }

    public void setProgress(Integer progress) {
        this.progress.set(progress);
    }
    
    
    public ObjectProperty<Integer> idObjectProperty(){
        return id;
    }
    
    public StringProperty priorityStringProperty() {
        return priority;
    }
    
    public StringProperty descriptionStringProperty(){
        return description;
    }
    public ObjectProperty<Integer> progressObjectProperty  (){
        return progress;
    }
    
    
}
