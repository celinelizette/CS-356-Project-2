package miniTwitter;

public class DriverClass {
	// This class drives the program using the Singleton Design pattern


	public static void main(String[] args) {
		AdminControlPanel.getInstance().run();

	}
}
