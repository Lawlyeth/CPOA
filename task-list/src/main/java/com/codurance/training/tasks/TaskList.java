package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final Map<String, List<Task>> tasks = new LinkedHashMap<>();
    private final BufferedReader in;
    private final PrintWriter out;

    private long lastId = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
    }

    public void run() {
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            try {
				execute(command);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    private void execute(String commandLine) throws ParseException {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                show();
                break;
            case "add":
                add(commandRest[1]);
                break;
            case "check":
                check(commandRest[1]);
                break;
            case "uncheck":
                uncheck(commandRest[1]);
                break;
            case "help":
                help();
                break;
            case "delete":
            	delete(commandRest[1]);
            	break;
            case "deadline":
            	deadline(commandRest[1]);
            	break;
            default:
                error(command);
                break;
        }
    }
    
    //On change la deadline d'une tâche
    private void deadline(String string) throws ParseException {
		// TODO Auto-generated method stub
     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
   	 String[] subcommandRest = string.split(" ", 2);
    
         
         //deleteTask(projectTask[0], projectTask[1]);
         int id = Integer.parseInt(subcommandRest[0]);
         
         for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
             for (Task task : project.getValue()) {
                 if (task.getId() == id) {
                	 
                	 
                	 Date d = sdf.parse(subcommandRest[1]);
					out.println(d);
					//task.setDate(d);
					task.activateDeadline(true);
                     task.setDeadline(d);
                     
                 }
             }
         }
     

	}

	//On delete la tâche
    private void delete(String string) {
    	 String[] subcommandRest = string.split(" ", 2);
         String subcommand = subcommandRest[0];
         if (subcommand.equals("project")) {
             deleteProject(subcommandRest[1]);
         } else if (subcommand.equals("task")) {
             String[] projectTask = subcommandRest[1].split(" ", 2);
             deleteTask(projectTask[0], projectTask[1]);
         }
		//System.out.println("vivante!");
	}

	private void deleteTask(String project, String description) {
		List<Task> projectTasks = tasks.get(project);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
            return;
        }
        if(projectTasks.contains(description))
       {
        	
       
        projectTasks.remove(0);
        }
		
	}

	private void deleteProject(String name) {
		// TODO Auto-generated method stub
		//tasks.put(name, new ArrayList<Task>());
		tasks.remove(name);
		
	}

	
    private void show() {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            out.println(project.getKey());
            //System.out.println(project.getKey());
            for (Task task : project.getValue()) {
				out.printf("    [%s] %d: %s %s%n", (task.isDone() ? "x" : " "),
						task.getId(), task.getDescription(),
						(task.isDeadline() ? ",deadline : "+task.getDeadline().toString() : ""));
				//System.out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            
            out.println();
            
        }
    }
    
   


    private void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            addProject(subcommandRest[1]);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            
            
            addTask(projectTask[0], projectTask[1]);
        }
    }

    private void addProject(String name) {
    	
        tasks.put(name, new ArrayList<Task>());
    	
    }

    private void addTask(String project, String description) {
        List<Task> projectTasks = tasks.get(project);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
            return;
        }
        projectTasks.add(new Task(nextId(), description, null,false));
    }

    private void check(String idString) {
        setDone(idString, true);
    }

    private void uncheck(String idString) {
        setDone(idString, false);
    }

    private void setDone(String idString, boolean done) {
        int id = Integer.parseInt(idString);
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId() == id) {
                    task.setDone(done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %d.", id);
        out.println();
    }

    private void help() {
        out.println("Commands:");
        out.println("  show");
        out.println("  add project <project name>");
        out.println("  add task <project name> <task description>");
        out.println("  check <task ID>");
        out.println("  uncheck <task ID>");
        out.println("  delete project <project name>");
        out.println("  delete task <project name> <task description>");
        out.println();
    }

    private void error(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }

    private long nextId() {
        return ++lastId;
    }
}
