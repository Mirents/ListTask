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
import javax.swing.DropMode;
import javax.swing.JList;

public class MyGUI extends JFrame {
	private MyListTask listTask;
	private JTextPane textPane;
	private JButton myButton;
	private JPanel contentPane;
	private ButtonEventListener buttonEventListener;
	
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
		
		myButton = new JButton("Open List Task");
		myButton.setBounds(12, 12, 134, 25);
		contentPane.add(myButton);
		
		textPane = new JTextPane();
		textPane.setBounds(12, 43, 218, 368);
		contentPane.add(textPane);
		
		buttonEventListener = new ButtonEventListener();
		myButton.addActionListener(buttonEventListener);
		
		// Создание списка задач
				listTask = new MyListTask(MyListTask.METHOD_INFLUENCE);
				// Заполнение списка
				listTask.openListTaskFromFile();
	}
	
	class ButtonEventListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
			if (e.getSource() == myButton) {
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
			// else JOptionPane.showMessageDialog(null, "Add Done", "Output", JOptionPane.PLAIN_MESSAGE);
			} catch(Exception eX) {
				JOptionPane.showMessageDialog(null, "Error!");				
			}
		}
	}
}
