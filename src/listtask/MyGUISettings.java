package listtask;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MyGUISettings extends JFrame {
	JTextField textFieldTimerWork;
	JTextField textFieldMiniBrake;
	JTextField textFieldBigBrake;
	JTextField textFieldPeriod;
	
	public MyGUISettings() {
		super("Настройки");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(100, 100);
		setLocationRelativeTo(null);
		setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
		this.setResizable(false);
		setVisible(true);
		
		Box box1 = Box.createHorizontalBox();
		JLabel label_4 = new JLabel("Время работы:");
		box1.add(label_4);
		textFieldTimerWork = new JTextField();
		textFieldTimerWork.setColumns(10);
		box1.add(textFieldTimerWork);
		
		Box box2 = Box.createHorizontalBox();
		JLabel label_5 = new JLabel("Маленький перерыв:");
		box2.add(label_5);
		textFieldMiniBrake = new JTextField();
		textFieldMiniBrake.setColumns(10);
		box2.add(textFieldMiniBrake);
		
		Box box3 = Box.createHorizontalBox();
		JLabel label_6 = new JLabel("Большой перерыв:");
		box3.add(label_6);
		textFieldBigBrake = new JTextField();
		textFieldBigBrake.setColumns(10);
		box3.add(textFieldBigBrake);
		
		Box box4 = Box.createHorizontalBox();
		JLabel label_7 = new JLabel("Периодов до большого перерыва:");
		box4.add(label_7);
		textFieldPeriod = new JTextField();
		textFieldPeriod.setColumns(10);
		box4.add(textFieldPeriod);
		
		Box box5 = Box.createHorizontalBox();
		JButton okButton = new JButton("ОК");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		box5.add(okButton);
		JButton cancelButton = new JButton("Отмена");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
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
	}
}
