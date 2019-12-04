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
	private JLabel myLabel = new JLabel("Input text:!");
	private JButton myButton = new JButton("Add Task");
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
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(12, 61, 366, 350);
		contentPane.add(textPane);
		
		myButton.addActionListener(new ButtonEventListener());
	}
	
	class ButtonEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "Add Done", "Output", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
