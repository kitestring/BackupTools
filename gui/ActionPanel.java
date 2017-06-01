package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class ActionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel scriptsListLabel = new JLabel("Scripts:");
	private JComboBox scriptsList = new JComboBox<>();
	DefaultComboBoxModel scriptsListModel = new DefaultComboBoxModel();
	
	private JLabel mirrorLabel = new JLabel("Mirror:");
	private JCheckBox mirrorCheck = new JCheckBox();
	
	private JButton setDirectoryBtn = new JButton("Set Directory");
	private JButton addScriptBtn = new JButton("Add Script");
	private JButton removeScriptBtn = new JButton("Remove Script");
	
	//private JFileChooser fileChooser;
	ActionFormListener actionFormListener;
	
	public ActionPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 525;
		dim.height = 100;
		setPreferredSize(dim);
		
		// Set Up Combo Box
		scriptsList.setModel(scriptsListModel);
	
		addComponents();
		
		// Set Directory Button Action Listener
		
		setDirectoryBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionFormEvent ev = new ActionFormEvent(this, ActionType.SetDirectory);
				if(actionFormListener != null) {
					actionFormListener.actionFormEventOccured(ev);
				}
			}
		});
		
		// Add Script Button Action Listener
		
		addScriptBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ActionFormEvent ev = new ActionFormEvent(this, ActionType.AddScript, mirrorCheck.isSelected());
				if(actionFormListener != null) {
					actionFormListener.actionFormEventOccured(ev);
				}
			}
		});
		
		// Remove Script Button Action Listener
		
		removeScriptBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(scriptsList.getItemCount() != 0) {
					ScriptListItem scriptListItem = (ScriptListItem) scriptsList.getSelectedItem();
					int id = scriptListItem.getId();
					int index = scriptsList.getSelectedIndex();
					ActionFormEvent ev = new ActionFormEvent(this, ActionType.RemoveScript, index, id);
					if(actionFormListener != null) {
						actionFormListener.actionFormEventOccured(ev);
					}
					
				}
				
			}
		});
		
		// scriptsList combo box action Listener
		
		scriptsList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ScriptListItem scriptListItem = (ScriptListItem) scriptsList.getSelectedItem();
				if(scriptListItem != null) {
					int id = scriptListItem.getId();
					ActionFormEvent ev = new ActionFormEvent(this, ActionType.ScriptSelected, id);
					if(actionFormListener != null) {
						actionFormListener.actionFormEventOccured(ev);
					}
				}
				
				
			}
		});
	}
	
	public void refresh(boolean mirrorCheck) {
		this.mirrorCheck.setSelected(mirrorCheck);
	}
	
	@SuppressWarnings("unchecked")
	public void addScript(int scriptId, String name) {
		String nameAbv;
		if(name.length() > 7) {
			nameAbv = name.substring(0, 7);
		}
		else {
			nameAbv = name;
		}
		scriptsListModel.addElement(new ScriptListItem(scriptId, nameAbv));
	}
	
	public void loadScriptList(Integer[] scriptId, String[] scriptName) {
		int i = 0;
		for(int id: scriptId) {
			addScript(id, scriptName[i]);
			i++;
		}
	}
	
	public void selectFirstScript() {
		scriptsList.setSelectedIndex(0);
	}
	
	public void selectLastScript() {
		scriptsList.setSelectedIndex(scriptsList.getItemCount() - 1);
	}
	
	public void removeScript(int index) {
		scriptsListModel.removeElementAt(index);
	}
	
	public void removeAllScripts() {
		scriptsListModel.removeAllElements();
	}
	
	public void setActionFormListener(ActionFormListener actionFormListener) {
		this.actionFormListener = actionFormListener;
	}
	private void addComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		////// R0 C0 - Mirror Label //////
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 0, 0);
		add(mirrorLabel, gc);
		
		// R0 C1 - Mirror Check Box //
		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(mirrorCheck, gc);					
		
		////// R1 C0 - Scripts Label //////
		gc.gridx = 0;
		gc.gridy++;
		gc.insets = new Insets(0, 4, 0, 0);
		gc.anchor = GridBagConstraints.CENTER;
		add(scriptsListLabel, gc);
		
		// R1 C1 - Scripts Combo Box //
		gc.gridx = 1;
		gc.insets = new Insets(0, 4, 0, 50);
		//gc.anchor = GridBagConstraints.LINE_START;
		add(scriptsList, gc);			
		
		////// R2 C2 - Set Directory Button //////
		gc.gridx = 2;
		gc.gridy++;
		gc.weightx = 0;
		gc.insets = new Insets(10, 0, 0, 0);
		add(setDirectoryBtn, gc);
		
		////// R2 C3 - Add Script Button //////
		gc.gridx++;
		gc.weightx = 0;
		//gc.insets = new Insets(0, 0, 0, 0);
		add(addScriptBtn, gc);
		
		////// R2 C4 - Remove Scripts Button //////
		gc.gridx++;
		gc.weightx = 0;
		//gc.insets = new Insets(0, 0, 0, 0);
		add(removeScriptBtn, gc);
	}
	
}

class ScriptListItem {
	private String text;
	private int id;
	
	public ScriptListItem(int id, String text) {
		this.text = text;
		this.id = id;
	}

	@Override
	public String toString() {
		return text;
	}

	public int getId() {
		return id;
	}

	
	
}