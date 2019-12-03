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
    
    final static int NUM_TASK = 10;       // Статическое количество элементов задач
    
	public static void main(String[] args) {
		Description = new String[NUM_TASK];
		Priority = new int[NUM_TASK];
		Influence = new int[NUM_TASK];
		
		System.out.print("It`s Ok!");
	}

}
