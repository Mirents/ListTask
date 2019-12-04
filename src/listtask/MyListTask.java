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
	
	// Смена задач местами для метода сортировки
	public void swapTask(MyTask TaskOne, MyTask TaskTwo) {
		MyTask TaskTemp = new MyTask(TaskOne);
		TaskOne.setAll(TaskTwo);
		TaskTwo.setAll(TaskTemp);
	}
	
	public void sortTask() {
		// Если главный метод - METHOD_PRIORITY
    	if (this.getSortMethod() == METHOD_PRIORITY) {
    		// Проходим по всему списку задач
			for(int i=0; i<this.elem-1; i++) {
				// Сначала сортируем задачи по главному методу
				for(int d=0; d<(this.elem-i-1); d++) {
					// Выделяем большее значение и меняем местами
					if(this.list[d].getPriority()>this.list[d+1].getPriority())
						swapTask(this.list[d], this.list[d+1]);
				}
				
				// Затем сортируем элементы по оставшемуся методу
				for(int z=0; z<this.elem-1;z++) {
					// Выделяем большее и меняем местами
					if(this.list[z].getPriority() == this.list[z+1].getPriority())
						if(this.list[z].getInfluence() > this.list[z+1].getInfluence())
							swapTask(this.list[z], this.list[z+1]);
				}
			}
		// В противном случае сортируем сначала по дополнительному методу, а затем по главному
		} else if(this.getSortMethod() == METHOD_INFLUENCE) {
			for(int i=0; i<this.elem-1; i++) {
				for(int d=0; d<(this.elem-i-1); d++) {
					if(this.list[d].getInfluence()>this.list[d+1].getInfluence())
						swapTask(this.list[d], this.list[d+1]);
				}
				
				for(int z=0; z<this.elem-1;z++) {
					if(this.list[z].getInfluence() == this.list[z+1].getInfluence())
						if(this.list[z].getPriority() > this.list[z+1].getPriority())
							swapTask(this.list[z], this.list[z+1]);
				}
			}
		}
	}
}
