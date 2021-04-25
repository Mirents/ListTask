package ru.listtask.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.table.AbstractTableModel;

/* Принцип действия программы заключается в создании динамического массива LinkedList из класса MyTask.
 * Функция сортировки использует ключевые слова - по приоритете или влиянию.
 * В этой версии программы чтение и запись данных задач производится из файла.*/

public class ListTask extends AbstractTableModel {
    public LinkedList<Task> linkedListTask;
    private String sortMethod;
	
    final public static String METHOD_PRIORITY = "METHOD_PRIORITY";
    final public static String METHOD_INFLUENCE = "METHOD_INFLUENCE";
    final private String FILE_LIST_TASK = "fileListTask.txt"; // Константа файла с данными задач
    
    private final Object[] columnsHeader = new String[] {"Наименование", "Влияние",
                                                   "Важность/срочность",  "Выполнение"};
    
    // Конструктор класса, создающий начальный массив из max количества элементов
    public ListTask() {
    	this.linkedListTask = new LinkedList<>();
    }

    // Геттеры и Сеттеры переменной sortMethod
    public String getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(String sortMethod) {
        this.sortMethod = sortMethod;
    }

    // Добавление новой задачи
    public void addTask(String description, int priority,
                    int influence, boolean complete) {
        linkedListTask.add(new Task(description, priority, influence, complete));
        sortTask();
    }

    public void addTask(String description, int priority,
            int influence, boolean complete, String dateTime) {
        this.linkedListTask.add(new Task(description, priority, influence, complete, dateTime));
        sortTask();
    }

    // Сортировка задач
    public void sortTask() {
        // Дополнительная сортировка, непонятно для чего нужна, без нее первый раз неправильно сортирует
        Collections.sort(linkedListTask, new Comparator<Task>() {
            @Override
            public int compare(Task T1, Task T2)
            {
                return T1.getPriority() - T2.getPriority();
            }
        });
        // Сортировка первым и вторым методом
        if (this.getSortMethod() == METHOD_PRIORITY) {
            Collections.sort(linkedListTask, new Comparator<Task>() {
                @Override
                public int compare(Task T1, Task T2)
                {
                    return T1.getPriority() - T2.getPriority();
                }
            });
        } else if(this.getSortMethod() == METHOD_INFLUENCE) {
            Collections.sort(linkedListTask, new Comparator<Task>() {
                @Override
                public int compare(Task T1, Task T2)
                {
                    return T1.getInfluence() - T2.getInfluence();
                }
            });
        }
        fireTableDataChanged();
    }

    public void openListTaskFromFile(boolean isReopen) {
        // Создание переменной для чтения файла
        BufferedReader reader = null;
        try {
            // Создание файла и проверка его наличия
            File file = new File(this.FILE_LIST_TASK);
            if (file.exists()) file.createNewFile();

            if(isReopen) this.linkedListTask.clear();

            // Если файл существует
            reader = new BufferedReader(new FileReader(this.FILE_LIST_TASK));
            String line;
            // Считать данные построчно, выделить строку и две цифры
            while ((line = reader.readLine()) != null) {
                String[] mass = line.split("\\|");
                if(mass[0].trim().equals(METHOD_INFLUENCE))
                    this.setSortMethod(METHOD_INFLUENCE);
                else if(mass[0].trim().equals(METHOD_PRIORITY))
                    this.setSortMethod(METHOD_PRIORITY);
                else {
                    int priority = Integer.parseInt(mass[1].trim());
                    int influence = Integer.parseInt(mass[2].trim());
                    boolean complete = Integer.parseInt(mass[3].trim()) == 1 ? true : false;
                    if (complete) {
                            this.addTask(mass[0], priority, influence, complete, mass[4]);
                    } else 
                    // Добавить новую задачу
                    this.addTask(mass[0], priority, influence, complete);
                }
            }
            // Завершить поток чтения
            reader.close();
        } catch(IOException e) {
            // TODO В случае изменения файла не обрабатывается это исключение,
            // а сразу выходит на исключение в файле ProgramListTask.java

            // В случае возникновения ошибки вывести ее в консоль
            System.out.println("Error " + e);
        }
    }

