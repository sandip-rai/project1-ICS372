package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import gui.GuiController;

public class ButtonExportListener implements ActionListener {
	GuiController guiController;
	
	//constructor
	public ButtonExportListener(GuiController guiController){
		this.guiController = guiController;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		 if (guiController.getFileAdapter().writeFile(guiController.getClinicalTrial())) { // If file is saved, prompt the user
             // and display manage file interface
             JOptionPane.showMessageDialog(null, "File saved successfully.");
             
         } else { // If no file saved, prompt and display manage file
             // interface again.
             JOptionPane.showMessageDialog(null, "File not saved.");
         }
	}

}