package trial;
/**
 * ClinicalTrial class has the arrayList of patients present in the trial.
 */

import java.util.ArrayList;

public class ClinicalTrial {
	//Creating an arrayList to hold the Patients in the trial
	private static ArrayList<Patient> allPatients = new ArrayList<Patient>();

	/**
	 * Getter for the allPatients arrayList
	 * @return allPatients the arrayList holding the Patients in the trial
	 */
	public static ArrayList<Patient> getAllPatients() {
		return allPatients;
	}

	/**
	 * findPatient finds if a patient is currently in a trial or not
	 * @param patientId the Id of the patient
	 * @return patient the patient if it exists in the allPatients arrayList
	 */
	public static Patient findPatient(String patientId){
		for (Patient patient : allPatients) { //loop through the allPatients arrayList
			if (patientId.equals(patient.getPatientId())) {
				return patient; //if patient exists in the trial
			}
		}
		return null; //if patient doesn't exist in the trial
	}

	/**
	 * Adds a Patient to the Clinical trial if that patient has not yet been added.
	 * @param patientId the Id of the patient
	 * @return boolean true if patient was added. False if another patient with the same ID was found
	 */
	public static Boolean addPatient(String patientId){
		for (Patient patient : allPatients) { //loop through the allPatients arrayList
			if (patientId.equals(patient.getPatientId())) {
				//a patient with that ID was found return false without adding a new patient
				return false;
			}
		}
		//if no patient was found then add a new patient to the trial
		Patient patient = new Patient(patientId);
		allPatients.add(patient);
		return true;
	}
}