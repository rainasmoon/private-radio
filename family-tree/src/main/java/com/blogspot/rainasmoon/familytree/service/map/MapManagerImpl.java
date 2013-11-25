package com.blogspot.rainasmoon.familytree.service.map;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.blogspot.rainasmoon.familytree.dao.people.ChildrenDao;
import com.blogspot.rainasmoon.familytree.entity.people.Children;
import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.entity.people.People;
import com.blogspot.rainasmoon.familytree.service.people.MarriageManager;
import com.blogspot.rainasmoon.familytree.service.people.PeopleManager;
import com.blogspot.rainasmoon.familytree.ui.ChildrenUI;
import com.blogspot.rainasmoon.familytree.ui.FamilyUI;
import com.blogspot.rainasmoon.familytree.ui.MarriageUI;
import com.blogspot.rainasmoon.familytree.ui.NodeUI;
import com.blogspot.rainasmoon.familytree.ui.PersonUI;
import com.blogspot.rainasmoon.familytree.vo.FamilyNode;
import com.blogspot.rainasmoon.familytree.vo.Graph;
import com.blogspot.rainasmoon.familytree.vo.MarriageNode;
import com.blogspot.rainasmoon.familytree.vo.Node;
import com.blogspot.rainasmoon.familytree.vo.PeopleNode;

@Component("mapManagerImpl")
@Transactional
public class MapManagerImpl implements MapManager {

    @Autowired
    private ChildrenDao childrenDao;
    
    @Autowired
    private MarriageManager marriageManager;
   
    @Autowired
    private PeopleManager peopleManager;
    
    @Deprecated
    @Override
    public Node getNode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Deprecated
    @Override
    public void openNode() {
        // TODO Auto-generated method stub

    }

    @Deprecated
    @Override
    public void closeNode() {
        // TODO Auto-generated method stub

    }

    @Deprecated
    @Override
    public Graph getGraph() {
        // TODO Auto-generated method stub
        return null;
    }

    @Deprecated
    @Override
    public boolean isPersonModel() {
        // TODO Auto-generated method stub
        return false;
    }

    @Deprecated
    @Override
    public boolean isMarriageMode() {
        // TODO Auto-generated method stub
        return false;
    }

    @Deprecated
    @Override
    public boolean isFamilyModel() {
        // TODO Auto-generated method stub
        return false;
    }

    @Deprecated
    @Override
    public Node searchFamily(long peopleId, int level, String status) {
        // TODO Auto-generated method stub
        FamilyNode node = new FamilyNode();
        MarriageNode mn = new MarriageNode();
        PeopleNode pn = new PeopleNode();
        People p = new People();
        p.setName("You");
        p.setSex("Male");

        pn.setPeople(p);
        mn.setHusbandNode(pn);
        mn.setWifeNode(pn);
        node.setSpousNode(mn);

        return node;
    }
    
    public NodeUI createFamilyUI(String fartherName) {
	
		PersonUI fatherUI = new PersonUI(fartherName);
		People husband= peopleManager.getPeople(fartherName);
				
		People wife = marriageManager.getWife(husband);
		if (wife == null || wife.isEmpty()) {
			return fatherUI;
		}
		PersonUI montherUI = new PersonUI(wife);
		
		MarriageUI marriageUI = new MarriageUI(fatherUI, montherUI);
		Marriage m = marriageManager.getMarriage(husband);
		List<Children> c = childrenDao.findChildren(m);
		List<PersonUI> cui = new ArrayList<PersonUI>();
		
		if (c == null || c.isEmpty()) {
			return marriageUI;
		}
		
		for(Children child : c) {
			cui.add(new PersonUI(child.getPeople()));
		}
		
		ChildrenUI childrenUI = new ChildrenUI(cui);
		FamilyUI family = new FamilyUI(marriageUI, childrenUI);
		
		return family;
	}

	@Override
	public NodeUI createHisParentUI(String name) {	
		People person= peopleManager.getPeople(name);
		People hisFather = peopleManager.getFather(person);
		
		return createFamilyUI(hisFather.getName());
	}

}
