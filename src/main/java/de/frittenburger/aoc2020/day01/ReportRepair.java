package de.frittenburger.aoc2020.day01;

import static de.frittenburger.meta.impl.HelpersLibrary.ConvertToNumberList;
import static de.frittenburger.meta.impl.HelpersLibrary.PrintLine;
import static de.frittenburger.meta.impl.HelpersLibrary.ReadLines;

import java.io.IOException;
import java.util.List;

public class ReportRepair {
	
	private void calulatePuzzleOne(String filename) throws IOException {
		
		List<String> lines;
		List<Long> numbers;
		int i;
		int j;
		
		lines = ReadLines(filename);

		numbers = ConvertToNumberList(lines);
		
	    for(i = 0;i < numbers.size();i = i + 1)
	    	for(j = 0;j < numbers.size();j = j + 1)
	    		if(numbers.get(i) + numbers.get(j) == 2020)
	    		{
	    			PrintLine(numbers.get(i) * numbers.get(j));
	    			return;
	    		}
		
		
	}
	
	private void calulatePuzzleTwo(String filename) throws IOException {
		
		List<String> lines;
		List<Long> numbers;
		int i;
		int j;
		int k;
		
		lines = ReadLines(filename);
		numbers = ConvertToNumberList(lines);
		
	    for(i = 0;i < numbers.size();i = i + 1)
	    	for(j = 0;j < numbers.size();j = j + 1)
		    	for(k = 0;k < numbers.size();k = k + 1)
		    		if(numbers.get(i) + numbers.get(j) + numbers.get(k) == 2020)
		    		{
		    			PrintLine(numbers.get(i) * numbers.get(j) * numbers.get(k));
		    			return;
		    		}
				
	}
	
	public static void main(String[] args) throws IOException {
		ReportRepair reportRepair;
		
		reportRepair = new ReportRepair();
		reportRepair.calulatePuzzleOne("src/main/resources/aoc2020/day01/input.txt");
		reportRepair.calulatePuzzleTwo("src/main/resources/aoc2020/day01/input.txt");

	}

	

	
}
