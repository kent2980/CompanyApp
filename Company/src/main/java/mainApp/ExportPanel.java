package mainApp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;

import setting.ExcelWrite;

public class ExportPanel extends JPanel implements ActionListener{
	private final SqliteAccess sq = new SqliteAccess();
	private final JLabel inputlabel = new BorderLabel("入力ファイル");
	private final JLabel outputlabel = new BorderLabel("出力フォルダ");
	private final JLabel inputpath = new JLabel();
	private final JLabel outputpath = new JLabel();
	private final JButton sansho = new JButton("参照");
	private final JButton sansho2 = new JButton("参照");
	private final JButton export = new JButton("エクスポート");
	private final JComboBox tanaban = new JComboBox();
	private JRadioButton check1 = new JRadioButton("TCM"),check2 = new JRadioButton("NXT")
			,check3 = new JRadioButton("GX"),check4 = new JRadioButton("TIM");
	ExportPanel(){
		
		//レイアウトマネージャー
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		
		//「入力ファイル」ラベル
		layout.putConstraint(SpringLayout.NORTH, inputlabel, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, inputlabel, 10, SpringLayout.WEST, this);
		this.add(inputlabel);
		
		//「入力ファイル」参照ボタン
		layout.putConstraint(SpringLayout.NORTH, sansho, 0, SpringLayout.NORTH, inputlabel);
		layout.putConstraint(SpringLayout.WEST, sansho, -100, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.EAST, sansho, -20, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, sansho, 0, SpringLayout.SOUTH, inputlabel);
		this.add(sansho);
		sansho.addActionListener(this);
		
		//入力ファイルパス表示ラベル
		inputpath.setBackground(Color.WHITE);
		inputpath.setOpaque(true);
		inputpath.setFont(new Font("meiryo.ttc",Font.BOLD,12));
		inputpath.setBorder(new BevelBorder(BevelBorder.LOWERED));
		inputpath.setText(sq.getSettingPath("INPUT"));
		layout.putConstraint(SpringLayout.NORTH, inputpath, 10, SpringLayout.SOUTH, inputlabel);
		layout.putConstraint(SpringLayout.WEST, inputpath, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, inputpath, -20, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, inputpath, 35, SpringLayout.NORTH, inputpath);
		this.add(inputpath);
		
		//「出力ファイル」ラベル
		layout.putConstraint(SpringLayout.NORTH, outputlabel, 20, SpringLayout.SOUTH, inputpath);
		layout.putConstraint(SpringLayout.WEST, outputlabel, 10, SpringLayout.WEST, this);
		this.add(outputlabel);
		
		//「出力ファイル」参照ボタン
		layout.putConstraint(SpringLayout.NORTH, sansho2, 0, SpringLayout.NORTH, outputlabel);
		layout.putConstraint(SpringLayout.WEST, sansho2, -100, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.EAST, sansho2, -20, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, sansho2, 0, SpringLayout.SOUTH, outputlabel);
		this.add(sansho2);
		sansho2.addActionListener(this);
		
		//出力ファイルパス表示ラベル
		outputpath.setBackground(Color.WHITE);
		outputpath.setOpaque(true);
		outputpath.setFont(new Font("meiryo.ttc",Font.BOLD,12));
		outputpath.setBorder(new BevelBorder(BevelBorder.LOWERED));
		outputpath.setText(sq.getSettingPath("OUTPUT"));
		layout.putConstraint(SpringLayout.NORTH, outputpath, 10, SpringLayout.SOUTH, outputlabel);
		layout.putConstraint(SpringLayout.WEST, outputpath, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, outputpath, -20, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, outputpath, 35, SpringLayout.NORTH, outputpath);
		this.add(outputpath);
		

		//棚番選択ラベル
		JLabel tanalabel = new JLabel("棚番選択");
		tanalabel.setFont(new Font("meiryo.ttc",Font.BOLD,14));
		layout.putConstraint(SpringLayout.NORTH, tanalabel, 30, SpringLayout.SOUTH, outputpath);
		layout.putConstraint(SpringLayout.WEST, tanalabel, 30, SpringLayout.WEST, this);
		this.add(tanalabel);
		//棚番選択ボックス
		ArrayList<String> tanalist = new ArrayList<>();
		for(int i=1;i<=30;i++) {
			tanalist.add("GC" + String.format("%02d", i));
			tanalist.add("GT" + String.format("%02d", i));
		}
		Collections.sort(tanalist);
		tanalist.add(0, "指定なし");
		String[] tanaarray = tanalist.toArray(new String[tanalist.size()]);
		DefaultComboBoxModel model = new DefaultComboBoxModel(tanaarray);
		tanaban.setModel(model);
		tanaban.setFont(new Font("meiryo.ttc",Font.BOLD,18));
		layout.putConstraint(SpringLayout.NORTH, tanaban, 30, SpringLayout.SOUTH, tanalabel);
		layout.putConstraint(SpringLayout.WEST, tanaban, 100, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, tanaban, -100, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, tanaban, 40, SpringLayout.NORTH, tanaban);
		this.add(tanaban);
		
		//マシン選択ラベル
		JLabel mclabel = new JLabel("マシン選択");
		mclabel.setFont(new Font("meiryo.ttc",Font.BOLD,14));
		layout.putConstraint(SpringLayout.NORTH, mclabel, 30, SpringLayout.SOUTH, tanaban);
		layout.putConstraint(SpringLayout.WEST, mclabel, 30, SpringLayout.WEST, this);
		this.add(mclabel);
		//マシン選択チェックボックス
		ButtonGroup bg = new ButtonGroup();
		bg.add(check1);bg.add(check2);bg.add(check3);bg.add(check4);
		JPanel p1 = new JPanel();
		FlowLayout layout_p1 = new FlowLayout();
		p1.setLayout(layout_p1);
		p1.add(check1);p1.add(check2);p1.add(check3);p1.add(check4);
		layout.putConstraint(SpringLayout.NORTH, p1, 10, SpringLayout.SOUTH, mclabel);
		layout.putConstraint(SpringLayout.WEST, p1, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, p1, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, p1, 30, SpringLayout.NORTH, p1);
		this.add(p1);
		
		//エクスポートボタン
		layout.putConstraint(SpringLayout.NORTH, export, 40, SpringLayout.SOUTH, p1);
		layout.putConstraint(SpringLayout.WEST, export, 80, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, export, -80, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, export, 50, SpringLayout.NORTH, export);
		this.add(export);
		export.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(sansho)) {
			JFileChooser file = new JFileChooser();
			file.showOpenDialog(this);
			File f = file.getSelectedFile();
			sq.setSettingPath("INPUT", f.getPath());
			inputpath.setText(f.getPath());
		}else if(e.getSource().equals(sansho2)){
			JFileChooser file = new JFileChooser();
			file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			file.showOpenDialog(this);
			File f = file.getSelectedFile();
			sq.setSettingPath("OUTPUT", f.getPath() + "\\");
			outputpath.setText(f.getPath() + "\\");
		}else if(e.getSource().equals(export)) {
			String tanaName = tanaban.getSelectedItem().toString();
			int mccode = check1.isSelected()?1:check2.isSelected()?2:check3.isSelected()?3:check4.isSelected()?4:0;
			ExcelWrite ex = new ExcelWrite(tanaName,mccode);
		}
	}
}
class BorderLabel extends JLabel{
	BorderLabel(String s){
		setText(s);
		setFont(new Font("meiryo.ttc",Font.BOLD,12));
		setBorder(new BevelBorder(BevelBorder.RAISED));
		setPreferredSize(new Dimension(100,30));
		setHorizontalAlignment(JLabel.CENTER);
	}
}