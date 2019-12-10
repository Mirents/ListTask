package listtask;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

public class MyGUI extends JFrame {
	private MyListTask listTask; // Список задач
	private JTextPane textPane;  // Текстовое поле для вывода списка
	private JButton loadFromFileButton; // Кнопка загрузки списка из файла
	private JButton deleteTaskButton;   // Кнопка удаления задачи из списка
	private JButton addTaskButton;      // Кнопка добавления задачи в список
	private JButton startTimerButton;
	private JButton pauseTimerButton;
	private JButton safeTaskToFileButton;
	private JButton resetTimerButton;
	private JPanel contentPane;  // Основное полотно для элементов управления
	private ButtonEventListener buttonEventListener; // Обработчик событий нажатия кнопок
	private JTextField numberDeleteTaskTextField; // Текстовое поле для ввода номера удаляемого поля
	private JTextField newTaskDescriptionTextField; // Текстовое поле для описания новой задачи
	private ButtonGroup methodSortRadioButtonGroup;
	private ButtonGroup influenceRadioButtonGroup;
	private ButtonGroup priorityRadioButtonGroup;
	JButton completeButton;
	
	JLabel timerLabel;
	private Timer timer;
	private int countTimerSecond = 0;
	// Время работы, время короткого перерыва, время длинного перерыва
	private int[] schemeTimerMinute = {25, 5, 30, 3};
	private int thisPeriodTimer = 0;
	private int circleWorkTimer = 0;
	private JRadioButton setSortPriorityRadioButton;
	private JRadioButton setSortInfluenceRadioButton;
	
	// Группа переключателей для выбора параметра Intluence
	JRadioButton influenceOneRadioButton;
	JRadioButton influenceTwoRadioButton;
	JRadioButton influenceTreeRadioButton;
	
	// Группа переключателей для выбора параметра Priority
	JRadioButton priorityOneRadioButton;
	JRadioButton priorityTwoRadioButton;
	JRadioButton priorityThreeRadioButton;
	JRadioButton priorityFourRadioButton;
	
	// Переменные для создания новой записи
	int priorityAddNewTask, influenceAddNewTask;
	String descriptionAddNewTask = null;
	private JScrollPane textScroll;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JTable table;
	
