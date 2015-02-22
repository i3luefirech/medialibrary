package media_lib;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;

@SuppressWarnings("serial")
public class GUI_settings extends GUI{

	JLabel title;
	
	JPanel myTitle;
	
	public GUI_settings(Pref mypref) {
		
		super(mypref);
		
		myTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		title = new JLabel();
		title.setText("Einstellungen");
		
		myTitle.add(title);
		
		add(myTitle);
	}

	public void actionPerformed(ActionEvent e) {
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
	}

}
