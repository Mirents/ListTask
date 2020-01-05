package listtask;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.io.File;

public class MyGUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	protected MyListTask listTask; // Список задач
	protected JTextPane textPane;  // Текстовое поле для вывода списка
	private JScrollPane textScroll; // Правый скролл прокрутки текстового поля с выводом списка
	
	// Удаление и пометка выполнения задачи
	private JButton deleteTaskButton;   // Кнопка удаления задачи из списка
	private JButton completeButton;  // Кнопка смены отметки выполнения задачи
	private JTextField completeAndDeleteTaskTextField; // Текстовое поле для ввода номера удаляемого поля или поля установки выполнения
	
	// Переменные и элементы управления для работы таймера
	protected JLabel timerLabel;  // Надпись времени
	protected Timer timer;  // Таймер
	private TimerListener timerListener;  // Слушатель событий таймера
	protected int countTimerSecond = 0;  // Счетчик времени в секундах
	protected int[] schemeTimerMinute = {25, 5, 30, 3}; // Время работы, время короткого перерыва, время длинного перерыва
	protected int thisPeriodTimer = 0; // Текущий период таймера (работа, короткий перерыв или длинный перерыв)
	private int circleWorkTimer = 0;  // Счетчик циклов до большого перерыва
	private JButton startTimerButton;   // Кнопка старта таймера
	private JButton pauseTimerButton;   // Кнопка паузы таймера
	private JButton resetTimerButton;   // Кнопка сброса и остановки таймера
	private JButton settingsTimerButton;  // Кнопка настройки таймера
	Clip gongSound;

	// Сортировка задач
	private ButtonGroup methodSortRadioButtonGroup; // Группа переключателей для установки метода сортировки
	private JRadioButton setSortPriorityRadioButton;  // Переключатель установки сортировки по приоритету
	private JRadioButton setSortInfluenceRadioButton;  // Переключатель установки сортировки по влиянию
	
	// Переменные для создания новой записи
	private JButton addTaskButton;      // Кнопка добавления задачи в список
	protected int priorityAddNewTask, influenceAddNewTask;  // Переменые для переключателей приоритета и зависимости
	private JLabel label_1;
	private JLabel label_2;
	
	MyGUISettings myGUISettings;  // Окно настроек таймера
	MyGUIAddTask myGUIAddTask;  // Окно добавления задачи
	
	public MyGUI() {		
		super("ListTask");  // Название окна
		this.setResizable(false);  // Запрет на изменение размера
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Сохранение данных при закрытии программы
	    addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent evt){
	        	listTask.safeListTaskInFile();  // Сохранение списка в файл
	        	System.exit(1);  // Выход из программы
	        }
	    });
	    
		this.setLocationRelativeTo(null);  // Положение в середине рабочего стола
		
		// Создание списка задач
		listTask = new MyListTask();
		// Заполнение списка
		listTask.openListTaskFromFile(false);
				
		listTask.openListTaskFromFile(true);
		listTask.sortTask();
				
		Box box1 = Box.createVerticalBox();
		Box box11 = Box.createHorizontalBox();
		Box box12 = Box.createHorizontalBox();
		
		JLabel label = new JLabel("Список задач");
		label.setBounds(10, 5, 95, 23);
		//contentPane.add(label);
		box11.add(label);
		
		label_1 = new JLabel("Сортировка:");
		label_1.setBounds(140, 5, 95, 23);
		//contentPane.add(label_1);
		box12.add(label_1);
		
		methodSortRadioButtonGroup = new ButtonGroup();
		
		setSortPriorityRadioButton = new JRadioButton("Важность/срочность");
		methodSortRadioButtonGroup.add(setSortPriorityRadioButton);
		setSortPriorityRadioButton.setSelected((listTask.getSortMethod().equals(MyListTask.METHOD_PRIORITY)) ?
				true : false);
		box12.add(setSortPriorityRadioButton);
		
		box1.add(box11);
		box1.add(box12);
		
		setSortInfluenceRadioButton = new JRadioButton("Влияние");
		methodSortRadioButtonGroup.add(setSortInfluenceRadioButton);
		setSortInfluenceRadioButton.setSelected((listTask.getSortMethod().equals(MyListTask.METHOD_INFLUENCE)) ?
				true : false);
		box12.add(setSortInfluenceRadioButton);
		
		Box box2 = Box.createHorizontalBox();

		textPane = new JTextPane();
		textScroll = new JScrollPane(textPane);
		textPane.setText("111111111111111111111111111111111111\n\n\n\n\n\n\n\n\n");
		box2.add(textScroll);
		
		Box box3 = Box.createHorizontalBox();
		Box box31 = Box.createHorizontalBox();
		Box box32 = Box.createHorizontalBox();
		Box box33 = Box.createVerticalBox();
		
		completeButton = new JButton("Выполнить");
		box32.add(completeButton);
		
		deleteTaskButton = new JButton("Удалить задачу");
		box32.add(deleteTaskButton);

		addTaskButton  = new JButton("Добавить задачу");
		
		label_2 = new JLabel("Номер задачи:");
		label_2.setBounds(5, 183, 105, 25);
		box31.add(label_2);
		
		completeAndDeleteTaskTextField = new JTextField();
		completeAndDeleteTaskTextField.setColumns(2);
		box31.add(completeAndDeleteTaskTextField);
		
		box33.add(box31);
		box33.add(box32);
		box3.add(box33);
		box3.add(Box.createHorizontalStrut(12));
		box3.add(addTaskButton);
		
		Box box4 = Box.createHorizontalBox();
		Box box41 = Box.createHorizontalBox();
		Box box42 = Box.createVerticalBox();
		Box box43 = Box.createHorizontalBox();
		Box box44 = Box.createHorizontalBox();
		
		timerLabel = new JLabel("");
		box44.add(timerLabel);
		
		startTimerButton = new JButton("Старт");
		box41.add(startTimerButton);
		
		pauseTimerButton = new JButton("Пауза");
		box41.add(pauseTimerButton);
		
		resetTimerButton = new JButton("Сбросить");
		box41.add(resetTimerButton);
		box42.add(box44);
		box42.add(box41);
		
		settingsTimerButton = new JButton("Настройки таймера");
		box43.add(settingsTimerButton);
		
		box4.add(box42);
		box4.add(Box.createHorizontalStrut(12));
		box4.add(box43);

		countTimerSecond = schemeTimerMinute[0]*60;
		timerLabel.setText(setTextTimer());
		timerListener = new TimerListener();

		timer = new Timer(1000, timerListener);
		timer.stop();

		deleteTaskButton.addActionListener(this);
		addTaskButton.addActionListener(this);
		setSortPriorityRadioButton.addActionListener(this);
		setSortInfluenceRadioButton.addActionListener(this);
		startTimerButton.addActionListener(this);
		pauseTimerButton.addActionListener(this);
		resetTimerButton.addActionListener(this);
		completeButton.addActionListener(this);
		settingsTimerButton.addActionListener(this);
		
		Box mailBox = Box.createVerticalBox();
		mailBox.setBorder(new EmptyBorder(0, 12, 12, 12));
		mailBox.add(box1);
		mailBox.add(Box.createVerticalStrut(12));
		mailBox.add(box2);
		mailBox.add(Box.createVerticalStrut(12));
		mailBox.add(box3);
		mailBox.add(Box.createVerticalStrut(12));
		mailBox.add(box4);
		this.setContentPane(mailBox);
		
		pack();
		textPane.setText(listTask.getAllText());
	}
	
	// TODO Сделать более лаконичную формулу
	protected String minuteToHour(int minute) {
		String s1 = "", s2 = "";
		if((minute/60)%60<10) s1 = "0";
		if((minute%60)<10) s2 = "0";
		return s1+((minute/60)%60)+":"+s2+(minute%60);
	}
	
	private void PlayGond(String file) {
		gongSound = null;
		try {
			File f = new File(file);
			AudioFileFormat aff = AudioSystem.getAudioFileFormat(f);
			AudioFormat af = aff.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, af);
			if(AudioSystem.isLineSupported(info)) {
				gongSound = (Clip) AudioSystem.getLine(info);
				AudioInputStream ais = AudioSystem.getAudioInputStream(f);
				gongSound.open(ais);
				gongSound.start();
				Thread.sleep(1500);
				gongSound.stop();
				gongSound.close();
			} else {
				System.out.println("This sound file doesnt support!");
			}
		} catch (Exception e) {
			System.out.println("Error sound!");
		}
	}
	
	protected String setTextTimer() {
		String text = "";
		switch (thisPeriodTimer) {
		case 0:
			text = "Работа, период " + (circleWorkTimer+1) + " из " + schemeTimerMinute[3];
			break;
		case 1:
			text = "Небольшой перерыв";
			break;
		case 2:
			text = "Большой перерыв";
			break;
		}
		return text + " " + minuteToHour(countTimerSecond);
	}
	
	class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(countTimerSecond>0) {
				countTimerSecond--;
				timerLabel.setText(setTextTimer());
				} else {
					if(thisPeriodTimer == 0) {
						if (circleWorkTimer < schemeTimerMinute[3]-1) {
							PlayGond("gong.wav");
							JOptionPane.showMessageDialog(null, "Работа закончена - небольшой перерыв!!!");
							System.out.println("Работа закончена - небольшой перерыв!!!");
							thisPeriodTimer = 1;
							countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
							timerLabel.setText(setTextTimer());
						} else {
							PlayGond("gong.wav");
							JOptionPane.showMessageDialog(null, "Работа закончена - большой перерыв!!!");
							System.out.println("Работа закончена - большой перерыв!!!");
							thisPeriodTimer = 2;
							countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
							timerLabel.setText(setTextTimer());
						}
						
					} else if(thisPeriodTimer == 1) {
						PlayGond("gong.wav");
						JOptionPane.showMessageDialog(null, "Небольшой перерыв закончен, пора работать!!!");
						System.out.println("Небольшой перерыв закончен, пора работать!!!");
						thisPeriodTimer = 0;
						circleWorkTimer++;
						countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
						timerLabel.setText(setTextTimer());
					} else if(thisPeriodTimer == 2) {
						PlayGond("gong.wav");
						JOptionPane.showMessageDialog(null, "Большой перерыв закончен, пора работать!!!");
						System.out.println("Большой перерыв закончен!!!");
						thisPeriodTimer = 0;
						circleWorkTimer = 0;
						countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
						timerLabel.setText(setTextTimer());
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
			countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
			timerLabel.setText(setTextTimer());
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
			myGUISettings = new MyGUISettings(this);
		}
		} catch(Exception eX) {
			JOptionPane.showMessageDialog(null, "Error!");				
		}
	}
}
