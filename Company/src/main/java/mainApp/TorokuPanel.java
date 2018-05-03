package mainApp;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
class TorokuPanel extends JPanel implements MouseListener{
	
	private SqliteAccess sq = new SqliteAccess();
	private JTextField text1 = new JTextField();
	private JTextField text2 = new JTextField();
	private JButton button1 = new JButton("登録");
	private JLabel l1 = new JLabel();
	@SuppressWarnings("rawtypes")
	private JComboBox tana;
	private JLabel l2 = new JLabel();
	private JRadioButton check1 = new JRadioButton("TCM"),check2 = new JRadioButton("NXT")
			,check3 = new JRadioButton("GX"),check4 = new JRadioButton("TIM");
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	TorokuPanel() throws SQLException {
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		/**
		 * 棚番選択ボックス
		 */
		//ラベル
		JLabel tanalabel = new JLabel("棚番");
		tanalabel.setFont(new Font("meiryo.ttc",Font.BOLD,14));
		layout.putConstraint(SpringLayout.NORTH, tanalabel, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, tanalabel, 30, SpringLayout.WEST, this);
		this.add(tanalabel);
		//コンボボックス
		ArrayList<String> tanalist = new ArrayList<>();
		for(int i = 1;i<=30;i++) {
			tanalist.add("GC" + String.format("%02d", i));
			tanalist.add("GT" + String.format("%02d", i));
		}
		Collections.sort(tanalist);
		tanalist.add(0, "指定なし");
		String[] tanabango = tanalist.toArray(new String[tanalist.size()]);		
		tana = new JComboBox(tanabango);
		tana.setFont(new Font("meiryo.ttc",Font.BOLD,14));
		layout.putConstraint(SpringLayout.NORTH, tana, 5, SpringLayout.SOUTH, tanalabel);
		layout.putConstraint(SpringLayout.WEST, tana, 30, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, tana, 140, SpringLayout.WEST, tana);
		layout.putConstraint(SpringLayout.SOUTH, tana, 40, SpringLayout.NORTH, tana);
		this.add(tana);
		/**
		 * マシン選択チェックボックス
		 */
		//ラベル
		JLabel mclabel = new JLabel("マシン選択");
		mclabel.setFont(new Font("meiryo.ttc",Font.BOLD,14));
		layout.putConstraint(SpringLayout.NORTH, mclabel, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, mclabel, 30, SpringLayout.EAST, tana);
		this.add(mclabel);
		//チェックボックス
		ButtonGroup bg = new ButtonGroup();
		bg.add(check1);bg.add(check2);bg.add(check3);bg.add(check4);
		JPanel p1 = new JPanel();
		FlowLayout layout_p1 = new FlowLayout();
		p1.setLayout(layout_p1);
		p1.add(check1);p1.add(check2);p1.add(check3);p1.add(check4);
		layout.putConstraint(SpringLayout.NORTH, p1, 40, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, p1, 10, SpringLayout.EAST, tana);
		layout.putConstraint(SpringLayout.EAST, p1, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, p1, 0, SpringLayout.SOUTH, tana);
		this.add(p1);
		/**
		 * ラベル（部品コード）
		 */
		JLabel clip_p = new JLabel("部品コード");
		layout.putConstraint(SpringLayout.NORTH, clip_p, 10, SpringLayout.SOUTH, tana);
		layout.putConstraint(SpringLayout.WEST, clip_p, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, clip_p, 100, SpringLayout.WEST, clip_p);
		layout.putConstraint(SpringLayout.SOUTH, clip_p, 25, SpringLayout.NORTH, clip_p);
		this.add(clip_p);
		/**
		/**
		 * テキストフィールド（部品コード）
		 */
		text1.setHorizontalAlignment(JTextField.CENTER);
		text1.setFont(new Font("meiryo.ttc",Font.BOLD,18));
		layout.putConstraint(SpringLayout.NORTH, text1, 2, SpringLayout.SOUTH, clip_p);
		layout.putConstraint(SpringLayout.WEST, text1, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, text1, -20, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, text1, 60, SpringLayout.NORTH, text1);
		this.add(text1);
		text1.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	text2.requestFocus(); 
	        }
	    });
		text1.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				text1.setText("");
				JComponent component = ( JComponent )( e.getSource() );
				component.getInputContext().setCharacterSubsets( null );
			}
			@Override
			public void focusLost(FocusEvent e) {
				text1.setText(text1.getText().toUpperCase().replace("-", ""));
				if(text1.getText().length()!=0 && text1.getText().substring(0, 3).equals("3N1"))
				text1.setText(text1.getText().substring(3,text1.getText().indexOf(" ")));
			}
		});
		/**
		 * ラベル（クリップ番号）
		 */
		JLabel clip_l = new JLabel("クリップ番号");
		layout.putConstraint(SpringLayout.NORTH, clip_l, 10, SpringLayout.SOUTH, text1);
		layout.putConstraint(SpringLayout.WEST, clip_l, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, clip_l, 100, SpringLayout.WEST, clip_l);
		layout.putConstraint(SpringLayout.SOUTH, clip_l, 25, SpringLayout.NORTH, clip_l);
		this.add(clip_l);
		/**
		 * テキストフィールド（クリップ番号）
		 */
		text2.setHorizontalAlignment(JTextField.CENTER);
		text2.setFont(new Font("meiryo.ttc",Font.BOLD,18));
		layout.putConstraint(SpringLayout.NORTH, text2, 2, SpringLayout.SOUTH, clip_l);
		layout.putConstraint(SpringLayout.WEST, text2, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, text2, -20, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, text2, 60, SpringLayout.NORTH, text2);
		this.add(text2);
		text2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button1.requestFocus();
			}
			
		});
		text2.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				text2.setText("");
				JComponent component = ( JComponent )( e.getSource() );
				component.getInputContext().setCharacterSubsets( null );
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(text1.getText().length()>0 && text2.getText().length()>0) {
					setDataToroku();
					text1.requestFocus();
				}
			}
		});
		/**
		 * 登録ボタン
		 */
		button1.setBackground(new Color(220,220,220));
		layout.putConstraint(SpringLayout.NORTH, button1, 20, SpringLayout.SOUTH, text2);
		layout.putConstraint(SpringLayout.WEST, button1, 40, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, button1, -40, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, button1, 40, SpringLayout.NORTH, button1);
		this.add(button1);
		button1.addMouseListener(this);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setDataToroku();
				text1.requestFocus();
			}
		});
		button1.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(KeyEvent.VK_ENTER == e.getKeyCode()) {
					setDataToroku();
					text1.requestFocus();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		/**
		 * OK/NG ラベル
		 */
		layout.putConstraint(SpringLayout.NORTH, l2, 5, SpringLayout.NORTH, l1);
		layout.putConstraint(SpringLayout.WEST, l2, 5, SpringLayout.WEST, l1);
		layout.putConstraint(SpringLayout.EAST, l2, 80, SpringLayout.WEST, l2);
		layout.putConstraint(SpringLayout.SOUTH, l2, 30, SpringLayout.NORTH, l2);
		this.add(l2);
		l2.setVisible(false);
		/**
		 * ラベル ---> 登録しました。
		 */		
		l1.setHorizontalAlignment(JTextField.CENTER);
		l1.setBackground(Color.WHITE);
		l1.setOpaque(true);
		l1.setBorder(new LineBorder(Color.BLACK,1,true));
		l1.setFont(new Font("meiryo.ttc",Font.BOLD,18));
		layout.putConstraint(SpringLayout.NORTH, l1, 20, SpringLayout.SOUTH, button1);
		layout.putConstraint(SpringLayout.WEST, l1, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, l1, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, l1, -10, SpringLayout.SOUTH, this);
		this.add(l1);
	}
	
	/**
	 * データ登録メソッド
	 * @param l1
	 */
	private void setDataToroku() {
		//正規分岐
		if(text1.getText().length()>0 && text2.getText().length()==3 && 
				isStringNumber(text2.getText()) && tana.getSelectedItem().toString().length()>0 && 
				(check1.isSelected() || check2.isSelected() || check3.isSelected() || check4.isSelected())) {
			String tanaban = tana.getSelectedItem().toString();
			String parts = text1.getText();
			int bango = Integer.parseInt(text2.getText());
			int mcCode = check1.isSelected()?1:check2.isSelected()?2:check3.isSelected()?3:check4.isSelected()?4:0;
			l1.setText(tanaban + " ---> " + bango + " = " + parts);
			text1.setText("");text2.setText("");
			sq.setPartsData(tanaban, parts, bango, mcCode);
		}else if(tana.getSelectedItem().toString().equals("指定なし")) {
			l1.setText("棚番を指定してください。");
			text2.setText("");
		}else if((check1.isSelected()||check2.isSelected()||check3.isSelected()||check4.isSelected())==false) {
			l1.setText("マシン名を指定してください。");
			text2.setText("");
		}else if(text1.getText().length()==0 && text2.getText().length()>0) {
			l1.setText("部品コードを入力してください。");
			text2.setText("");
		//クリップ番号が入力されていない場合
		}else if(text1.getText().length()>0 && text2.getText().length()==0) {
			l1.setText("クリップ番号を入力してください。");
			text1.setText("");
		//何も入力されていない場合
		}else if(text1.getText().length()==0 && text2.getText().length()==0) {
			l1.setText("何も入力されていません。");
		//クリップ番号が数字ではない場合
		}else if(isStringNumber(text2.getText())==false || text2.getText().length()!=3) {
			l1.setText("クリップ番号を数字(3桁）で入力してください。");
			text1.setText("");text2.setText("");
		}
	}
	
	private boolean isStringNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}
	public void setText1Focus() {
		text1.requestFocus();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource()==button1)
		button1.setBackground(new Color(155,201,222));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource()==button1)
		button1.setBackground(new Color(180,190,222));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource()==button1)
		button1.setBackground(new Color(180,190,222));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource()==button1)
			button1.setBackground(new Color(220,220,220));
	}
}


