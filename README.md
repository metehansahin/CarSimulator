# Car Simulator

## Project Proposal:
What will the application **do**?
- The primary usage is to test different cars to evaluate their performance in a simulation environment. There will be a few statistics for every car which determine their performance.

Who will **use it**?
- Engineers and/or stakeholders who want to gain valuable insight about certain aspects of a car. The simulation will be a tool to test inputs and parameters and see how a car performs relative to industry rivals.

Why is this project of **interest** to you?
- I am interested in autonomous systems, machine learning and automotive industry implementations. I see this project as an *opportunity* to gain some knowledge and practice while working on something that I am excited about.

## User Stories:
- As a user, I want to be able to create a new car style (SUV, Sedan, Coupe, etc.) and add it to my list of car styles.
- As a user, I want to be able to view a list of all the car styles.
- As a user, I want to be able to select a style and add a new car to the style.
- As a user, I want to be able to select a style and view a list of all the cars in that style.
- As a user, I want to be able to select a car and see statistics (model, weight, engine force, drag area) for that car.
- As a user, I want to be able to select a car and modify statistics (model, weight, engine force, drag area) for that car.
- As a user, I want to be able to start the simulation and see dynamic statistics (velocity, acceleration, time passed, distance travelled) of my car.
- As a user, I want to be able to save my styles and corresponding cars to file. 
- As a user, I want to be able to load my styles and corresponding cars from file.

### Phase 4: Task 2
- New style, suv, has been created. 
- suv has been added to the style list. 
- New car, tesla model 3, has been created. 
- tesla model 3 has been added to suv. 
- New car, urus, has been created. 
- urus has been added to suv. 
- New car, tesla model x, has been created. 
- tesla model x has been added to suv. 
- New style, sedan, has been created. 
- sedan has been added to the style list. 
- New style, sport, has been created. 
- sport has been added to the style list. 
- New car, porsche 911 targa gts, has been created. 
- porsche 911 targa gts has been added to sport.

### Phase 4: Task 3
My action classes are using the same method but the implementation is different for all of them. If I had more time to work on the project, I would've added an abstract class with actionPerformed(ActionEvent evt) method refactored, and other classes would extend this abstract class and implement the method.