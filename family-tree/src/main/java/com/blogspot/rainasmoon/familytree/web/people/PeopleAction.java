package com.blogspot.rainasmoon.familytree.web.people;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.blogspot.rainasmoon.familytree.entity.people.People;
import com.blogspot.rainasmoon.familytree.service.ServiceException;
import com.blogspot.rainasmoon.familytree.service.people.MarriageManager;
import com.blogspot.rainasmoon.familytree.service.people.PeopleManager;
import com.blogspot.rainasmoon.familytree.web.CrudActionSupport;

@Namespace("/people")
@Results({
		@Result(name = CrudActionSupport.RELOAD, location = "people.action", type = "redirect"),
		@Result(name = "search", location = "people-list.jsp", type = "freemarker") })

public class PeopleAction extends CrudActionSupport<People> {

	private static final long serialVersionUID = -5328257647799422676L;

	private Long peopleId;
	private Long husbandId;
	private Long wifeId;

	private People entity;
	private People husband;
	private People wife;

	private List<People> peopleList;

	@Autowired
	private PeopleManager peopleManager;

	@Autowired
	private MarriageManager marriageManager;

	@Override
	public People getModel() {

		return entity;
	}

	@Override
	public String list() throws Exception {
		logger.debug("PeopleAction list is running...");

		List<PropertyFilter> filters = PropertyFilter
				.buildFromHttpRequest(Struts2Utils.getRequest());

		husband = (People) Struts2Utils.getSessionAttribute("husband");
		wife = (People) Struts2Utils.getSessionAttribute("wife");


		logger.debug("filters is: " + filters);
		peopleList = peopleManager.search(filters);
		
		return SUCCESS;
	}

	public String love() throws Exception {

        logger.debug("love id is:" + peopleId);

        Map<String, Object> map = new HashMap<String, Object>();

		if (peopleId != null) {
			People p = peopleManager.getPeople(peopleId);
			if ("Male".equalsIgnoreCase(p.getSex())) {
				Struts2Utils.getSession().setAttribute("husband", p);
			} else {
				Struts2Utils.getSession().setAttribute("wife", p);
			}

            map.put("people", p);
            Struts2Utils.renderJson(map);
        }

        return null;
	}

	public String marry() throws Exception {

		husband = (People) Struts2Utils.getSessionAttribute("husband");
		wife = (People) Struts2Utils.getSessionAttribute("wife");

        Map<String, Object> map = new HashMap<String, Object>();

		if (husband == null) {
            map.put("error", "pls select a husband.");
            Struts2Utils.renderJson(map);

            return null;
		}

		if (wife == null) {
            map.put("error", "pls select a wife.");
            Struts2Utils.renderJson(map);

            return null;
		}

		if (marriageManager.isMarried(husband, wife)) {
            map.put("error", "They have aready marriaged for a long time.");
            Struts2Utils.renderJson(map);

            return null;
		}


		try {
			marriageManager.marry(husband, wife);
			Struts2Utils.getSession().removeAttribute("husband");
			Struts2Utils.getSession().removeAttribute("wife");
            map.put("result", "success");
            map.put("message", "marriage success.");
		} catch (ServiceException se) {
			logger.error(se.getMessage(), se);
            map.put("result", "fails");
            map.put("message", "marriage fails.");
		}

        Struts2Utils.renderJson(map);

        return null;
	}

	public String search() throws Exception {
		logger.debug("search run...");

        Map<String, Object> map = new HashMap<String, Object>();

        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(Struts2Utils.getRequest());

        husband = (People) Struts2Utils.getSessionAttribute("husband");
        wife = (People) Struts2Utils.getSessionAttribute("wife");

        logger.debug("filters is: " + filters);
        peopleList = peopleManager.search(filters);

        map.put("peopleList", peopleList);

        Struts2Utils.renderJson(map);
        return null;
	}

    public String ajaxsearch() throws Exception {
        logger.debug("ajax search run...");
        String callbackName = Struts2Utils.getParameter("callback");

        Map<String, String> map = Collections.singletonMap("html", "<p>Hello World!</p>");

        Struts2Utils.renderJsonp(callbackName, map);
        return null;
    }

	@Override
	public String input() throws Exception {

		return INPUT;
	}

	@Override
	public String save() throws Exception {
        logger.debug("save people :" + entity);

		peopleManager.savePeople(entity);

        Struts2Utils.renderText("save people success.");

        return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		logger.debug("prepare Model run...");
		entity = new People();

	}

	public List<People> getPeopleList() {
		return peopleList;
	}

	public void setPeopleList(List<People> peopleList) {
		this.peopleList = peopleList;
	}

	public People getHusband() {
		return husband;
	}

	public People getWife() {
		return wife;
	}

	public void setPeopleId(Long peopleId) {
		this.peopleId = peopleId;
	}

	public void setHusbandId(Long husbandId) {
		this.husbandId = husbandId;
	}

	public void setWifeId(Long wifeId) {
		this.wifeId = wifeId;
	}

}
