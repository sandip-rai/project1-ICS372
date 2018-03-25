package trial;
/**
 * Patient class contains patient Id, a boolean active for being in the trial, and an arraylist of corresponding readings.
 */

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class Patient {
	//Initialization
	private String patientId;
	private boolean active;
	private ArrayList<Reading> readings;

	/**
	 * Class Constructor specifying the patient Id
	 * @param patientId the id of the patient
	 */
	public Patient(String patientId) {
		this.patientId = patientId;
		this.setActive(true); 
		this.readings = new ArrayList<Reading>(); //Creates an arrayList of reading
	}

	/**
	 * Getter for patient Id
	 * @return patientId
	 */
	public String getPatientId() {
		return patientId;
	}

	/**
	 * Getter for patient's readings
	 * @return an arrayList of readings
	 */
	public ArrayList<Reading> getReadings() {
		return readings;
	}

	/**
	 * Creates a reading object for every reading type except the blood_pressure
	 * @param readingId the reading Id number
	 * @param type the reading type except blood_pressure
	 * @param value the reading value number
	 * @param date the date when reading was taken
	 */
	public void addReading(String readingId, String type, double value, Date date, Clinic clinic) {
		if (active) {
			Reading reading = new Reading(readingId, type, value, date, clinic);
			readings.add(reading);
		}
	}

	/**
	 * Creates a reading object for only the blood_pressure reading type
	 * @param readingId the reading Id number
	 * @param type the blood_pressure reading type
	 * @param value the reading value number
	 * @param date the date when reading was taken
	 */
	public void addReading(String readingId, String type, String bpValue, Date date, Clinic clinic) {
		if (active) {
			Reading reading = new Reading(readingId, type, bpValue, date, clinic);
			readings.add(reading);
		}
	}

	/**
	 * Adds the new readings to the Patient object readings arraylist if the patient is active i.e on trial
	 * @param readingId the reading Id number
	 * @param type the reading type
	 * @param value the reading value
	 * @param date the date of reading
	 */
	public void addNewReadings(String readingId, String type, String value, Date date, Clinic clinic) {
		System.out.println("new String value reading");
		if (active) { //Only add if the patient is active i.e on trial
			try {
				double numValue = Integer.parseInt(value); //Every reading value except of blood_pressure type will be parsed into a double
				Reading reading = new Reading(readingId, type, numValue, date, clinic); //Create the reading object with the passed values
				readings.add(reading); //add the new reading to the readings ArrayList of the patient object
				JOptionPane.showMessageDialog(null, "New Reading has been added."); //Prompt if reading added
			}
			catch (java.lang.NumberFormatException e) { //For the reading value of blood_pressure type which will be string
				String bpValue = value; //assign to the bpValue string
				Reading reading = new Reading(readingId, type, bpValue, date, clinic); //create reading object
				readings.add(reading); //add the new reading to the reading ArrayList
				JOptionPane.showMessageDialog(null, "New Reading has been added."); //Prompt if the reading is added
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Patient is not currently in the trial."); //Prompt if patient is not active in trial
		}
	}
	/**
	 * Returns patient's active boolean value
	 * @return active the boolean to show if patient is in trial
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets true if patient is in trial
	 * @param active the boolean to show if patient is in trial
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * toString method to print the parameters of the Patient class in string form
	 */
	@Override
	public String toString() {
		String string = new String();
		for (Reading reading : readings) {
			string += reading.toString();
		}
		return string;
	}


}
