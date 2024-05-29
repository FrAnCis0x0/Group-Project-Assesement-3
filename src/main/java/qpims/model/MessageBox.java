package qpims.model;

import javax.swing.*;

// Singleton class for MessageBox that shows different types of messages
public class MessageBox {
	private static MessageBox instance = null;
	private MessageBox() {
	}
	//get the instance of the MessageBox
	public static MessageBox getInstance() {
		if (instance == null) {
			instance = new MessageBox();
		}
		return instance;
	}
	public void showInfo(String message){
		//show message
		JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = optionPane.createDialog("Success");
		dialog.setAlwaysOnTop(true);//set the dialog always on top
		dialog.setVisible(true);
	}
	public void showError(String message){
		//show error message
		JOptionPane optionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog("Failure");
		dialog.setAlwaysOnTop(true);//set the dialog always on top
		dialog.setVisible(true);
	}
	
	public void showWarning(String message){
		//show warning message
		JOptionPane optionPane = new JOptionPane(message, JOptionPane.WARNING_MESSAGE);
		JDialog dialog = optionPane.createDialog("Warning");
		dialog.setAlwaysOnTop(true);//set the dialog always on top
		dialog.setVisible(true);
	}

}
