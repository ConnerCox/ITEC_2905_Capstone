## Synopsis

This is a program used for easily recording muliple firearms quickly. It also allows the user to quickly sort through their firearm collection by its brand, caliber, serial number, model, value, and most recently added.

## Motivation

I was motivated to create this project due to having multiple family members inquire of me to come and catalogue their firearms like I do for my grandfather. The way I handled cataloging process took forever, so instead I created this program to quicken the process.

## Installation

Download all of the files and run GunApplicationWindow.java with your favorite IDE. 


## GUI Example

This is how the GUI looks:

<img src="GUI.png" />

## Code Example

This snippet is the first half of what happens when a user enters a gun.

```
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
```

## Contributors

I would feel honored if anyone wanted to implement their own changes or offer suggestions! Feel free to contact me at my Twitter (doesn't exist yet) or email me at (yourNotGettingMyEmailAddress.fake)
