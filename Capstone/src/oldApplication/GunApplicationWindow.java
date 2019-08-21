/*
 * Author: Conner Cox
 * Date: June 19, 2019
 * 
 * Description: This application allows a user to add the information and image of a firearm, 
 * and then it stores and sorts it. This allows for easy record keeping of firearms.
 */
package oldApplication;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GunApplicationWindow {

	private JFrame frame;
	private JTextField tfBrand;
	private JTextField tfModel;
	private JTextField tfSerialNum;
	private JTextField tfEstValue;
	private JTextField tfCaliber;
	private JTextField tfAttachments;
	private JTabbedPane tabbedPane;
	private JPanel inputPanel;
	private JLabel lblTop;
	private JButton btnEnterGun;
	private JButton btnUploadImage;
	private JLabel lblImageSelected;
	private JLabel lblStatus;
	private JPanel searchPanel;
	private JButton btnBrand;
	private JButton btnRecent;
	private JButton btnCaliber;
	private JButton btnValue;
	private JLabel lblNotes;
	private JTextField tfNotes;
	//things that need to persist can go here
	ArrayList<Firearm> gunCollection = new ArrayList<Firearm>();
	ArrayList<Firearm> gunSortedModel = new ArrayList<Firearm>(gunCollection);
	ArrayList<Firearm> gunSortedBrand = new ArrayList<Firearm>(gunCollection);
	ArrayList<Firearm> gunSortedCaliber = new ArrayList<Firearm>(gunCollection);
	ArrayList<Firearm> gunSortedSerial = new ArrayList<Firearm>(gunCollection);
	ArrayList<Firearm> gunSortedValue = new ArrayList<Firearm>(gunCollection);
	BufferedImage image = null;
	private JScrollPane scrollPane;
	//gun collection file
	File gunFile;
	//Empty table and model
	private JTable table;
	String[] columnNames = {"Brand","Caliber","Model","Serial #","Est. Value","Attachments","Notes"};
	DefaultTableModel model = new DefaultTableModel(0, columnNames.length);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GunApplicationWindow window = new GunApplicationWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GunApplicationWindow() {
		initialize();
	}
	
	/**
	 * A method that add and then sorts any guns added to the collection
	 */
	public void addSortStoreGuns() {
		//update each sorted list with the complete list of guns
		gunSortedModel = new ArrayList<Firearm>(gunCollection);
		gunSortedBrand = new ArrayList<Firearm>(gunCollection);
		gunSortedCaliber = new ArrayList<Firearm>(gunCollection);
		gunSortedSerial = new ArrayList<Firearm>(gunCollection);
		gunSortedValue = new ArrayList<Firearm>(gunCollection);
		//sort each list with its respective comparator
		gunSortedModel.sort(new FirearmComparatorByModel());
		gunSortedBrand.sort(new FirearmComparatorByBrand());
		gunSortedCaliber.sort(new FirearmComparatorByCaliber());
		gunSortedSerial.sort(new FirearmComparatorBySerial());
		gunSortedValue.sort(new FirearmComparatorByValue());
		
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		//Things to do first
		gunFile = new File("gunCollection.file");
	
		//JTable and model
		model.setColumnIdentifiers(columnNames);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		
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
		addSortStoreGuns();
		
		//Frame
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{40, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		//Tabbed Pane for all selection panes
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		frame.getContentPane().add(tabbedPane, gbc_tabbedPane);
				
		//Input panel tab
		inputPanel = new JPanel();
		tabbedPane.addTab("Input Guns", null, inputPanel, null);
		inputPanel.setLayout(null);
		//Labels for input panel
		lblTop = new JLabel("Enter info about the gun you with to add.");
		lblTop.setBounds(85, 10, 259, 16);
		inputPanel.add(lblTop);
		
		JLabel lblBrand = new JLabel("Enter brand: ");
		lblBrand.setBounds(115, 41, 80, 16);
		inputPanel.add(lblBrand);
		
		JLabel lblModel = new JLabel("Enter model: ");
		lblModel.setBounds(112, 72, 83, 16);
		inputPanel.add(lblModel);

		JLabel lblSerialNum = new JLabel("Serial number: ");
		lblSerialNum.setBounds(101, 103, 94, 16);
		inputPanel.add(lblSerialNum);

		JLabel lblCaliber = new JLabel("Enter the caliber: ");
		lblCaliber.setBounds(85, 134, 110, 16);
		inputPanel.add(lblCaliber);

		JLabel lblEstValue = new JLabel("Estimated value: ");
		lblEstValue.setBounds(88, 162, 107, 16);
		inputPanel.add(lblEstValue);
				
		JTextArea lblAttachments = new JTextArea("Enter attachments\n(seperated by a comma):");
		lblAttachments.setBounds(42, 193, 153, 32);
		lblAttachments.setBackground(UIManager.getColor("Button.background"));
		lblAttachments.setEditable(false);
		inputPanel.add(lblAttachments);
		
		lblNotes = new JLabel("Notes:");
		lblNotes.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNotes.setBounds(88, 237, 107, 16);
		inputPanel.add(lblNotes);
		
		lblImageSelected = new JLabel("no image selected");
		lblImageSelected.setBounds(215, 274, 200, 16);
		inputPanel.add(lblImageSelected);

		lblStatus = new JLabel("Awaiting input...");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(10, 334, 145, 13);
		lblStatus.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		inputPanel.add(lblStatus);
		
		//Input panel text Fields
		tfBrand = new JTextField();
		tfBrand.setBounds(215, 38, 200, 26);
		inputPanel.add(tfBrand);
		tfBrand.setColumns(10);

		tfModel = new JTextField();
		tfModel.setBounds(215, 69, 200, 26);
		inputPanel.add(tfModel);
		tfModel.setColumns(10);

		tfSerialNum = new JTextField();
		tfSerialNum.setBounds(215, 100, 200, 26);
		inputPanel.add(tfSerialNum);
		tfSerialNum.setColumns(10);

		tfCaliber = new JTextField();
		tfCaliber.setBounds(215, 131, 200, 26);
		inputPanel.add(tfCaliber);
		tfCaliber.setColumns(10);

		tfEstValue = new JTextField();
		tfEstValue.setBounds(215, 165, 200, 26);
		inputPanel.add(tfEstValue);
		tfEstValue.setColumns(10);

		tfAttachments = new JTextField();
		tfAttachments.setBounds(215, 199, 200, 26);
		inputPanel.add(tfAttachments);
		tfAttachments.setColumns(10);
		
		tfNotes = new JTextField();
		tfNotes.setColumns(10);
		tfNotes.setBounds(215, 236, 200, 26);
		inputPanel.add(tfNotes);
		
		//Input Pane Buttons
		btnUploadImage = new JButton("Upload Image");
		btnUploadImage.setBounds(67, 269, 130, 29);
		inputPanel.add(btnUploadImage);
		btnUploadImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"JPG & PNG Images", "jpg", "png", "jpeg");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(chooser);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File imageFile = chooser.getSelectedFile();
					//try to make the selected file into a BufferedImage
					try {
						lblImageSelected.setText(imageFile.getName());
						BufferedImage img = ImageIO.read(imageFile);
						image = img;
					} catch (IOException e2) {
						lblImageSelected.setText("Failed to read image");
					}
				}
			}
		});
		
		btnEnterGun = new JButton("     Enter Gun     ");
		btnEnterGun.setBounds(10, 347, 145, 29);
		btnEnterGun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					//TODO: Enter Gun
					//if there is something written in brand and value slots  
					//(value has to have something in it) record and store new firearm
					if(!tfBrand.getText().equals("") && !tfEstValue.getText().equals("")) {						
						//Take everything from the fields
						String brand = tfBrand.getText().trim();
						String model = tfModel.getText().trim();
						String serialNum = tfSerialNum.getText().trim();
						String caliber = tfCaliber.getText().trim();
						String[] att = tfAttachments.getText().split(",");
						ArrayList<String> attachments = new ArrayList<String> (Arrays.asList(att));
						attachments.forEach(each -> each.trim());
						String toParse = tfEstValue.getText().replace('$', ' ').trim();
						double estValue = Double.parseDouble(toParse);
						String notes = tfNotes.getText().trim();
						
						//add gun to records
						Firearm gunToAdd = new Firearm(image, brand, model, serialNum, caliber, attachments, estValue, notes);
						gunCollection.add(gunToAdd);
						
						//update all gun lists
						addSortStoreGuns();
						
						//clear text fields
						tfBrand.setText("");
						tfModel.setText("");
						tfSerialNum.setText("");
						tfCaliber.setText("");
						tfEstValue.setText("");
						tfAttachments.setText("");
						tfNotes.setText("");
						
						//TODO: Set tfBrand to focus
						tfBrand.requestFocus();
						
						//Update the user that the gun was added successfully
						new Thread(()-> {
							try {
								lblStatus.setText("Added Successfully!");
								Thread.sleep(4000);
								lblStatus.setText("Awaiting input...");
							} catch (InterruptedException e1) {
								//Catch nothing, Thread.sleep shouldn't throw
								e1.printStackTrace();
							}
							
						}).start();
						
					}
					//else update the user that they need to fill in the fields
					else {
						new Thread(()-> {
							try {
								lblStatus.setText("Please fill in all the fields");
								Thread.sleep(4000);
								lblStatus.setText("Awaiting input...");
							} catch (InterruptedException e1) {
								//Catch nothing, Thread.sleep shouldn't throw
								e1.printStackTrace();
							}
						}).start();
					}
				} catch (Exception e2) {
					//This catches when new gun couldn't be created.
					e2.printStackTrace();
				}
			}
		});
		inputPanel.add(btnEnterGun);
		
		
		//Search Panel tab
		searchPanel = new JPanel();
		tabbedPane.addTab("Search Guns", null, searchPanel, null);
		searchPanel.setLayout(null);
		
		
		JLabel lblSearchBy = new JLabel("Sort by…");
		lblSearchBy.setBounds(184, 6, 57, 16);
		searchPanel.add(lblSearchBy);
		
		//Search Panel Buttons
		btnBrand = new JButton("Brand");
		btnBrand.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Clear out table
				int rowCount = model.getRowCount();
				for(int i=0; i<rowCount; i++) {
					model.removeRow(0);
				}
				
				//Add appropriate row to scroll pane
				for(int i=0; i<gunSortedBrand.size(); i++) {
					Firearm f = gunSortedBrand.get(i);
					Object[] rowData = {f.getBrand(),
										f.getCaliber(), 
										f.getModel(), 
										f.getSerialNum(),
										f.getEstValue(),
										f.getAttachments().toString(),
										f.getNotes()}; 
					//add the row
					model.addRow(rowData);
					table = new JTable(model);
					scrollPane = new JScrollPane(table);
					searchPanel.add(scrollPane);
				}
			}
		});
		btnBrand.setBounds(125, 34, 75, 29);
		searchPanel.add(btnBrand);
		
		btnRecent = new JButton("Recent");
		btnRecent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Clear out table
				int rowCount = model.getRowCount();
				for(int i=0; i<rowCount; i++) {
					model.removeRow(0);
				}
				
				//Add appropriate row to scroll pane
				for(int i=0; i<gunCollection.size(); i++) {
					Firearm f = gunCollection.get(i);
					Object[] rowData = {f.getBrand(),
										f.getCaliber(), 
										f.getModel(), 
										f.getSerialNum(),
										f.getEstValue(),
										f.getAttachments().toString(),
										f.getNotes()}; 
					//add the row
					model.addRow(rowData);
					table = new JTable(model);
					scrollPane = new JScrollPane(table);
					searchPanel.add(scrollPane);
				}
			}
		});
		btnRecent.setBounds(25, 34, 75, 29);
		searchPanel.add(btnRecent);
		
		btnCaliber = new JButton("Caliber");
		btnCaliber.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Clear out table
				int rowCount = model.getRowCount();
				for(int i=0; i<rowCount; i++) {
					model.removeRow(0);
				}
				
				//Add appropriate row to scroll pane
				for(int i=0; i<gunSortedCaliber.size(); i++) {
					Firearm f = gunSortedCaliber.get(i);
					Object[] rowData = {f.getBrand(),
										f.getCaliber(), 
										f.getModel(), 
										f.getSerialNum(),
										f.getEstValue(),
										f.getAttachments().toString(),
										f.getNotes()}; 
					//add the row
					model.addRow(rowData);
					table = new JTable(model);
					scrollPane = new JScrollPane(table);
					searchPanel.add(scrollPane);
				}
			}
		});
		btnCaliber.setBounds(225, 34, 75, 29);
		searchPanel.add(btnCaliber);
		
		btnValue = new JButton("Value");
		btnValue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Clear out table
				int rowCount = model.getRowCount();
				for(int i=0; i<rowCount; i++) {
					model.removeRow(0);
				}
				
				//Add appropriate row to scroll pane
				for(int i=0; i<gunSortedValue.size(); i++) {
					Firearm f = gunSortedValue.get(i);
					Object[] rowData = {f.getBrand(),
										f.getCaliber(), 
										f.getModel(), 
										f.getSerialNum(),
										f.getEstValue(),
										f.getAttachments().toString(),
										f.getNotes()}; 
					//add the row
					model.addRow(rowData);
					table = new JTable(model);
					scrollPane = new JScrollPane(table);
					searchPanel.add(scrollPane);
				}
			}
		});
		btnValue.setBounds(325, 34, 75, 29);
		searchPanel.add(btnValue);
		
		JButton btnModel = new JButton("Model");
		btnModel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Clear out table
				int rowCount = model.getRowCount();
				for(int i=0; i<rowCount; i++) {
					model.removeRow(0);
				}
				
				//Add appropriate row to scroll pane
				for(int i=0; i<gunSortedModel.size(); i++) {
					Firearm f = gunSortedModel.get(i);
					Object[] rowData = {f.getBrand(),
										f.getCaliber(), 
										f.getModel(), 
										f.getSerialNum(),
										f.getEstValue(),
										f.getAttachments().toString(),
										f.getNotes()}; 
					//add the row
					model.addRow(rowData);
					table = new JTable(model);
					scrollPane = new JScrollPane(table);
					searchPanel.add(scrollPane);
				}
			}
		});
		btnModel.setBounds(75, 1, 75, 29);
		searchPanel.add(btnModel);
		
		JButton btnSerialNumber = new JButton("Serial #");
		btnSerialNumber.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Clear out table
				int rowCount = model.getRowCount();
				for(int i=0; i<rowCount; i++) {
					model.removeRow(0);
				}
				
				//Add appropriate row to scroll pane
				for(int i=0; i<gunSortedSerial.size(); i++) {
					Firearm f = gunSortedSerial.get(i);
					Object[] rowData = {f.getBrand(),
										f.getCaliber(), 
										f.getModel(), 
										f.getSerialNum(),
										f.getEstValue(),
										f.getAttachments().toString(),
										f.getNotes()}; 
					//add the row
					model.addRow(rowData);
					table = new JTable(model);
					scrollPane = new JScrollPane(table);
					searchPanel.add(scrollPane);
				}
			}
		});
		btnSerialNumber.setBounds(275, 1, 75, 29);
		searchPanel.add(btnSerialNumber);
		
			
		//ScrollPane
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(6, 62, 417, 289);
		searchPanel.add(scrollPane);

		
		//Display Table
		//Clear out table
		int rowCount = model.getRowCount();
		for(int i=0; i<rowCount; i++) {
			model.removeRow(0);
		}
		
		//Add appropriate row to scroll pane
		for(int i=0; i<gunCollection.size(); i++) {
			Firearm f = gunCollection.get(i);
			Object[] rowData = {f.getBrand(),
								f.getCaliber(), 
								f.getModel(), 
								f.getSerialNum(),
								f.getEstValue(),
								f.getAttachments().toString(),
								f.getNotes()}; 
			//add the row
			model.addRow(rowData);
			table = new JTable(model);
			scrollPane = new JScrollPane(table);
			searchPanel.add(scrollPane);
		}
		//Update model, then table
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		searchPanel.add(scrollPane);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//if there is only one selected row
				ListSelectionModel selected = table.getSelectionModel();
				System.out.println("HERE");
				System.out.println(selected.isSelectionEmpty());
				System.out.println(table.getRowSelectionAllowed());
				
//				if(Array.getLength(table.getSelectedRows()) == 1) {
//					//Delete that gun in that row, Column is 4
//					String serial = model.getValueAt(table.getSelectedRow(), 4).toString();
//					for (int i = 0; i < gunCollection.size(); i++) {
//						if(gunCollection.get(i).getSerialNum().equals(serial)) {
//							gunCollection.remove(i);
//						}
//						addSortStoreGuns();
//					}
//					
//					//Update model, then table
//					table = new JTable(model);
//					scrollPane = new JScrollPane(table);
//					searchPanel.add(scrollPane);
//				}
			}
		});
		btnDelete.setBounds(311, 352, 117, 29);
		searchPanel.add(btnDelete);
		
		JLabel lblToDeleteSelect = new JLabel("To delete, select a gun then press \"Delete\".");
		lblToDeleteSelect.setBounds(14, 358, 286, 16);
		searchPanel.add(lblToDeleteSelect);
	}
}
