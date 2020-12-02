package de.frittenburger.meta.impl;

import java.util.List;

public class MetaValue {

	private Long longValue = null;
	private Boolean booleanValue = null;
	private String stringValue = null;
	private List<? extends Object> stringList;

	public MetaValue(Object object) {
		if(object instanceof Long)
			this.longValue = Long.class.cast(object);
		else if(object instanceof String)
			this.stringValue = String.class.cast(object);
		else
			throw new IllegalArgumentException("Unknown class "+object.getClass().getSimpleName());
	}

	public MetaValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public MetaValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public MetaValue(List<? extends Object> stringList) {
		this.stringList = stringList;
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

	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Class<T> class1) {
		return (List<T>) stringList;
	}

	public List<? extends Object> getList() {
		return stringList;
	}

	@Override
	public String toString() {
		
		if(longValue != null) return ""+longValue;
		if(booleanValue != null) return ""+booleanValue;
		if(stringList != null) return ""+stringList;
		if(stringValue != null) return stringValue;

		return "null";
	}

	public boolean isString() {
		return stringValue != null;
	}
	
	
}
