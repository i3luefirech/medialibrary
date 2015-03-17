package media_lib;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


@SuppressWarnings("serial")
public class GUI_library extends GUI{
	
	JPanel myTitle;
	JPanel myFileView;
	JPanel typeFilter;
	JPanel valueFilter;
	JPanel myCMDs;

	JButton button_edit;
	JButton button_copy;
	JButton button_save;
	JButton button_add;

	JLabel label_filter;
	JCheckBox check_filter;
	JCheckBox check_filter1;
	JCheckBox check_filter2;
	JComboBox<String> combo_filter;
	JComboBox<String> combo_filter1;
	JTextField tb_filter;
	JButton button_fand;
	JButton button_for;
	
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
		valueFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
		typeFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
		
		 ActionListener FilterListener = new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					String regex = "(";
					System.out.println("tshgbsed");
					if(check_filter.isSelected())
					{
						regex += "Audio";
				        System.out.println("0");
					}
					if(check_filter1.isSelected())
					{
						if(regex.compareTo("(")==0)
						{
							regex += "Video";
						}
						else
						{
							regex += "|Video";
						}
				        System.out.println("1");
					}
					if(check_filter2.isSelected())
					{
						if(regex.compareTo("(")==0)
						{
							regex += "Image";
						}
						else
						{
							regex += "|Image";
						}
				        System.out.println("2");
					}
					if(regex.compareTo("(")!=0)
					{
						regex += ")";
					}
					else
					{
						regex="$.^";
					}
			        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table_files.getModel());
			        sorter.setRowFilter(RowFilter.regexFilter(regex,2));
			        table_files.setRowSorter(sorter);
				}
			};
		
		label_filter = new JLabel();
		label_filter.setText("Filter");
		check_filter = new JCheckBox();
		check_filter.setText("Audio");
		check_filter.setSelected(true);
		check_filter.addActionListener(FilterListener);
		check_filter1 = new JCheckBox();
		check_filter1.setText("Video");
		check_filter1.setSelected(true);
		check_filter1.addActionListener(FilterListener);
		check_filter2 = new JCheckBox();
		check_filter2.setText("Image");
		check_filter2.setSelected(true);
		check_filter2.addActionListener(FilterListener);
		String [] list = {};
		list = fillList(list);
		combo_filter = new JComboBox<String>(list);
		String [] list1 = {"-----","grösser als","kleiner als","grösser gleich","kleiner gleich","gleich","beginnt mit","endet mit","enthält"};
		combo_filter1 = new JComboBox<String>(list1);
		tb_filter = new JTextField();
		tb_filter.setText("Filtervalue");
		tb_filter.setColumns(25);
		button_fand = new JButton();
		button_fand.setText("UND");
		button_for= new JButton();
		button_for.setText("ODER");
		
		typeFilter.add(label_filter);
		typeFilter.add(check_filter);
		typeFilter.add(check_filter1);
		typeFilter.add(check_filter2);
		valueFilter.add(combo_filter);
		valueFilter.add(combo_filter1);
		valueFilter.add(tb_filter);
		valueFilter.add(button_for);
		valueFilter.add(button_fand);
		
		myTitle.add(title);
		
		table_files = new JTable();
		table_model = (DefaultTableModel) table_files.getModel();
		table_model.setColumnIdentifiers(tableColumnsName);
		table_files.setAutoCreateRowSorter(true);
		table_files.setDefaultRenderer(Object.class, new TableCellRenderer(){
            private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row%2 == 0){
                    c.setBackground(Color.WHITE);
                }
                else {
                    c.setBackground(Color.LIGHT_GRAY);
                }                        
                return c;
            }

        });
		
		table_files.getColumn("More").setCellRenderer(new ButtonRenderer());
		table_files.getColumn("More").setCellEditor(new ButtonEditor(new JCheckBox()));
		
		table_files.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		for(int i = 0; i < table_files.getColumnCount(); i++)
		{
			TableColumn mycol = table_files.getColumnModel().getColumn(i);
			switch(i)
			{
			case 0:
				mycol.setPreferredWidth(80);
				break;
			case 1:
				mycol.setPreferredWidth(310);
				break;
			case 2:
				mycol.setPreferredWidth(80);
				break;
			case 3:
				mycol.setPreferredWidth(120);
				break;
			case 4:
				mycol.setPreferredWidth(210);
				break;
			case 5:
				mycol.setPreferredWidth(50);
				break;
			}
		}
		
		sp_files = new JScrollPane(table_files);
		sp_files.setPreferredSize(new Dimension(this.mypref.getPreferredSize().width-60, this.mypref.getPreferredSize().height-365));
		
		myFileView.add(sp_files);
		
		myCMDs.add(button_edit);
		myCMDs.add(button_copy);
		myCMDs.add(button_save);
		myCMDs.add(button_add);
		
		add(myTitle);
		add(typeFilter);
		add(valueFilter);
		add(myFileView);
		add(myCMDs);
	}

	public void fillTable() {
		ResultSet rs = null;
		try {
			rs = mydb.execute_query("SELECT * FROM Files");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNo = rsmd.getColumnCount();
			while(rs.next()){
				Object[] objects = new Object[colNo+2];
				int fileid = -1;
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
					else if(i == 0)
					{
						objects[i]=String.format("%08d",(int)rs.getObject(i+1));
						fileid=(int)rs.getObject(i+1);
					}
				}
				
				String keyw = "";
				ResultSet rs1 = mydb.execute_query("SELECT KeywordID FROM KeyFil WHERE FileID="+fileid);
				while(rs1.next())
				{
					ResultSet rs2 = mydb.execute_query("SELECT Name FROM KEywords WHERE KeywordID="+rs1.getInt(""));
					while(rs2.next())
					{
						keyw += (rs2.getString("name")+";");
					}
				}
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
