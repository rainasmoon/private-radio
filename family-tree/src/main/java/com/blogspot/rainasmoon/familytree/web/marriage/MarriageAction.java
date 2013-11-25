package com.blogspot.rainasmoon.familytree.web.marriage;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.blogspot.rainasmoon.familytree.entity.people.Marriage;
import com.blogspot.rainasmoon.familytree.service.people.MarriageManager;
import com.blogspot.rainasmoon.familytree.web.CrudActionSupport;

@Namespace("/marriage")
public class MarriageAction extends CrudActionSupport<Marriage>{

	private List<Marriage> marriageList;

	@Autowired
	private MarriageManager marriageManager;

	@Override
	public Marriage getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {

		List<PropertyFilter> filters = PropertyFilter
				.buildFromHttpRequest(Struts2Utils.getRequest());
		marriageList = marriageManager.search(filters);
		return SUCCESS;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public List<Marriage> getMarriageList() {
		return marriageList;
	}

	public void setMarriageList(List<Marriage> marriageList) {
		this.marriageList = marriageList;
	}

}
