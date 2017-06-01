package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class InputsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String defaultSwitches = "/fp /ts /r:0";

	private JLabel nameLabel = new JLabel("Script Name:");
	private JTextField nameText = new JTextField(20);

	private ButtonGroup directoryGroup = new ButtonGroup();

	private JRadioButton sourceDirectoryRadio = new JRadioButton("Source Directory:");
	private JTextField sourceDirectoryText = new JTextField(45);
	private File sourceDirectoryFile = null;

	private JRadioButton backupDirectoryRadio = new JRadioButton("Backup Directory:");
	private JTextField backupDirectoryText = new JTextField(45);
	private File backupDirecotryFile = null;

	private JRadioButton logDirectoryRadio = new JRadioButton("Log Directory (cannot contain spaces):");
	private JTextField logDirectoryText = new JTextField(45);
	private File logDirectoryFile = null;

	private JLabel robocopySwitchesLabel = new JLabel("Robocopy Switches (advanced):");
	private JTextField robocopySwitchesText = new JTextField(45);

	private JFileChooser fileChooser = new JFileChooser();

	public InputsPanel() {

		Dimension dim = getPreferredSize();
		dim.width = 525;
		setPreferredSize(dim);

		// Setup Radio buttons
		directoryGroup.add(sourceDirectoryRadio);
		directoryGroup.add(backupDirectoryRadio);
		directoryGroup.add(logDirectoryRadio);
		sourceDirectoryRadio.setSelected(true);
		sourceDirectoryRadio.setActionCommand("Source");
		backupDirectoryRadio.setActionCommand("Backup");
		logDirectoryRadio.setActionCommand("Log");

		// Setup text fields
		sourceDirectoryText.setEnabled(false);
		backupDirectoryText.setEnabled(false);
		logDirectoryText.setEnabled(false);
		robocopySwitchesText.setText(defaultSwitches);

		addComponents();
	}

	private void addComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		////// R0 C0 - Script Name Label //////
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 5, 0, 0);
		add(nameLabel, gc);

		////// R1 C0 - Script Name Text //////
		gc.gridx = 0;
		gc.gridy++;
		gc.insets = new Insets(0, 5, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(nameText, gc);

		////// R2 C0 - Source Directory Radio Button //////
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(sourceDirectoryRadio, gc);

		////// R3 C0 - Source Directory Text Box //////
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 0.1;
		gc.insets = new Insets(0, 5, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(sourceDirectoryText, gc);

		////// R4 C0 - Backup Directory Radio Button //////
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(backupDirectoryRadio, gc);

		////// R5 C0 - Backup Directory Text Box //////
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 0.1;
		gc.insets = new Insets(0, 5, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(backupDirectoryText, gc);

		////// R6 C0 - Log Directory Radio Button //////
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(logDirectoryRadio, gc);

		////// R7 C0 - Log Directory Text Box //////
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 0.1;
		gc.insets = new Insets(0, 5, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(logDirectoryText, gc);

		////// R8 C0 - Robocopy Switches Label //////
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(3, 5, 0, 0);
		add(robocopySwitchesLabel, gc);

		////// R9 C0 - Robocopy Switches Text Box //////
		gc.gridx = 0;
		gc.gridy++;
		gc.weightx = 0.1;
		gc.insets = new Insets(3, 5, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(robocopySwitchesText, gc);

	}

	public void setDirectory() {
		File file = null;

		if (directoryGroup.getSelection().getActionCommand() == logDirectoryRadio.getActionCommand()) {
			fileChooser.addChoosableFileFilter(new TextFileFilter());
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();

				if (file.getPath().contains(" ")) {
					warningDialogue("PATH CONTAINS SPACES", "The log file path cannot contain contain spaces.");
				} else {
					file = Utils.forceFileExtension("txt", file);
					logDirectoryText.setText(file.getPath());
					logDirectoryFile = file;
				}
			}
		} else {
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

				file = fileChooser.getSelectedFile();

				if (sourceDirectoryRadio.isSelected()) {
					if (file.equals(backupDirecotryFile)) {
						warningDialogue("SAME DIRECTORY", "Cannot be identical to backup directory.");
					} else {
						sourceDirectoryText.setText(file.getPath());
						sourceDirectoryFile = file;
					}
				} else if (backupDirectoryRadio.isSelected()) {

					if (file.equals(sourceDirectoryFile)) {
						warningDialogue("SAME DIRECTORY", "Cannot be identical to source directory.");
					} else {
						backupDirectoryText.setText(file.getPath());
						backupDirecotryFile = file;
					}

				}
			}

		}
		if (logDirectoryRadio.isSelected()) {

		}
	}

	public boolean isScriptCompelete() {
		return (nameText.getText().isEmpty() == false && sourceDirectoryFile != null && backupDirecotryFile != null);
	}

	private void warningDialogue(String title, String message) {
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.OK_OPTION | JOptionPane.WARNING_MESSAGE);
	}

	public String getName() {
		return nameText.getText();
	}

	public File getSourceDirectoryFile() {
		return sourceDirectoryFile;
	}

	public File getBackupDirecotryFile() {
		return backupDirecotryFile;
	}

	public File getLogDirectoryFile() {
		return logDirectoryFile;
	}

	public String getRobocopySwitches() {
		return robocopySwitchesText.getText();
	}
	
	public void refresh(boolean isNew, String nameText, File sourceDirectoryFile, File backupDirecotryFile, File logDirectoryFile, String robocopySwitchesText) {
		this.nameText.setText(nameText);
		this.sourceDirectoryFile = sourceDirectoryFile;
		this.backupDirecotryFile = backupDirecotryFile;
		this.logDirectoryFile = logDirectoryFile;
		if(isNew) {
			this.robocopySwitchesText.setText(defaultSwitches);
		}
		else {
			this.robocopySwitchesText.setText(robocopySwitchesText);
		}
		refreshDisplay();
	}
	
	private void refreshDisplay() {
		
		if(sourceDirectoryFile != null) {
			sourceDirectoryText.setText(sourceDirectoryFile.getPath());
		}
		else {
			sourceDirectoryText.setText("");
		}
			
		if(backupDirecotryFile != null) {
			backupDirectoryText.setText(backupDirecotryFile.getPath());
		}
		else {
			backupDirectoryText.setText("");
		}
		
		if(logDirectoryFile != null) {
			logDirectoryText.setText(logDirectoryFile.getPath());
		}
		else {
			logDirectoryText.setText("");
		}
		
	}

}
