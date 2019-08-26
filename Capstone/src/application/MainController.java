/*
 * Author: Conner Cox
 * Date: August 26, 2019
 * 
 * Version: 1.15 (halfway though first draft of version 2)
 * 
 * Description: This application allows a user to add the information and image of a firearm, 
 * and then it stores and sorts it. This allows for easy record keeping of firearms.
 */
package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class MainController {
	
	//elements that can be changed or read by fxml go here
	@FXML private TextField tfBrand;
	@FXML private TextField tfModel;
	@FXML private TextField tfSerial;
	@FXML private TextField tfCaliber;
	@FXML private TextField tfEstValue;
	@FXML private TextField tfAttachments;
	@FXML private TextField tfNotes;
	@FXML private ImageView ivPicture;
	
	//elements that do not need fxml or need persistance go here
	ArrayList<Firearm> gunCollection = new ArrayList<Firearm>();
	ArrayList<Firearm> gunSortedModel = new ArrayList<Firearm>(gunCollection);
	ArrayList<Firearm> gunSortedBrand = new ArrayList<Firearm>(gunCollection);
	ArrayList<Firearm> gunSortedCaliber = new ArrayList<Firearm>(gunCollection);
	ArrayList<Firearm> gunSortedSerial = new ArrayList<Firearm>(gunCollection);
	ArrayList<Firearm> gunSortedValue = new ArrayList<Firearm>(gunCollection);
	
	File gunFile;
	
	
	//methods that are activated by program go here
	//test method to make sure a widget works
	public void test(ActionEvent event) {
		int rand = (int) (Math.random()*50+1);
		//System.out.println(Integer.toString(rand));
		System.out.println(tfBrand.getText());
	}
	
	/**
	 * Adds a new gun to the collection.
	 */
	public void enterGun(ActionEvent Event) {
		//TODO if checking to make sure gun is properly entered
		
		//Take everything from the tfs
		String brand = tfBrand.getText().trim();
		String model = tfModel.getText().trim();
		String serial = tfSerial.getText().trim();
		String caliber = tfCaliber.getText().trim();
		String[] att = tfAttachments.getText().split(",");
		ArrayList<String> attachments = new ArrayList<String> (Arrays.asList(att));
		attachments.forEach(each -> each.trim());
		String toParse = tfEstValue.getText().replace('$', ' ').trim();
		double estValue = Double.parseDouble(toParse);
		String notes = tfNotes.getText().trim();
		
		//add gun to record
		Firearm gunToAdd = new Firearm(image, brand, model, serial, caliber, attachments, estValue, notes);
		gunCollection.add(gunToAdd);
		
		//update records
		addSortStoreGuns();
		
		//clear text fields
		tfBrand.setText("");
		tfModel.setText("");
		tfSerial.setText("");
		tfCaliber.setText("");
		tfEstValue.setText("");
		tfAttachments.setText("");
		tfNotes.setText("");
		
		//TODO: Set tfBrand to focus
		tfBrand.requestFocus();
	}
	
	/**
	 * Add and then sort any guns added to the collection
	 */
	public void addSortStoreGuns() {
		//TODO Change to only store guns
		
//		//update each sorted list with the complete list of guns
//		gunSortedModel = new ArrayList<Firearm>(gunCollection);
//		gunSortedBrand = new ArrayList<Firearm>(gunCollection);
//		gunSortedCaliber = new ArrayList<Firearm>(gunCollection);
//		gunSortedSerial = new ArrayList<Firearm>(gunCollection);
//		gunSortedValue = new ArrayList<Firearm>(gunCollection);
//		//sort each list with its respective comparator
//		gunSortedModel.sort(new FirearmComparatorByModel());
//		gunSortedBrand.sort(new FirearmComparatorByBrand());
//		gunSortedCaliber.sort(new FirearmComparatorByCaliber());
//		gunSortedSerial.sort(new FirearmComparatorBySerial());
//		gunSortedValue.sort(new FirearmComparatorByValue());
		
		//TODO May need to dedicate own method to the creation of gunfile.
		//it would likely get called at Start in main
		gunFile = new File("gunCollection.file");
		
		//file reading
		try {
			//create a file if none exists
			if(!gunFile.exists()) {
				gunFile.createNewFile();
			} else {
				FileInputStream fi = new FileInputStream(gunFile);
				ObjectInputStream oi = new ObjectInputStream(fi);
				gunCollection = (ArrayList<Firearm>) oi.readObject();
				addSortStoreGuns();
				oi.close();
				fi.close();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//store into file
		try {
			FileOutputStream f = new FileOutputStream(gunFile);
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(gunCollection);
			o.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
	}
}