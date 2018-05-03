package test;

import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class MainApp extends JFrame {
	public static void main(String[] args) {
		try {
			StartUpPanel st = new StartUpPanel();
			st.setVisible(true);
			MainApp app = new MainApp();
			st.setVisible(false);
			app.setVisible(true);
		}catch(SQLException e) {
			new JFrame().setVisible(true);
		}
	}

	public MainApp() throws SQLException {
		this.setTitle("クリップ登録！");
		this.setBounds(200, 100, 450, 600);
		this.getContentPane().add(new TabPanel());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

@SuppressWarnings("serial")
class TabPanel extends JTabbedPane implements ChangeListener{
	private EtsuranPanel p1 = new EtsuranPanel();
	private TorokuPanel p2 = new TorokuPanel();
	private DeletePanel p3 = new DeletePanel();
	private ExportPanel p4 = new ExportPanel();
	TabPanel() throws SQLException{		
		this.add("閲覧", p1);
		this.add("登録", p2);
		this.add("削除", p3);
		this.add("エクスポート", p4);
		this.setFont(new Font("meiryo.ttf",Font.BOLD,12));
		this.addChangeListener(this);
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		if(this.getSelectedIndex()==1)
			p2.setText1Focus();
		if(this.getSelectedIndex()==2)
			p3.setFocusInputBox();
	}
}
