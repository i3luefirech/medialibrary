package media_lib;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUI_valuefilter extends GUI {
	
	JTable table_files;
	
	JComboBox<String> combo_filter;
	JComboBox<String> combo_filter1;
	JTextField tb_filter;
	JButton button_fand;
	JButton button_for;
	
	private String [] fillList(String[] list) {
		String text = mypref.getFil();
		String[] lines = text.split("\r\n");
		return lines;
	}

	public GUI_valuefilter(Pref mypref, JTable ft) {
		super(mypref);
		
		table_files = ft;
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		title.setText("Filter");
		
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
		
		add(combo_filter);
		add(combo_filter1);
		add(tb_filter);
		add(button_for);
		add(button_fand);
	}

}
