package de.frittenburger.aoc2020.day02;

import static de.frittenburger.meta.impl.HelpersLibrary.ReadLines;
import static de.frittenburger.meta.impl.HelpersLibrary.Match;
import static de.frittenburger.meta.impl.HelpersLibrary.ConvertToNumber;
import static de.frittenburger.meta.impl.HelpersLibrary.ConvertToCharacterList;
import static de.frittenburger.meta.impl.HelpersLibrary.PrintLine;

import java.io.IOException;
import java.util.List;


public class PasswordPhilosophy {

	

	private void calulatePuzzleOne(String filename) throws IOException {

		int i,j;
		List<String> lines;
		List<String> match;
		String line;
		List<String> passwort;
		String character;
		int count;
		long fromRange;
		long toRange;
		String c;
		long valid;
		
		valid = 0;
		lines = ReadLines(filename);
		for(i = 0;i < lines.size();i = i + 1)
		{
			line = lines.get(i);
			match = Match(line,"([0-9]+)[-]([0-9]+)\\s+([a-z]):\\s+([a-z]+)");
			
			fromRange = ConvertToNumber(match.get(0));
			toRange = ConvertToNumber(match.get(1));
			passwort = ConvertToCharacterList(match.get(3));
			character = match.get(2);
			count = 0;
			for(j = 0;j < passwort.size();j = j + 1)
			{
				c = passwort.get(j);
				if(c.equals(character))
					count = count + 1;
			}
			
			//Password policy
			if(fromRange <= count && count <= toRange)
				valid = valid + 1;
			
		}
		PrintLine(valid);
	}
	
	private void calulatePuzzleTwo(String filename) throws IOException {
		int i,j;
		List<String> lines;
		List<String> match;
		String line;
		List<String> passwort;
		String character;
		int count;
		long index1;
		long index2;
		String c1,c2;
		long valid;
		
		valid = 0;
		lines = ReadLines(filename);
		for(i = 0;i < lines.size();i = i + 1)
		{
			line = lines.get(i);
			match = Match(line,"([0-9]+)[-]([0-9]+)\\s+([a-z]):\\s+([a-z]+)");
			
			index1 = ConvertToNumber(match.get(0));
			index2 = ConvertToNumber(match.get(1));
			passwort = ConvertToCharacterList(match.get(3));
			character = match.get(2);
			
			count = 0;
			c1 = passwort.get((int) index1 - 1);
			c2 = passwort.get((int) index2 - 1);		
			
			if(c1.equals(character))
				count = count + 1;
			if(c2.equals(character))
				count = count + 1;
			//Password policy
			if(count == 1)
				valid = valid + 1;
			
		}
		PrintLine(valid);
		
	}

	
	
	public static void main(String[] args) throws IOException {
		PasswordPhilosophy passwordPhilosophy;
		
		
		passwordPhilosophy = new PasswordPhilosophy();
		passwordPhilosophy.calulatePuzzleOne("src/main/resources/aoc2020/day02/input.txt");
		passwordPhilosophy.calulatePuzzleTwo("src/main/resources/aoc2020/day02/input.txt");

	}

	

}
