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
	private static final String ENDING = "\t\t]\n}";
	
	public static String textToJSON(ArrayList<String> input)
	{
		String output = "";
		output += BEGINNING;
		
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
					{
						//debug start
						//debug start
						/*
						System.out.println(currentReq.getId());
						System.out.println(currentReq.getDescription());
						System.out.println(currentReq.getDetails());						
						*/
						//debug end
						//debug end
						requirements.add(currentReq);
					}
					currentReq = new Requirement(line);
				}
				else if (currentReq != null)
				{
					currentReq.appendDetails(line);
				}
			}
		}
		requirements.add(currentReq);				//Add the last requirement to the list
		
		//debug start
		//debug start
		System.out.println(requirements.size());
		for (int i = 0; i < requirements.size(); i++)
		{
			System.out.println(requirements.get(i).getId());
		}
		//debug end
		//debug end
		
		//Second pass
		//Group requirements into main requirements and subrequirements
		ArrayList<Requirement> nestedReqs = nestRequirements(requirements);
		
		
		//Third pass
		//Convert to String
		
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
		ListIterator<Requirement> reqIterator = requirements.listIterator();
		while (reqIterator.hasNext())
		{
			Requirement newReq = reqIterator.next();
		}
		return nestedReqs;
	}
	
	
}