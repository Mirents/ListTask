package listtask;

public class MyTask {
	private String description; // Описание задачи
	private int priority;       // Приоритет: от 1 до 4
	private int influence;      // Влияние: от 1 до 3
	
	// Конструктор класса с параметрами
	MyTask(String description, int priority, int influence) {
		this.description = description;
		this.priority = priority;
		this.influence = influence;
	}

	// Конструктор класса без параметров
	MyTask() {
		this.description = "";
		this.priority = 0;
		this.influence = 0;
	}
}
