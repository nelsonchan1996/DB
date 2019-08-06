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

import java.util.HashMap;
import java.util.Map;

class Table{

   // An arraylist that stores each record of the table
   ArrayList<Record> table;
   // An arraylist that stores each field names of the table
   ArrayList<String> features;
   // An arraylist that stores composite primary key
   ArrayList<String> composite_key;
   // A Hashmap that stores both the primary key name and its index
   HashMap<String, Integer> composite_key_index;
   // An array that stores the maxwidth of each column
   int[] column_width;

   // A 2-D array that stores the content scanned from a readable file
   String[][] file_content;

   // the name of the table
   String table_name;

   // the index of current record
   int current_record_index;
   // the index of  the selected record
   int selected_record_index;
   // the number of features
   int num_of_features;

   // 2-space padding for printing
   final int padding = 2;

   // CONSTRUCTOR
   // Trivial Constructor
   Table(){

     this.table = new ArrayList<Record>();
     this.table_name = "";
     this.current_record_index = 0;
     this.selected_record_index = 0;
     this.num_of_features = 0;
     this.features = new ArrayList<String>();
     this.composite_key = new ArrayList<String>();
     this.composite_key_index = new HashMap<String, Integer>();

   }

   // Constructor that initialise the table
   Table(String table_name){

      this.table = new ArrayList<Record>();
      this.table_name = table_name;
      this.current_record_index = 0;
      this.selected_record_index = 0;
      this.num_of_features = 0;
      this.features = new ArrayList<String>();
      this.composite_key = new ArrayList<String>();
      this.composite_key_index = new HashMap<String, Integer>();
      this.setFeatures();
      this.setCompositePrimarykey();

   }

   // Constructor that initialise the table read from file
   Table(String table_name, int Type){

     this.table = new ArrayList<Record>();
     this.table_name = table_name;
     this.current_record_index = 0;
     this.selected_record_index = 0;
     this.num_of_features = 0;
     this.features = new ArrayList<String>();
     this.composite_key = new ArrayList<String>();
     this.composite_key_index = new HashMap<String, Integer>();

   }

   // RECORDS
   // Insert a record to the table arrayList
   void insertRecord(Record r){

      if(checkCompositePrimarykeyConstraint(r)==true){
         table.add(r);
         current_record_index++;
         System.out.println("Record inserted.");
      }
      else{
         System.out.println("Error: Record insertion failed."+
"The record violates the primary key constraint.");
      }

   }

   // Update the content of a record
   void updateRecord(int index){

     table.get(index).record.clear();
     System.out.print("INPUT THE NEW RECORD(NOTE: "+
"use space to seperate elements and '/'+ENTER to finish):\n");
     Scanner scanner = new Scanner(System.in);

     while(!scanner.hasNext("/")){
        table.get(index).record.add(scanner.next());
     }


   }

   // Delete a record from the table arraylist
   void deleteRecord(int index){

      if(index > table.size()){

         System.out.println("Error: Record deletion failed."+
"Record with this index does not exist.");

      }

      else{
         table.remove(index);
         System.out.println("Record deleted.");
      }

   }

   // Select a record by setting the selection index
   void selectRecord(){

      System.out.print("INPUT THE INDEX OF THE RECORD YOU WANT TO SELECT:\n");
      Scanner scanner = new Scanner(System.in);

      this.selected_record_index = scanner.nextInt();
      System.out.print("The record selected is: ");
      for(int i = 0; i < table.get(this.selected_record_index).record.size(); i++){

      System.out.print(table.get(this.selected_record_index).record.get(i)+" ");

      }
      System.out.println();

   }

   // FEATURES
   // Set the features(field names) and the number of features
   void setFeatures(){

      System.out.println("enter field names:(NOTE: "+
"use space to seperate elements and '/'+ENTER to finish):");
      Scanner scanner = new Scanner(System.in);

      while(!scanner.hasNext("/")){
         features.add(scanner.next());
      }

      this.num_of_features = features.size();

   }

   // KEY
   // Set the primary key
   void setCompositePrimarykey(){

      System.out.print("INPUT THE FEATURES TO BE SET AS PRIMARY KEYS(NOTE: "+
"use space to seperate elements and '/'+ENTER to finish):\n");
      Scanner scanner = new Scanner(System.in);

      while(!scanner.hasNext("/")){
         composite_key.add(scanner.next());
      }

      if(checkCompositePrimarykey()==true){
         System.out.println("The primary key are:");
         for(int n = 0; n < composite_key.size(); n++){
            System.out.print(composite_key.get(n)+" ");
         }
      }
      else{
         System.out.println("Primary allocation failed. "
+"Repeated key values not allowed. "
+"Please choose another feature or features.");
         for(int n = 0; n < composite_key.size(); n++){
            composite_key.remove(n);
         }
      }

   }

