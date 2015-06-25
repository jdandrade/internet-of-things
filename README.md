# internet-of-things

Java application to manage house devices, their power costs and total output using jfree.chart

Final project for OOP (Object Oriented Programming) Class.

Some parts of the code/file-names are in portuguese because this Java program was initially made specifically for the OOP class and we had to meet some requirements.

## Output

![My image](./tfpoo.png)

## How it works?

Basically this java program reads json files as input and creates a virtual world where all the devices are connected to a specific plug inside the house. 

It gives us LIVE information about power costs (Watt) being used by those devices.

You can manipulate advanced devices (i.e Washing machine) that have some more advanced settings like Programs (with specific cicles).

## How was it done?

Since it was made for the OOP class, it was done mainly by object creation/manipulation.

Lists were used to store Devices/Plugs/Rooms.

Output was done through jfree.chart library with the Observer design patter implementation. http://www.jfree.org/jfreechart/

Reading input from json files was done with the help of json-simple library. https://github.com/fangyidong/json-simple

## Left to do?

Need to clean code, finish class documentation and finish english translation.

