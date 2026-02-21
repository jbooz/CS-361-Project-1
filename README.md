# CS-361-Project-1
Repository for CS 361 Project 1. Team members are Jonathan Boozel and Tony Bowen

This program was created to simulate a deterministic finite autonoma (DFA). A DFA has a 5-tuple that is
used to describe it in every circumstance. The 5-tuple consists of Q (the set of all states), Sigma
(the alphabet of all characters), Delta (the table describing all the transitions from one state to another),
q0 (the initial/start state), and F (the set of all final states). This 5-tuple describes how a DFA uses states
and transitions to represent a finite autonoma. The finite autonoma is a complex way to represent acceptance of
strings into an alphabet of characters that is defined.

Compiling and running this code is quite easy. To compile the code, first ensure that the directory you are compiling
within is the outer project folder that encompasses the "lib" folder and the "p1Files" folder to ensure total functionality.
You can then compile the java files using "javac -cp DFATest.java". Afterwards, you can run the tests using the "java"
functionality and listing the specific junit files within the library to use for testing.