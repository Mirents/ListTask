package listtask;

public class MyTask {
	private String description; // Описание задачи
	private int priority;       // Приоритет: от 1 до 4
	private int influence;      // Влияние: от 1 до 3
	
	// Конструктор класса с параметрами
	MyTask(String description, int priority, int influence) {
		this.setDescription(description);
		this.setPriority(priority);
		this.setInfluence(influence);
	}

	// Конструктор класса без параметров
	MyTask() {
		this.setDescription("");
		this.setPriority(0);
		this.setInfluence(0);
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
}
