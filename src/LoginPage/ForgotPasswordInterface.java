package LoginPage;

public interface ForgotPasswordInterface {
	void openForgotPassDialog(String id);
	void openEnterIDDialog();
	void openEnterPinDialog(String id);
	boolean isIDExist(String id);
	boolean isPinCorrect(String id, int pin);
	void changePassword(String id, String newPassword);	
}