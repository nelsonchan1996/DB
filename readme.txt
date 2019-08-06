report of stage development

10/03/2019
11/03/2019

Started writing. Create two class one for record one for table.
Built methods to initialise record, scan the record content from user input and print record.
Built a 2D String array to store the content scanned.

12/03/2019

Since I found that if the records are stored in a 2D array,
I have to resize the array every time new record is added to the array, which is problematic.
I change it to an array list and afterwards the records,
tables and databases are all stored in a structure of array list.
Since it makes a lot of differences writing the stuff in 2D array and array list,
I kind of rewrote the first two classes.

13/03/2019

To scan and store the field names of a table, Instead of treating it as a record,
I use a new array list to store each field names and the size of this array list can be
used as the number of columns.

14/03/2019
15/03/2019

I learned to use BufferedWriter to read from and write to a file and added the most initial form of
methods to the table class. I also created a method to scan a single primary and check whether it violates
the constraint when it is scanned in and before a new record is added. But at this moment, composite primary key
is not allowed. Also, I started the database class and tried adding a few basic methods to it. In the class,
I created another array list that stores and manage tables.

16/03/2019

I got only 3 days left, but far from finishing the assignment, so I worked from 2pm till the next morning.
I firstly improved the previous coding by deleting unnecessary methods and variables.
I also created a bunch of printing function so that when the result is printed it looks more like a table.
I improved the file handling methods so that it can read from file and write to file a table.
Also, I completed the database class and its printing methods.

17/03/2019

I started working on the user interface class. At first I named it "Interface.java" but it did not work, so
I changed the name to UI. I wrote a menu on sheet and tried to implement it in the class. While calling classes
and methods from the UI class, I improved the other few classes again by finding and solving some bugs.
I also completed the methods to scan it and use composite primary keys instead of normal primary key.
Also, I completed the file managing methods and add them to the menu. I worked till 10 am on 18th March.

18/03/2019

When I woke up it's already 4pm. I haven't done any testing yet so I started doing the testing. I was thinking to do
some blackbox testing for some of the methods like printing functions that can not be tested, but time is running out..
Tried my best anyway. Hope next time I can start on the assignment in more advance and do it better.

Goodbye Db.
