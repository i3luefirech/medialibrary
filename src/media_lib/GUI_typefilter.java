/**
 * 
 */
package media_lib;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * @author ricu
 *
 */
@SuppressWarnings("serial")
public class GUI_typefilter extends GUI {
	
	JTable table_files;
	
	JCheckBox check_filter;
	JCheckBox check_filter1;
	JCheckBox check_filter2;

	public GUI_typefilter(Pref mypref, JTable ft) {
		super(mypref);
		
		table_files = ft;
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		title.setText("Filter");
		
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

		add(title);
		add(check_filter);
		add(check_filter1);
		add(check_filter2);
	}

}
