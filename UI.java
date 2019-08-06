import java.util.Scanner;
import java.util.ArrayList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.FileReader;
import java.io.BufferedReader;

class UI{

   // An arraylist that stores each table of the database
   ArrayList<Database> ui;
   // The number of databases
   int num_of_database;
   // The max length of database name
   int max_databasenamelength;
   // The index of the database selected
   int selected_database_index;

   // 2-space padding for printing
   final int padding = 2;

   UI(){

      this.ui = new ArrayList<Database>();
      this.num_of_database = 0;
      this.max_databasenamelength = 0;

   }

   // Check if the new database name already exists
   Boolean checkDatabasename(String name){

      for(int i = 0; i < this.ui.size(); i++){
         if(this.ui.get(i).database_name.equals(name)){
            return false;
         }
      }
      return true;

   }

   void printMainmenu(){

      System.out.println();
      System.out.println("MAIN MENU: ");
      System.out.println("1. VIEW ALL");
      System.out.println("2. CREATE DATABASE");
      System.out.println("3. SELECT DATABASE");
      System.out.println("4. EXIT\n");

   }

   void useMainmenu()throws IOException{

     int n = 0;

     while(n!=4){

     this.printMainmenu();

     Scanner scanner = new Scanner(System.in);
     n = scanner.nextInt();

     if(n == 1){

        this.printDatabase();

     }

     else if(n == 2){

        System.out.println("Set the name of the database:");
        Scanner scanner2 = new Scanner(System.in);
        String tempDatabasename = scanner2.next();
        if(this.checkDatabasename(tempDatabasename) == true){
           Database d = new Database(tempDatabasename);
           this.ui.add(d);
           System.out.println("Database " + tempDatabasename + " created.");
        }
        else{
           System.out.println("Error: database creation failed. "+
"A database with this name already exists. "+
"Please try another name.");
        }

     }

     else if(n==3){

        System.out.println("Eneter the name of the database you want to select:");
        Scanner scanner3 = new Scanner(System.in);
        String selectedDatabasename = scanner3.next();
        int selection = 0;
        for(int i = 0; i < this.ui.size(); i++){
           if(this.ui.get(i).database_name.equals(selectedDatabasename)){
              this.selected_database_index = i;
              selection ++;
           }
        }
        if(selection == 1){
           System.out.println("The database selected is: "+this.ui.get(this.selected_database_index).database_name);
           useDatabasemenu();
        }
        else{
           System.out.println("Error: Database not found.");
        }
     }

     else if(n==4){
          System.out.println("Exit.");
     }

     else{
           System.out.println("Error: command not found. Please enter again.");
     }

  }

}

   void printDatabasemenu(){

      System.out.println();
      System.out.println("CURRENT LOCATION:DATABASE "+
this.ui.get(this.selected_database_index).database_name+"/");
      System.out.println("DATABASE MENU: ");
      System.out.println("1. VIEW DATABASE");
      System.out.println("2. DELETE DATABASE");
      System.out.println("3. CREATE TABLE");
      System.out.println("4. SELECT TABLE");
      System.out.println("5. READ TABLE FROM FILE");
      System.out.println("6. BACK\n");

   }

