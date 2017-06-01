package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import controller.RobocopyController;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private InputsPanel inputPanel = new InputsPanel();
	private ActionPanel actionPanel = new ActionPanel();
	private RobocopyController robocopyController = new RobocopyController();
	private JFileChooser fileChooser = new JFileChooser();

	public MainFrame() {
		super("Backup Tools");
		setSize(530, 430);
		setMinimumSize(new Dimension(530, 430));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		fileChooser.addChoosableFileFilter(new QueueFileFilter());

		setJMenuBar(createMenuBar());

		setLayout(new BorderLayout());

		add(actionPanel, BorderLayout.SOUTH);
		add(inputPanel, BorderLayout.WEST);

		actionPanel.setActionFormListener(new ActionFormListener() {

			public void actionFormEventOccured(ActionFormEvent e) {

				if (e.getButtonId() == ActionType.SetDirectory) {
					inputPanel.setDirectory();
				}

				if (e.getButtonId() == ActionType.AddScript) {
					if (inputPanel.isScriptCompelete()) {
						
						String name = new String(inputPanel.getName());
						File source = new File(inputPanel.getSourceDirectoryFile().getPath());
						File backup = new File(inputPanel.getBackupDirecotryFile().getPath());
						File log = null;
						if(inputPanel.getLogDirectoryFile() != null) {
							log = new File(inputPanel.getLogDirectoryFile().getPath());
						}
						String switches = new String(inputPanel.getRobocopySwitches());
						boolean mirrorBackup = e.isMirror();
						robocopyController.addScript(name, source,	backup, log, switches, mirrorBackup);
						int scriptId = robocopyController.getLastId();
						actionPanel.addScript(scriptId, name);
						actionPanel.selectLastScript();
						
					} else {
						JOptionPane.showMessageDialog(rootPane,
								"The Script Name, Source Directory, & Backup Directory must be defined to add the script to the queque.",
								"INCOMPLETE SCRIPT", JOptionPane.OK_OPTION | JOptionPane.WARNING_MESSAGE);
					}
				}

				if (e.getButtonId() == ActionType.RemoveScript) {
					int index = e.getSelectedScriptIndex();
					int id = e.getSelectedScriptId();
					if(index == -1) {
						JOptionPane.showMessageDialog(rootPane,
								"No scripts have been added to backup sequence.",
								"CANNOT REMOVE SCRIPT", JOptionPane.OK_OPTION | JOptionPane.WARNING_MESSAGE);
					}
					else {
						actionPanel.removeScript(index);
						robocopyController.removeScript(id);
					}
				}
				
				if (e.getButtonId() == ActionType.ScriptSelected) {
					int id = e.getSelectedScriptId();
					String scriptName = robocopyController.getScriptName(id);
					File sourceDirectory = robocopyController.getSourceDirectory(id);
					File backupDirectory = robocopyController.getBackupDirectory(id);
					File logFile = robocopyController.getLogFile(id);
					String switches = robocopyController.getRobocopySwitches(id);
					boolean mirrorSelected = robocopyController.isMirrorBackup(id);
					
					inputPanel.refresh(false, scriptName, sourceDirectory, backupDirectory, logFile, switches);
					actionPanel.refresh(mirrorSelected);
					
					
				}
			}
		});

	}

	private JMenuBar createMenuBar() {

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem showDataBaseRobocopyScriptItem = new JMenuItem("Show DB");
		JMenuItem newRoboCopyScriptItem = new JMenuItem("New");
		JMenuItem openRoboCopyScriptItem = new JMenuItem("Open...");
		JMenuItem saveRoboCopyScriptItem = new JMenuItem("Save...");
		JMenuItem runRoboCopyScriptItem = new JMenuItem("Run");
		JMenuItem exitItem = new JMenuItem("Exit");
		
		fileMenu.add(showDataBaseRobocopyScriptItem);
		fileMenu.add(newRoboCopyScriptItem);
		fileMenu.add(openRoboCopyScriptItem);
		fileMenu.add(saveRoboCopyScriptItem);
		fileMenu.add(runRoboCopyScriptItem);
		fileMenu.add(exitItem);
		
		showDataBaseRobocopyScriptItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		showDataBaseRobocopyScriptItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(robocopyController.databaseToString());
			}
		});
		
		newRoboCopyScriptItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				robocopyController.clearScriptsDatabase();
				actionPanel.removeAllScripts();
				inputPanel.refresh(true, "", null, null, null, "");
			}
		});
		
		openRoboCopyScriptItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openRoboCopyScriptItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						robocopyController.loadFromFile(fileChooser.getSelectedFile());
						Integer[] scriptIdArray = robocopyController.getScriptIdArray();
						String[] scriptNameArray = robocopyController.getScriptNameArray();
						actionPanel.removeAllScripts();
						actionPanel.loadScriptList(scriptIdArray, scriptNameArray);
						actionPanel.selectFirstScript();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file.", "Error", JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		});
		
		saveRoboCopyScriptItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveRoboCopyScriptItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(robocopyController.databaseSize() >= 1) {
					if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
						try {
							File file = fileChooser.getSelectedFile();
							robocopyController.saveToFile(Utils.forceFileExtension("cpy", file));
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(MainFrame.this, "Could not save data from file.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(MainFrame.this, "No scripts added to queue.", "CANNOT SAVE", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});

		runRoboCopyScriptItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		runRoboCopyScriptItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Run");
				if(robocopyController.getScriptCount() > 0) {
					robocopyController.runBackupSequence();
				}
				else {
					JOptionPane.showMessageDialog(rootPane, "No Scripts Loaded", "CANNOT RUN", JOptionPane.OK_OPTION | JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int action = JOptionPane.showConfirmDialog(MainFrame.this,
						"Do you really want to exit this application?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				if (action == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});

		return menuBar;

	}
}
