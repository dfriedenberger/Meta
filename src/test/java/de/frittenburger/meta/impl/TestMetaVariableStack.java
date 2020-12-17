package de.frittenburger.meta.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.frittenburger.meta.model.BaseType;
import de.frittenburger.meta.model.MetaModel;

public class TestMetaVariableStack {

	@Test
	public void testString() {
		
		MetaVariableStack stack = new MetaVariableStack();
		
		MetaModel type = new MetaModel();
		type.type = BaseType.TString;
		stack.createVariable("name", type);
		stack.setVariable("name", new MetaValue("hello"));
		
		assertTrue(stack.getValue("name").isString());
		assertEquals("hello", stack.getValue("name").getString());
	}
	
	@Test
	public void testNumber() {
		
		MetaVariableStack stack = new MetaVariableStack();
		MetaModel type = new MetaModel();
		type.type = BaseType.TNumber;
		stack.createVariable("name", type);
		stack.setVariable("name", new MetaValue(1));
		
		assertEquals(1, stack.getValue("name").getLong());
	}
	
	@Test
	public void testList() {
		
		MetaVariableStack stack = new MetaVariableStack();
		MetaModel type = new MetaModel();
		type.type = BaseType.TList;
		type.items = new MetaModel();
		type.items.type = BaseType.TString;

		stack.createVariable("name", type);
		stack.setVariable("name", new MetaValue(new ArrayList<MetaValue>()));
		
		assertTrue(stack.getValue("name").isList());
		assertEquals(0,stack.getValue("name").getList().size());
	}
	
	@Test
	public void testObject() {
		
		MetaVariableStack stack = new MetaVariableStack();
		MetaModel type = new MetaModel();
		
		MetaModel property = new MetaModel();
		property.type = BaseType.TString;
		property.name = "test";
		
		type.type = BaseType.TObject;
		type.properties = new ArrayList<MetaModel>();
		type.properties.add(property);
		

		stack.createVariable("name", type);
		stack.setVariable("name.test", new MetaValue("Test"));
		
		assertTrue(stack.getValue("name.test").isString());
		assertEquals("Test",stack.getValue("name.test").getString());
		
	}

	
	@Test
	public void testObjectInitialisation() {
		
		MetaVariableStack stack = new MetaVariableStack();
		MetaModel type = new MetaModel();
		
		MetaModel property = new MetaModel();
		property.type = BaseType.TList;
		property.name = "test";
		
		type.type = BaseType.TObject;
		type.properties = new ArrayList<MetaModel>();
		type.properties.add(property);
		

		stack.createVariable("name", type);
		stack.setVariable("name", new MetaValue("new"));
		
		assertTrue(stack.getValue("name.test").isList());
		assertEquals(0,stack.getValue("name.test").getList().size());
		
	}

}
