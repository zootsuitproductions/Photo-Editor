HW07 ReadMe:

PLEASE NOTE: To run the scripts we wrote, and to run our tests, you need to unzip the res folder beforehand!! We had to zip this folder to get around the 125 file cap. Thank you!

PLEASE LOOK AT THE USEME FILE FOR INSTRUCTIONS ON HOW TO USE THE GUI WE MADE!

Image Citations: All the images that we have used in our scripts and for different testing purposes, have been created by Michael Redko and Daniel Santana. We authorize the use of our own images!

Import Notes:
-The name of our script file that we created hw07 is: HW07Script.txt 

- For our GUI, the photo that the client can see is always the topmost visible layer in the layered image. However, you are allowed to work on other layers in the layered image by selecting your specific layer in the selection list in our GUI and making it the current layer. However, if the layer you picked is not the topmost visible image layer in the layered image, then you will not be able to see that specific layer you are working on. You would have to make other layers on top of your layer transparent in order to make the layer you are working on the “topmost visible layer image: in the layered image.

We completed all parts of the program.


1.) How to Run The Program:
The main way to run the program is through the Jar file that we have created. Open a command prompt/terminal and navigate to the folder /res/jar_and_scripts/ to find our jar file. Now type java -jar hw07.jar. Now here you have three choices to make after typing this specific portion in. 

1.) The first choice is to type this word after: “-script”
2.) The second choice is to type this word after: “-text”
3.) The third choice is to type this word after: “-interactive”


Option 1: Script

If you run the program with the first choice, this means you are choosing to run the program by providing a script to give the program commands for creating and editing a layered image you want. 
After typing in the word “-script” you are required to type in the name of the script file you want our program to read. The script you provide needs to be in text file format and if you want your layered image to be created properly you need to use a script text file with the proper commands that the program can recognize. We have created one valid script file for you that you can use to see the scripting work properly. You can look in the USEME file we created in the commands section for reference on how our commands work.

 Here is what you should put in the command line to run our script choice properly:
java -jar  hw07.jar  -script  HW07Script.txt 
 Here is what you should put in the command line to run your script choice properly:
java -jar  hw07.jar  -script  <path-of-script-file>



Option 2: Text

If you run the program with the second choice, this means you are choosing to run the program by using the terminal itself and you are deciding to input the commands you want by hand in text. The view that you will see will be a textual view on the command line, not a graphical view. 

 Here is what you should put in the command line to run your text choice properly:
java -jar  hw07.jar  -text 


When you do it this way, the first thing that you will see is a prompt that states, “Type ‘script’ to run a script of commands or type ‘interactive’ to input to the console.” With our interactive manual text view, we still give you the option to use a script right off the bat or you can choose the interactive version where you type in the commands by yourself. 

To run our example script, you can type in “HW07Script.txt” after inputting “script”, and this will run the script and output the photos that the commands create and edit within the jar_and_scripts folder.

If you are planning to provide the name of your own valid .txt file that contains the script of commands you want to run, type in the word script. After typing in the word script, the program now understands that you want to insert a file with the correct commands. The program will output a message saying “Type the name of the script file you would like to run,  which should end in .txt”. The next thing you should type is the valid name of the .txt file you want the program to read and execute. Make sure that your file is in the .txt format and do not type in anything else besides the name of the file you want to be read if you want the program to function correctly. Here is when you should type in one of the valid script .txt files that we created for working properly. You can use the script file we created and it should work properly. If you type in an invalid file format that is not .txt or if you provide .txt file that does not exist, the program will not work properly!

If you are planning to type in commands interactively, you should type in the word “interactive” when you see the program prompt you. After typing in the word interactive, you are ready to start giving the program your commands. The first command you should give the program is the createLayer command. Nothing can be done in our program unless you create the layers first. Other functionality will not work unless the layers have been created by calling the createLayer command for as many layers as you want to make.  Look through the documentation on the HW06 portion of the new USEME file for hw07 on how to use the commands properly! 

Once you choose interactive or script, you have to stick to that choice. If you want to choose the other choice after starting, you will need to restart the program! For the script input, if you want to try running the second script .txt file we created, you will have to restart the program and type in the new script file you want to run!

BEFORE INTERACTIVELY RUNNING THE PROGRAM, LOOK AT THE USEME FILE FOR COMMAND DIRECTIONS!!






Option 3: Interactive 

If you run the program with the third choice, this means you are choosing to run the program by using the GUI we created for creating layered images. 

 Here is what you should put in the command line to run our script choice properly:
java -jar hw07.jar  -interactive

Once you type this into the command line properly, our GUI program will automatically start loading and will pop up on your machine. From there, you can use the GUI to create whatever layered images you want!





2.) Design/Model Changes:

	In order to create a GUI for our layered image editor, we had to create a new Controller and a new View to handle the new functionalities that arose from creating a GUI. 

	First, we created the GraphicalController class which was our new controller for our graphical view. We made this controller implement our existing interface, ILayeredEditorController. We wanted this GraphicalController to handle cases for all types of high-level events and we did not want to handle all the different types of low-level events that arose from the graphical view. Consequently, we created our own type of “Listener” called the IViewListener. This IViewListener interface provided all the functionality that allowed for an implementing class to only have to handle and listen to high-level events. Consequently, we turned our controller into a listener, making our GraphicalController implement this IViewListener. This meant that our controller was only handling high-level events that our graphical view was eliciting and did not care what the types of the low-level events were. The sole purpose of our GraphicalController was to listen for the right commands from user events from the graphical view and carry out the correct actions appropriately.

	On the other side, we created the GraphicalLayeredImageView class that implemented an IView interface that we created. In the IView interface that we laid out the correct functionality that our GraphicalLayeredImageView needed in order to interact with GraphicalController properly and send it the correct signals for when users interacted with the view in a specific way. The interface also laid out some methods that allowed for the GraphicalLayeredImageView to receive the minimum data it needed from the GraphicalController in order to output the necessary state of the layered image. For this assignment, the GraphicalLayeredImageView did most of the interaction with the user input and send the correct signals to our GraphicalController to carry out the proper events in the model. The GraphicalController responded by sending back the correct data of the layered image after the user input commands were carried out. 

	To handle the different action events that arose from the user input in the GraphicalLayeredImageView, we created the IActionEvent interface inside of the GraphicalLayeredImageView class. We did this in order to save file space and make it easier to add more ActionEvents in the future for the view. The IActionEvents we created were all function objects that made it more organized for the correct actions to occur when specific user events happened in the GraphicalLayeredImageView. We created different types of ActionEvent classes that implemented the IActionEvent interfaces. Every class was a function object that represented a different action event that could occur from user input and handled what would happen if the user caused this action event to occur through the view. In the future, this design would make it much easier for new elements to be added to the view and for their action events to be handled in a more organized fashion. 

	The last thing we changed was our main runner. Now, our main runner takes in arguments that will tell it which version of our program to run. Our main runner has a variation for running the text version of our program, the interactive(GUI) version of our program, and the script version of our program!
