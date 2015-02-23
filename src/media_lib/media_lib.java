/**
 * 
 */
package media_lib;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

/**
 * @author bluefire
 *
 */
public class media_lib {
	static JTextArea console;
	static JScrollPane listScroller;
	
	static GUI_search myGUI_s;
	static GUI_library myGUI_l;
	static GUI_settings myGUI_set;
	static Pref myPref;
	static mydb my_db;

	static JTabbedPane tabbedPane;
	
	static JPanel mainpanel;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		myPref = new Pref();

		console = new JTextArea();
		console.setAutoscrolls(true);
		console.setColumns(80);
		
		listScroller = new JScrollPane(console);
		listScroller.setPreferredSize(new Dimension(myPref.getPreferredSize().width-20, myPref.getPreferredSize().height-645));
		
		PrintStream con=new PrintStream(new TextAreaOutputStream(console,10000));
		try {
			System.setOut(con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.setErr(con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mainpanel = new JPanel();
		
		tabbedPane = new JTabbedPane();

		myGUI_s = new GUI_search(myPref);
		myGUI_l = new GUI_library(myPref);
		myGUI_set = new GUI_settings(myPref);
		my_db = new mydb(myPref);
		
		myGUI_s.setLayout(new BoxLayout(myGUI_s, BoxLayout.Y_AXIS));
		myGUI_l.setLayout(new BoxLayout(myGUI_l, BoxLayout.Y_AXIS));
		myGUI_set.setLayout(new BoxLayout(myGUI_set, BoxLayout.Y_AXIS));
		
		if(my_db.connect()==0){
			if(!myPref.gethasDB())
			{
				if(my_db.createDB()!=0)
				{
					initErr(2,"Create DB");
				}
			}
			try {
				mydb.execute_update("USE MEDIA");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			initErr(1,"SQL Connect");
		}
		
		tabbedPane.addTab("Suchpfade", null, myGUI_s, "Suchpfade");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.addTab("Bibliothek", null, myGUI_l, "Bibliothek");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		tabbedPane.addTab("Einstellungen", null, myGUI_set, "Einstellungen");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		
		mainpanel.add(tabbedPane,BorderLayout.CENTER);
		mainpanel.add(listScroller,BorderLayout.PAGE_END);
		
		final JFrame frame = new JFrame("");
		frame.addComponentListener(new ComponentListener(){

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
				// System.out.println("w: " + frame.getSize().width + "h: " + frame.getSize().height);
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img/logo.png"));
		frame.setTitle("AML - Another Media Library");
		frame.setLocationByPlatform(true);
		frame.add(mainpanel);
		frame.setSize(myPref.getPreferredSize());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void initErr(int errnr, String errmsg) {
		System.out.println("!!!!!! Error( " + errnr + " ): " + errmsg + " !!!!!!");
	}
}
