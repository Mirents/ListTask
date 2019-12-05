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

public class MyGUI extends JFrame {
	private MyListTask listTask; // Список задач
	private JTextPane textPane;  // Текстовое поле для вывода списка
	private JButton loadFromFileButton; // Кнопка загрузки списка из файла
	private JButton deleteTaskButton;   // Кнопка удаления задачи из списка
	private JButton addTaskButton;      // Кнопка добавления задачи в список
	private JPanel contentPane;  // Основное полотно для элементов управления
	private ButtonEventListener buttonEventListener; // Обработчик событий нажатия кнопок
	private JTextField numberDeleteTaskTextField;
	
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
		textPane.setBounds(12, 43, 218, 368);
		contentPane.add(textPane);
		
		loadFromFileButton = new JButton("Load List Task");
		loadFromFileButton.setBounds(12, 12, 134, 25);
		contentPane.add(loadFromFileButton);
		
		deleteTaskButton = new JButton("Delete Task");
		deleteTaskButton.setBounds(242, 12, 134, 25);
		contentPane.add(deleteTaskButton);
		
		addTaskButton  = new JButton("Add Task");
		addTaskButton.setBounds(242, 43, 134, 25);
		contentPane.add(addTaskButton);
		
		numberDeleteTaskTextField = new JTextField();
		numberDeleteTaskTextField.setBounds(387, 12, 48, 25);
		contentPane.add(numberDeleteTaskTextField);
		numberDeleteTaskTextField.setColumns(10);

		
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
