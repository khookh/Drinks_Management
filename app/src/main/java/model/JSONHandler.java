package model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the JSON database
 */
public class JSONHandler {


	private User activeUser;
	/**
	 * Integer used to keep
	 */
	private static Integer errorID = 0;

	/**
	 * Path to the used JSON database
	 */
	private String filePathJSON;

	/**
	 * Data of all users extracted from JSON file
	 */
	private Map<String, User> userData = new HashMap<String, User>();
	//todo private String errorPathJSON;

	/**
	 * Create a Handler for JSON file.
	 * Manages creation, deletion, and modifications on JSON files.
	 *
	 * @param filepath path to requested database location
	 */
	public JSONHandler(String filepath) {
		filePathJSON = filepath;
		File userfile;
		if (!doesJSONExists()) { //if json file does not exists then create a new one
			userfile = createNewJSON(filePathJSON);
		} else {
			userfile = new File(filePathJSON);
		}
		//todo remove
		ObjectMapper mapper = new ObjectMapper();
		try { //try to load the data from the json file
			userData = mapper.readValue(userfile, new TypeReference<HashMap<String, User>>() {
			});
		} catch (IOException e) {
			// if error then create a backup file and increments the error id
			String filename = "error" + errorID.toString() + ".json";
			errorID++;
			File errorFile = new File(filename);
			userfile.renameTo(errorFile);
			userfile = createNewJSON(filePathJSON);
			/*
			TODO check if error0 exists, error1, ..., errorN
			TODO manage the error window (notifying view)
			TODO NOTIFY VIEW with path to errorFile
			String errorMessage = "An error occured.\nPlease consider to manage the error from the " + filePathJSON +
					" file stored in the " + filename + " file.";
			AlertWindow alert = new AlertWindow("Corrupted File", errorMessage);
			 */
		}
	}

	/**
	 * Verify if the given user exists
	 *
	 * @param username user's username
	 * @return if user exist then return true
	 */
	public boolean doesUserExist(String username) {
		return userData.containsKey(username);
	}

	/**
	 * Get User from a given username
	 *
	 * @param username
	 * @return if user exists then return the user's data from JSON as User object
	 */
	public User getUser(String username) {
		return userData.get(username);
	}

	/**
	 * Add a User to the JSON data
	 *
	 * @param user
	 * @return if user does not already exists then adds it to the database and returns true, else returns false
	 */
	public boolean addUser(User user) {
		String username = user.getName();
		if (username.equals("")) {
			return false;
		} // refuses a blank username

		if (!doesUserExist(username)) { //if user does not already exist
			userData.put(username, user);
			updateJSON(); //save the data to the json file
			return true;
		}
		return false;
	}

	/**
	 * Delete a user from the JSON data, given a certain username
	 *
	 * @param username
	 * @return if the user has been deleted then returns true
	 */
	public boolean deleteUser(String username) {
		try {
			if (doesUserExist(username)) { // test if user exists before attempting to delete it
				userData.remove(username);
				updateJSON(); //save the data to the json file
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Update user data in JSON
	 *
	 * @param user
	 * @return if the user has been updated to the JSON then returns true
	 */
	public boolean updateUser(User user) {
		try {
			if (doesUserExist(user.getName())) { // test if user exists before attempting to update it
				deleteUser(user.getName()); // delete the user from json file
				addUser(user);// re add the user with the new data to it
				setActiveUser(user);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * Update the JSON database with current user's data
	 *
	 * @return
	 */
	private boolean updateJSON() {
		try {
			FileWriter writer = new FileWriter(filePathJSON, false); // try to write to file
			writer.write(new ObjectMapper().writeValueAsString(userData));
			writer.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Creates a new JSON file
	 *
	 * @param path
	 * @return
	 */
	private File createNewJSON(String path) {
		File myObj = new File(path);
		try {
			myObj.createNewFile();
			try (FileWriter writer = new FileWriter(path, false)) {
				Map<String, User> emptydata = new HashMap<>();
				writer.write(new ObjectMapper().writeValueAsString(emptydata));
			} catch (IOException e) {
				//System.exit(0);
			}
		} catch (IOException e) {
			//System.exit(0);
		}
		return myObj;
	}

	/**
	 * Verifies if a JSON file already exists, using the path given to the JSONHandler.
	 *
	 * @return
	 */
	private boolean doesJSONExists() {
		return new File(filePathJSON).exists(); // try to open the file from filepath. If error then file does not exist
	}

	/**
	 * Necessary to keep it public in order to perform test. Works like a setter to change errorID back to zero
	 */
	public static void resetErrorID() {
		errorID = 0;
	}

	public User getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(User activeUser) {
		this.activeUser = activeUser;
	}
}