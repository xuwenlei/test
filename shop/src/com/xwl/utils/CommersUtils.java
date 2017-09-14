package com.xwl.utils;

import java.util.UUID;

public class CommersUtils {
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
