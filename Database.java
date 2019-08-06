import java.util.Scanner;
import java.util.ArrayList;

class Database{

   // An arraylist that stores each table of the database
   ArrayList<Table> database;
   // Name of database
   String database_name;
   // Maximum length of table name
   int max_tablenamelength;
   // The index of the table selected
   int selected_table_index;

   // 2-space padding for printing
   final int padding = 5;

   // CONSTRUCTOR
   // Trivial Constructor
   Database(){

      this.database_name = null;
      this.database = new ArrayList<Table>();

   }

   // Constructor that initialise the database
   Database(String database_name){

      this.database_name = database_name;
      this.database = new ArrayList<Table>();

   }


   // Check whether the table name already exists or not
   Boolean checkTablename(String name){

     for(int i = 0; i < this.database.size(); i++){
        if(this.database.get(i).table_name.equals(name)){
           return false;
        }
     }
     return true;

   }

   // Add a table
   void addTable(Table t){

      database.add(t);

   }

   // Remove a table from database
   void dropTable(String table_name){

      int cnt = 0;

      for(int i = 0; i < database.size(); i++){

         if(database.get(i).table_name.equals(table_name)){
            database.remove(database.get(i));
            cnt++;
         }

      }

      if(cnt == 0){System.out.println("Table deletion failed. The table does not exist");}
      else if(cnt == 1){System.out.println("Table "+table_name+" is deleted.");}

   }

   // PRINTING
   // get the maximum table name length
   void getTablenamelength(){

      max_tablenamelength = 0;

      for(int i = 0; i < database.size(); i++){
         if(max_tablenamelength < database.get(i).table_name.length()){
            max_tablenamelength = database.get(i).table_name.length();
         }
      }

   }

   // Print the heading
   void printHeading(){

      int headinglength = max_tablenamelength + (padding+1)*2;

      System.out.print(" ");

      for(int i = 0; i < headinglength - 2; i++) {
         System.out.print("-");
      }

      System.out.print("\n");

   }

   // Print the padding space
   void printSpace(int n){

      for(int i = 0; i < n; i++){
         System.out.print(" ");
      }

   }

   // Print column bar
   void printColumn(){

      System.out.print("|");

   }

   // Print the database name
   void printDatabasename(){

      System.out.println("Database:" + this.database_name);

   }

   // Print the table name
   void printTablename(){

      int left_paddinglength;
      int right_paddinglength;

      for(int i = 0; i < database.size(); i++){

         if((max_tablenamelength - database.get(i).table_name.length()) % 2 == 0){
            left_paddinglength = right_paddinglength = (max_tablenamelength - database.get(i).table_name.length()) / 2;
         }
         else{
            left_paddinglength = (max_tablenamelength - database.get(i).table_name.length() + 1) / 2;
            right_paddinglength = max_tablenamelength - database.get(i).table_name.length() - left_paddinglength;
         }

         printColumn();
         printSpace(padding+left_paddinglength);
         System.out.print(database.get(i).table_name);
         printSpace(padding+right_paddinglength);
         printColumn();
         System.out.println();

      }

   }

   //Print a blank line
   void printBlankline(){

      printColumn();
      printSpace(padding);
      printSpace(max_tablenamelength);
      printSpace(padding);
      printColumn();
      System.out.print("\n");

   }

   //Print database
   void printDatabase(){

      getTablenamelength();
      printDatabasename();
      printHeading();
      printBlankline();
      printTablename();
      printBlankline();
      printHeading();

   }

   void testall(){

      // test addTable

      String testString1 = "aaa";
      String testString2 = "bbb";
      String testString3 = "ccc";
      String testString4 = "ddd";
      String testString5 = "eee";
      String testString6 = "fff";
      String testString7 = "ggg";
      String testString8 = "hhh";

      Record testRecord1 = new Record();

      testRecord1.record.add(testString1);
      testRecord1.record.add(testString2);
      testRecord1.record.add(testString3);
      testRecord1.record.add(testString4);

      Record testRecord2 = new Record();

      testRecord2.record.add(testString5);
      testRecord2.record.add(testString6);
      testRecord2.record.add(testString7);
      testRecord2.record.add(testString8);

      Table testTable1 = new Table();
      testTable1.table_name = "TABLE1";

      testTable1.table.add(testRecord1);
      testTable1.table.add(testRecord2);

      addTable(testTable1);

      assert(this.database.get(0).table.get(0).record.get(0).equals(testString1));
      assert(this.database.get(0).table.get(0).record.get(1).equals(testString2));
      assert(this.database.get(0).table.get(0).record.get(2).equals(testString3));
      assert(this.database.get(0).table.get(0).record.get(3).equals(testString4));
      assert(this.database.get(0).table.get(1).record.get(0).equals(testString5));
      assert(this.database.get(0).table.get(1).record.get(1).equals(testString6));
      assert(this.database.get(0).table.get(1).record.get(2).equals(testString7));
      assert(this.database.get(0).table.get(1).record.get(3).equals(testString8));

      Table testTable2 = new Table();
      testTable2.table_name = "TABLE2";

      testTable2.table.add(testRecord2);
      testTable2.table.add(testRecord1);

      addTable(testTable2);

      assert(this.database.get(1).table.get(0).record.get(0).equals(testString5));
      assert(this.database.get(1).table.get(0).record.get(1).equals(testString6));
      assert(this.database.get(1).table.get(0).record.get(2).equals(testString7));
      assert(this.database.get(1).table.get(0).record.get(3).equals(testString8));
      assert(this.database.get(1).table.get(1).record.get(0).equals(testString1));
      assert(this.database.get(1).table.get(1).record.get(1).equals(testString2));
      assert(this.database.get(1).table.get(1).record.get(2).equals(testString3));
      assert(this.database.get(1).table.get(1).record.get(3).equals(testString4));

      // test dropTable
      dropTable("TABLE1");

      assert(this.database.get(0).table.get(0).record.get(0).equals(testString5));
      assert(this.database.get(0).table.get(0).record.get(1).equals(testString6));
      assert(this.database.get(0).table.get(0).record.get(2).equals(testString7));
      assert(this.database.get(0).table.get(0).record.get(3).equals(testString8));
      assert(this.database.get(0).table.get(1).record.get(0).equals(testString1));
      assert(this.database.get(0).table.get(1).record.get(1).equals(testString2));
      assert(this.database.get(0).table.get(1).record.get(2).equals(testString3));
      assert(this.database.get(0).table.get(1).record.get(3).equals(testString4));

      System.out.println();
      System.out.println("Database testing passed.");

   }

   void run(String[] args) {

      if (args.length == 0) testall();

      else {
         System.err.println("Use:  java -ea Oxo or java Oxo");
         System.exit(1);
      }

   }

}
