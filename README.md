# cellsociety

CompSci 308 Cell Society Project

Team 1 -- Keegan, Jarod, Ian

Ian: Back-End
Jarod: Front-End
Keegan: XMl File Handling

September 14 - October 2

LangtonRules file from http://golly.cvs.sourceforge.net/viewvc/golly/golly/src/Rules/Langtons-Loops.table 
How to change the color of a series found at - https://stackoverflow.com/questions/11153370/how-to-set-specific-color-to-javafx-xychart-series

File to start the project:
* Running Main or GUI will both start the initial window.

files used to test the project and errors you expect your program to handle without crashing
* In order to test the file saving capabilities of the program, the class "Main.java" can be used to create an xml file based upon arbitrary values for each of the necessary parameters. Part of this check is written into the FileCreator class, which will create a randomized grid when there is no initial grid provided. This functionality also served to extend the capabilities of our program as it allows us to simultaneously meet the requirements of being able to save specific data for simulations, while also being able to randomly generate initial conditions. 

any data or resource files required by the project (including format of non-standard files)
* The following files store the information for running each of our simulations:
    *  Fire.xml , Life.xml , RPS.xml , Segregation.xml , Wator.xml , Langton.xml 
*  The following files contain information about the type of neighbors that the simulation uses:
    * Hex.xml , Square.xml , SquareNN.xml , HexTor.xml , SquareTor.xml , SquareTorNN.xml
* All of the aforementioned files are formatted such that the xml file contains an element node for each of the values that the program would need, with all of these elements being contained to one nodeList. 

any information about using the program (i.e., command-line/applet arguments, key inputs, interesting example data files, or easter eggs)
* An interesting feature of the program is the fact that the same text box can be used to create a simulation and to store the information based upon the text written into the box. Entering a string corresponding to the xml file desired and hitting the "enter" key will create a simulation based upon that data file, while hitting the "Export" button and typing in a string will save the simulation to a file corresponding to the string entered into the text box. To import files from data, the user must enter "data\\FILENAME.xml". By default, files will be exported to data with the proper extension.

any decisions, assumptions, or simplifications you made to handle vague, ambiguous, or conflicting requirements
* One decision that was made regarding the creation of xml files was to generalize the values stored in them, so that there was a basic format that the files followed, but this complicated a few areas of the code as not every aspect of the simulations could be generalized. The main issue was the creation of an array of variables that would change according to the simulation that was being run. Changing the number of items and their values was not difficult, but when there were no special variables needed for the simulation, something had to be done in order to combat nullPointer errors. The solution to this problem can be seen in the program through the use of the String "99", which when read tells the program that there are no specific values necessary for the simulation. 
* The Wator Simulation does not run in parallel to avoid collisions. It runs in random order to account for this. 

any known bugs, crashes, or problems with the project's functionality
* Some of the errors with file reading are misdiagnosed, but still addressed. 
* Infinite Grids are not compatible with charts, so the charts are disabled by default.
any extra features included in the project
* We researched reflection to make our program more flexible and avoid switch-cases.
your impressions of the assignment to help improve it in the future