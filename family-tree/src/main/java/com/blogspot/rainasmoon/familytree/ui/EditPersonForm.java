package com.blogspot.rainasmoon.familytree.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

public class EditPersonForm extends Form {

	private TextField name = new TextField("name");

	private TextField sex = new TextField("sex");
	private Button save = new Button("save");

	private Button cancel = new Button("Cancel");
	
	public EditPersonForm() {
		

		getLayout().addComponent(name);
		getLayout().addComponent(sex);
		
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(save);
		hl.addComponent(cancel);
				
		getLayout().addComponent(hl);
	}

	public Button getSave() {
		return save;
	}

	public void setSave(Button save) {
		this.save = save;
	}

	public Button getCancel() {
		return cancel;
	}

	public void setCancel(Button cancel) {
		this.cancel = cancel;
	}
	
}
