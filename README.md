# Description
This is a root insurance take-home coding exercise

# Problem Statement

Each line in the input file will start with a command. There are two possible commands.

The first command is Driver, which will register a new Driver in the app. Example:

`Driver Dan`

The second command is Trip, which will record a trip attributed to a driver. The line will be space delimited with the following fields: the command (Trip), driver name, start time, stop time, miles driven. Times will be given in the format of hours:minutes. We'll use a 24-hour clock and will assume that drivers never drive past midnight (the start time will always be before the end time). Example:

`Trip Dan 07:15 07:45 17.3`

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

Please open the gitbundle from your preferred terminal application and navigate into the main folder using cd driving_history. The core codes are located on the `/src` folder. Don't forget to run a bundle install if you want to run the tests!

Once completed you can run the program by executing `javac Main.java` to compile the java file, you could get all `.class` files. 

Then you could run `java Main` to get start from main function. You will get the following information: 
<Please enter the input file name (w/ .txt):

Please import the input file with an absolute or relative path(be sure to put your input file into /src folder). If you do not have a data file that meets the specifications outlined below in the "Problem Statement" section. Please feel free to input all test files `/test/data/input.txt` from terminal to use an example data set provided in this repository.

# Object Modeling

At this time, we create two classes to cover all required situations that the problem statement requires: Driver class and Trip class. They have aggregation relationships: Trip class is a classifier as a part of or subordinate to Driver class, which means every object in Class Driver is associated with zero or more objects in Class Trip, and that every object in Class Trip is associated with exactly one object in Class Driver. The classes that will be programmed, the main objects, or the interactions between classes and objects will be shown on the class diagram.

