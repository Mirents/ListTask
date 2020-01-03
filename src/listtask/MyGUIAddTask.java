package listtask;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MyGUIAddTask extends JFrame implements ComponentListener {
	private static final long serialVersionUID = 1L;
	MyGUI myGUI;
	
	private JTextField newTaskDescriptionTextField; // Текстовое поле для описания новой задачи
	private int priorityAddNewTask, influenceAddNewTask;  // Переменые для переключателей приоритета и зависимости
	String description;
	
	// Группа переключателей для выбора параметра Intluence при добавлении задачи
	private ButtonGroup influenceRadioButtonGroup; // Группа переключателей для установки зависимостри при создании новой задачи
	private JRadioButton influenceOneRadioButton;
	private JRadioButton influenceTwoRadioButton;
	private JRadioButton influenceTreeRadioButton;
	
	// Группа переключателей для выбора параметра Priority при добавлении задачи
	private ButtonGroup priorityRadioButtonGroup; // Группа переключателей для установки приоритета при создании новой задачи
	private JRadioButton priorityOneRadioButton;
	private JRadioButton priorityTwoRadioButton;
	private JRadioButton priorityThreeRadioButton;
	private JRadioButton priorityFourRadioButton;
	
	public MyGUIAddTask(MyGUI myGUI) {
		super("Добавить задачу");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		this.setResizable(false);
		setVisible(true);
		this.myGUI = myGUI;
		
		Box box1 = Box.createVerticalBox();
		JLabel label_4 = new JLabel("Описание задачи:");
		box1.add(label_4);
		newTaskDescriptionTextField = new JTextField();
		newTaskDescriptionTextField.setColumns(2);
		box1.add(newTaskDescriptionTextField);
		
		Box box2 = Box.createHorizontalBox();
		Box box21 = Box.createVerticalBox();
		influenceRadioButtonGroup = new ButtonGroup();

		influenceOneRadioButton = new JRadioButton("Зависит");
		influenceOneRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				influenceAddNewTask = 1;
			}
		});
		influenceRadioButtonGroup.add(influenceOneRadioButton);
		box21.add(influenceOneRadioButton);
		influenceOneRadioButton.setSelected(true);
		influenceAddNewTask = 1;
		
		influenceTwoRadioButton = new JRadioButton("Мало зависит");
		influenceTwoRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				influenceAddNewTask = 2;
			}
		});
		influenceRadioButtonGroup.add(influenceTwoRadioButton);
		box21.add(influenceTwoRadioButton);
		
		influenceTreeRadioButton = new JRadioButton("Не зависит");
		influenceTreeRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				influenceAddNewTask = 3;
			}
		});
		influenceRadioButtonGroup.add(influenceTreeRadioButton);
		box21.add(influenceTreeRadioButton);
		
		Box box22 = Box.createVerticalBox();
		priorityRadioButtonGroup = new ButtonGroup();
		
		priorityOneRadioButton = new JRadioButton("Важно и срочно");
		priorityOneRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				priorityAddNewTask = 1;
			}
		});
		priorityRadioButtonGroup.add(priorityOneRadioButton);
		box22.add(priorityOneRadioButton);
		priorityOneRadioButton.setSelected(true);
		priorityAddNewTask = 1;
		
		priorityTwoRadioButton = new JRadioButton("Важно и несрочно");
		priorityTwoRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				priorityAddNewTask = 2;
			}
		});
		priorityRadioButtonGroup.add(priorityTwoRadioButton);
		box22.add(priorityTwoRadioButton);
		
		priorityThreeRadioButton = new JRadioButton("Срочно и неважно");
		priorityThreeRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				priorityAddNewTask = 3;
			}
		});
		priorityRadioButtonGroup.add(priorityThreeRadioButton);
		box22.add(priorityThreeRadioButton);
		
		priorityFourRadioButton = new JRadioButton("Несрочно и неважно");
		priorityFourRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				priorityAddNewTask = 4;
			}
		});
		priorityRadioButtonGroup.add(priorityFourRadioButton);
		box22.add(priorityFourRadioButton);
		
		box2.add(box21);
		box2.add(box22);
		
		Box box3 = Box.createHorizontalBox();
		JButton okButton = new JButton("  ОК  ");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = newTaskDescriptionTextField.getText();
				if(!s.isEmpty()) {
					myGUI.listTask.addTask(s, priorityAddNewTask, influenceAddNewTask, false);
					JOptionPane.showMessageDialog(null, "Добавлена задача: " + s);
					myGUI.listTask.sortTask();
					myGUI.textPane.setText(myGUI.listTask.getAllText());
					dispose();
				} else JOptionPane.showMessageDialog(null, "Введите описание задачи");				
			}
		});
		box3.add(okButton);
		JButton cancelButton = new JButton("Отмена");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		box3.add(cancelButton);
		
		Box mailBox = Box.createVerticalBox();
		mailBox.setBorder(new EmptyBorder(12, 12, 12, 12));
		mailBox.add(box1);
		mailBox.add(Box.createVerticalStrut(12));
		mailBox.add(box2);
		mailBox.add(Box.createVerticalStrut(12));
		mailBox.add(box3);
		this.setContentPane(mailBox);
		pack();
		addComponentListener(this);
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
