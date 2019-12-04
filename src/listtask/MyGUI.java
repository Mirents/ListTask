package listtask;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;

public class MyGUI extends JFrame {
	MyListTask listTask;
	private JTextPane textPane;
	//private JButton myButton = new JButton("Add Task");
	private JPanel contentPane;
	
	public MyGUI() {		
		
		super("Test");
		setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 390, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton myButton = new JButton("Получить список задач из файла");
		myButton.setBounds(12, 12, 366, 25);
		contentPane.add(myButton);
		
		textPane = new JTextPane();
		textPane.setBounds(12, 61, 366, 350);
		contentPane.add(textPane);
		
		myButton.addActionListener(new ButtonEventListener());
		
		// Создание списка задач
				listTask = new MyListTask(MyListTask.METHOD_INFLUENCE);
				// Заполнение списка
				listTask.openListTaskFromFile();
	}
	
	class ButtonEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "Add Done", "Output", JOptionPane.PLAIN_MESSAGE);
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
	}
}
