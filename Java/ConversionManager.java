/*
This class is responsible for converting the text into JSON.
Written by Yehonatan (Jonathan) Shabash.
*/

import java.util.ArrayList;
import java.util.ListIterator;
import java.lang.Integer;
import java.lang.NumberFormatException;

public class ConversionManager
{
	//The only public method of the class
	//Converts an ArrayList of Strings containing the input into JSON string
	public static String textToJSON(ArrayList<String> input)
	{	
		//First pass
		//Generate a list requirements and subrequirements
		ArrayList<Requirement> requirements = generateRequirements(input);
		
		//Second pass
		//Put subrequirements into main requirements
		ArrayList<Requirement> nestedReqs = nestRequirements(requirements);
		
		//Third pass
		//Convert to JSON String
		String outputJSON = generateJSON(nestedReqs);
		
		return outputJSON;
	}
	
	//Helper functions
	
	private static ArrayList<Requirement> generateRequirements(ArrayList<String> input)
	{
		ArrayList<Requirement> requirements = new ArrayList<Requirement>();
		ListIterator<String> inputIterator = input.listIterator();
		Requirement currentReq = null;
		while (inputIterator.hasNext())
		{
			String line = inputIterator.next();
			{
				if (isRequirement(line))
				{
					if (currentReq != null)
						requirements.add(currentReq);
					currentReq = new Requirement(line);
				}
				else if (currentReq != null)
					currentReq.appendDetails(line);
			}
		}
		//Add the last requirement to the list
		requirements.add(currentReq);
		
		return requirements;
	}
	
	private static boolean isRequirement(String line)
	{
		int periodIndex = line.indexOf(".");
		//If the string doesn't have a period, return false
		if (periodIndex == -1)
			return false;
		//Now we check if everything before the first period is a number, which we consider sufficient to identify it as a new requirement
		String subline = line.substring(0, periodIndex);
		try
		{
			Integer.parseInt(subline);
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}
	
	private static ArrayList<Requirement> nestRequirements(ArrayList<Requirement> requirements)
	{
		ArrayList<Requirement> nestedReqs = new ArrayList<Requirement>();
		Requirement currentReq = requirements.get(0);
		ListIterator<Requirement> reqIterator = requirements.listIterator(1);
		while (reqIterator.hasNext())
		{
			Requirement newReq = reqIterator.next();
			//Check if the new requirement's ID has the current requirement's ID as a prefix
			if (newReq.getId().startsWith(currentReq.getId()))
				currentReq.addSubreq(newReq);
			else
			{
				nestedReqs.add(currentReq);
				currentReq = newReq;
			}
		}
		//Add the last requirement to the list
		nestedReqs.add(currentReq);	
		
		return nestedReqs;
	}
	
	private static String generateJSON(ArrayList<Requirement> nestedReqs)
	{
		//The beginning and end of the JSON file is always the same
		final String BEGINNING = "{\n\t\"RQs\": [\n";
		final String ENDING = "\t]\n}";
		
		String outputJSON = "";
		outputJSON += BEGINNING;
		
		Requirement currentReq = null;
		for (int i = 0;i < nestedReqs.size();i++)
		{
			currentReq = nestedReqs.get(i);
			outputJSON += "\t\t{\n";
			outputJSON += "\t\t\t\"RQ" + (i+1) + "\": \"" + currentReq.getId() + "\",\n";
			outputJSON += "\t\t\t\"Desc\" : \"" + currentReq.getDescription() + "\",\n";
			outputJSON += "\t\t\t\"Details\":\"" + currentReq.getDetails() + "\",\n";
			outputJSON += "\t\t\t\"SubRQs\":[" + subreqToJSON(currentReq.getSubreqs()) + "]\n";
			outputJSON += "\t\t}";
			//Add a comma, but only if this isn't the last requirement
			if (i != nestedReqs.size()-1)
				outputJSON += ",";
			outputJSON += "\n";
		}
		
		outputJSON += ENDING;
		
		return outputJSON;
	}
	
	private static String subreqToJSON(ArrayList<Requirement> subreq)
	{
		if (subreq.size() == 0)
			return "";
	
		String indent = "\t\t\t";
		String outputJSON = "\n";
		Requirement currentReq = null;
		for (int i = 0;i < subreq.size();i++)
		{
			currentReq = subreq.get(i);
			outputJSON += indent + "\t\t{\n";
			outputJSON += indent + "\t\t\t\"sub" + (i+1) + "\": \"" + currentReq.getId() + "\",\n";
			outputJSON += indent + "\t\t\t\"desc\" : \"" + currentReq.getDescription() + "\",\n";
			outputJSON += indent + "\t\t\t\"details\":\"" + currentReq.getDetails() + "\"\n";
			outputJSON += indent + "\t\t}";
			//Add a comma, but only if this isn't the last requirement
			if (i != subreq.size()-1)
				outputJSON += ",";
			outputJSON += "\n" + indent;
		}
		return outputJSON;
	}
	
}