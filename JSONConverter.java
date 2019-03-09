/*
Main file for converting txt requirements into JSON format.
Written by Yehonatan (Jonathan) Shabash.

Assumptions:
	All requirements start on seperate line in which:
		the only thing before the first period is a number
		the only thing before the first space is the ID of the requirement, which is a string of numbers and periods
		all subrequirements have an ID that starts with the ID of their parent requirement
		subrequirements are listed directly after their parent requirement
	Subrequirements have a maximum depth of one
		(That is, there are no subrequirements of subrequirements)
*/

import java.util.ArrayList;

public class JSONConverter
{
	public static void main(String arg[])
	{
		//Get input
		IOManager io = new IOManager();
		io.InstantiateReader();
		System.out.println("\nReading in input...\n");
		ArrayList<String> input = io.read();
		
		//Process input
		System.out.println("Converting to JSON...\n");
		String output = ConversionManager.textToJSON(input);
		
		//Write output
		String outFileName = io.InstantiateWriter();
		System.out.println("Printing output to " + outFileName + "...\n");
		io.write(output);
		
		//Clean Up
		io.closeReaders();
		System.out.println("Conversion complete.\n");
	}
}