	public MyGUI() {		
		super("ListTask");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setBounds(100, 100, 390, 450);
		this.setSize(510, 508);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		textPane = new JTextPane();
		//textPane.setBounds(5, 30, 500, 150);
		textScroll = new JScrollPane(textPane);
		textScroll.setBounds(5, 30, 500, 150);
		contentPane.add(textScroll);
		//contentPane.add(textPane);

		numberDeleteTaskTextField = new JTextField();
		numberDeleteTaskTextField.setBounds(430, 215, 48, 25);
		contentPane.add(numberDeleteTaskTextField);
		numberDeleteTaskTextField.setColumns(10);
		
		newTaskDescriptionTextField = new JTextField();
		newTaskDescriptionTextField.setBounds(5, 215, 200, 25);
		contentPane.add(newTaskDescriptionTextField);
		newTaskDescriptionTextField.setColumns(10);
		
		loadFromFileButton = new JButton("Загрузить");
		loadFromFileButton.setBounds(311, 284, 194, 25);
		contentPane.add(loadFromFileButton);
		
		deleteTaskButton = new JButton("Удалить задачу");
		deleteTaskButton.setBounds(210, 215, 187, 25);
		contentPane.add(deleteTaskButton);
		
		addTaskButton  = new JButton("Добавить задачу");
		addTaskButton.setBounds(5, 185, 200, 25);
		contentPane.add(addTaskButton);
		
		safeTaskToFileButton = new JButton("Сохранить");
		safeTaskToFileButton.setBounds(311, 252, 194, 25);
		contentPane.add(safeTaskToFileButton);

		// 
		priorityRadioButtonGroup = new ButtonGroup();
		
		priorityOneRadioButton = new JRadioButton("Важно и срочно");
		priorityOneRadioButton.setBounds(5, 264, 154, 23);
		priorityRadioButtonGroup.add(priorityOneRadioButton);
		contentPane.add(priorityOneRadioButton);
		priorityOneRadioButton.setSelected(true);
		priorityAddNewTask = 1;
		
		priorityTwoRadioButton = new JRadioButton("Важно и несрочно");
		priorityTwoRadioButton.setBounds(156, 264, 154, 23);
		priorityRadioButtonGroup.add(priorityTwoRadioButton);
		contentPane.add(priorityTwoRadioButton);
		
		priorityThreeRadioButton = new JRadioButton("Срочно и неважно");
		priorityThreeRadioButton.setBounds(5, 285, 151, 23);
		priorityRadioButtonGroup.add(priorityThreeRadioButton);
		contentPane.add(priorityThreeRadioButton);
		
		priorityFourRadioButton = new JRadioButton("Несрочно и неважно");
		priorityFourRadioButton.setBounds(156, 285, 154, 23);
		priorityRadioButtonGroup.add(priorityFourRadioButton);
		contentPane.add(priorityFourRadioButton);
		
		// 
		influenceRadioButtonGroup = new ButtonGroup();
		
		influenceOneRadioButton = new JRadioButton("Зависит");
		influenceOneRadioButton.setBounds(5, 239, 80, 23);
		influenceRadioButtonGroup.add(influenceOneRadioButton);
		contentPane.add(influenceOneRadioButton);
		influenceOneRadioButton.setSelected(true);
		influenceAddNewTask = 1;
		
		influenceTwoRadioButton = new JRadioButton("Мало зависит");
		influenceTwoRadioButton.setBounds(84, 239, 119, 23);
		influenceRadioButtonGroup.add(influenceTwoRadioButton);
		contentPane.add(influenceTwoRadioButton);
		
		influenceTreeRadioButton = new JRadioButton("Не зависит");
		influenceTreeRadioButton.setBounds(207, 239, 100, 23);
		influenceRadioButtonGroup.add(influenceTreeRadioButton);
		contentPane.add(influenceTreeRadioButton);
		
		// Создание списка задач
		listTask = new MyListTask();

		// Заполнение списка
		listTask.openListTaskFromFile(false);
		textPane.setText(listTask.getAllText());
		
		//
		methodSortRadioButtonGroup = new ButtonGroup();
		
		setSortPriorityRadioButton = new JRadioButton("Важность/срочность");
		setSortPriorityRadioButton.setBounds(320, 5, 166, 23);
		methodSortRadioButtonGroup.add(setSortPriorityRadioButton);
		contentPane.add(setSortPriorityRadioButton);
		setSortPriorityRadioButton.setSelected((listTask.getSortMethod().equals(MyListTask.METHOD_PRIORITY)) ?
				true : false);
		
		setSortInfluenceRadioButton = new JRadioButton("Влияние");
		setSortInfluenceRadioButton.setBounds(230, 5, 84, 23);
		methodSortRadioButtonGroup.add(setSortInfluenceRadioButton);
		contentPane.add(setSortInfluenceRadioButton);
		setSortInfluenceRadioButton.setSelected((listTask.getSortMethod().equals(MyListTask.METHOD_INFLUENCE)) ?
				true : false);
		
		// 
		startTimerButton = new JButton("Start");
		startTimerButton.setBounds(122, 320, 114, 25);
		contentPane.add(startTimerButton);
		
		pauseTimerButton = new JButton("Pause");
		pauseTimerButton.setBounds(257, 320, 114, 25);
		contentPane.add(pauseTimerButton);
		
		timerLabel = new JLabel("New label");
		timerLabel.setBounds(60, 320, 60, 25);
		timerLabel.setText("");
		contentPane.add(timerLabel);
		
		resetTimerButton = new JButton("Reset");
		resetTimerButton.setBounds(391, 320, 114, 25);
		contentPane.add(resetTimerButton);
		
		completeButton = new JButton("Отметить выполнение");
		completeButton.setBounds(210, 185, 187, 25);
		contentPane.add(completeButton);

		countTimerSecond = schemeTimerMinute[0]*60;
		timerLabel.setText(minuteToHour(countTimerSecond));
		TimerListener timerListener = new TimerListener();
		timer = new Timer(1000, timerListener);
		timer.stop();
		
		// 
		buttonEventListener = new ButtonEventListener();
		loadFromFileButton.addActionListener(buttonEventListener);
		deleteTaskButton.addActionListener(buttonEventListener);
		addTaskButton.addActionListener(buttonEventListener);
		safeTaskToFileButton.addActionListener(buttonEventListener);
		setSortPriorityRadioButton.addActionListener(buttonEventListener);
		setSortInfluenceRadioButton.addActionListener(buttonEventListener);
		priorityOneRadioButton.addActionListener(buttonEventListener);
		priorityTwoRadioButton.addActionListener(buttonEventListener);
		priorityThreeRadioButton.addActionListener(buttonEventListener);
		priorityFourRadioButton.addActionListener(buttonEventListener);
		influenceOneRadioButton.addActionListener(buttonEventListener);
		influenceTwoRadioButton.addActionListener(buttonEventListener);
		influenceTreeRadioButton.addActionListener(buttonEventListener);
		startTimerButton.addActionListener(buttonEventListener);
		pauseTimerButton.addActionListener(buttonEventListener);
		resetTimerButton.addActionListener(buttonEventListener);
		completeButton.addActionListener(buttonEventListener);
		
		//timer = new Timer(1000, buttonEventListener);
		this.setContentPane(contentPane);
		
		JLabel label = new JLabel("Список задач");
		label.setBounds(10, 5, 95, 23);
		contentPane.add(label);
		
		label_1 = new JLabel("Сортировка:");
		label_1.setBounds(140, 5, 95, 23);
		contentPane.add(label_1);
		
		label_2 = new JLabel("Номер задачи:");
		label_2.setBounds(400, 185, 105, 25);
		contentPane.add(label_2);
		
		label_3 = new JLabel("Таймер");
		label_3.setBounds(5, 320, 55, 25);
		contentPane.add(label_3);
		
		Object[][] array = new String[][] {{ "Сахар" , "кг", "1.5" },
            { "Мука"  , "кг", "4.0" },
            { "Молоко", "л" , "2.2" }, { "Молоко", "л" , "2.2" }, { "Молоко", "л" , "2.2" },
            { "Молоко", "л" , "2.2" }, { "Молоко", "л" , "2.2" }, { "Молоко", "л" , "2.2" }};
		Object[] columnsHeader = new String[] {"Наименование", "Ед.измерения", 
        "Количество"};
		table = new JTable(array, columnsHeader);
		JScrollPane tableScroll = new JScrollPane(table);
		tableScroll.setBounds(5, 350, 500, 110);
		contentPane.add(tableScroll);
	}
	