   // get composite primary key index
   // and stores it in a hashmap
   void getPrimarykeyindex(){

      for(int i = 0; i < composite_key.size(); i++){
         for(int j = 0; j < features.size(); j++){
            if(composite_key.get(i).equals(features.get(j))){
               composite_key_index.put(composite_key.get(i).toString(), j);
            }
         }
      }

      System.out.println(composite_key_index);

   }

   // Check whether the potential composite primary key fulfills the requirement
   Boolean checkCompositePrimarykey(){

      getPrimarykeyindex();

      String[] tempString = new String[table.size()];

      int n;

      for(int i = 0; i < table.size(); i++){
         tempString[i] = null;
         n = 0;

         while(n < this.composite_key.size()){
            tempString[i] = tempString[i] + table.get(i).record.get(composite_key_index.get(composite_key.get(n)));
            n++;
         }
         System.out.println(tempString[i]);
      }


      for(int j = 0; j < table.size(); j++){
         for(int k = j + 1; k < table.size(); k++){
            if(tempString[j].equals(tempString[k])){
               return false;
            }
         }
      }

      return true;

   }

   // Check whether the record to be inserted fulfills the primary constraint
   Boolean checkCompositePrimarykeyConstraint(Record r){

      String[] tempString = new String[table.size()];
      String tempnewString = null;
      int n;

      for(int i = 0; i < table.size(); i++){
         tempString[i] = null;
         n = 0;

         while(n < this.composite_key.size()){
            tempString[i] = tempString[i] + table.get(i).record.get(composite_key_index.get(composite_key.get(n)));
            n++;
         }

      }

      int t = 0;
      while(t < this.composite_key.size()){
         tempnewString = tempnewString + r.record.get(t);
         t++;
      }

      for(int l = 0; l < table.size(); l++){
         if(tempnewString.equals(tempString[l])){
            return false;
         }
      }
      return true;

   }

   // PRINTING
   // get the maximum width of each column
   void getColumnwidth(){

      // initialise the array
      this.column_width = new int [features.size()];
      // set the length of each feature as the initial width
      for(int k = 0; k < features.size(); k++){
         int temp = (features.get(k)).length();
         column_width[k] = temp;
      }

      for(int j = 0; j < table.size(); j++) {
         for(int i = 0; i < table.get(j).record.size(); i++) {
            if(column_width[i] < table.get(j).record.get(i).toString().length()){
               column_width[i] = table.get(j).record.get(i).toString().length();
            }
         }
      }

      // add padding to the column width
      for(int k = 0; k < features.size(); k++){
         column_width[k] = column_width[k]+padding*2;
      }

   }

