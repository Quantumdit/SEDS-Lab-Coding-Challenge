/*
This class is responsible for converting the text into JSON
Written by Yehonatan (Jonathan) Shabash.
*/

import java.util.ArrayList;
import java.util.ListIterator;
import java.lang.Integer;
import java.lang.NumberFormatException;

public class ConversionManager
{
	//The beginning and end of the JSON file is always the same
	private static final String BEGINNING = "{\n\t\"RQs\": [\n";
	private static final String ENDING = "\t]\n}";
	
	public static String textToJSON(ArrayList<String> input)
	{	
		//First pass
		//Generate requirements
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
		requirements.add(currentReq);				//Add the last requirement to the list
		
		//Second pass
		//Group requirements into main requirements and subrequirements
		ArrayList<Requirement> nestedReqs = nestRequirements(requirements);
		
		//Third pass
		//Convert to String
		String output = "";
		output += BEGINNING;
		currentReq = null;
		for (int i = 0;i < nestedReqs.size();i++)
		{
			currentReq = nestedReqs.get(i);
			output += "\t\t{\n";
			output += "\t\t\t\"RQ" + (i+1) + "\": \"" + currentReq.getId() + "\",\n";
			output += "\t\t\t\"Desc\" : \"" + currentReq.getDescription() + "\",\n";
			output += "\t\t\t\"Details\":\"" + currentReq.getDetails() + "\",\n";
			output += "\t\t\t\"SubRQs\":[" + subreqToJSON(currentReq.getSubreqs()) + "]\n";
			output += "\t\t}";
			//Add a comma, but only if this isn't the last requirement
			if (i != nestedReqs.size()-1)
				output += ",";
			output += "\n";
		}
		
		output += ENDING;
		return output;
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
		nestedReqs.add(currentReq);			//Add the last requirement to the list
		
		//TODO recurse on nestedReqs
		
		return nestedReqs;
	}
	
	private static String subreqToJSON(ArrayList<Requirement> subreq)
	{
		if (subreq.size() == 0)
			return "";
	
		String indent = "\t\t\t";
		String output = "\n";
		Requirement currentReq = null;
		for (int i = 0;i < subreq.size();i++)
		{
			currentReq = subreq.get(i);
			output += indent + "\t\t{\n";
			output += indent + "\t\t\t\"sub" + (i+1) + "\": \"" + currentReq.getId() + "\",\n";
			output += indent + "\t\t\t\"desc\" : \"" + currentReq.getDescription() + "\",\n";
			output += indent + "\t\t\t\"details\":\"" + currentReq.getDetails() + "\"\n";
			//TODO fix recursive subRQs
//			output += indent + "\t\t\t\"SubRQs\":[" + subreqToJSON(currentReq.getSubreqs()) + "]\n";
			output += indent + "\t\t}";
			//Add a comma, but only if this isn't the last requirement
			if (i != subreq.size()-1)
				output += ",";
			output += "\n" + indent;
		}
		return output;
	}
	
}