# Main file for converting txt requirements into JSON format.
# Written by Yehonatan (Jonathan) Shabash.


#This class is a single requirement. It can be either a main requirement or a subrequirement. A subrequirement should not have any subrequirements of its own.
class Requirement:
    
    #Input: A string "reqLine" that corresponds the beginning of a new requirement 
    def __init__ (self, reqLine):
        #Split the string into two parts, one before the first space (id) and one after the first space (description)
        splitLine = reqLine.split(maxsplit=1)
        self.id = splitLine[0]
        self.description = splitLine[1]
        self.details = None
        self.subreqs = list()

        #Adds a String "line" to the details of this requirement.
    def appendDetails(self, line):
        if (self.details is None):
            self.details = line
        #If the description already has stuff, add a space before adding more stuff
        self.details += " " + line

    #Adds a requirement "req" to the list of subrequirements
    def addSubreq(self, req):
        self.subreqs.append(req)

#This class is responsible for converting the text into JSON.
class _ConversionManager:

    #Converts an ArrayList of Strings containing the input into JSON string
    #Input: A list of strings representing the input delimited by newlines
    #Output: A String which is the JSON output
    def textToJSON(input):
        #First pass
        #Generate a list requirements and subrequirements
        requirements = _ConversionManager._generateRequirements(input)
        
        #debug start
        print("First Pass:")
        print(len(requirements))
        for req in requirements:
            print(req.id)
        print()
        #debug end
        
        #Second pass
        #Put subrequirements into main requirements
        nestedReqs = _ConversionManager._nestRequirements(requirements)
        
        #debug start
        print("Second Pass:")
        print(len(nestedReqs))
        for req in nestedReqs:
            print(req.id)
            for sub in req.subreqs:
                print("Sub: " + sub.id)
        print()
        #debug end
        
        #Third pass
        #Convert to JSON String
        outputJSON = _ConversionManager._generateJSON(nestedReqs)
        
        return outputJSON
    
    # #Helper functions
    
    #Converts the list of strings of the input into a list of requirements. Does not differentiate main requirements from subrequirements.
    #Input: A list of strings representing the input delimited by newlines
    #Output: A list of Requirements
    def _generateRequirements(input):
        requirements = list();
        currentReq = None;
        for line in input:
            if (_ConversionManager._isRequirement(line)):
                if (currentReq is not None):
                    requirements.append(currentReq)
                currentReq = Requirement(line)
            elif (currentReq is not None):
                currentReq.appendDetails(line)
        #Add the last requirement to the list
        requirements.append(currentReq)
        
        return requirements
    
    #Determines if line is the beginning of a new requirement
    #Input: String
    #Output: Boolean
    def _isRequirement(line):
        periodIndex = line.find(".")
        #If the string doesn't have a period, return false
        if (periodIndex == -1):
            return False
        #Now we check if everything before the first period is a number, which we consider sufficient to identify it as a new requirement
        subline = line[0:periodIndex]  #TODO check this works
        if (subline.isdigit()):
            return True
        else:
            return False
    
    #Turns a list of requirements and subrequirements into a list of requirements with subrequirements inside them
    #Input: List of type Requirement
    #Output: List of type Requirement
    def _nestRequirements(requirements):
        nestedReqs = list()
        currentReq = None
        for newReq in requirements:
            if (currentReq is None):
                currentReq = newReq
            #Check if the new requirement's ID has the current requirement's ID as a prefix
            elif (newReq.id.startswith(currentReq.id)):
                currentReq.addSubreq(newReq)
            else:
                nestedReqs.append(currentReq)
                currentReq = newReq
        #Add the last requirement to the list
        nestedReqs.append(currentReq)
        
        return nestedReqs
    
    #Turns a list of nested requirements into JSON
    #Input: List of type Requirement
    #Output: String
    def _generateJSON(nestedReqs):
        #The beginning and end of the JSON file is always the same
        BEGINNING = "{\n\t\"RQs\": [\n"
        ENDING = "\t]\n}"
        
        outputJSON = BEGINNING
        
        currentReq = None;
        for i in range(0,len(nestedReqs)):
            currentReq = nestedReqs[i]
            outputJSON += "\t\t{\n"
            outputJSON += "\t\t\t\"RQ" + str(i+1) + "\": \"" + currentReq.id + "\",\n"
            outputJSON += "\t\t\t\"Desc\" : \"" + currentReq.description + "\",\n"
            outputJSON += "\t\t\t\"Details\":\"" + currentReq.details + "\",\n"
            outputJSON += "\t\t\t\"SubRQs\":[" + _ConversionManager._subreqToJSON(currentReq.subreqs) + "]\n"
            outputJSON += "\t\t}"
            #Add a comma, but only if this isn't the last requirement
            if (i != len(nestedReqs)-1):
                outputJSON += ","
            outputJSON += "\n"
        
        outputJSON += ENDING
        
        return outputJSON
    
    #Turns a list of subrequirements into JSON.
    #Input: List of type Requirement
    #Output: String
    def _subreqToJSON(subreq):
        if (len(subreq) == 0):
            return ""
    
        indent = "\t\t\t"
        outputJSON = "\n"
        currentReq = None;
        for i in range(0,len(subreq)):
            currentReq = subreq[i]
            outputJSON += indent + "\t\t{\n"
            outputJSON += indent + "\t\t\t\"sub" + str(i+1) + "\": \"" + currentReq.id + "\",\n"
            outputJSON += indent + "\t\t\t\"desc\" : \"" + currentReq.description + "\",\n"
            outputJSON += indent + "\t\t\t\"details\":\"" + currentReq.details + "\",\n"
            outputJSON += indent + "\t\t}"
            #Add a comma, but only if this isn't the last requirement
            if (i != len(subreq)-1):
                outputJSON += ","
            outputJSON += "\n"

        return outputJSON


