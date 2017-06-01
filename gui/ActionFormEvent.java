package gui;

import java.util.EventObject;

public class ActionFormEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	private ActionType actionType;
	private boolean mirror;
	private int selectedScriptComboBoxIndex;
	private int selectedScriptId;

	public ActionFormEvent(Object source) {
		super(source);
	}
	
	//Constructor when set directory is clicked
	public ActionFormEvent(Object source, ActionType actionType) {
		super(source);
		this.actionType = actionType;
		
	}
	
	//Constructor when add script is clicked
	public ActionFormEvent(Object source, ActionType actionType, boolean mirror) {
		super(source);
		this.actionType = actionType;
		this.mirror = mirror;
	}
	
	//Constructor when remove script is clicked
	public ActionFormEvent(Object source, ActionType actionType, int selectedScriptComboBoxIndex, int selectedScriptId) {
		super(source);
		this.actionType = actionType;
		this.selectedScriptComboBoxIndex = selectedScriptComboBoxIndex;
		this.selectedScriptId = selectedScriptId;
	}
	
	public ActionFormEvent(Object source, ActionType actionType, int seletedScriptId) {
		super(source);
		this.actionType = actionType;
		this.selectedScriptId = seletedScriptId;
	}

	public ActionType getButtonId() {
		return actionType;
	}

	public void setButtonId(ActionType buttonId) {
		this.actionType = buttonId;
	}

	public boolean isMirror() {
		return mirror;
	}

	public void setMirror(boolean mirror) {
		this.mirror = mirror;
	}

	public int getSelectedScriptIndex() {
		return selectedScriptComboBoxIndex;
	}

	public void setselectedScriptComboBoxIndex(int selectedScriptComboBoxIndex) {
		this.selectedScriptComboBoxIndex = selectedScriptComboBoxIndex;
	}

	public int getSelectedScriptId() {
		return selectedScriptId;
	}

	public void setSelectedScriptId(int selectedScriptId) {
		this.selectedScriptId = selectedScriptId;
	}
	
	

}
