package de.frittenburger.meta.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class HelpersLibrary {

	
	static public List<String> ReadLines(String filename) throws IOException
	{
		return Files.readAllLines(new File(filename).toPath());
	}
	
	static public List<Long> ConvertToNumberList(List<String> lines) {
		return lines.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
	}
	
	static public long ConvertToNumber(String s) {
		return Long.parseLong(s);
	}
	
	static public List<String> ConvertToCharacterList(String input)
	{
		List<String> characters = new ArrayList<>();
		for(char c : input.toCharArray())
			characters.add(c+"");
		return characters;
	}
	
	static public void PrintLine(Object obj)
	{
		System.out.println(obj);
	}
	
	static public List<String> Match(String value,String pattern)
	{
		List<String> result = new ArrayList<>();
		Pattern p = Pattern.compile(pattern);    
        Matcher m = p.matcher(value);    
        if (m.find()) 
        {
        	for(int i = 0;i < m.groupCount();i++)
        		result.add(m.group(i+1));
        }    
		return result;
	}
}
