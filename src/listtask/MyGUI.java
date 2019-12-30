package listtask;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
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

public class MyGUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	protected MyListTask listTask; // Список задач
	private JPanel contentPane;  // Основное полотно для элементов управления
	protected JTextPane textPane;  // Текстовое поле для вывода списка
	private JScrollPane textScroll; // Правый скролл прокрутки текстового поля с выводом списка
	//private ButtonEventListener workEventListener; // Обработчик событий нажатия кнопок и переключателей
	
	// Удаление и пометка выполнения задачи
	private JButton deleteTaskButton;   // Кнопка удаления задачи из списка
	private JButton completeButton;  // Кнопка смены отметки выполнения задачи
	private JTextField completeAndDeleteTaskTextField; // Текстовое поле для ввода номера удаляемого поля или поля установки выполнения
	
	// Переменные и элементы управления для работы таймера
	private JLabel timerLabel;  // Надпись времени
	private Timer timer;  // Таймер
	private TimerListener timerListener;  // Слушатель событий таймера
	private int countTimerSecond = 0;  // Счетчик времени в секундах
	private int[] schemeTimerMinute = {25, 5, 30, 3}; // Время работы, время короткого перерыва, время длинного перерыва
	private int thisPeriodTimer = 0; // Текущий период таймера (работа, короткий перерыв или длинный перерыв)
	private int circleWorkTimer = 0;  // Счетчик циклов до большого перерыва
	private JButton startTimerButton;   // Кнопка старта таймера
	private JButton pauseTimerButton;   // Кнопка паузы таймера
	private JButton resetTimerButton;   // Кнопка сброса и остановки таймера
	private JButton settingsTimerButton;

	// Сортировка задач
	private ButtonGroup methodSortRadioButtonGroup; // Группа переключателей для установки метода сортировки
	private JRadioButton setSortPriorityRadioButton;  // Переключатель установки сортировки по приоритету
	private JRadioButton setSortInfluenceRadioButton;  // Переключатель установки сортировки по влиянию
	
	// Переменные для создания новой записи
	private JButton addTaskButton;      // Кнопка добавления задачи в список
	protected int priorityAddNewTask, influenceAddNewTask;  // Переменые для переключателей приоритета и зависимости
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	
	MyGUISettings myGUISettings;
	MyGUIAddTask myGUIAddTask;
	
	public MyGUI() {		
		super("ListTask");
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    
	    // Вопрос при выходе и сохранение данных в файле
	    addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent evt){
	        	/*int result = JOptionPane.showConfirmDialog(null,
	        			"Закрыть программу и сохранить данные?", "Выход из программы", 
	        			JOptionPane.YES_NO_OPTION, 
	        			JOptionPane.QUESTION_MESSAGE); */
	        	/*if (result == JOptionPane.YES_OPTION) { 
	        		listTask.safeListTaskInFile();
	        		System.exit(1);
	          }*/
	        	listTask.safeListTaskInFile();
	        	System.exit(1);
	        }
	    });
		this.setSize(510, 310);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		this.setContentPane(contentPane);
		
		textPane = new JTextPane();
		textScroll = new JScrollPane(textPane);
		textScroll.setBounds(5, 30, 500, 150);
		contentPane.add(textScroll);
		
		//Box box1 = Box.createHorizontalBox();
		
		completeAndDeleteTaskTextField = new JTextField();
		completeAndDeleteTaskTextField.setBounds(122, 215, 48, 25);
		contentPane.add(completeAndDeleteTaskTextField);
		completeAndDeleteTaskTextField.setColumns(10);
		//box1.add(completeAndDeleteTaskTextField);
		
		completeButton = new JButton("Выполнить");
		completeButton.setBounds(173, 215, 135, 25);
		contentPane.add(completeButton);
		//box1.add(completeButton);
		
		deleteTaskButton = new JButton("Удалить задачу");
		deleteTaskButton.setBounds(318, 215, 187, 25);
		contentPane.add(deleteTaskButton);
		//box1.add(deleteTaskButton);
		
		addTaskButton  = new JButton("Добавить задачу");
		addTaskButton.setBounds(5, 185, 200, 25);
		contentPane.add(addTaskButton);
		//box1.add(addTaskButton);
		//contentPane.add(box1);
		
		// Создание списка задач
		listTask = new MyListTask();

		// Заполнение списка
		listTask.openListTaskFromFile(false);
		textPane.setText(listTask.getAllText());
		
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
		
		startTimerButton = new JButton("Start");
		startTimerButton.setBounds(122, 250, 114, 25);
		contentPane.add(startTimerButton);
		
		pauseTimerButton = new JButton("Pause");
		pauseTimerButton.setBounds(257, 250, 114, 25);
		contentPane.add(pauseTimerButton);
		
		timerLabel = new JLabel("New label");
		timerLabel.setBounds(60, 250, 60, 25);
		timerLabel.setText("");
		contentPane.add(timerLabel);
		
		resetTimerButton = new JButton("Reset");
		resetTimerButton.setBounds(391, 250, 114, 25);
		contentPane.add(resetTimerButton);

		countTimerSecond = schemeTimerMinute[0]*60;
		timerLabel.setText(minuteToHour(countTimerSecond));
		timerListener = new TimerListener();
		timer = new Timer(1000, timerListener);
		timer.stop();
		
		// TODO Установки работают неправильно, срабатывают только после нажатия на "Reset"
		settingsTimerButton = new JButton("Настройки");
		settingsTimerButton.setBounds(372, 178, 114, 25);
				
		//workEventListener = new ButtonEventListener();
		deleteTaskButton.addActionListener(this);
		addTaskButton.addActionListener(this);
		setSortPriorityRadioButton.addActionListener(this);
		setSortInfluenceRadioButton.addActionListener(this);
		startTimerButton.addActionListener(this);
		pauseTimerButton.addActionListener(this);
		resetTimerButton.addActionListener(this);
		completeButton.addActionListener(this);
		settingsTimerButton.addActionListener(this);
		
		JLabel label = new JLabel("Список задач");
		label.setBounds(10, 5, 95, 23);
		contentPane.add(label);
		
		label_1 = new JLabel("Сортировка:");
		label_1.setBounds(140, 5, 95, 23);
		contentPane.add(label_1);
		
		label_2 = new JLabel("Номер задачи:");
		label_2.setBounds(15, 215, 105, 25);
		contentPane.add(label_2);
		
		label_3 = new JLabel("Таймер");
		label_3.setBounds(5, 250, 55, 25);
		contentPane.add(label_3);
		
		listTask.openListTaskFromFile(true);
		listTask.sortTask();
		textPane.setText(listTask.getAllText());
		if(listTask.getSortMethod().equals(MyListTask.METHOD_INFLUENCE)) setSortInfluenceRadioButton.setSelected(true);
		else setSortPriorityRadioButton.setSelected(true);
		
		contentPane.add(settingsTimerButton);
		//pack();
	}
	
	// TODO Сделать более лаконичную формулу
	public String minuteToHour(int minute) {
		String s1 = "", s2 = "";
		if((minute/60)%60<10) s1 = "0";
		if((minute%60)<10) s2 = "0";
		return s1+((minute/60)%60)+":"+s2+(minute%60);
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
							JOptionPane.showMessageDialog(null, "Работа закончена - небольшой перерыв!!!");
							System.out.println("Работа закончена - небольшой перерыв!!!");
							thisPeriodTimer = 1;
							countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
							timerLabel.setText(minuteToHour(countTimerSecond));
						} else {
							JOptionPane.showMessageDialog(null, "Работа закончена - большой перерыв!!!");
							System.out.println("Работа закончена - большой перерыв!!!");
							thisPeriodTimer = 2;
							countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
							timerLabel.setText(minuteToHour(countTimerSecond));
						}
						
					} else if(thisPeriodTimer == 1) {
						JOptionPane.showMessageDialog(null, "Небольшой перерыв закончен, пора работать!!!");
						System.out.println("Небольшой перерыв закончен, пора работать!!!");
						thisPeriodTimer = 0;
						circleWorkTimer++;
						countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
						timerLabel.setText(minuteToHour(countTimerSecond));
					} else if(thisPeriodTimer == 2) {
						JOptionPane.showMessageDialog(null, "Большой перерыв закончен, пора работать!!!");
						System.out.println("Большой перерыв закончен!!!");
						thisPeriodTimer = 0;
						circleWorkTimer = 0;
						countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
						timerLabel.setText(minuteToHour(countTimerSecond));
					}
					}
			}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		if (e.getSource() == deleteTaskButton) {
			int num = Integer.parseInt(completeAndDeleteTaskTextField.getText());
			JOptionPane.showMessageDialog(null, listTask.deleteTask(num-1));
			completeAndDeleteTaskTextField.setText("");
			listTask.sortTask();
			textPane.setText(listTask.getAllText());
		}
		if (e.getSource() == addTaskButton) {
			myGUIAddTask = new MyGUIAddTask(this);
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
		if (e.getSource() == startTimerButton) {
			
			timer.start();
		}
		if (e.getSource() == resetTimerButton) {
			timer.stop();
			thisPeriodTimer = 0;
			schemeTimerMinute = myGUISettings.getSchemeTimerMinute();
			countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
			timerLabel.setText(minuteToHour(countTimerSecond));
		}
		if (e.getSource() == pauseTimerButton) {
			timerLabel.setText("Pause");
			timer.stop();
		}
		
		if (e.getSource() == completeButton) {
			int num = Integer.parseInt(completeAndDeleteTaskTextField.getText());
			completeAndDeleteTaskTextField.setText("");
			// Установка времени выполнения
			Date date = new Date();
			SimpleDateFormat formatDate = new SimpleDateFormat("HH/mm/dd/MM/yy");
			JOptionPane.showMessageDialog(null, listTask.completeTask(num-1, formatDate.format(date)));
			textPane.setText(listTask.getAllText());
		}
		if (e.getSource() == settingsTimerButton) {
			myGUISettings = new MyGUISettings();
			schemeTimerMinute = myGUISettings.getSchemeTimerMinute();
			
			timer.stop();
			thisPeriodTimer = 0;
			countTimerSecond = schemeTimerMinute[0]*60;
			timerLabel.setText(minuteToHour(countTimerSecond));
		}
		} catch(Exception eX) {
			JOptionPane.showMessageDialog(null, "Error!");				
		}
	}
}
