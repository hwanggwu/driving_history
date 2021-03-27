# Description
This is a root insurance take-home coding exercise

# Problem Statement

Each line in the input file will start with a command. There are two possible commands.

The first command is Driver, which will register a new Driver in the app. Example:

`Driver Dan`

The second command is Trip, which will record a trip attributed to a driver. The line will be space delimited with the following fields: the command (Trip), driver name, start time, stop time, miles driven. Times will be given in the format of hours:minutes. We'll use a 24-hour clock and will assume that drivers never drive past midnight (the start time will always be before the end time). Example:

'Trip Dan 07:15 07:45 17.3'

Discard any trips that average a speed of less than 5 mph or greater than 100 mph.

Generate a report containing each driver with total miles driven and average speed. Sort the output by most miles driven to least. Round miles and miles per hour to the nearest integer.

Example input:

```
Driver Dan
Driver Lauren
Driver Kumi
Trip Dan 07:15 07:45 17.3
Trip Dan 06:12 06:32 21.8
Trip Lauren 12:01 13:16 42.0
```

Expected output:

```
Lauren: 42 miles @ 34 mph
Dan: 39 miles @ 47 mph
Kumi: 0 miles
```

# Run by the terminal

All tests are located in the 'test' folder. Each test file leverages java files in src. Please execute 'java Testxxx' to run a specific test.

# Object Modeling

At this time, we create two classes to cover all required situations that the problem statement requires: Driver class and Trip class. They have aggregation relationships: Trip class is a classifier as a part of or subordinate to Driver class, which means every object in Class Driver is associated with zero or more objects in Class Trip, and that every object in Class Trip is associated with exactly one object in Class Driver. The classes that will be programmed, the main objects, or the interactions between classes and objects will be shown on the class diagram (Figure 1.)

![Figure 1. Class diagram](https://user-images.githubusercontent.com/54546764/112647807-45233e00-8e1f-11eb-9270-c866d7fc5ecf.png)
<center>*Figure 1. Class diagram*</center>

In our software design, the sample will have only one use case: The driver signs up with the username that represents as Command Driver, and the user could take zero or multiple trips that will be recorded the specific details: the driver name, the start time, the end time, and the miles driven. All this information will be represented as a Trip command. A valid input file will consist of the above two commands (meet the command standard). Specific Details on the Use case diagram(Figure 2.)

![Figure 2. Use case diagram](https://user-images.githubusercontent.com/54546764/112648628-16599780-8e20-11eb-9fb5-fb21ec44c42a.png)

Our software design contains an activity diagram that portrays the control flow from a start point to a finish point showing the various decision paths that exist while the activity is being executed. The activity diagram describes both sequential processing and concurrent processing of activities, including input file parsing, command options, creating driver and trip class, sort miles by priority queue, and write report file. (Figure 3.)

![Figure 3. Activity diagram](https://user-images.githubusercontent.com/54546764/112648647-19ed1e80-8e20-11eb-85cd-01e4f7be869e.png)

To show the interactive relationships between objects in our driving record system, we create a sequence diagram that describes how—and in what order—a group of drivers and trips works together. The diagram documents an existing process and our system's requirements and to flush out a system's design. (Figure 4.)

![Figure 4. Sequence diagram](https://user-images.githubusercontent.com/54546764/112661600-1744f600-8e2d-11eb-8ff3-6d7bd9ed47b0.png)


# Running Tests

The development process is test-driven. In our software application, the test-driven environment was built by unit testing, integration testing, and functional testing. To ensure full coverage, unit tests are taken on each function/module of our codes to test functions in the program that could work well in isolation and cover edge cases as much as possible. Furthermore, integration tests and functional tests for assurance that the "units" could work together cohesively.
For example:

> In the Driver class, we try to cover edge cases in the driverName variable: take three unit tests on getDriverName() to get expected results in three situations: with full driverName, with empty string driverName(""), and with null driverName.

>In the Main class, we try to cover three different cases that could happen in the input file: including invalid command, command Driver with the same driver name, and a driver takes a trip before he/she registers as a driver (Command Trip is before Command Driver). We plan to throw certain exceptions to cover all of these cases to make sure the user could make sense of where the problem is and skip these invalid commands to keep parsing.

We also did integration tests and functional tests. The bottom-up approach is used in the integration tests on the Driver and Trip class, we created multiple drivers and trip objects to test them by considering all modules as one object.

Functional tests are important, we create multiple sample input files to run our codes in TestMain. We have considered various situations in the input file to ensure that the result we get is what we want, even though some "seasoning" seems useless, such as extra more spaces between commands, endTime is before the startTime and invalid date format. All these results will influence the modification of codes.

All tests are located in the 'test' folder. Each test file leverages java files in src. Please execute 'java Testxxx' to run a specific test. 

# Thought process

In the process of designing the driving records system, there are many interesting thinking processes and trade-offs:

## Driver, Trip or Driver, Trip, current_records or only one driver?

First, I need to classify that:
```
we simplify the codes by designing only one class: driver, which means we only need to update his/her mileage and time data when we get a trip command?
```
or
```
we magnify the difficulty by adding one more class named current_records to represent all records in the input file. 
```

I find that the former option will discard a lot of trip information, and the trip information is essential in our software design. For example, a Trip class may include departure, destination, road information, and so on in the future. Comparing to that, the second option makes the problem too complicated. If the input file is large, it means that the program will slow down and more memory will be wasted.

Since there are two types of commands in the problem, we just create two classes for each one, which is both efficient and flexible.

## What data structure should be used to store parsed information?
ArrayList, Tree, hashmap were in our consideration. It is worth noting that there is a common variable "drivername" in two different commands, so we can use the driver name as a "foreign key" to make associations between driver object and trip object. Therefore, "drivername" will be a "key" and its type is String. Data structures such as array and list can't store a string as a key. Therefore, hashmap/dictionary becomes our first pick. Also, its get() and put() will cost O(1) time, which could save more time and memory than other data structures. The value in the hashmap could be a driver object, which contains a useful variable: mileage and time to calculate speed and be sorted by mileage. 

Therefore, we use a hashmap to store the information parsed from the input file, and its entry will be 'HashMap<String, Driver>'.
## How to sort driver by mileage?
Our hashmap contains all current drivers in the input file as objects, we could either write a comparator to compare each driver's mileage to determine the order that we write on the report file. The other feasible and simple method is using a max heap to store the entry in the hashmap, and the heap maintains the driver with more miles driven is before the driver with less mileage. Moreover, we could use the heap to write our output file. After we push all entries in the hashmap, all drivers are ready to be written on the report with required orders. 

## If drivers have repeated names
In this project, because we don't know one certain trip is driven by which person if multiple users have the same names, we will ignore the repeated driver and print warning messages:
```
Warning: Driver XXX has already existed!
```
Then we will skip repeated Driver commands and keep processing on the input file.

## Where to filter trips with (< 5 mph and > 100 mph?)
Should we create trip objects on every Trip Command, even though the mph is out of our consideration? I think a lot on these trade-offs, and lists all merits and demerits as follow:
```
pro:
We can track the unnormal and dangerous records on a certain driver. especially those driving records with > 100 mph. Those over 100 mph driving records are valuable. We only need to find all records that meet our requirements for each driver. 
```
```
con:
Waste space and time. If an input file contains a large number of invalid records, are we going to save them all?
```
Finally, to increase the readability and flexibility of our driving record system, I decide to filter these invalid records before we create an object on them.
