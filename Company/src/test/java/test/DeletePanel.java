package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;

class DeletePanel extends JPanel implements FocusListener,ActionListener,ItemListener{
	
	//****** フィールドの宣言をします。  ******
	private final JLabel totana = new LabelFormat("棚番");
	private final JLabel tomc = new LabelFormat("マシン");
	private final CautionLabel tanaCaution = new CautionLabel("※必須");
	private final CautionLabel mcCaution = new CautionLabel("※必須");
	private final JComboBox tanaName = new ComboBoxFormat();
	private final JComboBox mcName = new ComboBoxFormat();
	private final JButton reset = new JButton("リセット");
	private final JTextField inputBox = new JTextField();
	private final JButton delete = new JButton("削除");
	
	/**
	 * デフォルトコンストラクタ
	 */
	DeletePanel(){
		
	//レイアウトを設置します。
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		
	//コンポーネントを配置します。
		//--- 棚番選択コンボボックス
		/** ラベル **/
		layout.putConstraint(SpringLayout.NORTH, totana, 20, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, totana, 20, SpringLayout.WEST, this);
		this.add(totana);
		/** コンポーネント **/
		List<String> tanaList = new ArrayList<>();
		for(int i=1;i<=30;i++) {
			tanaList.add("GC" + String.format("%02d", i));
			tanaList.add("GT" + String.format("%02d", i));
		}
		Collections.sort(tanaList);
		tanaList.add(0, "指定なし");
		String[] tanaArray = tanaList.toArray(new String[tanaList.size()]);
		DefaultComboBoxModel tanaModel = new DefaultComboBoxModel(tanaArray);
		tanaName.setModel(tanaModel);
		layout.putConstraint(SpringLayout.NORTH, tanaName, 0, SpringLayout.NORTH, totana);
		layout.putConstraint(SpringLayout.WEST, tanaName, 100, SpringLayout.WEST, this);
		this.add(tanaName);
		tanaName.addItemListener(this);
		/** 必須項目ラベル **/
		layout.putConstraint(SpringLayout.NORTH, tanaCaution, 0, SpringLayout.NORTH, totana);
		layout.putConstraint(SpringLayout.WEST, tanaCaution, 10, SpringLayout.EAST, tanaName);
		layout.putConstraint(SpringLayout.EAST, tanaCaution, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, tanaCaution, 0, SpringLayout.SOUTH, tanaName);
		this.add(tanaCaution);
		
		//マシン選択コンボボックス
		/** ラベル **/
		layout.putConstraint(SpringLayout.NORTH, tomc, 30, SpringLayout.SOUTH, totana);
		layout.putConstraint(SpringLayout.WEST, tomc, 20, SpringLayout.WEST, this);
		this.add(tomc);
		/** コンポーネント  **/
		mcName.addItem("指定なし");
		mcName.addItem("TCM");mcName.addItem("NXT");mcName.addItem("GX");mcName.addItem("TIM");
		layout.putConstraint(SpringLayout.NORTH, mcName, 0, SpringLayout.NORTH, tomc);
		layout.putConstraint(SpringLayout.WEST, mcName, 100, SpringLayout.WEST, this);
		this.add(mcName);
		mcName.addItemListener(this);
		/** 必須項目ラベル **/
		layout.putConstraint(SpringLayout.NORTH, mcCaution, 0, SpringLayout.NORTH, tomc);
		layout.putConstraint(SpringLayout.WEST, mcCaution, 10, SpringLayout.EAST, mcName);
		layout.putConstraint(SpringLayout.EAST, mcCaution, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, mcCaution, 0, SpringLayout.SOUTH, mcName);
		this.add(mcCaution);
		
		//リセットボタン
		/** アクションイベント **/
		reset.addActionListener(this);
		/** コンポーネント **/
		layout.putConstraint(SpringLayout.NORTH, reset, 20, SpringLayout.SOUTH, mcName);
		layout.putConstraint(SpringLayout.WEST, reset, 40, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, reset, -40, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, reset, 40, SpringLayout.NORTH, reset);
		this.add(reset);
		
		//インプットボックス
		inputBox.setHorizontalAlignment(JTextField.CENTER);
		inputBox.setFont(new Font("meiryo.ttc",Font.BOLD,18));
		layout.putConstraint(SpringLayout.NORTH, inputBox, 20, SpringLayout.SOUTH, reset);
		layout.putConstraint(SpringLayout.WEST, inputBox, 30, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, inputBox, -30, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, inputBox, 80, SpringLayout.NORTH, inputBox);
		this.add(inputBox);
		inputBox.addFocusListener(this);
		inputBox.addActionListener(this);
		
		//削除ボタン
		delete.setFont(new Font("meiryo.ttc",Font.BOLD,14));
		layout.putConstraint(SpringLayout.NORTH, delete, 20, SpringLayout.SOUTH, inputBox);
		layout.putConstraint(SpringLayout.WEST, delete, 40, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, delete, -40, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, delete, 40, SpringLayout.NORTH, delete);
		this.add(delete);
		delete.addActionListener(this);
		delete.addFocusListener(this);
	}
	public void setFocusInputBox(){
		inputBox.requestFocus();
	}
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource().equals(inputBox))
			inputBox.setText("");		
		if(e.getSource().equals(delete))
			inputBox.requestFocus();
	}
	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource().equals(inputBox)) {
			SqliteAccess sq = new SqliteAccess(); 
			String tananame = tanaName.getSelectedItem().toString();
			int mccode = mcName.getSelectedIndex();
			inputBox.setText(inputBox.getText().toUpperCase());
			if(new NumberIs().isNumber(inputBox.getText())==true && inputBox.getText().length()==3) {
				sq.deletePartsData(tananame, mccode, Integer.parseInt(inputBox.getText()));
			}else if(inputBox.getText().length()>0 && inputBox.getText().substring(0, 3).equals("3N1")) {
				inputBox.setText(inputBox.getText().substring(3,inputBox.getText().indexOf(" ")));
				sq.deletePartsData(tananame, inputBox.getText());
			}else {
				sq.deletePartsData(tananame, inputBox.getText());
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(inputBox)) {
			delete.requestFocus();
		}else if(e.getSource().equals(delete)) {
			inputBox.requestFocus();
		}else if(e.getSource().equals(reset)) {
			final String toTanaName = tanaName.getSelectedItem().toString();
			final int mcCode = mcName.getSelectedItem().toString().equals("TCM")?1:
							   mcName.getSelectedItem().toString().equals("NXT")?2:
							   mcName.getSelectedItem().toString().equals("GX")?3:
							   mcName.getSelectedItem().toString().equals("TIM")?4:0;
			new ResetDialog(toTanaName,mcCode);
		}
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		//棚番選択ボックス
		if(e.getSource().equals(tanaName)) {
			if(tanaName.getSelectedItem().toString().equals("指定なし")) {
				tanaCaution.setNGLabel();
			}else {
				tanaCaution.setOKLabel();
			}
		}
		//マシン選択ボックス
		if(e.getSource().equals(mcName)) {
			if(mcName.getSelectedItem().toString().equals("指定なし")) {
				mcCaution.setNGLabel();
			}else {
				mcCaution.setOKLabel();
			}
		}
	}
}

