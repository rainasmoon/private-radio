package com.rainasmoon.privateradio.utils.file;

import java.io.File;

public class FileData {
	public static final int TYPE_FILE = 0;
	public static final int TYPE_FOLDER = 1;

	private String name = "text";
	private int type;

	public FileData(File f) {
		name = f.getName();
		type = f.isFile() ? TYPE_FILE : TYPE_FOLDER;
	}

	public FileData(String name, int type) {
		
		this.name = name;
		this.type = type;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAFolder() {
		return type == TYPE_FOLDER;
	}

	public boolean isAFile() {
		return type == TYPE_FILE;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}