package listtask;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

/* Принцип действия программы заключается в создании динамического массива LinkedList из класса MyTask.
 * Функция сортировки использует ключевые слова - по приоритете или влиянию.
 * В этой версии программы чтение и запись данных задач производится из файла.*/

public class MyListTask {
	private LinkedList<MyTask> listLL;
	private String sortMethod;
	
    final public static String METHOD_PRIORITY = "METHOD_PRIORITY";
    final public static String METHOD_INFLUENCE = "METHOD_INFLUENCE";
    final private String FILE_LIST_TASK = "fileListTask.txt"; // Константа файла с данными задач
    
    // Конструктор класса, создающий начальный массив из max количества элементов
    MyListTask() {
    	//this.setSortMethod(METHOD_INFLUENCE);
    	this.listLL = new LinkedList<MyTask>();
    	//this.openListTaskFromFile();
    }

    // Геттеры и Сеттеры переменной sortMethod
	public String getSortMethod() {
		return sortMethod;
	}

	public void setSortMethod(String sortMethod) {
		this.sortMethod = sortMethod;
	}
	
	// Добавление новой задачи
	public void addTask(String description, int priority,
			int influence, boolean complete) {
		this.listLL.add(new MyTask(description, priority, influence, complete));
	}
	
	public void addTask(String description, int priority,
			int influence, boolean complete, String dateTime) {
		this.listLL.add(new MyTask(description, priority, influence, complete, dateTime));
	}
	
	// Вывод всех задач на экран с приоритетным методом сортировки
	public void showAllTask() {
		System.out.println("This Sort Method: "+this.getSortMethod());
		for (int i=0; i<this.listLL.size(); i++)
			System.out.println("Priority " + this.listLL.get(i).getPriority() +
					" Influence " + this.listLL.get(i).getInfluence() +
					" - " + this.listLL.get(i).getDescription() +
					" - " + this.listLL.get(i).isComplete());
	}

	public String getAllText() {
		String text = "";
		for (int i=0; i<this.listLL.size(); i++)
			text += (i+1) + ". Priority " + this.listLL.get(i).getPriority() +
					" Influence " + this.listLL.get(i).getInfluence() +
					" - " + this.listLL.get(i).getDescription() +
					" - " + this.listLL.get(i).isComplete()+ "\n";
		return text;
	}

	// Смена задач местами для метода сортировки
	/*public void swapTask(MyTask TaskOne, MyTask TaskTwo) {
		MyTask TaskTemp = new MyTask(TaskOne);
		TaskOne.setAll(TaskTwo);
		TaskTwo.setAll(TaskTemp);
	}*/
	
	// Сортировка задач
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
	
	public void openListTaskFromFile(boolean isReopen) {
		// Создание переменной для чтения файла
		BufferedReader reader = null;
		try {
			// Создание файла и проверка его наличия
			File file = new File(this.FILE_LIST_TASK);
			if (file.exists()) file.createNewFile();

			if(isReopen) this.listLL.clear();
			
			// Если файл существует
			reader = new BufferedReader(new FileReader(this.FILE_LIST_TASK));
			String line;
			// Считать данные построчно, выделить строку и две цифры
			while ((line = reader.readLine()) != null) {
				String[] mass = line.split("\\|");
				if(mass[0].trim().equals(METHOD_INFLUENCE))
					this.setSortMethod(METHOD_INFLUENCE);
				else if(mass[0].trim().equals(METHOD_PRIORITY))
					this.setSortMethod(METHOD_PRIORITY);
				else {
					int priority = Integer.parseInt(mass[1].trim());
					int influence = Integer.parseInt(mass[2].trim());
					boolean complete = Integer.parseInt(mass[3].trim()) == 1 ? true : false;
					if (complete) {
						this.addTask(mass[0], priority, influence, complete, mass[4]);
					} else 
					// Добавить новую задачу
					this.addTask(mass[0], priority, influence, complete);
				}
			}
			// Завершить поток чтения
			reader.close();
		} catch(IOException e) {
			// TODO В случае изменения файла не обрабатывается это исключение,
			// а сразу выходит на исключение в файле ProgramListTask.java
			
			// В случае возникноения ошибки вывести ее в консоль
			System.out.println("Error " + e);
		}
	}
	
	public void safeListTaskInFile() {
		try {
			// Создание файла и его проверка
			File file = new File(this.FILE_LIST_TASK);
			if (file.exists()) file.createNewFile();
			
			// Создание буфера для записи данных
			PrintWriter pw = new PrintWriter(file);
			
			pw.println(this.getSortMethod()+"||");
			// Построчная запись данных в файл
			for(int i=0; i<this.listLL.size(); i++)
				pw.println(this.listLL.get(i).getDescription()+"|"+
						this.listLL.get(i).getPriority()+"|"+
						this.listLL.get(i).getInfluence()+"|"+
						(this.listLL.get(i).isComplete() ? (1 + "|" + this.listLL.get(i).getDateTime()) : 0));
			
			// Закрытие потока после использования
			pw.close();
			System.out.println("Create file and write data - DONE!");
		} catch(IOException e) {
			// В случае возникноения ошибки вывести ее в консоль
			System.out.println("Error " + e);
		}
	}
	
	public String deleteTask(int number) {
		try {
				return "Задача удалена: " + this.listLL.remove(number).getDescription();
			} catch(Exception e) {
				return e.toString();
			}
	}
	
	public String completeTask(int number, String dateTime) {
		try {
			    if(!this.listLL.get(number).isComplete()) {
			    	this.listLL.get(number).setComplete(true);
			    	this.listLL.get(number).setDateTimeIsComplete(dateTime);
			    	return "Задача выполнена";
			    }
			    else {
			    	this.listLL.get(number).setComplete(false);
			    	this.listLL.get(number).clearDateTimeIsComplete();
			    	return "Задача не выполнена";
			    }
			} catch(Exception e) {
				return e.toString();
			}
	}
}
