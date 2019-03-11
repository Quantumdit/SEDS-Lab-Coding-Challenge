Converts requirement text into JSON format.

Written by Yehonatan (Jonathan) Shabash.

Input Assumptions:
	All requirements start on separate line in which:
		The only thing before the first period is a number
		The only thing before the first space is the ID of the requirement, which is a string of numbers and periods
		All subrequirements have an ID that starts with the ID of their parent requirement
		Subrequirements are listed directly after their parent requirement
	Subrequirements do not have their own subrequirements

Compilation:
	Run "javac *.java" in terminal in a folder with all the source files
Running:
	Run "java JSONConverter" in terminal in a folder with all the compiled class files
Output:
	The results will be output into a file called "output*.txt", where * is some number.
		The number will be chosen so that no previous file is overwritten.
	The exact name of the output file will be printed to the console when the program is running