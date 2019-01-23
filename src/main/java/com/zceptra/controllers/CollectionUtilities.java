package com.zceptra.controllers;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtilities {
	
	public static <T> List<T> getListFrom(Iterable<T> iterable)	{
		
		List<T> list = new ArrayList<>();
		for(T e: iterable)	{
			list.add(e);
		}
		return list;
	}
}
