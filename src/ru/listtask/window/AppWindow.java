package ru.listtask.window;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.Timer;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import static ru.listtask.utils.ConfProperties.getConfProperties;
import ru.listtask.utils.GongPlayer;
import ru.listtask.utils.ListTask;
import ru.listtask.utils.PropertiesConstant;

public class AppWindow extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    protected ListTask listTask; // Список задач

    // Удаление и пометка выполнения задачи
    private JButton deleteTaskButton;   // Кнопка удаления задачи из списка

    // Переменные и элементы управления для работы таймера
    protected JLabel timerLabel;  // Надпись времени
    protected Timer timer;  // Таймер
    private TimerListener timerListener;  // Слушатель событий таймера
    protected int countTimerSecond = 0;  // Счетчик времени в секундах
    protected int[] schemeTimerMinute = {30, 5, 25, 7}; // Время работы, время короткого перерыва, время длинного перерыва
    protected int thisPeriodTimer = 0; // Текущий период таймера (работа, короткий перерыв или длинный перерыв)
    private int circleWorkTimer = 0;  // Счетчик циклов до большого перерыва
    private JButton startTimerButton;   // Кнопка старта таймера
    private JButton pauseTimerButton;   // Кнопка паузы таймера
    private JButton resetTimerButton;   // Кнопка сброса и остановки таймера
    private JButton settingsTimerButton;  // Кнопка настройки таймера

    // Сортировка задач
    private ButtonGroup methodSortRadioButtonGroup; // Группа переключателей для установки метода сортировки
    private JRadioButton setSortPriorityRadioButton;  // Переключатель установки сортировки по приоритету
    private JRadioButton setSortInfluenceRadioButton;  // Переключатель установки сортировки по влиянию

    // Переменные для создания новой записи
    private JButton addTaskButton;      // Кнопка добавления задачи в список
    protected int priorityAddNewTask, influenceAddNewTask;  // Переменые для переключателей приоритета и зависимости
    private JLabel labelSort;
    private JLabel labelList;

    private SettingsWindow settingsWindow;  // Окно настроек таймера
    private AddTaskWindow AddTaskWindow;  // Окно добавления задачи

    private GongPlayer gongPlayer;
    private JCheckBox checkBoxMute;
    
    private TableColumnModel columnModel;
    private JTable table;

    public AppWindow() {
        super("ListTask");  // Название окна
        this.setResizable(false);  // Запрет на изменение размера
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Сохранение данных при закрытии программы

        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent evt){
                listTask.safeListTaskInFile();  // Сохранение списка в файл
                getConfProperties().savePropertyToFile();
                System.exit(1);  // Выход из программы
            }
        });
        this.setBounds(0, 0, 600, 400);

        // Создание элемента воспроизведения звука
        gongPlayer = new GongPlayer();
        
        schemeTimerMinute[0] = getConfProperties().getPropertyInt(PropertiesConstant.TIME_WORK);
        schemeTimerMinute[1] = getConfProperties().getPropertyInt(PropertiesConstant.TIME_LITTLE_BREAK);
        schemeTimerMinute[2] = getConfProperties().getPropertyInt(PropertiesConstant.TIME_BIG_BREAK);
        schemeTimerMinute[3] = getConfProperties().getPropertyInt(PropertiesConstant.TIME_PERIODS);
                                        
        // Создание списка задач
        listTask = new ListTask();
        // Заполнение списка
        listTask.openListTaskFromFile(false);

        listTask.openListTaskFromFile(true);
        listTask.sortTask();

        table = new JTable(listTask);
        columnModel = table.getColumnModel();
        Enumeration<TableColumn> e = columnModel.getColumns();
        int num = 0;
        while ( e.hasMoreElements() ) {
            TableColumn column = (TableColumn)e.nextElement();
            switch (num) {
            case 0:
                column.setMinWidth(220);
                column.setMaxWidth(320);
                break;
            case 1:
                column.setMinWidth(20);
                column.setMaxWidth(80);
                break;
            case 2:
                column.setMinWidth(20);
                column.setMaxWidth(150);
                break;
            case 3:
                column.setMinWidth(20);
                column.setMaxWidth(50);
            }
            num++;
        }
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false);

        JComboBox<String> combo_influence = new JComboBox<String>(new String[]
            { "Зависит", "Частично зависит", "Не зависит"});

        JComboBox<String> combo_priority = new JComboBox<String>(new String[]
            { "Важно и срочно", "Важно и не срочно", "Не важно и срочно", "Не важно и не срочно"});

        DefaultCellEditor editor_influence = new DefaultCellEditor(combo_influence);
         DefaultCellEditor editor_priority = new DefaultCellEditor(combo_priority);
        table.getColumnModel().getColumn(1).setCellEditor(editor_influence);
        table.getColumnModel().getColumn(2).setCellEditor(editor_priority);

        Box box1 = Box.createVerticalBox();
        Box box11 = Box.createHorizontalBox();
        Box box12 = Box.createHorizontalBox();

        labelList = new JLabel("Список задач");
        labelList.setBounds(10, 5, 95, 23);
        box11.add(labelList);
        box1.add(Box.createVerticalStrut(10));

        labelSort = new JLabel("Сортировка:");
        labelSort.setBounds(140, 5, 95, 23);
        box12.add(labelSort);

        methodSortRadioButtonGroup = new ButtonGroup();

        setSortPriorityRadioButton = new JRadioButton("Важность/срочность");
        methodSortRadioButtonGroup.add(setSortPriorityRadioButton);
        setSortPriorityRadioButton.setSelected((listTask.getSortMethod().equals(ListTask.METHOD_PRIORITY)));
        box12.add(setSortPriorityRadioButton);

        box1.add(box11);
        box1.add(box12);

        setSortInfluenceRadioButton = new JRadioButton("Влияние");
        methodSortRadioButtonGroup.add(setSortInfluenceRadioButton);
        setSortInfluenceRadioButton.setSelected((listTask.getSortMethod().equals(ListTask.METHOD_INFLUENCE)));
        box12.add(setSortInfluenceRadioButton);

        Box box2 = Box.createHorizontalBox();
        box2.add(new JScrollPane(table));

        Box box3 = Box.createHorizontalBox();
        deleteTaskButton = new JButton("Удалить задачу");
        addTaskButton  = new JButton("Добавить задачу");

        box3.add(deleteTaskButton);
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
        
        checkBoxMute = new JCheckBox("Без звука");
        if("on".equals(getConfProperties().getProperty(PropertiesConstant.MUTE)))
            checkBoxMute.setSelected(true);
        else
            checkBoxMute.setSelected(false);
        box43.add(checkBoxMute);
        
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
        settingsTimerButton.addActionListener(this);
        checkBoxMute.addActionListener(this);

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
    }

    // TODO Сделать более лаконичную формулу
    protected String minuteToHour(int minute) {
        String s1 = "", s2 = "";
        if((minute/60)%60<10) s1 = "0";
        if((minute%60)<10) s2 = "0";
        return s1+((minute/60)%60)+":"+s2+(minute%60);
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
                        if(!checkBoxMute.isSelected())
                            gongPlayer.play();
                        JOptionPane.showMessageDialog(null, "Работа закончена - небольшой перерыв!");
                        System.out.println("Работа закончена - небольшой перерыв!!!");
                        thisPeriodTimer = 1;
                        countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
                        timerLabel.setText(setTextTimer());
                    } else {
                        if(!checkBoxMute.isSelected())
                            gongPlayer.play();
                        JOptionPane.showMessageDialog(null, "Работа закончена - большой перерыв!");
                        System.out.println("Работа закончена - большой перерыв!!!");
                        thisPeriodTimer = 2;
                        countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
                        timerLabel.setText(setTextTimer());
                    }
                } else if(thisPeriodTimer == 1) {
                    if(!checkBoxMute.isSelected())
                        gongPlayer.play();
                    JOptionPane.showMessageDialog(null, "Небольшой перерыв закончен, пора работать!");
                    System.out.println("Небольшой перерыв закончен, пора работать!!!");
                    thisPeriodTimer = 0;
                    circleWorkTimer++;
                    countTimerSecond = schemeTimerMinute[thisPeriodTimer]*60;
                    timerLabel.setText(setTextTimer());
                } else if(thisPeriodTimer == 2) {
                    if(!checkBoxMute.isSelected())
                        gongPlayer.play();
                    JOptionPane.showMessageDialog(null, "Большой перерыв закончен, пора работать!");
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
                listTask.deleteTask(table.getSelectedRow());
        }
        if (e.getSource() == addTaskButton) {
                AddTaskWindow = new AddTaskWindow(this);
        }
        if (e.getSource() == setSortPriorityRadioButton) {
                listTask.setSortMethod(ListTask.METHOD_PRIORITY);
                listTask.sortTask();
        }
        if (e.getSource() == setSortInfluenceRadioButton) {
                listTask.setSortMethod(ListTask.METHOD_INFLUENCE);
                listTask.sortTask();
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
        if (e.getSource() == checkBoxMute) {
            if(checkBoxMute.isSelected())
                getConfProperties().setProperty(PropertiesConstant.MUTE, "on");
            else
                getConfProperties().setProperty(PropertiesConstant.MUTE, "off");
                    
        }
        if (e.getSource() == pauseTimerButton) {
                timerLabel.setText("Pause");
                timer.stop();
        }
        if (e.getSource() == settingsTimerButton) {
                settingsWindow = new SettingsWindow(this);
        }
        } catch(HeadlessException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error!");				
        }
    }
}
