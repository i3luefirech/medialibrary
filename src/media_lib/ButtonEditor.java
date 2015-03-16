package media_lib;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

@SuppressWarnings("serial")
class ButtonEditor extends DefaultCellEditor {
	  protected JButton button;

	  private String label;
	  private int row = -1;
	  private boolean isopen = false;
	  
	  public ButtonEditor(JCheckBox jCheckBox) {
	    super(jCheckBox);
	    button = new JButton();
	    button.setOpaque(true);
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        fireEditingStopped();
	      }
	    });
	  }

	  public Component getTableCellEditorComponent(JTable table, Object value,
	      boolean isSelected, int row, int column) {
	    if (isSelected) {
	      button.setForeground(table.getSelectionForeground());
	      button.setBackground(table.getSelectionBackground());
	    } else {
	      button.setForeground(table.getForeground());
	      button.setBackground(table.getBackground());
	    }
	    label = (value == null) ? "" : value.toString();
	    button.setText(label);
	    if(this.row==-1)
	    {
	    	this.row = row;
	    }
	    else if(this.row==row)
	    {
	    	this.row=-1;
	    }
	    return button;
	  }

	  public Object getCellEditorValue() {
	    return new String(label);
	  }

	  public boolean stopCellEditing() {
	    return super.stopCellEditing();
	  }

	  protected void fireEditingStopped() {
		if(this.row!=-1)
		{
			if(!isopen)
			{
				// TODO open
				System.out.println("Open "+row);
				isopen=true;
			}
		}
		else
		{
			if(isopen)
			{
				// TODO close
				System.out.println("Close");
				isopen=false;
			}
		}
	    super.fireEditingStopped();
	  }
	}
