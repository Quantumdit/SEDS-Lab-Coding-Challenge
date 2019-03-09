/*
This class is a single requirement, as read from the input
Written by Yehonatan (Jonathan) Shabash.
*/

import java.util.ArrayList;

public class Requirement
{
	//Variables
	private String id;
	private String description;
	private String details = "";
	private ArrayList<Requirement> subreqs = new ArrayList<Requirement>();
	
	//Getters
	public String getId() {return id;}
	public String getDescription() {return description;}
	public String getDetails() {return details;}
	public ArrayList<Requirement> getSubreqs() {return subreqs;}
	
	//Functions
	
	public Requirement(String reqLine)
	{
		//Split the string into two parts, one before the first space (id) and one after the first space (description)
		String[] splitLine = reqLine.split(" ",2);
		id = splitLine[0];
		description = splitLine[1];
	}
	
	public void appendDetails(String line)
	{
		//If the description already has stuff, add a space before adding more stuff
		if (!details.equals(""))
			details += " ";
		details += line;
	}
	
	public void addSubRequirement(Requirement req)
	{
		subreqs.add(req);
	}
}