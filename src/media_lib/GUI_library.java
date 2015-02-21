package media_lib;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class GUI_library extends JPanel implements ActionListener, ListSelectionListener{
	
	Pref mypref;
	
	JLabel title;
	
	JPanel myTitle;
	
	public GUI_library(Pref mypref) {
		
		super();
		
		this.mypref = mypref;
		
		myTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		title = new JLabel();
		title.setText("Bibliothek");
		
		myTitle.add(title);
		
		add(myTitle);
	}

	public void actionPerformed(ActionEvent e) {
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
	}

}
