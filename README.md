# dotToNuSMVConverter
A tool for converting Mealy machine .dot models to NuSMV models which can be checked against specifications. 
It takes as input one or more .dot files encoding Mealy machines and generates as output corresponding [NuSMV][NUSMV] 
models for these machines. The user can use NuSMV to check these models against LTL properties of choice.
The transformation from Mealy machines to NuSMV models is described in a [recent publication][SSH]. 

# How to Run
Easiest way to run is by importing the project into eclipse and running the main method of main.DotToNuSMVConverter, 
and supplying a dot model from the 'resources' directory. 

# Final Notes
The code has been copied/adapted from the repository for work relevant to the publication mentioned in the introduction. Due to 
privacy concerns, this repository is not accessible. This tool, however, bears no such concerns, so it could be extracted. 
The code is a bit nasty, but works for the most part. 

[SSH]: http://spinroot.com/spin/symposia/ws17/SPIN_2017_paper_30.pdf
[SSH-MAPPER]: https://gitlab.science.ru.nl/pfiteraubrostean/ssh-mapper
[NUSMV]: http://nusmv.fbk.eu/
