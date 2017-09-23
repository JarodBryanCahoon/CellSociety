### Part 1

* One implementation decision on the part of the frontend/GUI that allows some level of encapsulation and data hiding is the flow of data from the XMLReader into the GUI, before that information is passed to the Simulation class to make a simulation. This completely seperates the concepts of the XMLReader from the simulation, and disallows the simulation from knowing about the XMLReader.
* As it currently stands, we are trying to seperate our GUI as much as we can from the rest of the project, allowing us to have a single implementation of the GUI which works for everything, so in that sense, not much inheritance is going on here.
* Ideally, our UI would be almost entirely seperate from the Simulation, and the UI would access information from the simulation, allowing it to display the information on the screen, though the UI itself would be completely hidden from the Simulation, Cell, and XMLReader classes.
* One main error case which could happen in this situation is that the XMLReader doesn't return a "valid" (non-null) 2d array of Cells, which we could then report to the user, letting them know that their file is possibly configured in a manner which would not allow our reader to comprehend it.
* I think this design is good because it best seperates the concept of the front end from the concept of the back-end, and minimizes the amount of direct dependencies which are put onto the Simulation as a direct result of the GUI. Though the GUI "probes" and updates the Simulation, the Simulation doesn't necessitate that this updating come explicitly from a GUI.

### Part 2

* Almost none of the other areas of the project have direct dependencies upon the GUI, though the GUI does update/control the behavior of other areas of the project, such as calling the Update() method for the Simulation. It also requires the the Simulation return/update something for the GUI to display, as well as the XMLReader to return something which the GUI can instantiate an object with.
* These dependecies are present because of the way that we chose to implement the project, and we believe that they are the best way to handle the inevitable dependency issues which arise in a project such as this one.
* These dependencies are fairly minimal in the sense that they don't really require certain backdoor functionalities to resolve, and are fairly clearly displayed when it is understood how each of the seperate parts of the program interface with one another.
* Since there aren't really any sub/superclasses in the GUi, I will go over some of these classes which are present in the Cells themselves.
    * Cell (masterclass) and LifeCell (subclass) both have instance variables which contain the information about their current state and about what theirr next state will be. However, the behavior which dictates how these states change are implemented in the LifeCells themselves (the subclasses).

### Part 3

* Use cases
    1. The user submits an empty string to find the file
        * In this case an error pop-up will occur, telling the user to input a valid string
    2. The user submits a stirng which is the name of the file
        * The GUI takes this string, verifies that it is non-null, and passes it along to the XMLReader to create the cells.
    3. The user decides to pause the simulation at a certain step.
    4. The user decides to reset the animation.
    5. The user decides to export the current state of the simulation as an XML file

* I am most excited to work on the problem of having a grid of cells with a set size, regardless of the number of cells.
* This is also the problem I am worried about working on most, as it seems hard to code in the instance that the size of the grid grows.
    
