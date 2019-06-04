package com.example.to_do;

public class Item {
    private int id;
    private String task;
    private String date;
    private boolean isCompleted;
    private String priority;


    public Item() {
    }

    public Item(int id, String task, String date, boolean isCompleted, String priority) {
        this.id = id;
        this.task = task;
        this.date = date;
        this.isCompleted = isCompleted;
        this.priority = priority;
    }

    public Item(String task, String date, boolean isCompleted, String priority) {
        this.task = task;
        this.date = date;
        this.isCompleted = isCompleted;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
