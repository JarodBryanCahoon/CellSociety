## Introduction

In this program, we are trying to create a simulation of cellular automata. The primary design goal of this project is to create an NxN set of cells which interact interact with each other according to set rules that are passed into the program from a file. We aim to make the primary architecture of the design be such that the cell only needs information about its immediate neighbors, and not of the greater matrix. The greatest flexibility should be afforded to the implementation of the provided rules, such that there can exist great variability in these rules.  The manner in which the cells are stored and updated should be closed -- the rules should be flexible enough in their design to make changing this process unnecessary. 

## Overview

The design is divided into three major parts: the user interface, the simulation, and interaction with files. The GUI stores a Simulation object and has access to the Cells that are storing data. Different rules are represented by subclasses of the abstract Cell class, which will handle stepping and updating differently. The GUI calls on the FileHandler to construct a 2D array of Cells (based on a user selection of Simulation) from an XML file, then the GUI constructs a Simulation and passes in the array. A picture of the interaction between classes is given below. 
The classes and public methods relevant to each are:
* class FileHandler
    * public static Cell[][] read(String fileName)
    * public static void save(Cell[][] cells, String fileName)
* abstract class Cell
    * public void step(List<Cell> neighborhood)
    * public void update()
    * public Node getImage()
    * protected Object getState()
* class Simulation
    * public Simulation(Cell[][] cells)
    * private Cell[][]
    * Public void step()
* public class GUI
    * public GUI()

![First Image](IMAGE_TEMP.jpg?raw=true, "Design Structure")
	
## User Interface:
To start off the program, a display window will display a menu in which the various options for the simulation are detailed. The simulation will start at the selection of a simulation type by the user, which can be done by selecting a file from a drop-down menu. This file will tell the program which simulation to run, and which rules the simulation are to be followed as this file will contain all pertinent information. Once a file has been selected, the user can then press the RUN button to initiate the simulation which will work by forcing the GUI class to create a simulation. At any point, the user can pause the simulation by pressing the STOP button, which will tell the GUI class to stop calling step() functions on the simulation. However, during the simulation, the screen will display the current status of the simulation and will update periodically according to rules set forth in the GUI class. A picture of the the initial design for the window is shown below. 

![Second Image](IMAGE_TEMP_2.jpg?raw=true, "User Interface Design")

NOT INCLUDED IN DRAWING : Popup appears if user enters name of file not found in any of the project directories.
	
## Design Details:
* Cell -
    * Since cell is an abstract class, we will have subclasses of cell representing each different simulation possible (i.e. SegregationCell, FireCell, PredPreyCell). These individual implementations of the cells will contain the state data which is unique to each of the different simulations, such as the rules for cell transformation, and each unique state the cell can be in during the specific simulation.
    * Each subclass of the Cell class will contain the logic necessary for updating itself, and will have access to the state of each of the cells surrounding it. When the Simulation object calls the step() method, the cell calculates what it will change to after it updates its state, and stores it in an instance variable. Then, accessing these instance variables, all of the cells will simultaneously transform into the states that they stored in their instance variables.
    * Cell step(List<Cell>neighborhood) evaluates the condition of all of the cells surrounding it, and determines what the next state of the cell will be.
    * Cell update() simply changes the cell from its current state to the state which was determined in the step() method.
    * Node getImage() returns a node to the GUI, so that the GUI can display the node. The logic for what is returned is determined in each subclass of cell.
* FileHandler -
    * The FileHandler class will be responsible for extracting the data from an XML file. With the data in the XML file, this class will be able to create a 2D array of cells by calling the cell constructors. This array of cells will then be sent to the GUI in order to begin the simulation. This class will have two main methods, read(String fileName) which will take the file and return an array of cells, and save(Cell[][] cells, String fileName), which will take this array of cells and save it to a file. 
* Simulation - 
    * The Simulation is responsible for handling each step by stepping then updating each Cell. It may be needed to handle special cases where Cells require distant information, like in the Segregation Simulation where Cells can move to random empty locations in the array. 
* GUI - 
    * The “highest” class in the program. It constructs and calls on the other classes and takes user input as needed. 

### Use Cases:
* Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
    * The Game of Life rules would need to be encoded into a subclass of Cell. This subclass’s step method would count the number of living cells around it and decide its new state. This would be stored until update is called, at which point it would change to this new state. 
* Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
    * Since we would already encode the Game of Life rules into the specific subclass of cell, and the cells step based upon their neighbors (which are also cells) being given to them in a List, we would simply encode missing neighbors as being null objects in the List, which the Game of Life logic in the cell would interpret as the cell being on the side of the simulation.
* Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
    * In order to update the current generation of cells, the GUI class will call step() on the Simulation class, which will in turn tell the cells to update themselves according to the rules set forth. However, the cells will update themselves in a two-part manner, first by determining what their next state will be and then by changing every cell’s state at the same time after all determinations have been made. The GUI will update the images displayed accordingly. 
* Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire
    * The FileHandler will already be constructing the cells, so if a subclass (FireCell, in the case of probCatch) takes in a value as a parameter, the FileHandler will pass that value to each cell it constructs. 
* Switch simulations: use the GUI to change the current simulation from Game of Life to Wator
    * The GUI will allow you to type and select another XML file which you can then choose and begin to run. This lets you change the simulation without closing and re-opening a different GUI to continue looking at cool simulations.

## Design Considerations:

When constructing the design of the program, the first question we encountered was how to implement the rules of the game. We discussed whether we wanted to have the cells to have multiple types, according to the rules they followed, or to have two separate classes being a rules class and a generic cells class, where the rules would be separate from the cells and could be applied to them according to the simulation type. We initially considered splitting the file into specific simulation type, and initial conditions. We decided to keep the file as one, with both the simulation type and initial conditions present in the file because storing initial configurations separately put them out of context -- they were only relevant in the context of a set of rules.

## Team Responsibilities:

Jarod Cahoon - Developing GUI.

Ian Eldridge-Allegra - Developing simulation, cells, subclasses of cells

Keegan O’Reilly - Creating XML files, reading in the file (FileHandler)

In order to create the program, many of the components can be completed relatively individually, but we will need to come together to finalize the combination of these parts to create a working program. 
