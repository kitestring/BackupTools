package gui;

import java.io.File;

public class Utils {
	
	public static String getFileExtension(String name) {
		
		// Were checking to see what number character the last . is in the string so that we can grab the extension
		int pointIndex = name.lastIndexOf(".");
		
		//If it cannot find the . anywhere in the string 
		if(pointIndex == -1) {
			return null;
		}
		
		
		//Verifies that there are  actually are characters after the .
		if(pointIndex == name.length()-1) {
			return null;
		}
		
		return name.substring(pointIndex+1, name.length());
		
	}
	
	public static File forceFileExtension(String forcedExtenstion, File file) {
		
		String currentEntenstion = getFileExtension(file.getPath());
		String forcedExtenstionPath = file.getPath() + "." + forcedExtenstion;
		File forcedExtenstionFile = new File(forcedExtenstionPath);
		
		if(currentEntenstion != null) {
			if(currentEntenstion.equals(forcedExtenstion)) {
				return file;
			}
		}
		return forcedExtenstionFile;
	}
}
