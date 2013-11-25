package com.blogspot.rainasmoon.babysitter.web.chat;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.blogspot.rainasmoon.babysitter.entity.account.User;
import com.blogspot.rainasmoon.babysitter.web.CrudActionSupport;

@Namespace("/chat")
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "/", type = "redirect") })
public class ChatAction  extends CrudActionSupport<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7032973214161404005L;

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
