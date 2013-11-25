package com.blogspot.rainasmoon.familytree.ui;

import java.util.ArrayList;
import java.util.List;

import org.mortbay.log.Log;

import com.vaadin.ui.HorizontalLayout;

public class ChildrenUI extends NodeUI {

	private List<PersonUI> children; 
	
	private HorizontalLayout layout;

	public ChildrenUI() {
		layout = new HorizontalLayout();		
	}
	
	public ChildrenUI(List<PersonUI> children) {
		this();
		this.children = children;
		for(int i = 0; i < children.size(); i++){
			if (i != 0) {
				layout.addComponent(getSeperator());
			}
			layout.addComponent(children.get(i).getLayout());
		}
	}

	public ChildrenUI(PersonUI ... person) {
		this();
		children = new ArrayList<PersonUI>();		
		
		for (int i = 0; i < person.length; i++) {
			log.info("the size of chinldren:" + person.length);
			if (i != 0) {
				layout.addComponent(getSeperator());
			}
			children.add(person[i]);
			layout.addComponent(person[i].getLayout());
		}		
	}

	public List<PersonUI> getChildren() {
		return children;
	}

	public void setChildren(List<PersonUI> children) {
		this.children = children;
	}

	public HorizontalLayout getLayout() {
		return layout;
	}

	public void setLayout(HorizontalLayout layout) {
		this.layout = layout;
	}
}