class LabelFormat extends JLabel{
	LabelFormat(String s){
		this.setPreferredSize(new Dimension(60,40));
		this.setText(s);
		this.setFont(new Font("meiryo.ttf",Font.BOLD,18));
	}
}
class ComboBoxFormat extends JComboBox{
	ComboBoxFormat(){
		this.setFont(new Font("meiryo.ttc",Font.BOLD,17));
		this.setPreferredSize(new Dimension(200,40));
	}
}
class CautionLabel extends JLabel{
	CautionLabel(String s){
		setText(s);
		setVerticalAlignment(JLabel.CENTER);
		setOpaque(false);
		setForeground(Color.RED);
		setFont(new Font("meiryo.ttc",Font.BOLD,12));
		setHorizontalAlignment(JLabel.CENTER);
	}
	void setNGLabel() {
		setText("※必須");
		setForeground(Color.RED);
		setFont(new Font("meiryo.ttc",Font.BOLD,12));
	}
	void setOKLabel() {
		setText("OK!");
		setForeground(Color.BLUE);
		setFont(new Font("meiryo.ttc",Font.BOLD,14));
	}
}
class NumberIs{
	public boolean isNumber(String num) {
	    try {
	        Integer.parseInt(num);
	        return true;
	        } catch (NumberFormatException e) {
	        return false;
	    }
	}
}