
#This class takes in input from a file and writes output to a file.
#Written by Yehonatan (Jonathan) Shabash.

class IOManager:
	
	#Functions
	def __init__(self):
        reader = None
        writer = None
    
	def instantiateReader():
		printInstructions()
		# Scanner scan = new Scanner(System.in);
		# boolean fileFound = false;
		# do
		# {
			# try
			# {
				# String filename = scan.nextLine();
				# print("");
				# if (filename.equalsIgnoreCase("quit") || filename.equalsIgnoreCase("q"))
				# {
					# System.exit(0);
				# }
				# else if(filename.equals(""))
					# filename = "demo.txt";
				# reader = new BufferedReader(new FileReader(filename));
				# fileFound = true;
			# }
			# catch (FileNotFoundException e)
			# {
				# print("Could not find the specified file, please try again.\n");
				# System.out.print("Filename: ");
			# }
			
		# } while (!fileFound);
	
	def printInstructions():
		print("\nPlease enter the name of the input text file (including the file extension).");
		print("If the file is in a different directory, include the path to the file.");
		print("If no text is entered, the program will try to execute on demo.txt in this directory.");
		print("Enter \"Quit\" to exit.\n");
		print("Filename: ", end="");
	
	#instantiateReader must be called first
	# public ArrayList<String> read()
	# {
		# ArrayList<String> lines = new ArrayList<String>();
		# try
		# {
			# String line = reader.readLine();
			# while (line != null)
			# {
				# lines.add(line);
				# line = reader.readLine();
			# }			
		# }
		# catch (IOException e)
		# {
			# print("Failed to read file.\n");
			# e.printStackTrace();
		# }
		# return lines;
	# }
	
	# public void closeReader()
	# {
		# try
		# {
			# reader.close();
		# }
		# catch (IOException e)
		# {
			# print("Failed to close file reader.\n");
			# e.printStackTrace();
		# }
	# }
	
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