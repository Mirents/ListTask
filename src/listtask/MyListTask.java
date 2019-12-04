package listtask;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/* Принцип действия программы заключается в создании динамического массива LinkedList из класса MyTask.
 * Функция сортировки использует ключевые слова - по приоритете или влиянию.*/

public class MyListTask {
	private LinkedList<MyTask> listLL;
	private String sortMethod;
	
    final public static String METHOD_PRIORITY = "METHOD_PRIORITY";
    final public static String METHOD_INFLUENCE = "METHOD_INFLUENCE";
    
    // Конструктор класса, создающий начальный массив из max количества элементов
    MyListTask(String sortMethod) {
    	this.setSortMethod(sortMethod);
    	this.listLL = new LinkedList<MyTask>();
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
		this.listLL.add(new MyTask(description, priority, influence));
	}
	
	// Вывод всех задач на экран с приоритетным методом сортировки
	public void showAllTask() {
		System.out.println("This Sort Method: "+this.getSortMethod());
		for (int i=0; i<this.listLL.size(); i++)
			System.out.println("Priority " + this.listLL.get(i).getPriority() +
					" Influence " + this.listLL.get(i).getInfluence() +
					" - " + this.listLL.get(i).getDescription());
	}
	
	// Смена задач местами для метода сортировки
	/*public void swapTask(MyTask TaskOne, MyTask TaskTwo) {
		MyTask TaskTemp = new MyTask(TaskOne);
		TaskOne.setAll(TaskTwo);
		TaskTwo.setAll(TaskTemp);
	}*/
	
	public void sortTask() {
		// Дополнительная сортировка, непонятно для чего нужна, без нее первый раз неправильно сортирует
		Collections.sort(listLL, new Comparator<MyTask>() {
			@Override
			public int compare(MyTask T1, MyTask T2)
			{
				return T1.getPriority() - T2.getPriority();
			}
	    });
		// Сортировка первым и вторым методом
    	if (this.getSortMethod() == METHOD_PRIORITY) {
    		Collections.sort(listLL, new Comparator<MyTask>() {
    			@Override
    			public int compare(MyTask T1, MyTask T2)
    			{
    				return T1.getPriority() - T2.getPriority();
    			}
    	    });
		} else if(this.getSortMethod() == METHOD_INFLUENCE) {
			Collections.sort(listLL, new Comparator<MyTask>() {
				@Override
				public int compare(MyTask T1, MyTask T2)
				{
					return T1.getInfluence() - T2.getInfluence();
				}
			});
		}
	}
}
