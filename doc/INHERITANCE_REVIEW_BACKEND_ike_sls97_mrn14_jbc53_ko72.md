## Part 1

* My cell class makes its state unchangable by other classes, including the subclasses, which only have access to the nextState. Subclasses of cell encapsulate most of the rules of the game, though the Simulation class will require extension to handle some of the rules for moving cells.
* Cell is an abstract class representing the basic functions of a cell -- each subclass holds a set of rules to determine how to step. Similarly, Simulation is a general simulation, but can be extended for specific functionality. 
* The Cell class, Simulation class, and AbstractGrid class will be closed, but open for extension.
* Some simulations produce collisions, but these will be given rules to resolve those cases. Otherwise, the simulation just needs to check for a valid initial input. 
* This design is flexible -- it should handle any new cell simulation by extending the given classes. Simple cases, like the game of life, were very easy to implement by only extending cell. Given the constraints of the project, "good" just means flexible and understandable. 

## Part 2

* The simulation assumes that the GUI calls step and that the display is handled elsewhere, but it is not directly dependent on the front-end or FileHandler. 
* The dependency depends only on the GUI's behavior.
* To minimize dependencies, the Simulation could instantiate its own grid, but it is easier to handle as-is.
* The Cell class has a generic type, which varies in the subclasses, but the update behavior is the same. The step behavior is entirely different, since this just changes nextState. 

## Part 3

* 5 use cases: 
	* Run game of life
	* Run Wator
	* Run segregation
	* Run fire
	* Make a class handling a generic, square simulation with no cell movement
* I'm excited to hit the next sprint and see how flexible our design really is
* I'm worried about... hitting the next sprint and seeing how flexible our design really is