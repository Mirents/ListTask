package listtask;
/* Принцип действия программы заключается в создании статического класса MyListTask с массивом из max
 * количества элементов другого класса - MyTask.
 * Функция сортировки использует ключевые слова - по приоритете или влиянию.*/

public class MyListTask {
	private MyTask[] list;
	private int elem;
	private int max;
	private String sortMethod;
	
    final static String METHOD_PRIORITY = "METHOD_PRIORITY";
    final static String METHOD_INFLUENCE = "METHOD_INFLUENCE";
    
    // Конструктор класса, создающий начальный массив из maxколичества элементов
    MyListTask(int max, String sortMethod) {
    	this.list = new MyTask[max];
    	this.elem = 0;
    	this.max = max;
    	this.setSortMethod(sortMethod);
    }

    // Геттеры и Сеттеры переменной sortMethod
	public String getSortMethod() {
		return sortMethod;
	}

	public void setSortMethod(String sortMethod) {
		this.sortMethod = sortMethod;
	}
	
	// Добавление новой задачи
	public void addTask(String description, int priority, int influence) {
		this.list[this.elem] = new MyTask(description, priority, influence);
		this.elem++;
	}
	
	// Вывод всех задач на экран с приоритетным методом сортировки
	public void showAllTask() {
		System.out.println("This Sotr Merhod: "+this.getSortMethod());
		for (int i=0; i<this.elem; i++)
			System.out.println("Priority " + this.list[i].getPriority() +
					" Influence " + this.list[i].getInfluence() +
					" - " + this.list[i].getDescription());
	}
}
