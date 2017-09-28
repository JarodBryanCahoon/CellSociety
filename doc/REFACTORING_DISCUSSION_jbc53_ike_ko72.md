### Refactoring Dicusssion

## Changes Made
    1) Gui Changes
        * Extracted method for changing speed, helping to eliminate duplicate code across two methods.
        * Split setLayout() into smaller methods, which simplifies the method in general, and makes it more readable. 
    2) Simulation
        * Extracted a protected method for stepping all of the cells, so that our subclasses could use it, which removes duplicate code in the subclasses.
    3) File Handler
        * Removed static variables and made them local.

## Intended Changes
    1) GUI Changes
        * Splitting the GUI into two distinct parts, Simulation Panes and Simulation Manager, which allows for several Simulations to be ran side by side and updated at the same time.
    2) FileReader
        * Generalize values stored in XML files, such that the fileReader would not have to differentiate between Cell types.
        
