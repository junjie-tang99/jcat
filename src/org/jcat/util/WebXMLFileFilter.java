package org.jcat.util;

import java.io.File;
import java.io.FileFilter;

public class WebXMLFileFilter implements FileFilter  {

	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		if (f!=null&&f.isFile()&&f.getName().equals("web.xml"))
			return true;
		else
			return false;
	}

}
