package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;

public class ScriptsDataBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LinkedHashMap<Integer, Script> robocopyScriptsMap;
	
	public ScriptsDataBase() {
		robocopyScriptsMap = new LinkedHashMap<Integer, Script>();
	}
	
	public Script getScript(int index) {
		return robocopyScriptsMap.get(index);
	}
	
	public LinkedHashMap<Integer, Script> getAllScripts() {
		return robocopyScriptsMap;
	}
	
	public void removeScript(int key) {
		robocopyScriptsMap.remove(key);
	}

	public void removeAllScripts() {
		robocopyScriptsMap.clear();
		Script.setCount(0);
	}
	
	public void addScript(Script newScript) {
		int key = getLastId();
		robocopyScriptsMap.put(key, newScript);
	}
	
	public int getLastId() {
		return Script.getCount() -1;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ScriptsDataBase []\n\n");
		
		sb.append("robocopyScriptsMap:").append("\n");
		for(Integer key: robocopyScriptsMap.keySet()) {
			sb.append("key: " + key + " - value: " + robocopyScriptsMap.get(key))
			.append("\n");
		}
		
		
		sb.append("\nDatabase size: " + robocopyScriptsMap.size()).append("\n");
		sb.append("\nScript Count: ")
		.append(Script.getCount());
		return sb.toString();
	}
	
	public String getScriptName(int key) {
		return robocopyScriptsMap.get(key).getName();
	}
	
	public File getSourceDirectory(int key) {
		return robocopyScriptsMap.get(key).getSource();
	}
	
	public File getBackupDirectory(int key) {
		return robocopyScriptsMap.get(key).getBackup();
	}
	
	public File getLogFile(int key) {
		return robocopyScriptsMap.get(key).getLog();
	}
	
	public String getRobocopySwitches(int key) {
		return robocopyScriptsMap.get(key).getRoboCopySwitches();
	}
	
	public boolean isMirrorBackup(int key) {
		return robocopyScriptsMap.get(key).isMirrorBackup();
	}
	
	public int getScriptCount() {
		return robocopyScriptsMap.size();
	}
	
	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		Script[] scriptArray = convertMapToArray();
		
		oos.writeObject(scriptArray);
		
		oos.close();
	}
	
	private Script[] convertMapToArray() {
		
		Script[] scriptArray = new Script[robocopyScriptsMap.size()];
		int index = 0;
		
		for(Integer key: robocopyScriptsMap.keySet()) {
			scriptArray[index] = robocopyScriptsMap.get(key);
			index++;
		}
		
		return scriptArray;
	}
	
	public void loadFromFile(File file) throws IOException{
		
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			Script[] scriptArray = (Script[]) ois.readObject();
			
			removeAllScripts();
			for(int index = 0; index < scriptArray.length; index++) {
				robocopyScriptsMap.put(scriptArray[index].getId(), scriptArray[index]);
			}
			Script.setCount(getLargestKey() + 1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ois.close();
	}
	
	public int databaseSize() {
		return robocopyScriptsMap.size();
	}
	
	private int getLargestKey() {
		Integer[] keyArray = getScriptIdArray();
		Integer highestValue = keyArray[0];
		if(keyArray.length > 1) {
			for(Integer i = 1; i < keyArray.length; i ++) {
				if(keyArray[i] > highestValue) {
					highestValue = keyArray[i];
				}
			}
		}
		
		return highestValue;
	}
	
	public Integer[] getScriptIdArray() {
		Integer[] keyArray = new Integer[databaseSize()];
		int i = 0;
		for(Integer key: robocopyScriptsMap.keySet()) {
			keyArray[i] = key;
			i++;
		}
		return keyArray;
	}
	
	
	public String[] getScriptNameArray() {
		String[] nameArray = new String[databaseSize()];
		int i = 0;
		for(Integer key: robocopyScriptsMap.keySet()) {
			nameArray[i] = robocopyScriptsMap.get(key).getName();
			i++;
		}
		return nameArray;
				
	}
	
}
