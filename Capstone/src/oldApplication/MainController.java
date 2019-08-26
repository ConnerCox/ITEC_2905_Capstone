package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class MainController {
	
	//elements that can be changed or read by fxml go here
	@FXML
	private TextField tfBrand;
	private TextField tfModel;
	private TextField tfSerial;
	private TextField tfCaliber;
	private TextField tfAttachments;
	private TextField tfNotes;
	private ImageView ivPicture;
	
	//methods that are activated by program go here
	public void test(ActionEvent event) {
		int rand = (int) (Math.random()*50+1);
		System.out.println(Integer.toString(rand));
	}
	
	public void enterGun(ActionEvent Event) {
		//TODO if checking to make sure gun is properly entered
		Firearm newFirearm = 
	}

}