package listtask;


/* Version 0.04
 * Программа создает список задач в виде динамического массива LinkedList из классов MyTask и
 * сортирует их по выбранному параметру - приоритет или влияние.
 * Все данные о задачах считываются из файла.
 * Приоритет варьируется от 1 до 4 и основывается на матрице Эйзенхауэра, в которой значения
 * принимают следующий вид:
 * 1 - срочные и важные дела;
 * 2 - не срочные и важные дела;
 * 3 - срочные и неважные дела;
 * 4 - не срочные и неважные дела.
 * Вторая часть сортировки подразумевает три значения от 1 до 3. (К сожалению не нашел источника и названия этого
 * процесса) Они подразумевают следующее:
 * 1 - те дела и занятия, полностью зависящие от человека;
 * 2 - дела и занятия, частично зависящие от человека;
 * 3 - все то, что беспокоит, но при этом не зависит от человека.*/

public class ProgramListTask {
	static MyListTask listTask;
        
	public static void main(String[] args) {
		// Создание списка задач
		listTask = new MyListTask(MyListTask.METHOD_INFLUENCE);
		// Заполнение списка
		listTask.openListTaskFromFile();
		
		// Вывод на экран до сотировки
		System.out.println("Task under sort");
		listTask.showAllTask();
		
		// Вывод на экран после сортировки методом по умолчанию - METHOD_PRIORITY
		System.out.println("\nTask before sort");
		listTask.sortTask();
		listTask.showAllTask();
		
		// Вывод на экран после сортировки вторым методом - METHOD_INFLUENCE
		System.out.println("\nTask before sort");
		listTask.setSortMethod(MyListTask.METHOD_PRIORITY);
		listTask.sortTask();
		listTask.showAllTask();
	}
}