![Figure 1. Class diagram](https://user-images.githubusercontent.com/54546764/112647807-45233e00-8e1f-11eb-9270-c866d7fc5ecf.png)

In our software design, the sample will have only one use case: The driver signs up with the username that represents as Command Driver, and the user could take zero or multiple trips that will be recorded the specific details: the driver name, the start time, the end time, and the miles driven. All this information will be represented as a Trip command. A valid input file will consist of the above two commands (meet the command standard). Specific Details on the Use case diagram.

![Figure 2. Use case diagram](https://user-images.githubusercontent.com/54546764/112737264-830f8780-8f2f-11eb-93b5-a7b54cbc1f8b.png)

Our software design contains an activity diagram that portrays the control flow from a start point to a finish point showing the various decision paths that exist while the activity is being executed. The activity diagram describes both sequential processing and concurrent processing of activities, including input file parsing, command options, creating driver and trip class, sort miles by priority queue, and write report file.

![Figure 3. Activity diagram](https://user-images.githubusercontent.com/54546764/112737816-adfbda80-8f33-11eb-87c5-70ff7704b465.png)

To show the interactive relationships between objects in our driving record system, we create a sequence diagram that describes how???and in what order???a group of drivers and trips works together. The diagram documents an existing process and our system's requirements and to flush out a system's design.

![Figure 4. Sequence diagram](https://user-images.githubusercontent.com/54546764/112737266-86a30e80-8f2f-11eb-9f81-de2be539692f.png)


# Test Approach

The development process is test-driven. In our software application, the test-driven environment was built by: 
* unit testing
* integration testing
* functional testing. 

To ensure full coverage, unit tests are taken on each function/module of our codes to test functions in the program that could work well in isolation and cover edge cases as much as possible. Furthermore, integration tests and functional tests for assurance that the "units" could work together cohesively.
For example:

> In the Driver class, we try to cover edge cases in the driverName variable: take three unit tests on getDriverName() to get expected results in three situations: with full driverName, with empty string driverName(""), and with null driverName.

> In the Main class, we try to cover three different cases that could happen in the input file: including invalid command, command Driver with the same driver name, and a driver takes a trip before he/she registers as a driver (Command Trip is before Command Driver). We plan to throw certain exceptions to cover all of these cases to make sure the user could make sense of where the problem is and skip these invalid commands to keep parsing.

We also did integration tests and functional tests. The bottom-up approach is used in the integration tests on the Driver and Trip class, we created multiple drivers and trip objects to test them by considering all modules as one object.

Functional tests are important, we create multiple sample input files to run our codes in TestMain. We have considered various situations in the input file to ensure that the result we get is what we want, even though some "seasoning" seems useless, such as extra more spaces between commands, endTime is before the startTime and invalid date format. All these results will influence the modification of codes.

All tests are located in the 'test' folder. Each test file leverages java files in src. Please execute 'java Testxxx' to run a specific test. 

# Thought process

In the process of designing the driving records system, there are many interesting thinking processes and trade-offs:

## Driver, Trip or Driver, Trip, current_records or only one driver?

First, I need to classify that:

> we simplify the codes by designing only one class: driver, which means we only need to update his/her mileage and time data when we get a trip command?

or

> we magnify the difficulty by adding one more class named current_records to represent all records in the input file. 

I find that the former option will discard a lot of trip information, and the trip information is essential in our software design. For example, a Trip class may include departure, destination, road information, and so on in the future. Comparing to that, the second option makes the problem too complicated. If the input file is large, it means that the program will slow down and more memory will be wasted.

Since there are two types of commands in the problem, we just create two classes for each one, which is both efficient and flexible.

## What data structure should be used to store parsed information?
ArrayList, Tree, hashmap were in our consideration. It is worth noting that there is a common variable 'drivername' in two different commands, so we can use the driver name as a "foreign key" to make associations between driver object and trip object. Therefore, "drivername" will be a "key" and its type is String. Data structures such as array and list can't store a string as a key. 

A data structure named Hashmap/dictionary could store String type variable as a key, and its get() and put() will cost O(1) time that could save more time and memory than other data structures. The value in the hashmap could be a driver object, which contains a useful variable: mileage and time to calculate speed (mph). 

Therefore, we use a hashmap to store the information parsed from the input file, and its entry will be 'HashMap<String, Driver>'.

## Some language idioms to be used
Some new features could be implemented on this coding exercise to increase the readability and performance of codes.

### Java IO reader: BufferReader & BufferWriter
Java 8 convented BufferReader as a replacement of DataInputStreams for textual input that buffers characters so as to provide for the efficient reading of characters, arrays, and lines. Without buffering, each invocation of read() or readLine() could cause bytes to be read from the file, converted into characters, and then returned, which can be very inefficient.

### DateTimeFormatter
DateTimeFormatter is a formatter for printing and parsing date-time objects, which could handle many complex patterns letters, such as yyyy-MMM-dd, d MMM uuuu, and so on. The reasons why I use this formatter is becasue:
* The formatter could handle much more complex time formats, to ensure the flexity of our system.
* The formatter created from a pattern can be used as many times as necessary, it is immutable and is thread-safe.
* It could parse a string of dateTime by a straightforward way. 

## Handles exceptions in input file
For each command, I use try-catch block to catch some exceptions in the input file. My thought is to skip all invalid commands and keep parsing the rest input to gather valid information. Meanwhile, the system will print the invalid command line and the stack trace.

For Command `Driver`, I try to handle such exceptions :
*  Identical userName
*  Not meet the standard format of Trip command, such as: 'Driver'

For Command `Trip`, I try to handle such exceptions :
* DateTime can't be parsed such as: "27:04", "10.21"
* EndTime is before the startTime such as: start at "16:04", and end at "11:01"
* Not meet the standard format of Trip command, such as: 'Trip 10:25 12:22 55'

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

>pro:
>We can track the unnormal and dangerous records on a certain driver. especially those driving records with > 100 mph. Those over 100 mph driving records are >valuable. We only need to find all records that meet our requirements for each driver. 

>con:
> Waste space and time. If an input file contains a large number of invalid records, it will waste a lot space to store invalid information

Finally, to increase the readability and flexibility of our driving record system, I decide to filter these invalid records before we create an object on them.
