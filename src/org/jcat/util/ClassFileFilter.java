package org.jcat.util;

import java.io.File;
import java.io.FileFilter;


public class ClassFileFilter implements FileFilter {

	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		if (f!=null&&f.isFile()&&f.getName().endsWith(".class"))
			return true;
		else
			return false;
	}




}
