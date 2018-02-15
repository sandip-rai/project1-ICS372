

/**
 * Gui class to show options to add, show patients list, and get input file from the user.
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;



public class Gui extends JPanel {

	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	JFrame frame;

	// Adding buttons to the GUI interface; these buttons are later called in
	// actionPerformed event.

	JButton buttonPatientsList = new JButton("Show Patient info");
	JButton buttonUploadFile = new JButton("Upload File");

	// Initializing
	JComboBox<String> comboBoxPatientsIds;
	FileHandler fh = new FileHandler();

	public void mainMenu() {
		// Create menu components
				JMenuBar menuBar = new JMenuBar();
				JMenu menu = new JMenu("Menu");

				// Add menus to menu bar
				menuBar.add(menu);

				// Create and add menuItems to menu
				JMenuItem patientInfo = menu.add("Patient Info");
				JMenuItem manageFile = menu.add("Manage Files");

				patientInfo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
						displayPatientList();
					}
				});

				manageFile.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
						manageFile();
					}
				});
				
				//ComboBox to hold the patient id from the patients
				comboBoxPatientsIds = new JComboBox<String>();
				comboBoxPatientsIds.addItem("New patient"); //Display New Patient for the dropdown list in the first place
				for (Patient patient : ClinicalTrial.getAllPatients()) {
					comboBoxPatientsIds.addItem(patient.getPatientId()); //Fill the comboBox from the ClinicalTrial arrayList
				}

				JButton buttonStartTrial = new JButton("Start Patient Trial"); //Button
				buttonStartTrial.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (comboBoxPatientsIds.getSelectedItem().toString() == "New patient") {
							frame.dispose();
							addPatient(); //Selecting new patient will call addPatient to allow to add new patient
						} else {//already in trial
							if (ClinicalTrial.findPatient(comboBoxPatientsIds.getSelectedItem().toString()).isActive()) {
								JOptionPane.showMessageDialog(null, "This Patient is already active in trial");
							} else {//added to trail
								ClinicalTrial.findPatient(comboBoxPatientsIds.getSelectedItem().toString()).setActive(true);
								JOptionPane.showMessageDialog(null, "This patient is set to actve and started trial");
							}
						}
					}
				});
		
		//Array to hold the reading types which will be showed in the comboBox
		String[] readingTypes = new String[] { "Weight", "Steps", "Temp", "Blood Pressure" };
		JComboBox<String> comboBoxReadingType = new JComboBox<String>(readingTypes);

		JButton buttonAddReading = new JButton("Add");
		

		// Creates labels and user input textFeild
		JLabel addReading = new JLabel("Add a new reading:");
		JLabel id = new JLabel("Reading ID:");
		JLabel date = new JLabel("Reading Date:");
		JLabel type = new JLabel("Reading Type:");
		JLabel value = new JLabel("Reading Value:");
		JTextField valueInput = new JTextField(16);
		JTextField idInput = new JTextField(16);
		JTextField dateInput = new JTextField(16);
		JTextField pastReadingDisplay = new JTextField(16);
		
		//When Add button is pressed
		buttonAddReading.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
 				//Get the new entered values in the textfield
				String readingId = idInput.getText();
 				String readingType = (String) comboBoxReadingType.getSelectedItem();
 				String readingValue = valueInput.getText();
 				String readingDate = dateInput.getText();
 				try {
 					//Prompt the user if reading values aren't filled
 					if(readingId.equals("") || readingValue.equals("") || readingDate.equals("")) {
 						JOptionPane.showMessageDialog(null, "Please fill in the values for every field.");
 					} else { //If all values are filled, add them to to corresponding Patient
 						long date = Long.parseLong(readingDate); //Change date from String to long
 						//Get the patient from the ClinicalTrial arraylist and add the new readings to that patient
 						ClinicalTrial.findPatient(comboBoxPatientsIds.getSelectedItem().toString()).
 																					addNewReadings(readingId, readingType, readingValue, date);
 						
 						//Clear the textfields for new input
 						idInput.setText("");
 						valueInput.setText("");
 						dateInput.setText("");
 					}
 				} catch(NullPointerException ex) { //Catch the error if no patient is selected to for adding readings.
 					JOptionPane.showMessageDialog(null, "Please select a Patient to add readings.");
 				}
			};
		});

		// Set all labels not editable
		pastReadingDisplay.setEditable(false);

		

		// Create JPanels
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JPanel panel7 = new JPanel();

		panel1.setLayout(new GridLayout(0, 3, 10, 10));
		panel2.setLayout(new GridLayout(0, 3, 10, 10));
		panel3.setLayout(new GridLayout(0, 3, 10, 10));
		panel4.setLayout(new GridLayout(0, 3, 10, 10));
		panel5.setLayout(new GridLayout(0, 3, 10, 10));
		panel6.setLayout(new GridLayout(0, 3, 10, 10));
		panel7.setLayout(new GridLayout(0, 3, 10, 10));

		panel1.add(comboBoxPatientsIds);
		panel1.add(buttonStartTrial);
		panel2.add(addReading);
		panel3.add(id);
		panel3.add(idInput);
		panel4.add(date);
		panel4.add(dateInput);
		panel5.add(type);
		panel5.add(comboBoxReadingType);
		panel6.add(value);
		panel6.add(valueInput);
		panel7.add(buttonAddReading);
		
		

		// Frame setup
		frame = new JFrame();
		frame.setLayout(new GridLayout(10, 10, 50, 50));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);

		frame.add(panel1);
		frame.add(panel2);
		frame.add(panel3);
		frame.add(panel4);
		frame.add(panel5);
		frame.add(panel6);
		frame.add(panel7);

		frame.pack();
		frame.setLocation(150, 150);
		frame.setVisible(true);
	}

	/**
	 * 
	 */
	public void manageFile() {
		JButton buttonUpload = new JButton("Upload a json file");
		JButton buttonSave = new JButton("Save as a json file");

		// Creating JPanels
		JPanel panel1 = new JPanel();

		panel1.setLayout(new GridLayout(0, 3, 10, 10));

		// Create menu components
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");

		// Add menus to menu bar
		menuBar.add(menu);

		// Create and add menuItems to menu
		JMenuItem back = menu.add("Back");

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainMenu();
			}
		});

		// Adding the textField and upload button to the panels
		panel1.add(buttonUpload);
		panel1.add(buttonSave);

		buttonUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				fh.readJsonFile();
				displayPatientList();
			}
		});

		// need to be implemented
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fh.writeJsonFile();
			}
		});

		frame = new JFrame();
		frame.setLayout(new GridLayout(4, 4, 10, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);

		frame.add(panel1);

		frame.pack();
		frame.setLocation(150, 150);
		frame.setVisible(true);
	}

	/**
	 * uploadFile gets called from actionPerformed method, gets the input file
	 * and passes the filePath to readJsonFile in FileHandler class
	 */

	public void addPatient() {
		JLabel label = new JLabel("PatientID:");
		JTextField inputText = new JTextField("");
		JButton buttonAdd = new JButton("Add");
		JTextField addPatientState = new JTextField("Click Add button to add new Patient");
		addPatientState.setEditable(false);
		JButton back = new JButton("Back");

		// Creating JPanels
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();

		panel1.setLayout(new GridLayout(0, 1, 10, 10));
		panel2.setLayout(new GridLayout(0, 3, 10, 10));
		panel3.setLayout(new GridLayout(0, 3, 10, 10));

		// Create menu components
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");

		// Add menus to menu bar
		menuBar.add(menu);

		// Create and add menuItems to menu
		JMenuItem patientInfo = menu.add("Patient Info");
		JMenuItem manageFile = menu.add("Manage Files");

		patientInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				displayPatientList();
			}
		});

		manageFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				manageFile();
			}
		});

		// Adding the textField and upload button to the panels
		panel1.add(addPatientState);
		panel2.add(label);
		panel2.add(inputText);
		panel3.add(buttonAdd);
		panel3.add(back);
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainMenu();
			}
		});
			

		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tempId = inputText.getText(); // assign the PatientId
														// that user inputs
				if (tempId == null || tempId.equals("")) {// make sure the user
															// has entered a
															// patient ID
					addPatientState.setText("Please enter a patient ID");
				} else if (ClinicalTrial.addPatient(tempId)) {
					ClinicalTrial.findPatient(tempId).setActive(true);
					addPatientState.setText("Added! Ready for next patient. ");
					inputText.setText("");
				} else {
					addPatientState.setText("That patient is already in this trial.");
				}
			}
		});

		frame = new JFrame();
		frame.setLayout(new GridLayout(4, 4, 45, 45));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);

		frame.add(panel1);
		frame.add(panel2);
		frame.add(panel3);

		frame.pack();
		frame.setLocation(150, 150);
		frame.setVisible(true);

	}

	/**
	 * displayPatientList shows the list of patients present in the Clinical
	 * Trial and gives option to show info or start a trial for a patient
	 */
	public void displayPatientList() {
		JComboBox<String> comboBoxPatientsIds = new JComboBox<String>();
		for (Patient patient : ClinicalTrial.getAllPatients()) {
			comboBoxPatientsIds.addItem(patient.getPatientId());
		}

		JLabel label = new JLabel("Patients List: Select a Patient");

		JButton buttonShowInfo = new JButton("Show Patient's Info");
		JButton buttonResumeTrial = new JButton("Resume Patient Trial");
		JButton buttonEndTrial = new JButton("End Patient Trial");

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");

		// Add menus to menu bar
		menuBar.add(menu);
		JMenuItem back = menu.add("Back");

		// JPanel for the text
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(0, 3, 10, 10));
		panel1.add(label);

		// JPanel for the comboBox and button
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(0, 3, 10, 10));
		panel2.add(comboBoxPatientsIds);
		panel2.add(buttonResumeTrial);
		panel2.add(buttonEndTrial);
		panel2.add(buttonShowInfo);

		// Select a patient id from the dropdown menu and display the
		// corresponding patient info
		buttonShowInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				try {//Handle the exception by prompting a user to select a patient or add a patient if list is empty
					// Print the info of selected patient id
					displayPatientInfo(
							ClinicalTrial.getAllPatients().get(comboBoxPatientsIds.getSelectedIndex()).getPatientId());
				} catch (ArrayIndexOutOfBoundsException ex){ 
					JOptionPane.showMessageDialog(null, "Please select a patient from the list. Add a patient if list is empty.");
					displayPatientList(); //Go back to the display frame again
				}
			};
		});

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainMenu();
			};
		});

		buttonResumeTrial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { //Handle the exception if no patient is selected to resume the trial.
					ClinicalTrial.findPatient(
							ClinicalTrial.getAllPatients().get(comboBoxPatientsIds.getSelectedIndex()).getPatientId())
							.setActive(true);
					JOptionPane.showMessageDialog(null, "Patient ID: "
							+ ClinicalTrial.getAllPatients().get(comboBoxPatientsIds.getSelectedIndex()).getPatientId()
							+ "\nTrial has been activated");
				} catch (ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null, "Please select a patient from the list. Add a patient if list is empty.");
					displayPatientList(); //Go back to the display frame again
				}
				
			};
		});

		// sets active to false on click, displays confirmation message
		buttonEndTrial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {//Handle the exception if no patient is selected to end the trial.
					// Set patient's active to false
					ClinicalTrial.findPatient(
							ClinicalTrial.getAllPatients().get(comboBoxPatientsIds.getSelectedIndex()).getPatientId())
							.setActive(false);
					JOptionPane.showMessageDialog(null, "Patient ID: "
							+ ClinicalTrial.getAllPatients().get(comboBoxPatientsIds.getSelectedIndex()).getPatientId()
							+ "\nTrial has ended");
				} catch (ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null, "Please select a patient from the list. Add a patient if list is empty.");
					displayPatientList(); //Go back to the display frame again
				}
				
			};
		});

		// Create a frame and add the two panels created
		frame = new JFrame();
		frame.setLayout(new GridLayout(5, 1, 10, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);
		frame.add(panel1);
		frame.add(panel2);
		frame.pack();
		frame.setLocation(150, 150);
		frame.setVisible(true);
	}

	/**
	 * displayPatientInfo shows the patient info of the corresponding patient id
	 *
	 * @param selectedPatient
	 *            patientID of the called patient
	 */
	public void displayPatientInfo(String selectedPatient) {
		JButton buttonBack = new JButton("Back");

		// Two textfields to show the PatientID and the patient info
		JTextField patientID = new JTextField(16);
		JTextField reading = new JTextField();
		reading.setSize(200, 200);

		// set textfields
		patientID.setText("PatientID: " + selectedPatient);
		reading.setText(ClinicalTrial.findPatient(selectedPatient).getReadings().toString());
		patientID.setEditable(false); // Textfield is read-only
		reading.setEditable(false); // Textfield is read-only
		// Create two panels
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(0, 3, 10, 10));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(0, 3, 10, 10));

		// Add elements to the panels
		panel1.add(patientID);
		// panel1.add(checkbox);
		panel1.add(buttonBack);
		panel2.add(reading);

		// Go back to previous menu to show list of patient ids if pressed back
		// button
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				displayPatientList();
			}
		});
		// Create the frame and add the panels to it
		frame = new JFrame();
		frame.setLayout(new GridLayout(3, 1, 10, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel1);
		frame.add(panel2);
		frame.pack();
		frame.setLocation(150, 150);
		frame.setVisible(true);
	}

	/**
	 * Starts the patient trial by allowing user to add new reading type and
	 * value, and adds them to the patient's reading info
	 *
	 * @param selectedPatient
	 */

}
