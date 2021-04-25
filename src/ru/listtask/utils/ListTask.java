package ru.listtask.utils;

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

public class ListTask {
	private LinkedList<Task> listTask;
	private String sortMethod;
	
    final public static String METHOD_PRIORITY = "METHOD_PRIORITY";
    final public static String METHOD_INFLUENCE = "METHOD_INFLUENCE";
    final private String FILE_LIST_TASK = "fileListTask.txt"; // Константа файла с данными задач
    
    // Конструктор класса, создающий начальный массив из max количества элементов
    public ListTask() {
    	this.listTask = new LinkedList<>();
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
		this.listTask.add(new Task(description, priority, influence, complete));
	}
	
	public void addTask(String description, int priority,
			int influence, boolean complete, String dateTime) {
		this.listTask.add(new Task(description, priority, influence, complete, dateTime));
	}
	
	// Вывод всех задач на экран с приоритетным методом сортировки
	public void showAllTask() {
		System.out.println("This Sort Method: "+this.getSortMethod());
		for (int i=0; i<this.listTask.size(); i++)
			System.out.println("Priority " + this.listTask.get(i).getPriority() +
					" Influence " + this.listTask.get(i).getInfluence() +
					" - " + this.listTask.get(i).getDescription() +
					" - " + this.listTask.get(i).isComplete());
	}

	public String getAllText() {
		String text = "";
		for (int i=0; i<this.listTask.size(); i++)
			text += (i+1) + ". " + this.listTask.get(i).getDescription() +
			(this.listTask.get(i).isComplete() ? (" - YES " + this.listTask.get(i).getTime() + " " +
					this.listTask.get(i).getDate()) : " NO") + "\n";
		return text;
	}
	
	// Сортировка задач
	public void sortTask() {
		// Дополнительная сортировка, непонятно для чего нужна, без нее первый раз неправильно сортирует
		Collections.sort(listTask, new Comparator<Task>() {
			@Override
			public int compare(Task T1, Task T2)
			{
				return T1.getPriority() - T2.getPriority();
			}
	    });
        // Сортировка первым и вторым методом
    	if (this.getSortMethod() == METHOD_PRIORITY) {
    		Collections.sort(listTask, new Comparator<Task>() {
    			@Override
    			public int compare(Task T1, Task T2)
    			{
    				return T1.getPriority() - T2.getPriority();
    			}
    	    });
		} else if(this.getSortMethod() == METHOD_INFLUENCE) {
			Collections.sort(listTask, new Comparator<Task>() {
				@Override
				public int compare(Task T1, Task T2)
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

			if(isReopen) this.listTask.clear();
			
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
			
			// В случае возникновения ошибки вывести ее в консоль
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
			for(int i=0; i<this.listTask.size(); i++)
				pw.println(this.listTask.get(i).getDescription()+"|"+
						this.listTask.get(i).getPriority()+"|"+
						this.listTask.get(i).getInfluence()+"|"+
						(this.listTask.get(i).isComplete() ? (1 + "|" + this.listTask.get(i).getDateTime()) : 0));
			
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
				return "Задача удалена: " + this.listTask.remove(number).getDescription();
			} catch(Exception e) {
				return e.toString();
			}
	}
	
	public String completeTask(int number, String dateTime) {
		try {
			    if(!this.listTask.get(number).isComplete()) {
			    	this.listTask.get(number).setComplete(true);
			    	this.listTask.get(number).setDateTimeIsComplete(dateTime);
			    	return "Задача выполнена";
			    }
			    else {
			    	this.listTask.get(number).setComplete(false);
			    	this.listTask.get(number).clearDateTimeIsComplete();
			    	return "Задача не выполнена";
			    }
			} catch(Exception e) {
				return e.toString();
			}
	}
}
