package test;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class ResetDialog extends JDialog{
	ResetDialog(final String tanaban,final int mcCode){
		this.setBounds(330, 320, 200, 150);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		JPanel p1 = new JPanel();
		SpringLayout layout = new SpringLayout();
		p1.setLayout(layout);
		JLabel l1 = new JLabel("本当にリセットしますか？");
		l1.setFont(new Font("meiryo.ttc",Font.BOLD,12));
		layout.putConstraint(SpringLayout.NORTH, l1, 20, SpringLayout.NORTH, p1);
		layout.putConstraint(SpringLayout.WEST, l1, 15, SpringLayout.WEST, p1);
		p1.add(l1);
		JButton b1 = new JButton("OK");
		layout.putConstraint(SpringLayout.NORTH, b1, 60, SpringLayout.NORTH, p1);
		layout.putConstraint(SpringLayout.WEST, b1, 50, SpringLayout.WEST, p1);
		layout.putConstraint(SpringLayout.EAST, b1, -50, SpringLayout.EAST, p1);
		layout.putConstraint(SpringLayout.SOUTH, b1, 30, SpringLayout.NORTH, b1);
		p1.add(b1);
		this.getContentPane().add(p1);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SqliteAccess sq = new SqliteAccess();
				System.out.println(mcCode);
				if(tanaban.equals("指定なし")) {
					sq.deletePartsData(mcCode);
				}else if(mcCode==0) {
					sq.deletePartsData(tanaban);
				}else {
					sq.deletePartsData(tanaban,mcCode);
				}
				setViewFalse();
			}		
		});
	}
	private void setViewFalse() {
		this.setVisible(false);
	}
}
