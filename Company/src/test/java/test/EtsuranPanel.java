package test;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
class EtsuranPanel extends JPanel implements ActionListener,FocusListener{
	
	private DefaultTableModel model;
	private JTable t;
	private JTextField sertch;
	private JButton koshin;
	private SqliteAccess sq = new SqliteAccess();
	@SuppressWarnings("rawtypes")
	private JComboBox menu;
	private String[] columnname = {"No","部品コード","棚番"};
	private JScrollPane sp;
	private JRadioButton check1 = new JRadioButton("TCM"),check2 = new JRadioButton("NXT")
			,check3 = new JRadioButton("GX"),check4 = new JRadioButton("TIM");
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	EtsuranPanel() throws SQLException{
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		/**
		 * 棚番表示ラベル
		 */
		JLabel l1 = new JLabel("棚番");
		layout.putConstraint(SpringLayout.NORTH, l1, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, l1, 30, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, l1, 50, SpringLayout.WEST, l1);
		layout.putConstraint(SpringLayout.SOUTH, l1, 25, SpringLayout.NORTH, l1);
		this.add(l1);
		/**
		 * 棚番選択コンボボックス
		 */
		List<String> tana = new ArrayList<>();
		for(int i = 1;i<=30;i++) {
			tana.add("GC" + String.format("%02d", i));
			tana.add("GT" + String.format("%02d", i));
		}
		Collections.sort(tana);
		tana.add(0, "指定なし");
		String[] tanaban = new String[tana.size()];
		for(int i = 0;i<tana.size();i++) {
			tanaban[i] = tana.get(i);
		}
		menu = new JComboBox(tanaban);
		layout.putConstraint(SpringLayout.NORTH, menu, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, menu, -5, SpringLayout.EAST, l1);
		layout.putConstraint(SpringLayout.EAST, menu, 120, SpringLayout.WEST, menu);
		layout.putConstraint(SpringLayout.SOUTH, menu, 25, SpringLayout.NORTH, menu);
		this.add(menu);
		menu.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				setTableData();
			}
			
		});
		JPanel checkpanel = new JPanel();
		FlowLayout layout2 = new FlowLayout();
		checkpanel.setLayout(layout2);
		check1.setSelected(true);
		ButtonGroup bg = new ButtonGroup();
		bg.add(check1);bg.add(check2);bg.add(check3);bg.add(check4);
		checkpanel.add(check1);
		checkpanel.add(check2);
		checkpanel.add(check3);
		checkpanel.add(check4);
		layout.putConstraint(SpringLayout.NORTH, checkpanel, 5, SpringLayout.SOUTH, menu);
		layout.putConstraint(SpringLayout.WEST, checkpanel, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, checkpanel, 260, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, checkpanel, 30, SpringLayout.NORTH, checkpanel);
		this.add(checkpanel);
		check1.addActionListener(this);
		check2.addActionListener(this);
		check3.addActionListener(this);
		check4.addActionListener(this);
		/**
		 * 表示テーブル
		 */
		model = new DefaultTableModel(columnname,0);
		t = new JTable(model);
		t.setDefaultEditor(Object.class, null);
		sp = new JScrollPane(t);
		DefaultTableColumnModel columnmodel = (DefaultTableColumnModel) t.getColumnModel();
		columnmodel.getColumn(2).setMaxWidth(60);columnmodel.getColumn(2).setMinWidth(60);
		t.setFont(new Font("meiryo.ttc",Font.BOLD,16));
		t.setRowHeight(47);
		DefaultTableColumnModel column = (DefaultTableColumnModel)t.getColumnModel();
		column.getColumn(0).setMinWidth(80);
		column.getColumn(0).setMaxWidth(80);
		layout.putConstraint(SpringLayout.NORTH, sp, 10, SpringLayout.SOUTH, checkpanel);
		layout.putConstraint(SpringLayout.WEST, sp, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, sp, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, sp, -10, SpringLayout.SOUTH, this);
		this.add(sp);
		/**
		 * サーチパネル
		 */
		sertch = new JTextField("部品検索");
		layout.putConstraint(SpringLayout.NORTH, sertch, 5, SpringLayout.NORTH, checkpanel);
		layout.putConstraint(SpringLayout.WEST, sertch, 0, SpringLayout.EAST, checkpanel);
		layout.putConstraint(SpringLayout.EAST, sertch, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, sertch, 0, SpringLayout.SOUTH, checkpanel);
		this.add(sertch);
		sertch.addActionListener(this);
		sertch.addFocusListener(this);
		sertch.setBackground(new Color(245,252,255));
		sertch.setHorizontalAlignment(JTextField.CENTER);
		/**
		 * リセットボタン
		 */
		JButton reset = new JButton("リセット");
		reset.setFont(new Font("meiryo.ttc",Font.BOLD,12));
		layout.putConstraint(SpringLayout.NORTH, reset, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, reset, 20, SpringLayout.EAST, menu);
		layout.putConstraint(SpringLayout.EAST, reset, 100, SpringLayout.WEST, reset);
		layout.putConstraint(SpringLayout.SOUTH, reset, 25, SpringLayout.NORTH, reset);
		this.add(reset);
		reset.addActionListener(new ActionListener() {

			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				String tanaban = menu.getSelectedItem().toString();
				int mcCode = check1.isSelected()?1:check2.isSelected()?2:check3.isSelected()?3:check4.isSelected()?4:0;
				ResetDialog resetbutton = new ResetDialog(tanaban,mcCode);
			}
			
		});
		/**
		 * 更新ボタン
		 */
		koshin = new JButton("更新");
		koshin.setFont(new Font("meiryo.ttc",Font.BOLD,12));
		layout.putConstraint(SpringLayout.NORTH, koshin, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, koshin, 10, SpringLayout.EAST, reset);
		layout.putConstraint(SpringLayout.EAST, koshin, 70, SpringLayout.WEST, koshin);
		layout.putConstraint(SpringLayout.SOUTH, koshin, 25, SpringLayout.NORTH, koshin);
		this.add(koshin);
		koshin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(koshin)) {
					setTableData();
				}
			}
			
		});
	}
	private void setTableData() {
		model.setRowCount(0);
		int mcCode = check1.isSelected()?1:check2.isSelected()?2:check3.isSelected()?3:check4.isSelected()?4:0;
		String tanaName = menu.getSelectedItem().toString();
		for(int j = 101;j<=999;j++) {
			String[] slist = new String[3];
			slist[0] = "      " + String.valueOf(j);
			if(tanaName.equals("指定なし")) {
				slist[1] = ("  " + sq.getPartsName(j, mcCode)).replace("null", "");
				slist[2] = ("  " + sq.getTanaNAme(j, mcCode)).replace("null", "");
			}else {
				slist[1] = ("  " + sq.getPartsName(tanaName,j, mcCode)).replace("null", "");
				slist[2] = ("  " + sq.getTanaNAme(tanaName, j, mcCode)).replace("null", "");
			}
			model.addRow(slist);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(check1)||e.getSource().equals(check2)||
			e.getSource().equals(check3)||e.getSource().equals(check4)) {
			setTableData();
		}else if(e.getSource().equals(sertch)) {
			sertch.setText(sertch.getText().toUpperCase());
			setScroll(sp.getVerticalScrollBar(),t,sertch.getText().toUpperCase().trim());
		}
	}
	private void setScroll(JScrollBar scroll,JTable T,String S) {
		int t=-1;
		for(int i = 0;i<899;i++) {
			String ss = String.valueOf(T.getValueAt(i, 1)).trim();
			if(ss.equals(S)&&ss.isEmpty()==false) {
				t=i;
				T.setRowSelectionInterval(t,t);
				int maxvalue = scroll.getMaximum();
				scroll.setValue(maxvalue/898*t);
			}
		}
		if(t==-1) {
			//new Dialog1();
		}
	}
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource().equals(sertch)) {
			sertch.setText("");
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		
	}
}
class Dialog1 extends JDialog{
	Dialog1(){
		this.setBounds(280,200,250,100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JPanel p1 = new JPanel();
		this.getContentPane().add(p1);
		SpringLayout layout = new SpringLayout();
		p1.setLayout(layout);
		JLabel l1 = new JLabel("入力した部品が見つかりません");
		layout.putConstraint(SpringLayout.NORTH, l1, 10, SpringLayout.NORTH, p1);
		p1.add(l1);
	}
}
