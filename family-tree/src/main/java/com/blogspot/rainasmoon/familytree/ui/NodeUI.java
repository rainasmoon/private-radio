package com.blogspot.rainasmoon.familytree.ui;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Label;

public abstract class NodeUI {
	
	public static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("WH:");

	
	private String type;
	private States state;	
	private boolean isOpened;
	
	public abstract AbstractLayout getLayout();		
	
	public static Label getSeperator() {
		return new Label("------");
	}
	
	public static Label getVseperator() {
		return new Label("|");
	}
	
	public enum States {
		asA_person,
		asA_parterner,
		asA_family_parent,
		asA_family_child,
	}
	
	public void setState(States state) {
		this.state = state;
	}
	
	public States getState() {
		return state;
	}
	
	public boolean isFamilyUI() {
		return this instanceof FamilyUI;		
	}
	
	public boolean isMarriageUI() {
		return this instanceof MarriageUI;		
	}
	
	public boolean isPersonUI() {
		return this instanceof PersonUI;		
	}
	
	public void setOpended() {
		isOpened = true;
	}
	
	public void setClosed() {
		isOpened = false;
	}
	
	public boolean isOpened() {
		return isOpened;
	}
	
	public boolean isClosed() {
		return !isOpened;
	}
	
	public String toString() {
		return "states:" + state;
	}
}
