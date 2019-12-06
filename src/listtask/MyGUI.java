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
import javax.swing.JLabel;

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
	
	JLabel timerLabel;
	private Timer timer;
	private int countTimerSecond = 0;
	// Время работы, время короткого перерыва, время длинного перерыва
	private int[] schemeTimerMinute = {25,5,15};
	
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
	
	public MyGUI() {		
		super("Test");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setBounds(100, 100, 390, 453);
		this.setSize(512, 453);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		this.setContentPane(contentPane);
		
		textPane = new JTextPane();
		textPane.setBounds(12, 43, 218, 173);
		contentPane.add(textPane);
		
		numberDeleteTaskTextField = new JTextField();
		numberDeleteTaskTextField.setBounds(452, 6, 48, 25);
		contentPane.add(numberDeleteTaskTextField);
		numberDeleteTaskTextField.setColumns(10);
		
		newTaskDescriptionTextField = new JTextField();
		newTaskDescriptionTextField.setBounds(252, 80, 193, 19);
		contentPane.add(newTaskDescriptionTextField);
		newTaskDescriptionTextField.setColumns(10);
		
		loadFromFileButton = new JButton("Загрузить");
		loadFromFileButton.setBounds(286, 266, 134, 25);
		contentPane.add(loadFromFileButton);
		
		deleteTaskButton = new JButton("Удалить задачу");
		deleteTaskButton.setBounds(253, 6, 187, 25);
		contentPane.add(deleteTaskButton);
		
		addTaskButton  = new JButton("Добавить задачу");
		addTaskButton.setBounds(253, 43, 192, 25);
		contentPane.add(addTaskButton);
		
		safeTaskToFileButton = new JButton("Сохранить");
		safeTaskToFileButton.setBounds(285, 235, 134, 25);
		contentPane.add(safeTaskToFileButton);

		// 
		priorityRadioButtonGroup = new ButtonGroup();
		
		priorityOneRadioButton = new JRadioButton("Важно и срочно");
		priorityOneRadioButton.setBounds(355, 107, 154, 23);
		priorityRadioButtonGroup.add(priorityOneRadioButton);
		contentPane.add(priorityOneRadioButton);
		priorityOneRadioButton.setSelected(true);
		priorityAddNewTask = 1;
		
		priorityTwoRadioButton = new JRadioButton("Важно и несрочно");
		priorityTwoRadioButton.setBounds(355, 137, 154, 23);
		priorityRadioButtonGroup.add(priorityTwoRadioButton);
		contentPane.add(priorityTwoRadioButton);
		
		priorityThreeRadioButton = new JRadioButton("Срочно и неважно");
		priorityThreeRadioButton.setBounds(355, 166, 154, 23);
		priorityRadioButtonGroup.add(priorityThreeRadioButton);
		contentPane.add(priorityThreeRadioButton);
		
		priorityFourRadioButton = new JRadioButton("Срочно и неважно");
		priorityFourRadioButton.setBounds(355, 193, 154, 23);
		priorityRadioButtonGroup.add(priorityFourRadioButton);
		contentPane.add(priorityFourRadioButton);
		
		// 
		influenceRadioButtonGroup = new ButtonGroup();
		
		influenceOneRadioButton = new JRadioButton("Зависит");
		influenceOneRadioButton.setBounds(238, 107, 120, 23);
		influenceRadioButtonGroup.add(influenceOneRadioButton);
		contentPane.add(influenceOneRadioButton);
		influenceOneRadioButton.setSelected(true);
		influenceAddNewTask = 1;
		
		influenceTwoRadioButton = new JRadioButton("Мало зависит");
		influenceTwoRadioButton.setBounds(238, 134, 120, 23);
		influenceRadioButtonGroup.add(influenceTwoRadioButton);
		contentPane.add(influenceTwoRadioButton);
		
		influenceTreeRadioButton = new JRadioButton("Не зависит");
		influenceTreeRadioButton.setBounds(238, 161, 120, 23);
		influenceRadioButtonGroup.add(influenceTreeRadioButton);
		contentPane.add(influenceTreeRadioButton);
		
		// Создание списка задач
		listTask = new MyListTask();

		// Заполнение списка
		listTask.openListTaskFromFile(false);
		textPane.setText(listTask.getAllText());
		
		//
		methodSortRadioButtonGroup = new ButtonGroup();
		
		setSortPriorityRadioButton = new JRadioButton("Сортировать по важности");
		setSortPriorityRadioButton.setBounds(8, 0, 218, 23);
		methodSortRadioButtonGroup.add(setSortPriorityRadioButton);
		contentPane.add(setSortPriorityRadioButton);
		setSortPriorityRadioButton.setSelected((listTask.getSortMethod().equals(MyListTask.METHOD_PRIORITY)) ?
				true : false);
		
		setSortInfluenceRadioButton = new JRadioButton("Сортировать по влиянию");
		setSortInfluenceRadioButton.setBounds(8, 18, 218, 23);
		methodSortRadioButtonGroup.add(setSortInfluenceRadioButton);
		contentPane.add(setSortInfluenceRadioButton);
		setSortInfluenceRadioButton.setSelected((listTask.getSortMethod().equals(MyListTask.METHOD_INFLUENCE)) ?
				true : false);
		
		// 
		startTimerButton = new JButton("Start");
		startTimerButton.setBounds(90, 300, 114, 25);
		contentPane.add(startTimerButton);
		
		pauseTimerButton = new JButton("Pause");
		pauseTimerButton.setBounds(90, 335, 114, 25);
		contentPane.add(pauseTimerButton);
		
		timerLabel = new JLabel("New label");
		timerLabel.setBounds(230, 356, 120, 15);
		contentPane.add(timerLabel);
		
		resetTimerButton = new JButton("Reset");
		resetTimerButton.setBounds(90, 372, 114, 25);
		contentPane.add(resetTimerButton);

		countTimerSecond = schemeTimerMinute[0]*60;
		timerLabel.setText(minuteToHour(countTimerSecond));
		
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
		
		//timer = new Timer(1000, buttonEventListener);
	}
	
	// TODO Сделать более лаконичную формулу
	public String minuteToHour(int minute) {
		String s1 = "", s2 = "";
		if((minute/60)%60<10) s1 = "0";
		if((minute%60)<10) s2 = "0";
		return s1+((minute/60)%60)+":"+s2+(minute%60) + " min";
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
				timer = new Timer(1000, new ActionListener() {
					
				    @Override
					public void actionPerformed(ActionEvent arg0) {
						timerLabel.setText(minuteToHour(countTimerSecond));
						countTimerSecond--;
					}
				});
				timer.start();
			}
			if (e.getSource() == resetTimerButton) {
				timer.stop();
				countTimerSecond = schemeTimerMinute[0]*60;
				timerLabel.setText(minuteToHour(countTimerSecond));
			}
			if (e.getSource() == pauseTimerButton) {
				timerLabel.setText("Pause");
				timer.stop();
			}
			
			} catch(Exception eX) {
				JOptionPane.showMessageDialog(null, "Error!");				
			}
		}
	}
}
