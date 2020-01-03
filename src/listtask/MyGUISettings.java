package listtask;

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

public class MyGUISettings extends JFrame implements ComponentListener {
	private static final long serialVersionUID = 1L;
	JTextField textFieldTimerWork;
	JTextField textFieldMiniBrake;
	JTextField textFieldBigBrake;
	JTextField textFieldPeriod;
	
	public MyGUISettings(MyGUI myGUI) {
		super("Настройки");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//this.setSize(100, 100);
		setLocationRelativeTo(null);
		this.setResizable(false);
		setVisible(true);
		
		Box box1 = Box.createHorizontalBox();
		JLabel label_4 = new JLabel("Время работы:");
		box1.add(label_4);
		textFieldTimerWork = new JTextField();
		textFieldTimerWork.setColumns(2);
		textFieldTimerWork.setText(String.valueOf(myGUI.schemeTimerMinute[0]));
		box1.add(textFieldTimerWork);
		
		Box box2 = Box.createHorizontalBox();
		JLabel label_5 = new JLabel("Маленький перерыв:");
		box2.add(label_5);
		textFieldMiniBrake = new JTextField();
		textFieldMiniBrake.setColumns(2);
		textFieldMiniBrake.setText(String.valueOf(myGUI.schemeTimerMinute[1]));
		box2.add(textFieldMiniBrake);
		
		Box box3 = Box.createHorizontalBox();
		JLabel label_6 = new JLabel("Большой перерыв:");
		box3.add(label_6);
		textFieldBigBrake = new JTextField();
		textFieldBigBrake.setColumns(2);
		textFieldBigBrake.setText(String.valueOf(myGUI.schemeTimerMinute[2]));
		box3.add(textFieldBigBrake);
		
		Box box4 = Box.createHorizontalBox();
		JLabel label_7 = new JLabel("Периодов до большого перерыва:");
		box4.add(label_7);
		textFieldPeriod = new JTextField();
		textFieldPeriod.setColumns(2);
		textFieldPeriod.setText(String.valueOf(myGUI.schemeTimerMinute[3]));
		box4.add(textFieldPeriod);
		
		Box box5 = Box.createHorizontalBox();
		JButton okButton = new JButton("  ОК  ");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					myGUI.schemeTimerMinute[0] = Integer.parseInt(textFieldTimerWork.getText());
					myGUI.schemeTimerMinute[1] = Integer.parseInt(textFieldMiniBrake.getText());
					myGUI.schemeTimerMinute[2] = Integer.parseInt(textFieldBigBrake.getText());
					myGUI.schemeTimerMinute[3] = Integer.parseInt(textFieldPeriod.getText());
					
					myGUI.timer.stop();
					myGUI.thisPeriodTimer = 0;
					myGUI.countTimerSecond = myGUI.schemeTimerMinute[0]*60;
					myGUI.timerLabel.setText(myGUI.minuteToHour(myGUI.countTimerSecond));
					
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
