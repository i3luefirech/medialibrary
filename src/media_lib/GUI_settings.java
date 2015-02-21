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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class GUI_settings extends JPanel implements ActionListener, ListSelectionListener{
	
	Pref mypref;
	
	JLabel title;
	
	JButton choosefolder;
	JFileChooser chooser;
	JLabel foldername;
	JButton addfolder;
	
	JScrollPane listScroller;
	DefaultListModel<String> searchfolders;
	JList<String> show_sf;
	
	JButton deleteSF;
	JButton clearSF;
	JButton searchSF;
	JButton searchAllSF;

	JTextArea console;
	
	JPanel myTitle;
	JPanel myConsole;
	JPanel myChoose;
	JPanel mySearchFolders;
	JPanel mySFcmds;
	
	public GUI_settings(Pref mypref) {
		
		super();
		
		this.mypref = mypref;
		
		myChoose = new JPanel(new FlowLayout(FlowLayout.LEFT));
		myConsole = new JPanel(new FlowLayout(FlowLayout.LEFT));
		myTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mySearchFolders = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mySFcmds = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		title = new JLabel();
		title.setText("AML - Another Media Library");
		
		myTitle.add(title);
		
		choosefolder = new JButton("Wähle einen Suchpfad");
		choosefolder.addActionListener(this);
		foldername = new JLabel();
		foldername.setText("Keine Auswahl ");
		addfolder = new JButton("Suchpfad hinzufügen");
		addfolder.addActionListener(this);
		addfolder.setEnabled(false);
		
		myChoose.add(choosefolder);
		myChoose.add(foldername);
		myChoose.add(addfolder);
		
		searchfolders = new DefaultListModel<String>();
		searchfolders = fillList(searchfolders);
		show_sf=new JList<String>(searchfolders);
		show_sf.addListSelectionListener(this);
		show_sf.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listScroller = new JScrollPane(show_sf);
		listScroller.setPreferredSize(new Dimension(880, 100));
		
		mySearchFolders.add(listScroller);
		
		deleteSF = new JButton("entferne Suchpfad");
		deleteSF.addActionListener(this);
		deleteSF.setEnabled(false);
		clearSF = new JButton("lösche Suchpfadliste");
		clearSF.addActionListener(this);
		searchSF = new JButton("Durchsuche markierten Pfad");
		searchSF.addActionListener(this);
		searchSF.setEnabled(false);
		searchAllSF = new JButton("Durchsuche alle Pfade");
		searchAllSF.addActionListener(this);
		
		mySFcmds.add(searchSF);
		mySFcmds.add(searchAllSF);
		mySFcmds.add(deleteSF);
		mySFcmds.add(clearSF);
		
		console = new JTextArea();
		console.setRows(10);
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
		add(mySFcmds);
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
			if(show_sf.getSelectedIndex()==-1)
			{
				chooser.setCurrentDirectory(new java.io.File("."));
			}
			else
			{
				chooser.setCurrentDirectory(new java.io.File(show_sf.getSelectedValue()));
			}
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
				System.out.println("Keine Auswahl ");
				foldername.setText("Keine Auswahl ");
				addfolder.setEnabled(false);
			}
		}
		if(e.getSource()==addfolder)
		{
			mypref.setSF(mypref.getSF()+foldername.getText()+"\r\n");
			foldername.setText("Keine Auswahl ");
			addfolder.setEnabled(false);
		}
		if(e.getSource()==deleteSF)
		{
			
		}
		if(e.getSource()==clearSF)
		{
			
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource()==show_sf)
		{
			if(show_sf.getSelectedIndex()==-1)
			{
				deleteSF.setEnabled(false);
				searchSF.setEnabled(false);
			}
			else
			{
				deleteSF.setEnabled(true);
				searchSF.setEnabled(true);
			}
		}
	}

	public void initErr(int errnr, String errmsg) {
		System.out.println("!!!!!! Error( " + errnr + " ): " + errmsg + " !!!!!!");
	}

}
