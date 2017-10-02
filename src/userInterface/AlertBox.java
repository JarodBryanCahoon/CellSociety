package userInterface;

public interface AlertBox{
	
	/**
	 * Displays the text for the appropriate notification
	 * Can be used for either errors or simple alerts
	 */
	public void display();
	
	/**
	 * Closes the text box
	 * Displays specifically how the text box is closed
	 */
	public void close();
	
}
