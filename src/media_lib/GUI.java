package media_lib;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class GUI extends JPanel implements ActionListener, ListSelectionListener{
	
	protected Pref mypref;
	
	protected JLabel title;
	
	public GUI(Pref mypref) {
		
		super();
		
		this.mypref = mypref;
		
		title = new JLabel();
		
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

}
