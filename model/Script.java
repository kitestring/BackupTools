package model;

import java.io.File;
import java.io.Serializable;

public class Script implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private static int count = 0;
	private String name;
	private File source;
	private File backup;
	private File log;
	private String roboCopySwitches;
	private boolean mirrorBackup;

	public Script(String name, File source, File backup, File log,
			String roboCopySwitches, boolean mirrorBackup) {
		
		this.name = name;
		this.source = source;
		this.backup = backup;
		this.log = log;
		this.roboCopySwitches = roboCopySwitches;
		this.mirrorBackup = mirrorBackup;
		
		this.id = count;
		count++;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public File getLog() {
		return log;
	}

	public void setLog(File logDirectory) {
		this.log = logDirectory;
	}

	public String getRoboCopySwitches() {
		return roboCopySwitches;
	}

	public void setRoboCopySwitches(String roboCopySwitches) {
		this.roboCopySwitches = roboCopySwitches;
	}

	public boolean isMirrorBackup() {
		return mirrorBackup;
	}

	public void setMirrorBackup(boolean mirrorBackup) {
		this.mirrorBackup = mirrorBackup;
	}

	public static int getCount() {
		return count;
	}
	
	

	@Override
	public String toString() {
		return "Script [id=" + id + ", name=" + name + ", source=" + source + ", backup=" + backup + ", log=" + log
				+ ", roboCopySwitches=" + roboCopySwitches + ", mirrorBackup=" + mirrorBackup + "]";
	}

	public File getSource() {
		return source;
	}

	public void setSource(File source) {
		this.source = source;
	}

	public File getBackup() {
		return backup;
	}

	public void setBackup(File backup) {
		this.backup = backup;
	}

	public static void setCount(int count) {
		Script.count = count;
	}
	
	
	
}
