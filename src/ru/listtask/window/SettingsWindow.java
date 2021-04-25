package ru.listtask.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import static ru.listtask.utils.ConfProperties.getConfProperties;
import ru.listtask.utils.PropertiesConstant;

public class SettingsWindow extends JFrame implements ComponentListener {
	private static final long serialVersionUID = 1L;
	JTextField textFieldTimerWork;
	JTextField textFieldMiniBrake;
	JTextField textFieldBigBrake;
	JTextField textFieldPeriod;
	
	public SettingsWindow(AppWindow parentWindow) {
		super("Настройки таймера");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//this.setSize(100, 100);
		setLocationRelativeTo(null);
		this.setResizable(false);
		setVisible(true);
		
		Box box1 = Box.createHorizontalBox();
		JLabel labelTime = new JLabel("Время работы:");
		box1.add(labelTime);
		textFieldTimerWork = new JTextField();
		textFieldTimerWork.setColumns(2);
		textFieldTimerWork.setText(String.valueOf(parentWindow.schemeTimerMinute[0]));
		box1.add(textFieldTimerWork);
		
		Box box2 = Box.createHorizontalBox();
		JLabel labelMiniBreak = new JLabel("Маленький перерыв:");
		box2.add(labelMiniBreak);
		textFieldMiniBrake = new JTextField();
		textFieldMiniBrake.setColumns(2);
		textFieldMiniBrake.setText(String.valueOf(parentWindow.schemeTimerMinute[1]));
		box2.add(textFieldMiniBrake);
		
		Box box3 = Box.createHorizontalBox();
		JLabel labelBigbreak = new JLabel("Большой перерыв:");
		box3.add(labelBigbreak);
		textFieldBigBrake = new JTextField();
		textFieldBigBrake.setColumns(2);
		textFieldBigBrake.setText(String.valueOf(parentWindow.schemeTimerMinute[2]));
		box3.add(textFieldBigBrake);
		
		Box box4 = Box.createHorizontalBox();
		JLabel labelCountPeriod = new JLabel("Периодов до большого перерыва:");
		box4.add(labelCountPeriod);
		textFieldPeriod = new JTextField();
		textFieldPeriod.setColumns(2);
		textFieldPeriod.setText(String.valueOf(parentWindow.schemeTimerMinute[3]));
		box4.add(textFieldPeriod);
		
		Box box5 = Box.createHorizontalBox();
		JButton okButton = new JButton("  ОК  ");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					parentWindow.schemeTimerMinute[0] = Integer.parseInt(textFieldTimerWork.getText());
					parentWindow.schemeTimerMinute[1] = Integer.parseInt(textFieldMiniBrake.getText());
					parentWindow.schemeTimerMinute[2] = Integer.parseInt(textFieldBigBrake.getText());
					parentWindow.schemeTimerMinute[3] = Integer.parseInt(textFieldPeriod.getText());
                                        
                                        getConfProperties().setProperty(PropertiesConstant.TIME_WORK, textFieldTimerWork.getText());
                                        getConfProperties().setProperty(PropertiesConstant.TIME_LITTLE_BREAK, textFieldMiniBrake.getText());
                                        getConfProperties().setProperty(PropertiesConstant.TIME_BIG_BREAK, textFieldBigBrake.getText());
                                        getConfProperties().setProperty(PropertiesConstant.TIME_PERIODS, textFieldPeriod.getText());
					
					parentWindow.timer.stop();
					parentWindow.thisPeriodTimer = 0;
					parentWindow.countTimerSecond = parentWindow.schemeTimerMinute[0]*60;
					parentWindow.timerLabel.setText(parentWindow.setTextTimer());
					
					dispose();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error input number!");		
				}
			}
		});
		box5.add(okButton);
		JButton cancelButton = new JButton("Отмена");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		box5.add(cancelButton);
		
		Box mailBox = Box.createVerticalBox();
		mailBox.setBorder(new EmptyBorder(12, 12, 12, 12));
		mailBox.add(box1);
		mailBox.add(Box.createVerticalStrut(12));
		mailBox.add(box2);
		mailBox.add(Box.createVerticalStrut(12));
		mailBox.add(box3);
		mailBox.add(Box.createVerticalStrut(12));
		mailBox.add(box4);
		mailBox.add(Box.createVerticalStrut(12));
		mailBox.add(box5);
		this.setContentPane(mailBox);
		
		pack();
		addComponentListener(this);
	}

        @Override
	public void componentHidden(ComponentEvent arg0) {}
        
	@Override
	public void componentMoved(ComponentEvent arg0) {}

	@Override
	public void componentResized(ComponentEvent arg0) {}

	@Override
	public void componentShown(ComponentEvent arg0) {}
}
