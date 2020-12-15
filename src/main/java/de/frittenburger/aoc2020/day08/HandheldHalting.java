package de.frittenburger.aoc2020.day08;

import static de.frittenburger.meta.impl.HelpersLibrary.ReadLines;
import static de.frittenburger.meta.impl.HelpersLibrary.Match;
import static de.frittenburger.meta.impl.HelpersLibrary.ConvertToNumber;
import static de.frittenburger.meta.impl.HelpersLibrary.ConvertToCharacterList;
import static de.frittenburger.meta.impl.HelpersLibrary.PrintLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class HandheldHalting {

	class Command {
		private String instruction;
		private int argument;
	}
	
	class Result {
		private boolean terminate;
		private int acc;		
	}

	public List<Command> readProgram(String filename) throws IOException {
		
		int i;
		List<Command> program;
		List<String> lines;
		List<String> match;
		String line;
		Command command;
		int argument;
		
		program = new ArrayList<>();
		lines = ReadLines(filename);
		for(i = 0;i < lines.size();i = i + 1)
		{
			line = lines.get(i);
			match = Match(line,"([a-z]+)\\s+([+-])([0-9]+)");
			
			
			argument = (int) ConvertToNumber(match.get(2));
			if(match.get(1).equals("-"))
				argument = argument * -1;
			command = new Command();
			command.instruction = match.get(0);
			command.argument = argument;
			program.add(command);
		}
		return program;
	}
	
	
	private Result calulatePuzzleOne(List<Command> program) throws IOException {
		int ix;
		int acc;
		Command cmd;
		Set<Integer> cache;
		Result result;
		
		
		ix = 0;
		acc = 0;
		cache = new HashSet<>();
		
		
		while(true)
		{

			if(cache.contains(ix))
			{
				result = new Result();
				result.terminate = false;
				result.acc = acc;
				return result;
			}
			cache.add(ix);
			

			cmd = program.get(ix);
			

			if(cmd.instruction.equals("acc"))
			{
				acc += cmd.argument;
				ix = ix + 1;
			}
			if(cmd.instruction.equals("nop"))
			{
				ix = ix + 1;
			}
			if(cmd.instruction.equals("jmp"))
			{
				ix = ix + cmd.argument;
			}
		}
	
	}
	
	
	
	
	public static void main(String[] args) throws IOException {
		HandheldHalting handheldHalting;
		List<Command> program;
		Result result;
		
		handheldHalting = new HandheldHalting();
		program = handheldHalting.readProgram("src/main/resources/aoc2020/day08/input.txt");
		result = handheldHalting.calulatePuzzleOne(program);
		
		PrintLine(result.acc);

	}

	

}
