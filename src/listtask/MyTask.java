package listtask;

public class MyTask {
	private String description; // Описание задачи
	private int priority;       // Приоритет: от 1 до 4
	private int influence;      // Влияние: от 1 до 3
	private boolean complete;   // Указатель выполнения задачи
	
	// Конструктор класса с параметрами
	MyTask(String description, int priority, int influence, boolean complete) {
		this.setDescription(description);
		this.setPriority(priority);
		this.setInfluence(influence);
		this.setComplete(complete);
	}

	// Конструктор класса без параметров
	MyTask() {
		this.setDescription("");
		this.setPriority(0);
		this.setInfluence(0);
		this.setComplete(false);
	}

	MyTask(MyTask taskTemp) {
		this.setDescription(taskTemp.getDescription());
		this.setInfluence(taskTemp.getInfluence());
		this.setPriority(taskTemp.getPriority());
		this.setComplete(taskTemp.isComplete());
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

	public boolean isComplete() {
		return this.complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	public void setAll(MyTask taskTemp) {
		this.setDescription(taskTemp.getDescription());
		this.setInfluence(taskTemp.getInfluence());
		this.setPriority(taskTemp.getPriority());
		this.setComplete(taskTemp.isComplete());
	}
}
