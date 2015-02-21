package media_lib;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class GUI extends JPanel implements ActionListener{
	
	Pref mypref;
	
	JLabel title;
	
	JButton choosefolder;
	JFileChooser chooser;
	JLabel foldername;
	JButton addfolder;
	
	JScrollPane listScroller;
	DefaultListModel<String> searchfolders;
	JList<String> show_sf;

	JTextArea console;
	
	JPanel myTitle;
	JPanel myConsole;
	JPanel myChoose;
	JPanel mySearchFolders;
	
	@SuppressWarnings("unchecked")
	public GUI(Pref mypref) {
		
		super();
		
		this.mypref = mypref;
		
		myChoose = new JPanel(new FlowLayout(FlowLayout.LEADING));
		myConsole = new JPanel(new FlowLayout(FlowLayout.LEADING));
		myTitle = new JPanel(new FlowLayout(FlowLayout.LEADING));
		mySearchFolders = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		title = new JLabel();
		title.setText("AML - Another Media Library");
		
		myTitle.add(title);
		
		choosefolder = new JButton("Wähle einen Suchpfad");
		choosefolder.addActionListener(this);
		foldername = new JLabel();
		foldername.setText("No Selection ");
		addfolder = new JButton("Suchpfad hinzufügen");
		addfolder.addActionListener(this);
		addfolder.setEnabled(false);
		
		myChoose.add(choosefolder);
		myChoose.add(foldername);
		myChoose.add(addfolder);
		
		searchfolders = new DefaultListModel<String>();
		searchfolders = fillList(searchfolders);
		show_sf=new JList<String>(searchfolders);
		listScroller = new JScrollPane(show_sf);
		listScroller.setPreferredSize(new Dimension(880, 100));
		
		mySearchFolders.add(listScroller);
		
		console = new JTextArea();
		console.setRows(20);
		console.setColumns(80);
		
		myConsole.add(console);
		
		PrintStream con=new PrintStream(new TextAreaOutputStream(console,20));
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
		
		add(myTitle);
		add(myChoose);
		add(mySearchFolders);
		add(myConsole);
	}
	
	private DefaultListModel<String> fillList(DefaultListModel<String> list) {
		String text = mypref.getSF();
		String[] lines = text.split("\r\n");
		for(int i = 0; i < lines.length; i++)
		{
			list.addElement(lines[i]);
		}
		return list;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==choosefolder)
		{
			chooser = new JFileChooser(); 
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			// disable the "All files" option.
			chooser.setAcceptAllFileFilterUsed(false);
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
				System.out.println("getCurrentDirectory(): " +  chooser.getCurrentDirectory());
				System.out.println("getSelectedFile() : " +  chooser.getSelectedFile());
				foldername.setText(""+chooser.getSelectedFile());
				addfolder.setEnabled(true);
			}
			else {
				System.out.println("No Selection ");
				foldername.setText("No Selection ");
				addfolder.setEnabled(false);
			}
		}
		if(e.getSource()==addfolder)
		{
			//searchfolders.setText(searchfolders.getText()+foldername.getText()+"\r\n");
			mypref.setSF(mypref.getSF()+foldername.getText()+"\r\n");
			foldername.setText("No Selection ");
			addfolder.setEnabled(false);
		}
	}

	public void setSearchfolders(String sf) {
		// TODO Auto-generated method stub
		
	}

	public void initErr(int errnr, String errmsg) {
		// TODO Auto-generated method stub
		
	}
}
