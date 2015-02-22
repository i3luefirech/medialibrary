package media_lib;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

@SuppressWarnings("serial")
public class GUI_search extends GUI{
	
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
	JButton syncDB;
	JLabel found;
	
	JPanel myTitle;
	JPanel myChoose;
	JPanel mySearchFolders;
	JPanel mySFcmds;
	JPanel myFound;

	ArrayList<String> list_audio;
	ArrayList<String> list_video;
	ArrayList<String> list_image;
	
	public GUI_search(Pref mypref) {
		
		super(mypref);
		
		myChoose = new JPanel(new FlowLayout(FlowLayout.LEFT));
		myTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mySearchFolders = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mySFcmds = new JPanel(new FlowLayout(FlowLayout.LEFT));
		myFound = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		list_audio = new ArrayList<String>();
		list_video = new ArrayList<String>();
		list_image = new ArrayList<String>();
		
		title = new JLabel();
		title.setText("Suchpfad");
		
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
		listScroller.setAutoscrolls(true);
		listScroller.setWheelScrollingEnabled(true);
		listScroller.setPreferredSize(new Dimension(this.mypref.getPreferredSize().width-40, this.mypref.getPreferredSize().height-365));
		
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
		syncDB = new JButton("Synchronisiere DB mit Suche");
		syncDB.addActionListener(this);
		syncDB.setEnabled(false);
		
		mySFcmds.add(searchSF);
		mySFcmds.add(searchAllSF);
		mySFcmds.add(deleteSF);
		mySFcmds.add(clearSF);
		mySFcmds.add(syncDB);
		
		found = new JLabel();
		found.setText("Gefundene Files: Audio: 0 Video: 0 Image: 0 ");
		
		myFound.add(found);
		
		add(myTitle);
		add(myChoose);
		add(mySearchFolders);
		add(mySFcmds);
		add(myFound);
	}
	
	private DefaultListModel<String> fillList(DefaultListModel<String> list) {
		String text = mypref.getSF();
		String[] lines = text.split("\r\n");
		list.clear();
		for(int i = 0; i < lines.length; i++)
		{
			list.addElement(lines[i]);
		}
		return list;
	}
	
	private void chooser()
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
			foldername.setText(""+chooser.getSelectedFile());
			addfolder.setEnabled(true);
		}
		else {
			System.out.println("Keine Auswahl ");
			foldername.setText("Keine Auswahl ");
			addfolder.setEnabled(false);
		}
	}
	
	private void add_folder(){
		mypref.setSF(mypref.getSF()+foldername.getText()+"\r\n");
		searchfolders = fillList(searchfolders);
		foldername.setText("Keine Auswahl ");
		addfolder.setEnabled(false);
	}
	
	private void clear_folder(){
		mypref.setSF("");
		searchfolders = fillList(searchfolders);
	}
	
	private void delete_folder(){
		String replace = (show_sf.getSelectedValue()+"\r\n");
		String old =  mypref.getSF();
		int pos_beg = old.indexOf(replace);
		int pos_end = old.indexOf(replace)+replace.length();
		mypref.setSF(old.substring(0, pos_beg)+old.substring(pos_end));
		searchfolders = fillList(searchfolders);
	}
	
	private void listFilesForFolder(final File folder)
	{
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            System.out.println("Search folder " + fileEntry);
	            listFilesForFolder(fileEntry);
	        } else {
	        	if(fileEntry.getName().contains("."))
	        	{
	        		int point = fileEntry.getName().lastIndexOf(".");
	        		String extension = fileEntry.getName().substring(point+1).toLowerCase();
	        		switch(extension)
	        		{
	        			// Audio
	        			case "aif":
	        			case "iff":
	        			case "m3u":
	        			case "m4a":
	        			case "mid":
	        			case "mp3":
	        			case "mpa":
	        			case "ra":
	        			case "wav":
	        			case "wma":
	        			case "flac":
	        				list_audio.add(fileEntry.getName());
	        				break;
	        			// Video
	        			case "3g2":
	        			case "3gp":
	        			case "asf":
	        			case "asx":
	        			case "avi":
	        			case "flv":
	        			case "m4v":
	        			case "mov":
	        			case "mp4":
	        			case "mpg":
	        			case "mpeg":
	        			case "rm":
	        			case "srt":
	        			case "swf":
	        			case "vob":
	        			case "wmv":
	        				list_video.add(fileEntry.getName());
		        			break;
		        		// Image
	        			case "bmp":
	        			case "dds":
	        			case "gif":
	        			case "jpg":
	        			case "jpeg":
	        			case "png":
	        			case "psd":
	        			case "pspimage":
	        			case "tga":
	        			case "thm":
	        			case "tif":
	        			case "tiff":
	        			case "yuv":
	        				list_image.add(fileEntry.getName());
		        			break;
	        		}
	        	}
	        }
	    }
	}
	
	private void searchSF(){
		System.out.println("Begin search...");
		final File folder = new File(show_sf.getSelectedValue());
		list_audio.clear();
		list_video.clear();
		list_image.clear();
		listFilesForFolder(folder);
		System.out.println("Search successfully...");
		found.setText("Gefundene Files: Audio: " + list_audio.size() + " Video: " + list_video.size() + " Image: " + list_image.size() + " ");
		syncDB.setEnabled(true);
	}

	private void searchAllSF(){
		System.out.println("Begin search...");
		list_audio.clear();
		list_video.clear();
		list_image.clear();
		for(int i = 0; i < searchfolders.getSize(); i++){
			final File folder = new File(searchfolders.get(i));
			System.out.println("Search path["+i+"]...");
			listFilesForFolder(folder);
		}
		System.out.println("Search successfully...");
		found.setText("Gefundene Files: Audio: " + list_audio.size() + " Video: " + list_video.size() + " Image: " + list_image.size() + " ");
		syncDB.setEnabled(true);
	}
	
	private void syncDB(){
		System.out.println("SyncDB...");
		syncDB.setEnabled(false);
		
		// sync
		ResultSet rs;
		try {
			rs = mydb.execute_query("SELECT * FROM Files");
			// iterate through the java resultset
			while (rs.next())
			{
				int id = rs.getInt("FileID");
				String path = rs.getString("Path");
				int type = rs.getInt("Type");
				String name = rs.getString("Name");
				 
				// print the results
				System.out.format("%s, %s, %s, %s, %s, %s\n", id, path, type, name);
			}
			found.setText("Gefundene Files: Audio: 0 Video: 0 Image: 0 ");
			System.out.println("SyncDB successfully...");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		list_audio.clear();
		list_video.clear();
		list_image.clear();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==choosefolder)
		{
			chooser();
		}
		if(e.getSource()==addfolder)
		{
			add_folder();
		}
		if(e.getSource()==clearSF)
		{
			clear_folder();
		}
		if(e.getSource()==deleteSF)
		{
			delete_folder();
		}
		if(e.getSource()==searchSF)
		{
			searchSF();
		}
		if(e.getSource()==searchAllSF)
		{
			searchAllSF();
		}
		if(e.getSource()==syncDB)
		{
			syncDB();
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

}
