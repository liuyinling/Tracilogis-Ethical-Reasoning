The MAS is configured with one product agent.

All the configurations of the product agent have been tested.

--26/02/2023

I am facing the problem of *positionChecked.

Problems seem solved. Look at the communication between ASO and checkP.

We can feel free to create new beliefs to record the values of some parameters. 
For example, 
+!check_pos(Ag1,Ag2,X,Y)[perform]:{ B Unblocked} <- +.lock, -Unblocked, +para(Ag1,Ag2,X,Y), print(check_pos_startToCheck), checkPos(Ag1,Ag2,X,Y), +Unblocked, -.lock;
+!check_pos(Ag1,Ag2,X,Y)[perform]:{~B Unblocked} <- +.lock, .send(Ag1,:tell,failureCheckPos), -.lock;

+.received(:tell, B): {True} <- +B;
+positionChecked:{B para(Ag1,Ag2,X,Y)} <- print(check_pos_positionChecked), .send(Ag1,:tell,posV), -para(Ag1,Ag2,X,Y), delete(positionChecked);

--26/03/2023

This version has "ensured" that there is not collision between products.
-31/03/2023

We are going to integrate the ethical aspect into multi-agent systems. 
-03/04/2023

The first version of integration of the ethical aspect is finised.
-05/04/2023

This version removes all prints.
-10/04/2023