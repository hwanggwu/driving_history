# Description
This is a root insurance take home coding exercise

# Problem Statement

Each line in the input file will start with a command. There are two possible commands.

The first command is Driver, which will register a new Driver in the app. Example:

'Driver Dan'

The second command is Trip, which will record a trip attributed to a driver. The line will be space delimited with the following fields: the command (Trip), driver name, start time, stop time, miles driven. Times will be given in the format of hours:minutes. We'll use a 24-hour clock and will assume that drivers never drive past midnight (the start time will always be before the end time). Example:

'Trip Dan 07:15 07:45 17.3'

Discard any trips that average a speed of less than 5 mph or greater than 100 mph.

Generate a report containing each driver with total miles driven and average speed. Sort the output by most miles driven to least. Round miles and miles per hour to the nearest integer.

Example input:

'''
Driver Dan
Driver Lauren
Driver Kumi
Trip Dan 07:15 07:45 17.3
Trip Dan 06:12 06:32 21.8
Trip Lauren 12:01 13:16 42.0
'''
Expected output:
'''
Lauren: 42 miles @ 34 mph
Dan: 39 miles @ 47 mph
Kumi: 0 miles
'''

# Run by the terminal

All tests are located in the 'test' folder. Each test file leverages java files in src. Please execute 'java Testxxx' to run specific test.

# Object Modeling

At this time, we create two class to cover all required situations that the problem statement requires: Driver class and Trip class. They have aggreagation relationships: Trip class is a classifier as a part of or subordinate to Driver class, which means every object in Class Driver is associated with zero or more objects in Class Trip, and that every object in Class Trip is associated with exactly one object in Class Driver. The classes that will actually be programmed, the main objects, or the interactions between classes and objects will be shown on the class diagram (Figure 1.)

![Figure 1. Class diagram](https://user-images.githubusercontent.com/54546764/112647807-45233e00-8e1f-11eb-9270-c866d7fc5ecf.png)

In our software design, the sample will have only one use case: The driver signs up with the username that represents as Command Driver, and the user could take zero or multiple trips that will be recorded the specific details: the driver name, the start time, the end time, and the miles driven. All these information will be represented as a Trip command. A valid input file will consist of the above two commands (meet the command standard). Specific Details on the Use case diagram(Figure 2.)

![Figure 2. Use case diagram](https://user-images.githubusercontent.com/54546764/112648628-16599780-8e20-11eb-9fb5-fb21ec44c42a.png)

Our software design contains an activity diagram portrays the control flow from a start point to a finish point showing the various decision paths that exist while the activity is being executed. The activity diagram describes both sequential processing and concurrent processing of activities, including input file parse, command options, creating driver and trip class, sort miles by priorityqueue, and write report file. (Figure 3.)

![Figure 3. Activity diagram](https://user-images.githubusercontent.com/54546764/112648647-19ed1e80-8e20-11eb-85cd-01e4f7be869e.png)

To show the interactive relationships between objects in our driving record system, we create a sequence diagram that describes how—and in what order—a group of drivers and trips works together. The diagram documents an existing process and our system's requirements and to flush out a system's design. (Figure 4.)

![Figure 4. Sequence diagram](https://user-images.githubusercontent.com/54546764/112648656-1bb6e200-8e20-11eb-97b1-4570c97580a5.png)

# Running Tests

## Date/ time exceeds 24 hours
throw exceptions
## Trips before drivers
Throws exception if no such driver has the driving record

## If drivers have repeated names
In this project, becuase we don't know which trip is belonging to specific person with same names, we will ignore the repeated driver and print error messages:
Driver: XXX is already existed!

# Thought process

Two class: Trips and Driver, a driver could have multiple driving records. At first, I want to use one class to solve, but separete driver and trips will increase codes readability. 

No drivingrecord class, because that is a redundant on resource. Drivers already have their trips.

Use hashMap to store input Driver name and Driver class, and use priorityqueue to store the entry and sort by drivers milleage. Handle the repeated name exception and trips-before-driver exception.

Where to filter trips with (< 5 mph and > 100 mph?)
should we create each trip as a class? No -- that will waste too much time and space. So we filter these invalid trips at read file step.


