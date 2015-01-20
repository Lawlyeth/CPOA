package com.codurance.training.tasks;

import java.util.Date;

public final class Task {
    private final long id;
    private final String description;
    private boolean done;
    private Date deadline;
    private Date date;
    
    //Contructeur
    
    public Task(long pId, String pDescription, Date pDeadline) {
        this.id = pId;
        this.description = pDescription;
        this.deadline = pDeadline;
        this.done = false;
        this.date = new Date();
    }
    
    //Getters

    public long getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }
    
    public Date getDeadline(){
    	return this.deadline;
    }
    
    public Date getDate(){
    	return this.date;
    }

    public boolean isDone() {
        return this.done;
    }
    
    //Setters
    
    public void setDone(boolean done) {
        this.done = done;
        
    }
    
    public void setDeadline(Date pDeadline){
    	this.deadline = pDeadline;
    }
    
    public void setDate(Date pDate){
    	this.date = pDate;
    }
}
