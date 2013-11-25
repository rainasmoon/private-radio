package com.blogspot.rainasmoon.familytree.ui;

import com.vaadin.ui.HorizontalLayout;

public class MarriageUI extends NodeUI {

	private PersonUI husband;
	private PersonUI wife;
	
	private HorizontalLayout layout;

	
	
	public MarriageUI(PersonUI husband, PersonUI wife) {
		super();
		this.husband = husband;
		this.wife = wife;
		if (husband.getLayout() != null) {
			layout = new HorizontalLayout();
			layout.addComponent(husband.getLayout());
			if (wife.getLayout() != null) {
				layout.addComponent(getSeperator());
				layout.addComponent(wife.getLayout());
			}
		}
	}

	public PersonUI getHusband() {
		return husband;
	}

	public void setHusband(PersonUI husband) {
		this.husband = husband;
	}

	public PersonUI getWife() {
		return wife;
	}

	public void setWife(PersonUI wife) {
		this.wife = wife;
	}

	public HorizontalLayout getLayout() {
		return layout;
	}

	public void setLayout(HorizontalLayout layout) {
		this.layout = layout;
	}
	
}