   void useDatabasemenu() throws IOException{

     int n = 0;

     while(n != 6){

        this.printDatabasemenu();

        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();

        if(n == 1){

           this.ui.get(selected_database_index).printDatabase();

        }

        else if(n == 2){

          this.ui.remove(selected_database_index);
          System.out.println("The database is deleted.");
          this.selected_database_index = 0;
          n = 6;

        }

        else if(n == 3){

          System.out.println("Set the name of the table:");
          Scanner scanner3 = new Scanner(System.in);
          String tempTablename = scanner3.next();

          if(this.ui.get(selected_database_index).checkTablename(tempTablename) == true){
             Table t = new Table(tempTablename);

             this.ui.get(selected_database_index).addTable(t);
             System.out.println("Table " + tempTablename + " created.");
          }
          else{
             System.out.println("Error: table creation failed. "+
  "A table with this name already exists. "+
  "Please try another name.");
          }

        }

        else if(n == 4){

           System.out.println("Eneter the name of the table you want to select:");
           Scanner scanner4 = new Scanner(System.in);
           String selectedTablename = scanner4.next();
           int table_selection = 0;

           for(int i = 0; i < this.ui.get(this.selected_database_index).database.size(); i++){
              if(this.ui.get(this.selected_database_index).database.get(this.ui.get(this.selected_database_index).selected_table_index).table_name
.equals(selectedTablename)){
                 this.ui.get(this.selected_database_index).selected_table_index = i;
                 table_selection ++;
              }
           }

           if(table_selection == 1){
              System.out.println("The table selected is: "+
this.ui.get(this.selected_database_index).database.get(this.ui.get(this.selected_database_index).selected_table_index).table_name);
              useTablemenu();
           }
           else{
              System.out.println("Error: Table not found.");
           }

        }

        else if(n == 5){

          System.out.println("Eneter the name of file you want to read:");
          Scanner scanner5 = new Scanner(System.in);
          String selectedFilename = scanner5.next();
          File tempfile = new File(selectedFilename+".txt");
          if(tempfile.canRead()){
             try{

                Table t = new Table(selectedFilename, 0);
                this.ui.get(this.selected_database_index).database.add(t);
                System.out.println("THE SIZE OF DATABASE IS:" + this.ui.get(this.selected_database_index).database.size());
                int temptableindex = this.ui.get(this.selected_database_index).database.size()-1;
                this.ui.get(this.selected_database_index).database.get(temptableindex).readTable(tempfile);

             } catch(IOException a){throw a;}
          }
          else{
             System.out.println("File not readable.");
          }
        }

        else if(n == 6){
              System.out.println("Back to previous directory.");
        }

        else{
              System.out.println("Error: command not found. Please enter again.");
        }

     }

   }

   void printTablemenu() {

      System.out.println();
      System.out.println("CURRENT LOCATION:DATABASE "+
this.ui.get(this.selected_database_index).database_name+"/TABLE "+
this.ui.get(this.selected_database_index).database.get(this.ui.get(this.selected_database_index).selected_table_index).table_name+
"/");
      System.out.println("TABLE MENU: ");
      System.out.println("1. VIEW TABLE");
      System.out.println("2. DELETE TABLE");
      System.out.println("3. CREATE RECORD");
      System.out.println("4. SELECT RECORD");
      System.out.println("5. WRITE TABLE TO FILE");
      System.out.println("6. BACK\n");

   }

   void useTablemenu() throws IOException{

      int n = 0;

      while(n!=6){

         this.printTablemenu();

         Scanner scanner = new Scanner(System.in);
         n = scanner.nextInt();

         int temp_table_index = this.ui.get(selected_database_index).selected_table_index;

         if(n == 1){

            this.ui.get(selected_database_index).database.get(temp_table_index).printTable();

         }

         else if(n == 2){

            this.ui.get(selected_database_index).database.remove(temp_table_index);
            n = 6;

         }

         else if(n == 3){

            int temp_numoffeatures = this.ui.get(selected_database_index).database.get(temp_table_index).features.size();
            Record r = new Record(temp_numoffeatures);
            this.ui.get(selected_database_index).database.get(temp_table_index).insertRecord(r);

         }

         else if(n == 4){

            System.out.println("Enter the index of record you want to select.");
            Scanner scanner2 = new Scanner(System.in);
            int temp_record_index = scanner2.nextInt();

            if(temp_record_index > (this.ui.get(selected_database_index).database.get(temp_table_index).table.size()-1)){
               System.out.println("Error: such a record does not exist.");
               n = 6;
            }
            else{
               this.ui.get(selected_database_index).database.get(temp_table_index).selected_record_index = temp_record_index;
               useRecordmenu();
            }

         }

         else if(n == 5){

            this.ui.get(selected_database_index).database.get(temp_table_index).writeTable();
            System.out.println("The table is written to the file "+
            this.ui.get(selected_database_index).database.get(temp_table_index).table_name+".txt");

         }

         else if(n == 6){

               System.out.println("Back to previous directory.");

         }

         else{

               System.out.println("Error: command not found. Please enter again.");

         }

      }

   }