	// TODO Сделать более лаконичную формулу
	public String minuteToHour(int minute) {
		String s1 = "", s2 = "";
		if((minute/60)%60<10) s1 = "0";
		if((minute%60)<10) s2 = "0";
		return s1+((minute/60)%60)+":"+s2+(minute%60);
	}
	
	class ButtonEventListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
			if (e.getSource() == loadFromFileButton) {
				listTask.openListTaskFromFile(true);
				listTask.sortTask();
				textPane.setText(listTask.getAllText());
				if(listTask.getSortMethod().equals(MyListTask.METHOD_INFLUENCE)) setSortInfluenceRadioButton.setSelected(true);
				else setSortPriorityRadioButton.setSelected(true);
			}
			if (e.getSource() == deleteTaskButton) {
				int num = Integer.parseInt(numberDeleteTaskTextField.getText());
				JOptionPane.showMessageDialog(null, listTask.deleteTask(num-1));
				numberDeleteTaskTextField.setText("");
				listTask.sortTask();
				textPane.setText(listTask.getAllText());
			}
			if (e.getSource() == addTaskButton) {
				String description = newTaskDescriptionTextField.getText();
				if(!description.isEmpty()) {
					listTask.addTask(description, priorityAddNewTask, influenceAddNewTask, false);
					JOptionPane.showMessageDialog(null, "Добавлена задача: " + description);
					newTaskDescriptionTextField.setText("");
				}
				else JOptionPane.showMessageDialog(null, "Введите описание задачи");
				listTask.sortTask();
				textPane.setText(listTask.getAllText());
			}
			if (e.getSource() == safeTaskToFileButton) {
				listTask.safeListTaskInFile();
			}
			if (e.getSource() == setSortPriorityRadioButton) {
				listTask.setSortMethod(MyListTask.METHOD_PRIORITY);
				listTask.sortTask();
				textPane.setText(listTask.getAllText());
			}
			if (e.getSource() == setSortInfluenceRadioButton) {
				listTask.setSortMethod(MyListTask.METHOD_INFLUENCE);
				listTask.sortTask();
				textPane.setText(listTask.getAllText());
			}
			if (e.getSource() == priorityOneRadioButton) {
				priorityAddNewTask = 1;
			}
			if (e.getSource() == priorityTwoRadioButton) {
				priorityAddNewTask = 2;
			}
			if (e.getSource() == priorityThreeRadioButton) {
				priorityAddNewTask = 3;
			}
			if (e.getSource() == priorityFourRadioButton) {
				priorityAddNewTask = 4;
			}
			if (e.getSource() == influenceOneRadioButton) {
				influenceAddNewTask = 1;
			}
			if (e.getSource() == influenceTwoRadioButton) {
				influenceAddNewTask = 2;
			}
			if (e.getSource() == influenceTreeRadioButton) {
				influenceAddNewTask = 3;
			}
			if (e.getSource() == startTimerButton) {
				
				timer.start();
			}
			if (e.getSource() == resetTimerButton) {
				timer.stop();
				thisPeriodTimer = 0;
				countTimerSecond = schemeTimerMinute[0]*60;
				timerLabel.setText(minuteToHour(countTimerSecond));
			}
			if (e.getSource() == pauseTimerButton) {
				timerLabel.setText("Pause");
				timer.stop();
			}
			
