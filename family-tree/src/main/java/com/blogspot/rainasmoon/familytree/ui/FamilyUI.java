package com.blogspot.rainasmoon.familytree.ui;

import java.util.List;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class FamilyUI extends NodeUI{

	private MarriageUI parents;
	private ChildrenUI children;
	
	private VerticalLayout layout;

	
	
	public FamilyUI(MarriageUI parents, ChildrenUI children) {
		super();
		this.parents = parents;
		this.children = children;		
		if (parents.getLayout() != null) {
			layout = new VerticalLayout();
			layout.addComponent(parents.getLayout());
			if (children.getLayout() != null) {
				layout.addComponent(getVseperator());
				layout.addComponent(children.getLayout());
			}
		}
		
	}
	
	public FamilyUI() {		
	}

	
	public MarriageUI getParents() {
		return parents;
	}

	public void setParents(MarriageUI parents) {
		this.parents = parents;
	}

	public ChildrenUI getChildren() {
		return children;
	}

	public void setChildren(ChildrenUI children) {
		this.children = children;
	}

	public VerticalLayout getLayout() {
		return layout;
	}

	public void setLayout(VerticalLayout layout) {
		this.layout = layout;
	}
	
	
	
}
