package listtask;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class MyGUI extends JFrame {
	private MyListTask listTask; // Список задач
	private JTextPane textPane;  // Текстовое поле для вывода списка
	private JButton loadFromFileButton; // Кнопка загрузки списка из файла
	private JButton deleteTaskButton;   // Кнопка удаления задачи из списка
	private JButton addTaskButton;      // Кнопка добавления задачи в список
	private JPanel contentPane;  // Основное полотно для элементов управления
	private ButtonEventListener buttonEventListener; // Обработчик событий нажатия кнопок
	private JTextField numberDeleteTaskTextField; // Текстовое поле для ввода номера удаляемого поля
	private JTextField newTaskDescriptionTextField; // Текстовое поле для описания новой задачи
	
	// Группа переключателей для выбора параметра Intluence
	JRadioButton influenceOneRadioButton;
	JRadioButton influenceTwoRadioButton;
	JRadioButton influenceTreeRadioButton;
	
	// Группа переключателей для выбора параметра Priority
	JRadioButton priotityOneRadioButton;
	JRadioButton priotityTwoRadioButton;
	JRadioButton priotityThreeRadioButton;
	JRadioButton priotityFourRadioButton;
	private JButton safeTaskToFileButton;
	private JButton sortTaskButton;
	private JRadioButton setSortPriorityRadioButton;
	private JRadioButton setSortInfluenceRadioButton;
	
	public MyGUI() {		
		super("Test");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setBounds(100, 100, 390, 453);
		this.setSize(461, 453);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		this.setContentPane(contentPane);
		
		textPane = new JTextPane();
		textPane.setBounds(12, 88, 218, 323);
		contentPane.add(textPane);
		
		numberDeleteTaskTextField = new JTextField();
		numberDeleteTaskTextField.setBounds(387, 6, 48, 25);
		contentPane.add(numberDeleteTaskTextField);
		numberDeleteTaskTextField.setColumns(10);
		
		newTaskDescriptionTextField = new JTextField();
		newTaskDescriptionTextField.setBounds(242, 90, 193, 19);
		contentPane.add(newTaskDescriptionTextField);
		newTaskDescriptionTextField.setColumns(10);
		
		influenceOneRadioButton = new JRadioButton("Зависит");
		influenceOneRadioButton.setBounds(238, 116, 144, 23);
		contentPane.add(influenceOneRadioButton);
		
		influenceTwoRadioButton = new JRadioButton("Мало зависит");
		influenceTwoRadioButton.setBounds(238, 143, 144, 23);
		contentPane.add(influenceTwoRadioButton);
		
		influenceTreeRadioButton = new JRadioButton("Не зависит");
		influenceTreeRadioButton.setBounds(238, 170, 144, 23);
		contentPane.add(influenceTreeRadioButton);
		
		priotityOneRadioButton = new JRadioButton("Priotity 1");
		priotityOneRadioButton.setBounds(238, 210, 144, 23);
		contentPane.add(priotityOneRadioButton);
		
		priotityTwoRadioButton = new JRadioButton("Priotity 2");
		priotityTwoRadioButton.setBounds(238, 240, 144, 23);
		contentPane.add(priotityTwoRadioButton);
		
		priotityThreeRadioButton = new JRadioButton("Priotity 3");
		priotityThreeRadioButton.setBounds(238, 269, 144, 23);
		contentPane.add(priotityThreeRadioButton);
		
		priotityFourRadioButton = new JRadioButton("Priotity 4");
		priotityFourRadioButton.setBounds(238, 296, 144, 23);
		contentPane.add(priotityFourRadioButton);
		
		setSortPriorityRadioButton = new JRadioButton("Сортировать по важности");
		setSortPriorityRadioButton.setBounds(12, 39, 218, 23);
		contentPane.add(setSortPriorityRadioButton);
		
		setSortInfluenceRadioButton = new JRadioButton("Сортировать по влиянию");
		setSortInfluenceRadioButton.setBounds(12, 57, 218, 23);
		contentPane.add(setSortInfluenceRadioButton);
		
		loadFromFileButton = new JButton("Загрузить");
		loadFromFileButton.setBounds(253, 386, 134, 25);
		contentPane.add(loadFromFileButton);
		
		deleteTaskButton = new JButton("Удалить задачу");
		deleteTaskButton.setBounds(221, 6, 154, 25);
		contentPane.add(deleteTaskButton);
		
		addTaskButton  = new JButton("Добавить задачу");
		addTaskButton.setBounds(242, 53, 193, 25);
		contentPane.add(addTaskButton);
		
		safeTaskToFileButton = new JButton("Сохранить");
		safeTaskToFileButton.setBounds(252, 355, 134, 25);
		contentPane.add(safeTaskToFileButton);
		
		sortTaskButton = new JButton("Сортировать задачи");
		sortTaskButton.setBounds(12, 6, 197, 25);
		contentPane.add(sortTaskButton);
		
		buttonEventListener = new ButtonEventListener();
		loadFromFileButton.addActionListener(buttonEventListener);
		deleteTaskButton.addActionListener(buttonEventListener);
		addTaskButton.addActionListener(buttonEventListener);
		
		// Создание списка задач
				listTask = new MyListTask(MyListTask.METHOD_INFLUENCE);
				// Заполнение списка
				listTask.openListTaskFromFile();
	}
	
	class ButtonEventListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
			if (e.getSource() == loadFromFileButton) {
				String str = "";
				
				// Вывод на экран до сортировки
				System.out.println("Task under sort");
				listTask.showAllTask();
				str = "Task under sort\n" +listTask.getAllText(); 
				textPane.setText(str);
				
				// Вывод на экран после сортировки методом по умолчанию - METHOD_PRIORITY
				System.out.println("\nTask before sort");
				listTask.sortTask();
				listTask.showAllTask();
				str += ("\nTask before sort - METHOD_PRIORITY\n" + listTask.getAllText());
				textPane.setText(str);
				
				// Вывод на экран после сортировки вторым методом - METHOD_INFLUENCE
				System.out.println("\nTask before sort");
				listTask.setSortMethod(MyListTask.METHOD_PRIORITY);
				listTask.sortTask();
				listTask.showAllTask();
				str += ("\nTask before sort - METHOD_INFLUENCE\n" + listTask.getAllText());
				textPane.setText(str);
			}
			if (e.getSource() == deleteTaskButton) {
				int num = Integer.parseInt(numberDeleteTaskTextField.getText());
				JOptionPane.showMessageDialog(null, listTask.deleteTask(num-1));
			}
			if (e.getSource() == addTaskButton) {
				JOptionPane.showMessageDialog(null, "Press addTaskButton");
			}
			// else JOptionPane.showMessageDialog(null, "Add Done", "Output", JOptionPane.PLAIN_MESSAGE);
			} catch(Exception eX) {
				JOptionPane.showMessageDialog(null, "Error!");				
			}
		}
	}
}