    public void safeListTaskInFile() {
        try {
            // Создание файла и его проверка
            File file = new File(this.FILE_LIST_TASK);
            if (file.exists()) file.createNewFile();

            // Создание буфера для записи данных
            PrintWriter pw = new PrintWriter(file);

            pw.println(this.getSortMethod()+"||");
            // Построчная запись данных в файл
            for(int i=0; i<this.linkedListTask.size(); i++)
                pw.println(this.linkedListTask.get(i).getDescription()+"|"+
                                this.linkedListTask.get(i).getPriority()+"|"+
                                this.linkedListTask.get(i).getInfluence()+"|"+
                                (this.linkedListTask.get(i).getComplete() ? (1 +
                                        "|" + this.linkedListTask.get(i).getDateTime()) : 0));

            // Закрытие потока после использования
            pw.close();
        } catch(IOException e) {
            // В случае возникноения ошибки вывести ее в консоль
            System.out.println("Error " + e);
        }
    }

    public String deleteTask(int rowIndex) {
        try {
            String message = "Задача удалена: " +
                    this.linkedListTask.remove(rowIndex).getDescription();
            sortTask();
            return message;
        } catch(Exception e) {
            return e.toString();
        }
    }

    public String completeTask(int number, String dateTime) {
        try {
            if(!this.linkedListTask.get(number).getComplete()) {
                this.linkedListTask.get(number).setComplete(true);
                this.linkedListTask.get(number).setDateTimeIsComplete(dateTime);
                return "Задача выполнена";
            }
            else {
                this.linkedListTask.get(number).setComplete(false);
                this.linkedListTask.get(number).clearDateTimeIsComplete();
                return "Задача не выполнена";
            }
        } catch(Exception e) {
            return e.toString();
        }
    }

    // Количество строк
    @Override
    public int getRowCount() {
        return linkedListTask.size();
    }

    // Количество столбцов
    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Task t = linkedListTask.get(rowIndex);
        switch (columnIndex) {
            case 0: return t.getDescription();
            case 1: return getStringInfluence(t.getInfluence());
            case 2: return getStringPriority(t.getPriority());
            case 3: return t.getComplete();
        }
        return "Не определена";
    }
    
    @Override
    public Class getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                linkedListTask.get(rowIndex).setDescription(value.toString());
                break;
            case 1:
                linkedListTask.get(rowIndex).setInfluence(getNumInfluence((String) value));
                break;
            case 2:
                linkedListTask.get(rowIndex).setPriority(getNumPriority((String) value));
                break;
            case 3:
                linkedListTask.get(rowIndex).setComplete(((Boolean) value));
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    @Override
    public String getColumnName(int col) {
        return (String) columnsHeader[col];
    }
    
    @Override
     public boolean isCellEditable(int row, int col) {
         return true;
     }
     
     private int getNumPriority(String value) {
         switch (value) {
            case "Важно и срочно":
                return 1;
            case "Важно и не срочно":
                return 2;
            case "Не важно и срочно":
                return 3;
            default:
                return 4;
         }
     }
     
     private int getNumInfluence(String value) {
         switch (value) {
            case "Зависит":
                return 1;
            case "Частично зависит":
                return 2;
            default:
                return 3;
         }
     }
     
     private String getStringPriority(int value) {
         switch (value) {
            case 1:
                return "Важно и срочно";
            case 2:
                return "Важно и не срочно";
            case 3:
                return "Не важно и срочно";
            default:
                return "Не важно и не срочно";
         }
     }
     
     private String getStringInfluence(int value) {
         switch (value) {
            case 1:
                return "Зависит";
            case 2:
                return "Частично зависит";
            default:
                return "Не зависит";
         }
     }
}
