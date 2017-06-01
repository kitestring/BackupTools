package gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class TextFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		
		//To prevent from filtering directories away we need to do the following
		if(f.isDirectory()) {
			return true;
		}
		
		String name = f.getName();
		
		String extension = Utils.getFileExtension(name);
		
		if(extension == null) {
			return false;
		}

		if(extension.equals("txt")) {
			return true;
		}
		
		return false;
			
	}

	@Override
	public String getDescription() {
		return "Normal Text File (*.txt)";
	}

}
