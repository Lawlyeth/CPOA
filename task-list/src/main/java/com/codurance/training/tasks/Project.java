package com.codurance.training.tasks;

import java.util.ArrayList;
import java.util.List;


public class Project {
	
	private String name;
	private List<Task> tasks;
	
	public Project(String pName){
		this.name = pName;
		this.tasks = new ArrayList();
	}
	
}
