package com.blogspot.rainasmoon.familytree.ui;

import java.util.HashMap;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.blogspot.rainasmoon.familytree.service.ServiceProvider;
import com.blogspot.rainasmoon.familytree.service.SpringContextHelper;
import com.blogspot.rainasmoon.familytree.service.map.MapManager;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.client.ui.layout.Margins;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout.MarginInfo;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

@Configurable(preConstruction = true)  
public class MainApplication extends Application {

	public static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("WH:");
	
	private static final long serialVersionUID = -4977362938701154788L;

//	private final MapManager mapManager = ServiceProvider
//			.getService(MapManager.class);
	
//	@Autowired
//	private MapManager mapManager;
//	
	@Override
	public void init() {

		Window mainWindow = new Window("Family tree sample.");
		
	    // Tab 1 content
        VerticalLayout l1 = new VerticalLayout();
        l1.setMargin(true);
        l1.addComponent(new Label("There are no previously saved actions."));
        // Tab 2 content
        VerticalLayout l2 = new VerticalLayout();
        l2.setMargin(true);
        l2.addComponent(new Label("There are no saved notes."));
        // Tab 3 content
        VerticalLayout l3 = new VerticalLayout();
        l3.setMargin(true);
        l3.addComponent(new Label("There are currently no issues."));

        TabSheet t = new TabSheet();
//        t.setHeight("200px");
//        t.setWidth("400px");

        t.setSizeFull();
        
        t.addTab(l1, "view 1");
        t.addTab(l2, "view 2");
        t.addTab(l3, "view 3");

        mainWindow.addComponent(t);

//		buildMainWindow(mainWindow);
		
		setMainWindow(mainWindow);
	}

	private void buildMainWindow(final Window mainWindow) {
		 final TextField searchName = new TextField("search name:");
		 Button search = new Button("search");
		 mainWindow.addComponent(searchName);
		 mainWindow.addComponent(search);
		 
		 mainWindow.showNotification((String) searchName.getValue());
		 
		 search.addListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				SpringContextHelper helper = new SpringContextHelper(event.getComponent().getApplication());  
				MapManager mapManager = (MapManager) helper.getBean("mapManager");  
			
				NodeUI family = mapManager.createFamilyUI((String) searchName.getValue());

				//father
				mainWindow.addComponent(family.getLayout());
				
			}
		});
		
	}

}