   // Print the heading
   void printHeading(){

      int sum = 0;
      for(int k = 0; k < features.size(); k++){
         sum = sum + column_width[k];
      }
      int headinglength = sum + (features.size()+1);
      for(int i = 0; i < headinglength; i++){
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

   // Print the features(field names/attributes)
   void printFeatures(){

      int left_paddinglength;
      int right_paddinglength;

      printHeading();
      printColumn();

      for(int i = 0; i < features.size(); i++) {

         if((column_width[i]-features.get(i).toString().length())%2 == 0){
            left_paddinglength = (column_width[i] - features.get(i).toString().length())/2;
            right_paddinglength = column_width[i] - features.get(i).toString().length() - left_paddinglength;
         }
         else{
            left_paddinglength = (column_width[i] - features.get(i).toString().length() + 1)/2;
            right_paddinglength = column_width[i] - features.get(i).toString().length() - left_paddinglength;
         }

         printSpace(left_paddinglength);
         System.out.print(features.get(i));
         printSpace(right_paddinglength);
         printColumn();

      }
      System.out.print("\n");
      printHeading();

   }

   // Print record
   void printRecord(){

      int left_paddinglength;
      int right_paddinglength;

      for(int j = 0; j < table.size(); j++) {
         printColumn();
         for(int i = 0; i < ((table.get(j)).record).size(); i++) {
            if((column_width[i] - table.get(j).record.get(i).toString().length()) % 2 == 0){
               left_paddinglength = (column_width[i] - table.get(j).record.get(i).toString().length()) / 2;
               right_paddinglength = column_width[i] - table.get(j).record.get(i).toString().length() - left_paddinglength;
            }
            else{
               left_paddinglength = (column_width[i] - table.get(j).record.get(i).toString().length() + 1) / 2;
               right_paddinglength = column_width[i] - table.get(j).record.get(i).toString().length() - left_paddinglength;
            }
            printSpace(left_paddinglength);
            System.out.print(table.get(j).record.get(i));
            printSpace(right_paddinglength);
            printColumn();
         }
         System.out.print("\n");
      }

   }

   // Print the table
   void printTable(){

      getColumnwidth();
      printFeatures();
      printRecord();
      printHeading();

   }

   // FILE
   // Read the table from the file
   void readTable(File file) throws IOException {

      BufferedReader br = null;
      FileReader fr = null;

      try{

        fr = new FileReader(file);
        br = new BufferedReader(fr);

        int numofrows = 0;
        int numofcols = 0;
        int cnt = 0;
        String fieldnames;
        String tempFieldname;
        String sCurrentLine;

        fieldnames = br.readLine();

        Scanner scanner = new Scanner(fieldnames);

        while(scanner.hasNext()){
           tempFieldname = scanner.next();
           features.add(tempFieldname);
           numofcols ++;
        }
        System.out.println("numofcols: "+numofcols);

        while ((sCurrentLine = br.readLine()) != null) {
           Record a = new Record();
           Scanner scanner2 = new Scanner(sCurrentLine);
           while(cnt < numofcols){
              a.record.add(scanner2.next());
              cnt ++;
           }
           cnt = 0;
           table.add(a);
           System.out.println(sCurrentLine);
           numofrows ++;

        }

        System.out.println("numofrows: "+numofrows);

        } catch (IOException e) {

        System.out.println("Problem reading from file.");
        throw e;

      }

   }

   // Write the table to the file
   void writeTable() throws IOException {

       try {
           //Whatever the file path is.
           File statText = new File(table_name+".txt");
           FileOutputStream is = new FileOutputStream(statText);
           OutputStreamWriter osw = new OutputStreamWriter(is);
           Writer w = new BufferedWriter(osw);

           for(int i = 0; i < features.size(); i++) {
              w.write(features.get(i));
              w.write(" ");
           }
           w.write("\n");

           for(int j = 0; j < table.size(); j ++){
              for(int i = 0; i < ((table.get(j)).record).size(); i++){
                 w.write(((table.get(j)).record).get(i)+" ");
              }
              w.write("\n");
           }
           w.close();

       } catch (IOException e) {
           System.out.println("Problem writing to the file.");
           throw e;
       }

   }

   void testall(){

      //test Constructor
      assert(this.current_record_index == 0);
      assert(this.selected_record_index == 0);
      assert(this.num_of_features == 0);

      // test insertTable()
      Record testRecord1 = new Record();

      String testString1 = "aaa";
      String testString2 = "bbb";
      String testString3 = "ccc";
      String testString4 = "ddd";
      String testString5 = "eee";
      String testString6 = "fff";
      String testString7 = "ggg";
      String testString8 = "hhh";

      testRecord1.record.add(testString1);
      testRecord1.record.add(testString2);
      testRecord1.record.add(testString3);
      testRecord1.record.add(testString4);

      this.insertRecord(testRecord1);

      assert(this.table.get(0).record.get(0).equals(testString1));
      assert(this.table.get(0).record.get(1).equals(testString2));
      assert(this.table.get(0).record.get(2).equals(testString3));
      assert(this.table.get(0).record.get(3).equals(testString4));

      Record testRecord2 = new Record();

      testRecord2.record.add(testString5);
      testRecord2.record.add(testString6);
      testRecord2.record.add(testString7);
      testRecord2.record.add(testString8);

      this.table.add(testRecord2);

      assert(this.table.get(1).record.get(0).equals(testString5));
      assert(this.table.get(1).record.get(1).equals(testString6));
      assert(this.table.get(1).record.get(2).equals(testString7));
      assert(this.table.get(1).record.get(3).equals(testString8));

      // test deleteTable()

      this.deleteRecord(0);

      assert(this.table.get(0).record.get(0).equals(testString5));
      assert(this.table.get(0).record.get(1).equals(testString6));
      assert(this.table.get(0).record.get(2).equals(testString7));
      assert(this.table.get(0).record.get(3).equals(testString8));
      System.out.println();
      System.out.println("Table testing passed.");

   }

   void run(String[] args) {

      if (args.length == 0) testall();
      else {
         System.err.println("Use:  java -ea Oxo or java Oxo");
         System.exit(1);
      }
   }

}