			if (e.getSource() == completeButton) {
				int num = Integer.parseInt(numberDeleteTaskTextField.getText());
				JOptionPane.showMessageDialog(null, listTask.completeTask(num-1));
				numberDeleteTaskTextField.setText("");
				textPane.setText(listTask.getAllText());
			}
			
			} catch(Exception eX) {
				JOptionPane.showMessageDialog(null, "Error!");				
			}
		}
	}
	
	class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(countTimerSecond>0) {
				countTimerSecond--;
				timerLabel.setText(minuteToHour(countTimerSecond));
				} else {
					if(thisPeriodTimer == 0) {
						if (circleWorkTimer < schemeTimerMinute[3]-1) {
							//JOptionPane.showMessageDialog(null, "Работа закончена - небольшой перерыв!!!");
							System.out.println("Работа закончена - небольшой перерыв!!!");
							thisPeriodTimer = 1;
							countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
							timerLabel.setText(minuteToHour(countTimerSecond));
						} else {
							//JOptionPane.showMessageDialog(null, "Работа закончена - большой перерыв!!!");
							System.out.println("Работа закончена - большой перерыв!!!");
							thisPeriodTimer = 2;
							countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
							timerLabel.setText(minuteToHour(countTimerSecond));
						}
						
					} else if(thisPeriodTimer == 1) {
						//JOptionPane.showMessageDialog(null, "Небольшой перерыв закончен, пора работать!!!");
						System.out.println("Небольшой перерыв закончен, пора работать!!!");
						thisPeriodTimer = 0;
						circleWorkTimer++;
						countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
						timerLabel.setText(minuteToHour(countTimerSecond));
					} else if(thisPeriodTimer == 2) {
						//JOptionPane.showMessageDialog(null, "Большой перерыв закончен, пора работать!!!");
						System.out.println("Большой перерыв закончен!!!");
						thisPeriodTimer = 0;
						circleWorkTimer = 0;
						countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
						timerLabel.setText(minuteToHour(countTimerSecond));
					}
					}
			}
	}
}
