package de.frittenburger.meta.impl;

import java.util.List;

public class MetaValue {

	private Long longValue = null;
	private Boolean booleanValue = null;
	private String stringValue = null;
	private List<MetaValue> list;


	public MetaValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public MetaValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public MetaValue(List<MetaValue> list) {
		this.list = list;
	}

	
	public MetaValue(Long longValue) {
		this.longValue = longValue;
	}
	
	public MetaValue(int intValue) {
		this.longValue = (long) intValue;
	}

	public long getLong() {
		return longValue;
	}
	
	public int toInteger() {
		return longValue.intValue();
	}
	
	public boolean getBoolean()
	{
		return booleanValue;
	}

	public String getString() {
		return stringValue;
	}



	public List<MetaValue> getList() {
		return list;
	}

	@Override
	public String toString() {
		
		if(longValue != null) return ""+longValue;
		if(booleanValue != null) return ""+booleanValue;
		if(list != null) return ""+list;
		if(stringValue != null) return stringValue;

		return "null";
	}

	public boolean isString() {
		return stringValue != null;
	}

	public boolean isList() {
		return list != null;
	}
	
	
}
