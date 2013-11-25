package com.blogspot.rainasmoon.familytree.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public abstract class AbstractActionSupport extends ActionSupport {

    private static final long serialVersionUID = -5545841250171307531L;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String execute() throws Exception {
        return list();
    }

    public abstract String list() throws Exception;
   
}
