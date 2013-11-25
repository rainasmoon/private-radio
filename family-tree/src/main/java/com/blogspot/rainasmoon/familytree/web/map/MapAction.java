package com.blogspot.rainasmoon.familytree.web.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.entity.people.People;
import com.blogspot.rainasmoon.familytree.service.map.MapManager;
import com.blogspot.rainasmoon.familytree.service.people.ChildrenManager;
import com.blogspot.rainasmoon.familytree.service.people.MarriageManager;
import com.blogspot.rainasmoon.familytree.service.people.PeopleManager;
import com.blogspot.rainasmoon.familytree.vo.Node;
import com.blogspot.rainasmoon.familytree.web.AbstractActionSupport;

@Namespace("/map")
public class MapAction extends AbstractActionSupport {

    private static final long serialVersionUID = -3888892813552418561L;
    
    private long peopleId;
    private People people;

    private People grandPa;
    private People grandMa;
    private People father;
    private People mother;
    private List<People> siblingList;
    private List<People> peopleList;

    private List<Marriage> marriageList;

    private String fatherName;
    private String motherName;
    private String siblingName;
    private String siblingSex;
    private String spouseName;

    private int level;
    private String status;

    @Autowired
    private PeopleManager peopleManager;

    @Autowired
    private MarriageManager marriageManager;

    @Autowired
    private ChildrenManager childrenManager;

    @Autowired
    @Qualifier("mapManagerImpl")
    private MapManager mapManager;

    public String list() {

        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());

        logger.debug("filters is: " + filters);
        peopleList = peopleManager.search(filters);

        return SUCCESS;
    }

    public String map() {
        people = peopleManager.getPeople(peopleId);
        grandPa = peopleManager.getGrandPa(people);
        grandMa = peopleManager.getGrandMa(people);
        father = peopleManager.getFather(people);
        mother = peopleManager.getMother(people);

        siblingList = peopleManager.getSibling(people);

        marriageList = peopleManager.getMarriageList(people);
        return list();
    }

    public String mapGraph() {

        logger.debug("the mapManager: " + mapManager);

        Node node = mapManager.searchFamily(peopleId, level, status);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("node", node);
        Struts2Utils.renderJson(map);
        return null;
    }

    public String saveFamily() {
        logger.debug("saveFamily run...");
        logger.debug("Father:" + fatherName);
        logger.debug("Mother:" + motherName);

        People father = new People();
        father.setName(fatherName);
        father.setSex(People.SEX_MALE);
        People mother = new People();
        mother.setName(motherName);
        mother.setSex(People.SEX_FEMALE);

        peopleManager.savePeople(father);
        peopleManager.savePeople(mother);
        Marriage marriage = marriageManager.marry(father, mother);

        if (peopleId > 0) {
            logger.info("save son to family.");
            people = peopleManager.getPeople(peopleId);
            childrenManager.createChild(marriage, people);
        }
        Struts2Utils.renderText("save family success.");

        return null;

    }

    public People getGrandPa() {
        return grandPa;
    }

    public People getGrandMa() {
        return grandMa;
    }

    public People getFather() {
        return father;
    }

    public People getMother() {
        return mother;
    }

    public List<People> getSiblingList() {
        return siblingList;
    }

    public List<People> getPeopleList() {
        return peopleList;
    }

    public Long getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(Long peopleId) {
        this.peopleId = peopleId;
    }

	public List<Marriage> getMarriageList() {
		return marriageList;
	}

    public People getPeople() {
        return people;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public void setSiblingName(String siblingName) {
        this.siblingName = siblingName;
    }

    public void setSiblingSex(String siblingSex) {
        this.siblingSex = siblingSex;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

}
