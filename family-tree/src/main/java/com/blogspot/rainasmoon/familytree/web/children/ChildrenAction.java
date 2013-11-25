package com.blogspot.rainasmoon.familytree.web.children;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.blogspot.rainasmoon.familytree.entity.people.Children;
import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.entity.people.People;
import com.blogspot.rainasmoon.familytree.service.people.ChildrenManager;
import com.blogspot.rainasmoon.familytree.service.people.MarriageManager;
import com.blogspot.rainasmoon.familytree.service.people.PeopleManager;
import com.blogspot.rainasmoon.familytree.web.CrudActionSupport;

@Namespace("/children")
@Results({ @Result(name = CrudActionSupport.RELOAD, location = "children.action", type = "redirect") })
public class ChildrenAction extends CrudActionSupport<Marriage> {

	private static final long serialVersionUID = -260921426257066549L;

	private Long marriageId;
	private Long peopleId;

	private Long childrenId;

	private Marriage marriage;

	private List<People> peopleList;

	private List<Children> childrenList;

	@Autowired
	private PeopleManager peopleManager;

	@Autowired
	private MarriageManager marriageManager;

	@Autowired
	private ChildrenManager childrenManager;

	@Override
	public Marriage getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {

		List<PropertyFilter> filters = PropertyFilter
				.buildFromHttpRequest(Struts2Utils.getRequest());

		logger.debug("filters: " + filters);

		// TODO: SB code
		if (marriageId != null) {
			marriage = marriageManager.getMarriage(marriageId);
			Struts2Utils.getSession().setAttribute("marriage", marriage);
		}

		if (marriage == null) {

			marriage = (Marriage) Struts2Utils.getSessionAttribute("marriage");
		}
		logger.debug("in list the marriageId is :" + marriageId);

		childrenList = childrenManager.findChildren(marriage);


		peopleList = peopleManager.search(filters);
		return SUCCESS;
	}

    public String search() throws Exception {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());

        logger.debug("filters: " + filters);

        peopleList = peopleManager.search(filters);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("peopleList", peopleList);

        Struts2Utils.renderJson(map);

        return null;
    }

    public String children() throws Exception {
        if (marriage == null) {

            marriage = (Marriage) Struts2Utils.getSessionAttribute("marriage");
        }
        logger.debug("in list the marriageId is :" + marriageId);

        childrenList = childrenManager.findChildren(marriage);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("children", childrenList);

        Struts2Utils.renderJson(map);
        return null;
    }

	public String child() throws Exception {

		if (marriage == null) {
			marriage = (Marriage) Struts2Utils.getSessionAttribute("marriage");
		}

		People child = peopleManager.getPeople(peopleId);

		if (marriage == null) {
			addActionMessage("marriage can't be null.");
			return RELOAD;
		}

		if (child == null) {
			addActionMessage("child can't be null.");
			return RELOAD;
		}

		if (childrenManager.hasThisChild(marriage, child)) {
			addActionMessage("this family have this child for a long time, but you confirm a truth.");
			return RELOAD;
		}

		childrenManager.createChild(marriage, child);
        return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		childrenManager.deleteChildren(childrenId);
        return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub

	}

	public Long getMarriageId() {
		return marriageId;
	}

	public void setMarriageId(Long marriageId) {
		this.marriageId = marriageId;
	}

	public Long getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(Long peopleId) {
		this.peopleId = peopleId;
	}

	public List<People> getPeopleList() {
		return peopleList;
	}

	public void setPeopleList(List<People> peopleList) {
		this.peopleList = peopleList;
	}

	public Long getChildrenId() {
		return childrenId;
	}

	public void setChildrenId(Long childrenId) {
		this.childrenId = childrenId;
	}

	public List<Children> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<Children> childrenList) {
		this.childrenList = childrenList;
	}

}
