package com.codurance.training.tasks;

import java.util.Date;

public final class Task {
    private final long id;
    private  final String description;
    private boolean done;
    private Date deadline;
    private boolean bdeadline;
   
    
    //Contructeur
    
    public Task(long pId, String pDescription, Date pDeadline,boolean bdeadline) {
        this.id = pId;
        this.description = pDescription;
        this.deadline = pDeadline;
        this.bdeadline=bdeadline;
        this.done = false;
       
    }
    
    //Getters

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
      
    }
    
    public Date getDeadline(){
    	System.out.println("lol" +deadline +"lol");
    	return deadline;
    }
    public boolean isDeadline() {
    	System.out.println("lol" +bdeadline +"lol");
        return bdeadline;
    }
    
   

    public boolean isDone() {
        return this.done;
    }
    
    //Setters
    
    public void setDone(boolean done) {
        this.done = done;
        
    }
    
  
    
    public void activateDeadline(boolean bdeadline){
    	this.bdeadline = bdeadline;
    }
    public void setDeadline(Date pDeadline){
    	this.deadline = pDeadline;
    }
    
 
}
