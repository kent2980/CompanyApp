package test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;

public class StartUpPanel extends JDialog {
	public static void main(String[] args) {
		StartUpPanel st = new StartUpPanel();
		st.setVisible(true);
	}
	StartUpPanel(){
		this.setBounds(200, 200, 280, 60);
		this.setUndecorated(true);
		JPanel p = new JPanel();
		BevelBorder border = new BevelBorder(BevelBorder.RAISED);
		p.setBorder(border);
		SpringLayout layout = new SpringLayout();
		p.setLayout(layout);
		JLabel l = new JLabel("起動中です  しばらくお待ちください．．．");
		layout.putConstraint(SpringLayout.NORTH, l, 10, SpringLayout.NORTH, p);
		layout.putConstraint(SpringLayout.WEST, l, 10, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.EAST, l, -10, SpringLayout.EAST, p);
		layout.putConstraint(SpringLayout.SOUTH, l, -10, SpringLayout.SOUTH, p);
		p.add(l);
		this.getContentPane().add(p,BorderLayout.CENTER);
	}
}
