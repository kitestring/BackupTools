package controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import model.Script;
import model.ScriptsDataBase;

public class RobocopyController {
	
	ScriptsDataBase scriptsDB = new ScriptsDataBase();
	
	public int getLastId() {
		return scriptsDB.getLastId();
	}
	
	public String getScriptName(int id) {
		return scriptsDB.getScriptName(id);
	}
	
	public File getSourceDirectory(int id) {
		return scriptsDB.getSourceDirectory(id);
	}
	
	public File getBackupDirectory(int id) {
		return scriptsDB.getBackupDirectory(id);
	}
	
	public File getLogFile(int id) {
		return scriptsDB.getLogFile(id);
	}
	
	public String getRobocopySwitches(int id) {
		return scriptsDB.getRobocopySwitches(id);
	}
	
	public boolean isMirrorBackup(int id) {
		return scriptsDB.isMirrorBackup(id);
	}
	
	public void removeScript(int id) {
		scriptsDB.removeScript(id);
	}
	
	public void clearScriptsDatabase() {
		scriptsDB.removeAllScripts();
	}
	
	public void addScript(String name, File source, File backup, File log, String roboCopySwitches, boolean isMirrorBackup) {
		scriptsDB.addScript(new Script(name, source, backup, log, roboCopySwitches, isMirrorBackup));
	}
	
	public int getScriptCount() {
		
		return scriptsDB.getScriptCount();
	}
	
	private void executeConcoleStatement(String command) {
		try {
			Runtime.getRuntime().exec(command);
			System.out.println("Running...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done...");
	}
	
	private String buildScript(Script script) {
		
		StringBuilder sb = new StringBuilder();
		
		File sourceDirectory = script.getSource();
		File backupDirectory = script.getBackup();
		File logDirectory = script.getLog();
		String robocopySwitches = script.getRoboCopySwitches();
		boolean isMirrorBackup = script.isMirrorBackup();
		
		sb.append("robocopy ")
		.append("\"").append(sourceDirectory.getPath()).append("\" ")
		.append("\"").append(backupDirectory.getPath()).append("\"");
		
		if(logDirectory != null) {
			sb.append(" /LOG+:").append(logDirectory.getPath());
		}
		
		if(robocopySwitches != null) {
			sb.append(" ").append(robocopySwitches);
		}
		
		if(isMirrorBackup) {
			sb.append(" ").append("/mir");
		}
		else {
			sb.append(" ").append("/e");
		}
		
		return sb.toString();
	}
	
	public void runBackupSequence() {
		LinkedHashMap<Integer, Script> scriptsMap = new LinkedHashMap<Integer, Script>();
		String robocopyScript;
		
		scriptsMap = scriptsDB.getAllScripts();
		
		for(Integer key: scriptsMap.keySet()) {
			robocopyScript = buildScript(scriptsMap.get(key));
			System.out.println(robocopyScript);
			executeConcoleStatement(robocopyScript);
		}
		
	}
	
	public void saveToFile(File file) throws IOException {
		scriptsDB.saveToFile(file);
	}
	
	public void loadFromFile(File file) throws IOException {
		scriptsDB.loadFromFile(file);
	}
	
	public int databaseSize() {
		return scriptsDB.databaseSize();
	}
	

	public Integer[] getScriptIdArray() {
		return scriptsDB.getScriptIdArray();
	}


	public String[] getScriptNameArray() {
		return scriptsDB.getScriptNameArray();
	}
	
	public String databaseToString() {
		return scriptsDB.toString();
	}
}
