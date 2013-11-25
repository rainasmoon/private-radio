package com.blogspot.rainasmoon.familytree.ui;

import com.blogspot.rainasmoon.familytree.entity.people.People;
import com.blogspot.rainasmoon.familytree.service.ServiceProvider;
import com.blogspot.rainasmoon.familytree.service.SpringContextHelper;
import com.blogspot.rainasmoon.familytree.service.map.MapManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;



public class PersonUI extends NodeUI implements ClickListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4296287831813104380L;

	private String name;
	
	private Button me;
	private MarriageUI marriage;
	private FamilyUI family;
	
	private Component descendant;
	private HorizontalLayout layout;
	
	
	public PersonUI(People p) {
		if (p != null) {
			this.name = p.getName();
			me = new Button(p.getName());
			me.addListener(this);
			layout = new HorizontalLayout();
			layout.addComponent(me);
		}
	}
	
	public PersonUI(String name) {
		this.name = name;
		me = new Button(name);
		me.addListener(this);
		layout = new HorizontalLayout();
		layout.addComponent(me);
	}

	@Override
	public void buttonClick(ClickEvent event) {			
			openMenu();
			return;
	}
	
	private void openMenu() {

		final Window menuWindow = new Window();
		menuWindow.setHeight("300px");
		menuWindow.setWidth("300px");

		Menu menu = new Menu();	
		
		menu.getOpen().addListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {					
								
				SpringContextHelper helper = new SpringContextHelper(event.getComponent().getApplication());  
				MapManager mapManager = (MapManager) helper.getBean("mapManager");  
				
					NodeUI family = mapManager.createFamilyUI(name);
										
					if (isClosed()) {
						setDescendant(family.getLayout());
						getLayout().removeComponent(me);
						getLayout().addComponent(family.getLayout());
						setOpended();
					}
					
					getLayout().getWindow().removeWindow(menuWindow);
			}

			
		});
		
		menu.getOpenParents().addListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				SpringContextHelper helper = new SpringContextHelper(event.getComponent().getApplication());  
				MapManager mapManager = (MapManager) helper.getBean("mapManager");  
				
				//TODO: glen will maintains it in along time future...
					NodeUI parent = mapManager.createHisParentUI(name);
					
					event.getComponent().getWindow().removeAllComponents();
					event.getComponent().getWindow().addComponent(parent.getLayout());
				
			}
		});
		
		menu.getClose().addListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {				
					getLayout().removeComponent(getDescendant());
					getLayout().getWindow().removeWindow(menuWindow);	
					setClosed();
			}
		});
		
		menu.getEdit().addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				editWindow();
				getLayout().getWindow().removeWindow(menuWindow);
			}
		});
		
		
		menuWindow.addComponent(menu);
		layout.getWindow().addWindow(menuWindow);

	}

	@SuppressWarnings("serial")
	private void editWindow() {
		final Window editWindow = new Window();

		editWindow.setHeight("300px");
		editWindow.setWidth("300px");

		EditPersonForm editPersonForm = new EditPersonForm();
		editWindow.addComponent(editPersonForm);

		editPersonForm.getCancel().addListener(new Button.ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6153503950101920547L;

			@Override
			public void buttonClick(ClickEvent event) {
				layout.getWindow().removeWindow(editWindow);

			}
		});

		editPersonForm.getSave().addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				layout.getWindow().removeWindow(editWindow);

			}
		});

		layout.getWindow().addWindow(editWindow);

	}
	
	public Button getMe() {
		return me;
	}

	public void setMe(Button me) {
		this.me = me;
	}

	public Component getDescendant() {
		return descendant;
	}

	public void setDescendant(Component descendant) {
		this.descendant = descendant;
	}

	public HorizontalLayout getLayout() {	
		return layout;
	}

	public void setLayout(HorizontalLayout layout) {
		this.layout = layout;
	}

	public MarriageUI getMarriage() {
		return marriage;
	}

	public void setMarriage(MarriageUI marriage) {
		this.marriage = marriage;
	}

	public FamilyUI getFamily() {
		return family;
	}

	public void setFamily(FamilyUI family) {
		this.family = family;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
