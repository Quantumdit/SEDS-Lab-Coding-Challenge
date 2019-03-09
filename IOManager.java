/*
This class takes in input from a file.
Written by Yehonatan (Jonathan) Shabash.
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Integer;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IOManager
{	
	//Variables
	private BufferedReader reader;
	private BufferedWriter writer;
	
	//Getters
	public BufferedReader getReader() {return reader;}
	public BufferedWriter getWriter() {return writer;}
	
	//Functions
	
	public void InstantiateReader()
	{
		printInstructions();
		Scanner scan = new Scanner(System.in);
		boolean fileFound = false;
		do
		{
			try
			{
				String filename = scan.nextLine();
				if (filename.equalsIgnoreCase("quit") || filename.equalsIgnoreCase("q"))
					System.exit(0);
				else if(filename.equals(""))
					filename = "demo.txt";
				reader = new BufferedReader(new FileReader(filename));
				fileFound = true;
			}
			catch (FileNotFoundException e)
			{
				System.out.println("Could not find the specified file, please try again.");
			}
			
		} while (!fileFound);
	}
	
	private void printInstructions()
	{
		System.out.println("Please enter the name of the input text file (including the file extension).");
		System.out.println("If the file is in a different directory, include the path to the file.");
		System.out.println("If no text is entered, the program will try to execute on demo.txt in this directory.");
		System.out.println("Enter \"Quit\" to exit.");
	}
	
	public ArrayList<String> read()
	{
		ArrayList<String> lines = new ArrayList<String>();
		try
		{
			String line = reader.readLine();
			while (line != null)
			{
				lines.add(line);
				line = reader.readLine();
			}			
		}
		catch (IOException e)
		{
			System.out.println("Failed to read file.");
			e.printStackTrace();
		}
		return lines;
	}
	
	public void InstantiateWriter()
	{
		File outFile;
		int counter = 0;
		boolean fileAlreadyExists = true;
		do
		{
			outFile = new File("output" + Integer.toString(counter) + ".txt");
			fileAlreadyExists = outFile.exists();
			counter++;
		} while(fileAlreadyExists);
		System.out.println("Printing output to " + outFile.getName() + "...");
	
		try
		{			
			writer = new BufferedWriter(new FileWriter(outFile));
		}
		catch (IOException e)
		{
			System.out.println("Failed to create output file.");
			e.printStackTrace();
		}
	}
	
	public void write(String output)
	{
		try
		{
			writer.write(output);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void closeReaders()
	{
		try
		{
			reader.close();
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}