   void printRecordmenu(){

      System.out.println();
      System.out.println("RECORD MENU: ");
      System.out.println("1. VIEW RECORD");
      System.out.println("2. DELETE RECORD");
      System.out.println("3. UPDATE RECORD");
      System.out.println("4. BACK\n");

   }

   void useRecordmenu(){

      int n = 0;

      while(n!=4){

         this.printRecordmenu();

         Scanner scanner = new Scanner(System.in);
         n = scanner.nextInt();

         int temp_table_index = this.ui.get(selected_database_index).selected_table_index;
         int temp_record_index = this.ui.get(selected_database_index).database.get(temp_table_index).selected_record_index;

         if(n == 1){

            this.ui.get(selected_database_index).database.get(temp_table_index).table.get(temp_record_index).printRecord();

         }

         else if(n == 2){

           this.ui.get(selected_database_index).database.get(temp_table_index).deleteRecord(temp_record_index);
           n = 4;

         }

         else if(n == 3){

            this.ui.get(selected_database_index).database.get(temp_table_index).updateRecord(temp_record_index);

         }

         else if(n == 4){

               System.out.println("Back to previous directory.");

         }

         else{

               System.out.println("Error: command not found. Please enter again.");

         }


      }

   }

   // PRINTING
   // get the maximum table name length
   void getDatabasenamelength(){

      max_databasenamelength = 0;

      for(int i = 0; i < ui.size(); i++){
         if(max_databasenamelength < ui.get(i).database_name.length()){
            max_databasenamelength = ui.get(i).database_name.length();
         }
      }

   }

   // Print the heading
   void printHeading(){

      int headinglength = max_databasenamelength + (padding+1)*2;

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

   void printDatabaseNames(){

      int left_paddinglength;
      int right_paddinglength;

      for(int i = 0; i < ui.size(); i++){

         if((max_databasenamelength - ui.get(i).database_name.length()) % 2 == 0){
            left_paddinglength = right_paddinglength = (max_databasenamelength - ui.get(i).database_name.length()) / 2;
         }
         else{
            left_paddinglength = (max_databasenamelength - ui.get(i).database_name.length() + 1) / 2;
            right_paddinglength = max_databasenamelength - left_paddinglength - ui.get(i).database_name.length();
         }

         printColumn();
         printSpace(padding+left_paddinglength);
         System.out.print(ui.get(i).database_name);
         printSpace(padding+right_paddinglength);
         printColumn();
         System.out.println();

      }

   }

   //Print a blank line
   void printBlankline(){

      printColumn();
      printSpace(padding);
      printSpace(max_databasenamelength);
      printSpace(padding);
      printColumn();
      System.out.print("\n");

   }


   //Print database
   void printDatabase(){

      getDatabasenamelength();
      printHeading();
      printBlankline();
      printDatabaseNames();
      printBlankline();
      printHeading();

   }

   void testall(){

     // test addDatabase
     Database testDatabase1 = new Database();
     testDatabase1.database_name = "D1";

     Database testDatabase2 = new Database();
     testDatabase2.database_name = "D2";

     this.ui.add(testDatabase1);
     this.ui.add(testDatabase2);

     this.ui.get(0).database_name.equals("D1");
     this.ui.get(1).database_name.equals("D2");

     // test deleteDatabase

     this.ui.remove(testDatabase1);
     this.ui.get(0).database_name.equals("D2");

     System.out.println();
     System.out.println("UI testing passed.");

   }

   void run(String[] args) {

      if (args.length == 0) testall();
      else {
         System.err.println("Use:  java -ea Oxo or java Oxo");
         System.exit(1);
      }
   }

   public static void main(String[] args) {

      System.out.println("------------------------");
      System.out.println("TESTING START");
      System.out.println("------------------------");
      Record testRecord = new Record();
      testRecord.run(args);
      System.out.println("------------------------");
      Table testTable = new Table();
      testTable.run(args);
      System.out.println("------------------------");
      Database testDatabase = new Database();
      testDatabase.run(args);
      System.out.println("------------------------");
      UI testUI = new UI();
      testUI.run(args);
      System.out.println("------------------------");
      System.out.println("TESTING DONE");
      System.out.println("------------------------");

      UI a = new UI();
      try{a.useMainmenu();} catch(IOException e){} finally{
      }

   }

}
