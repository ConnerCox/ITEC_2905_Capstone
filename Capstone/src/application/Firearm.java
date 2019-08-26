package application;

/*
 * Author: Conner Cox
 * Date: June 19, 2019
 * 
 * Description: This is the firearm class that is used in GunApplicationWindow.java. 
 * It defines the attributes of a firearm object.
 */
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

public class Firearm implements Serializable {
	//attributes of a Firearm
	private BufferedImage image;
	private String brand;
	private String model;
	private String serialNum;
	private String caliber;
	private ArrayList<String> attachments;
	private double estValue;
	private String notes;
	
	//zero arg constructor
	public Firearm() {
		image = null;
		brand = null;
		model = null;
		serialNum = null;
		caliber = null;
		attachments = new ArrayList<String>();
		estValue = 0;
		notes = "";
	}
	
	//constructor
	public Firearm(BufferedImage image, String brand, String model, String serialNum, String caliber,
			ArrayList<String> attachments, double estValue, String notes) {

		this.image = image;
		this.brand = brand;
		this.model = model;
		this.serialNum = serialNum;
		this.caliber = caliber;
		this.attachments = attachments;
		this.estValue = estValue;
		this.notes = notes;
	}
	
	//getters
	public BufferedImage getImage() {
		return image;
	}
	public String getBrand() {
		return brand;
	}
	public String getModel() {
		return model;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public String getCaliber() {
		return caliber;
	}
	public ArrayList<String> getAttachments() {
		return attachments;
	}
	public double getEstValue() {
		return estValue;
	}
	public String getNotes() {
		return notes;
	}
	
	//setters
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public void setCaliber(String caliber) {
		this.caliber = caliber;
	}
	public void setAttachments(ArrayList<String> attachments) {
		this.attachments = attachments;
	}
	public void setEstValue(double estValue) {
		this.estValue = estValue;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Override
	public String toString() {
		String s = "Brand: "+brand+", Model: "+model+", Serial Number: "+serialNum+", Caliber: "+caliber+", Attachments: "+attachments+", EstValue: "+estValue+", and Notes: "+notes;
		return s;
	}
	
}
