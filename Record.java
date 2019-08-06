import java.util.Scanner;
import java.util.ArrayList;

class Record{

   // An arraylist used to store each element of the record
   ArrayList<String> record;
   // the number of elements the record contains
   int numoffeatures;
   // the number of elements scanned
   int numofelements;

   // CONSTRUCTOR
   // Trivial Constructor
   Record(){

      this.record = new ArrayList<String>();
      this.numoffeatures = 0;
      this.numofelements = 0;

   }

   // Constructor that initialise the record
   Record(int numoffeatures){

     this.record = new ArrayList<String>();
     this.numoffeatures = numoffeatures;
     this.numofelements = 0;

     fillRecord();

   }

   // Fill the record by scanning user input
   void fillRecord(){

      System.out.print("INPUT THE RECORD(NOTE: "+
"use space to seperate elements and '/'+ENTER to finish):\n");
      Scanner scanner = new Scanner(System.in);

      while(!scanner.hasNext("/") && (this.numofelements < this.numoffeatures)){
         record.add(scanner.next());
         this.numofelements++;
      }

   }

   // Print the record
   void printRecord(){

     for(int i = 0; i < record.size(); i++) {
        System.out.print(record.get(i)+" ");
     }
     System.out.print("\n");

   }

   void testall(){

      assert(this.numoffeatures == 0);
      assert(this.numofelements == 0);

      String testString = "aaa";
      String testString2 = "bbb";

      this.record.add(testString);
      assert(this.record.get(0).equals(testString));

      this.record.add(testString2);
      assert(this.record.get(1).equals(testString2));

      this.record.remove(testString);
      assert(this.record.get(0).equals(testString2));

      System.out.println("Record testing passed.");

   }

   void run(String[] args) {

      if (args.length == 0) testall();
      else {
         System.err.println("Use:  java -ea Oxo or java Oxo");
         System.exit(1);
      }
   }

}
