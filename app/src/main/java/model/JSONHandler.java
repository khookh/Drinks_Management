package model;

import android.content.Context;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * JSONHander : handle the users data
 */
public class JSONHandler extends Observable {
	User activeUser;
	File file;
	FileWriter filewriter = null;
	BufferedWriter bufferedWriter = null;
	private Map<String, User> userData = new HashMap<String, User>();
	String filePathJSON;
	public JSONHandler(Context context, String filepath) {
		filePathJSON = filepath;
		file = new File(context.getFilesDir(),filepath);
		if(!file.exists()){
			try {
				file.createNewFile();
				filewriter = new FileWriter(file.getAbsoluteFile());
				bufferedWriter = new BufferedWriter(filewriter);
				Map<String, User> emptydata = new HashMap<>();
				bufferedWriter.write(new ObjectMapper().writeValueAsString(emptydata));
				bufferedWriter.close();
			}catch(IOException e){
				e.printStackTrace();
				System.out.println("FAIL JSB open");
			}
		}
		else{System.out.println("JSB file already exists");}
		ObjectMapper mapper = new ObjectMapper();
		try {
			userData = mapper.readValue(file, new TypeReference<HashMap<String, User>>() {
			});
		} catch (IOException e) {
			System.out.println("failed to extract to userdata JSB");
			e.printStackTrace();
		}
	}

	/**
	 * add a user to the .json
	 * @param user
	 */
	public void addUser(User user) {
		if(!doesUserExist(user.getName())){
			userData.put(user.getName(), user);
			updateJSON();
		}
		else{System.out.println("User already exists JSB");}

	}

	/**
	 * check if the user exists
	 * @param username
	 * @return boolean ; true if user exists
	 */
	public boolean doesUserExist(String username) {
		return userData.containsKey(username);
	}

	/**
	 * delete the user from the .json
	 * @param user
	 */
	public void deleteUser(User user){
		if(doesUserExist(user.getName())){
			userData.remove(user.getName());
			updateJSON();
		}
	}

	/**
	 * update the user stored in the .json by an newer one
	 * @param user
	 */
	public void updateUser(User user){
		if(doesUserExist(user.getName())){
			deleteUser(user);
			addUser(user);
		}
	}

	/**
	 * notify the rest of the model
	 */
	public void notifyObs(){
		setChanged(); //activate true flag : data has been changed
		notifyObservers(); //notify observers of changes
	}

	/**
	 * return the user having this username
	 * @param username
	 * @return
	 */
	public User getUser(String username) {
		return userData.get(username);
	}

	/**
	 * write userData into the .json
	 */
	public void updateJSON(){
		try {
			filewriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(filewriter);
			bw.write(new ObjectMapper().writeValueAsString(userData));
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Update failed JSB");
		}
		notifyObs(); //notify les observeurs à chaque update !
	}
	public Map<String, User> getUserData() {
		return userData;
	}
	public User getActiveUser() {
		return activeUser;
	}
	public void setActiveUser(User activeUser) {
		this.activeUser = activeUser;
	}

}
