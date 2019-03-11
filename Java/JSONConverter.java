/*
Main file for converting txt requirements into JSON format.
Written by Yehonatan (Jonathan) Shabash.

Input Assumptions:
	All requirements start on separate line in which:
		The only thing before the first period is a number
		The only thing before the first space is the ID of the requirement, which is a string of numbers and periods
		All subrequirements have an ID that starts with the ID of their parent requirement
		Subrequirements are listed directly after their parent requirement
	Subrequirements do not have their own subrequirements
*/

import java.util.ArrayList;

public class JSONConverter
{
	public static void main(String arg[])
	{
		//Get input
		IOManager io = new IOManager();
		io.instantiateReader();
		System.out.println("Reading in input...\n");
		ArrayList<String> input = io.read();
		io.closeReader();
		
		//Process input
		System.out.println("Converting to JSON...\n");
		String output = ConversionManager.textToJSON(input);
		
		//Write output
		String outFileName = io.instantiateWriter();
		System.out.println("Printing output to " + outFileName + "...\n");
		io.write(output);
		io.closeWriter();
		
		//Clean Up
		System.out.println("Conversion complete.\n");
	}
}