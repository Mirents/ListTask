package listtask;
/* Программа создает список задач и сортирует их по выбранному параметру - приоритет или влияние.
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
    static String[] Description; // Список задач
    static int[] Priority;       // Приоритет каждой задачи, от 1 до 4
    static int[] Influence;      // Влияние каждой задачи, от 1 до 3
    static String SortMethod;
    
    final static int NUM_TASK = 6;       // Статическое количество элементов задач
    final static String METHOD_PRIORITY = "METHOD_PRIORITY";
    final static String METHOD_INFLUENCE = "METHOD_INFLUENCE";
    
    // Добавляет по номеру NumTask описание и другие характеристики задачи
    public static void setTask(int NumTask, String Description, int Priority, int Influence) {
    	ProgramListTask.Description[NumTask] = Description;
    	ProgramListTask.Priority[NumTask] = Priority;
    	ProgramListTask.Influence[NumTask] = Influence;
    }
    
    public static void swapTask(int NumTask1, int NumTask2) {
    	// Временные переменные для сохранения значения
    	String D;
    	int P, I;
    	// Сохранения значения во временных переменных
    	D = ProgramListTask.Description[NumTask1];
    	P = ProgramListTask.Priority[NumTask1];
    	I = ProgramListTask.Influence[NumTask1];
    	
    	// Перенос данных из изменяемого в изменяемое значения
    	ProgramListTask.Description[NumTask1] = ProgramListTask.Description[NumTask2];
    	ProgramListTask.Priority[NumTask1] = ProgramListTask.Priority[NumTask2];
    	ProgramListTask.Influence[NumTask1] = ProgramListTask.Influence[NumTask2];
    	
    	// Сохранение временных переменных в перенесенной задаче
    	ProgramListTask.Description[NumTask2] = D;
    	ProgramListTask.Priority[NumTask2] = P;
    	ProgramListTask.Influence[NumTask2] = I;
    }
    
	public static void main(String[] args) {
		// Создание переменных
		Description = new String[NUM_TASK];
		Priority = new int[NUM_TASK];
		Influence = new int[NUM_TASK];
		
		// Заполнение массива заначениями
		setTask(0, "Read a book.", 2, 1);
		setTask(1, "Go to the shop.", 1, 3);
		setTask(2, "Play with the dog.", 2, 3);
		setTask(3, "Clean the conate.", 1, 1);
		setTask(4, "Collect old things.", 3, 2);
		setTask(5, "Mow the lawn.", 4, 3);
		SortMethod = METHOD_PRIORITY;
		
		if (SortMethod == METHOD_PRIORITY) {
			for(int i=0; i<NUM_TASK-1; i++) {
				for(int d=0; d<(NUM_TASK-i-1); d++) {
					if(Priority[d]>Priority[d+1]) swapTask(d, d+1);
				}
				
				for(int z=0; z<NUM_TASK-1;z++) {
					if(Priority[z] == Priority[z+1])
						if(Influence[z] > Influence[z+1]) swapTask(z, z+1);
				}
			}
		} else {
			for(int i=0; i<NUM_TASK-1; i++) {
				for(int d=0; d<(NUM_TASK-i-1); d++) {
					if(Influence[d]>Influence[d+1]) swapTask(d, d+1);
				}
				
				for(int z=0; z<NUM_TASK-1;z++) {
					if(Influence[z] == Influence[z+1])
						if(Priority[z] > Priority[z+1]) swapTask(z, z+1);
				}
			}
		}
		
		for(int i=0;i<NUM_TASK;i++) {
			System.out.println(Priority[i] + "-" + Influence[i] + "-" + Description[i]);
		}
		
		// Тестовый вывод строки на экран
		System.out.print("It`s Ok!");
	}

}
