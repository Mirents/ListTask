package ru.listtask.utils;

public final class Task {
    private String description; // Описание задачи
    private int priority;       // Приоритет: от 1 до 4
    private int influence;      // Влияние: от 1 до 3
    private boolean complete;   // Указатель выполнения задачи
    private int HH=0, mm=0, dd=0, MM=0, yy=0;

    // Конструктор класса с параметрами
    Task(String description, int priority, int influence, boolean complete) {
        this.setDescription(description);
        this.setPriority(priority);
        this.setInfluence(influence);
        this.setComplete(complete);
    }

    Task(String description, int priority, int influence, boolean complete, String dateTime) {
        this.setDescription(description);
        this.setPriority(priority);
        this.setInfluence(influence);
        this.setComplete(complete);
        this.setDateTimeIsComplete(dateTime);
    }

    // Конструктор класса без параметров
    Task() {
        this.setDescription("");
        this.setPriority(0);
        this.setInfluence(0);
        this.setComplete(false);
    }

    Task(Task taskTemp) {
        this.setDescription(taskTemp.getDescription());
        this.setInfluence(taskTemp.getInfluence());
        this.setPriority(taskTemp.getPriority());
        this.setComplete(taskTemp.getComplete());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getInfluence() {
        return influence;
    }

    public void setInfluence(int influence) {
        this.influence = influence;
    }

    public boolean getComplete() {
        return this.complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void setAll(Task taskTemp) {
        this.setDescription(taskTemp.getDescription());
        this.setInfluence(taskTemp.getInfluence());
        this.setPriority(taskTemp.getPriority());
        this.setComplete(taskTemp.getComplete());
    }

    public void setDateTimeIsComplete(String dateTime) {
        String[] mass = dateTime.split("/");
        HH = Integer.parseInt(mass[0].trim());
        mm = Integer.parseInt(mass[1].trim());
        dd = Integer.parseInt(mass[2].trim());
        MM = Integer.parseInt(mass[3].trim());
        yy = Integer.parseInt(mass[4].trim());
    }

    public void clearDateTimeIsComplete() {
        HH = 0;
        mm = 0;
        dd = 0;
        MM = 0;
        yy = 0;
    }

    public String getDateTime() {
        return this.HH + "/" + this.mm + "/" + this.dd + "/" + this.MM + "/" + this.yy;
    }

    public String getDate() {
        return this.dd + "." + this.MM + "." + this.yy;
    }

    public String getTime() {
        return this.HH + "." + this.mm;
    }
}
