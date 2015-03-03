package media_lib;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
public class GUI_library extends GUI{
	
	JPanel myTitle;
	JPanel myFileView;
	JPanel myFilter;
	JPanel myCMDs;

	JButton button_edit;
	JButton button_copy;
	JButton button_save;
	JButton button_add;

	JLabel label_filter;
	JCheckBox check_filter;
	JComboBox<String> combo_filter;
	JButton button_fadd;
	JButton button_fuse;
	
	JTable table_files;
	
	String[] tableColumnsName = {"FileID","FilePath","Type","Name","Keywords","More"}; 
	DefaultTableModel table_model;
	
	JScrollPane sp_files;
	
	private String [] fillList(String[] list) {
		String text = mypref.getFil();
		String[] lines = text.split("\r\n");
		return lines;
	}
	
	public GUI_library(Pref mypref) {
		
		super(mypref);

		myTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		myFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
		myFileView = new JPanel(new FlowLayout(FlowLayout.LEFT));
		myCMDs = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		button_edit = new JButton();
		button_copy = new JButton();
		button_save = new JButton();
		button_add = new JButton();

		button_edit.setText("Bearbeiten");
		button_copy.setText("Kopieren");
		button_save.setText("Speichern");
		button_add.setText("Hinzufügen");
		
		title = new JLabel();
		title.setText("Bibliothek");
		
		label_filter = new JLabel();
		label_filter.setText("Filter");
		check_filter = new JCheckBox();
		check_filter.setText("aktiv");
		String [] list = {};
		list = fillList(list);
		combo_filter = new JComboBox<String>(list);
		button_fadd = new JButton();
		button_fadd.setText("Hinzufügen");
		button_fuse = new JButton();
		button_fuse.setText("Benutzen");
		
		myFilter.add(label_filter);
		myFilter.add(check_filter);
		myFilter.add(combo_filter);
		myFilter.add(button_fuse);
		myFilter.add(button_fadd);
		
		myTitle.add(title);
		
		table_files = new JTable();
		table_model = (DefaultTableModel) table_files.getModel();
		table_model.setColumnIdentifiers(tableColumnsName);
		table_files.setAutoCreateRowSorter(true);
		
		table_files.getColumn("More").setCellRenderer(new ButtonRenderer());
		table_files.getColumn("More").setCellEditor(new ButtonEditor(new JCheckBox()));
		
		sp_files = new JScrollPane(table_files);
		sp_files.setPreferredSize(new Dimension(this.mypref.getPreferredSize().width-60, this.mypref.getPreferredSize().height-365));
		
		myFileView.add(sp_files);
		
		myCMDs.add(button_edit);
		myCMDs.add(button_copy);
		myCMDs.add(button_save);
		myCMDs.add(button_add);
		
		add(myTitle);
		add(myFilter);
		add(myFileView);
		add(myCMDs);
	}

	public void actionPerformed(ActionEvent e) {
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
	}

	public void fillTable() {
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			rs = mydb.execute_query("SELECT * FROM Files");
			rs1 = mydb.execute_query("SELECT * FROM Keywords");
			rs2 = mydb.execute_query("SELECT * FROM KeyFil");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNo = rsmd.getColumnCount();
			while(rs.next()){
				Object[] objects = new Object[colNo+2];
				for(int i=0;i<colNo;i++){
					if(i%2==1)
					{
						objects[i]=mydb.mysql_remove_escape_string((String) rs.getObject(i+1));
					}
					else if(i == 2)
					{
						switch(Integer.parseInt(rs.getObject(i+1).toString()))
						{
							case 1:
								objects[i]="Audio";
								break;
							case 2:
								objects[i]="Video";
								break;
							case 3:
								objects[i]="Image";
								break;
							default:
								objects[i]="Unknown";
						}
					}
					else
					{
						objects[i]=String.format("%08d",(int)rs.getObject(i+1));
					}
				}
				
				String keyw = "TODO";
				objects[colNo] = keyw;
				
				String more = "...";
				objects[colNo+1] = more;
				
				table_model.addRow(objects);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
