package model;

import android.content.Context;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

//TEST CLASS
public class JSONHandlerB {
	File file;
	FileWriter filewriter = null;
	BufferedWriter bufferedWriter = null;
	private Map<String, User> userData = new HashMap<String, User>();
	String filePathJSON;
	public JSONHandlerB(Context context, String filepath) {
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
				System.out.println("openJSB successfuly");
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
			System.out.println("extract to user data JSB");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addUser(User user) {
		if(!doesUserExist(user.getName())){
			userData.put(user.getName(), user);
			updateJSON();
			System.out.println("User added JSB");
		}
		else{System.out.println("User already exists JSB");}

	}
	public boolean doesUserExist(String username) {
		return userData.containsKey(username);
	}
	public void updateJSON(){
		try {
			filewriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(filewriter);
			bw.write(new ObjectMapper().writeValueAsString(userData));
			bw.close();
			System.out.println("Update succesfully JSB");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Update failed JSB");
		}
	}
	public Map<String, User> getUserData() {
		return userData;
	}

}