#This class takes in input from a file and writes output to a file.
class IOManager:
    
    # Functions
    
    # Print instructions for the user
    def printInstructions():
        print("\nPlease enter the name of the input text file (including the file extension).");
        print("If the file is in a different directory, include the path to the file.");
        print("If no text is entered, the program will try to execute on demo.txt in this directory.");
        print("Enter \"Quit\" to exit.\n");
        print("Filename: ", end="");
    
    # Read the file and return a list of strings, delimited by newlines
    def readInput():
        IOManager.printInstructions()
        fileFound = False
        while (not(fileFound)):
            try:
                filename = input()
                print("")
                if (filename.lower() == "quit" or filename.lower == "q"):
                    sys.exit()
                elif (filename == ""):
                    filename = "demo.txt"
                with open(filename, 'r', encoding = "cp1252") as f:         #TODO consider changing encoding or removing encoding, just need it to work on my machine
                    dataString = f.read()
                    dataList = dataString.rsplit("\n")
                fileFound = True
            except (IOError):
                print("Could not find the specified file, please try again.\n")
                print("Filename: ", end="")
        return dataList
        
    # #Returns name of the file being written to
    # public String instantiateWriter()
    # {
        # File outFile;
        # int counter = 0;
        # boolean fileAlreadyExists = true;
        # do
        # {
            # outFile = new File("output" + Integer.toString(counter) + ".txt");
            # fileAlreadyExists = outFile.exists();
            # counter++;
        # } while(fileAlreadyExists);
    
        # try
        # {            
            # writer = new BufferedWriter(new FileWriter(outFile));
        # }
        # catch (IOException e)
        # {
            # print("Failed to create output file.\n");
            # e.printStackTrace();
        # }
        
        # return outFile.getName();
    # }
    
    # #instantiateWriter must be called first
    # public void write(String output)
    # {
        # try
        # {
            # writer.write(output);
        # }
        # catch (IOException e)
        # {
            # e.printStackTrace();
        # }
    # }
    
    # public void closeWriter()
    # {
        # try
        # {
            # writer.close();
        # }
        # catch (IOException e)
        # {
            # print("Failed to close file writer.\n");
            # e.printStackTrace();
        # }
    # }
        
def main():
    #Get input
    input = IOManager.readInput();
    print("Reading in input...\n")
    
    #Process input
    print("Converting to JSON...\n")
    output = _ConversionManager.textToJSON(input);
    
    #Write output
#        String outFileName = io.instantiateWriter();
#    print("Printing output to " + outFileName + "...\n");
#        io.write(output);
#        io.closeWriter();
    
    #Clean Up
    print("Conversion complete.\n")
        
        
if __name__ == "__main__":
    main()