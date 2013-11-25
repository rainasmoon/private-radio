package com.blogspot.rainasmoon.familytree.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class Menu extends  VerticalLayout{

	private Button open = new Button("open");
	private Button openParents = new Button("open parents");
	private Button close = new Button("close");
	private Button edit = new Button("edit");
	
	public Menu() {
			
		setSizeUndefined();
		setSpacing(true);
		setMargin(true);

		
		addComponent(open);
		addComponent(openParents);
		addComponent(close);
		addComponent(edit);
		
	}

	public Button getOpen() {
		return open;
	}

	public void setOpen(Button open) {
		this.open = open;
	}

	public Button getClose() {
		return close;
	}

	public void setClose(Button close) {
		this.close = close;
	}

	public Button getEdit() {
		return edit;
	}

	public void setEdit(Button edit) {
		this.edit = edit;
	}

	public Button getOpenParents() {
		return openParents;
	}

	public void setOpenParents(Button openParents) {
		this.openParents = openParents;
	}
}
