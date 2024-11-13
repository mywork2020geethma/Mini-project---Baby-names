package BabyNamesMiniProject;

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;



/**
 * Mini Project Coursera
 * 
 * @author Geethma 
 * @version 2022/02/10
 */
public class BabyNames {
  
// select file and return total number of names
public void totalNames(){
FileResource fr = new FileResource();
CSVParser parser = fr.getCSVParser(false); //because no header row
int numTot = 0;
int numGirls = 0;
int numBoys = 0;
for (CSVRecord row : parser){
int numBorn = Integer.parseInt(row.get(2)); // get the second column
String gender = row.get(1); //get the second column
numTot ++;
if (gender.equals("F")){
numGirls++; }
if (gender.equals("M")){
numBoys++; }
}
System.out.println("Total baby names: "+numTot+ " | Total female names: "+numGirls+ " | Total male names: " +numBoys);

}

//select file and get the rank of a name given
public int getRank(int year, String name, String gender){
//String fileName = "yob" + year + "short.csv";
//FileResource fr = new FileResource("fileName");
FileResource fr = new FileResource();
CSVParser parser = fr.getCSVParser(false); //because no header row
int rank = 0;
int nameRank = 0 ;
for (CSVRecord row : parser){
    String genderFile = row.get(1);
    String nameFile = row.get(0);
if (genderFile.equals(gender)){
    rank ++;
if (nameFile.equals(name)) 
    nameRank = rank;
}
}

if (nameRank == 0) return -1;

else return nameRank;
}

//open file and get the name of the given rank
public String getName(int year, int rank, String gender){
FileResource fr = new FileResource();
CSVParser parser = fr.getCSVParser(false); //because no header row
int rankFile = 0;
//String name = "";
String nameFile = "";
for (CSVRecord row : parser){
    String genderFile = row.get(1);
if (genderFile.equals(gender)){
    rankFile ++;

if (rankFile == rank){ 
    nameFile = row.get(0);
}
}
}
if (nameFile.length() == 0) return "No Name";
else return nameFile;
}

//name would have been named if they were born in a different year
public String whatIsNameInYear(int oldYear, int newYear, String nameNow, String gender){
int rank = getRank(oldYear, nameNow, gender); //the file which is including current name
String nameOther = getName(newYear, rank , gender); //file where we need to search the new name
return nameOther;
}

//get the rank of a name in a CSV parser
public int rankOfNameinGivenParser(CSVParser parser, String name, String gender){
int rank = 0;
int nameRank = -1;
for (CSVRecord row : parser){
    String genderFile = row.get(1);
if (genderFile.equals(gender)){
    rank ++;
    String nameFile = row.get(0);
if (nameFile.equals(name)) 
    nameRank = rank;
}
}
return nameRank; //if the name is not in parser return -1
}

//the year with the highest rank for the name and gender
public int yearOfHighestRank(String name, String gender){

int highestRank = -1;
String fileName = "";
DirectoryResource dr = new DirectoryResource();
  //itterate over selected files
for (File f : dr.selectedFiles()){
FileResource fr = new FileResource(f);
CSVParser parser = fr.getCSVParser(false);
int rankCurrent = rankOfNameinGivenParser(parser, name,gender);
if (highestRank == -1){
    highestRank = rankCurrent;
    fileName = f.getPath();
}
else if((highestRank > rankCurrent) && (rankCurrent!= -1)){
    highestRank = rankCurrent;
    fileName = f.getPath();
}
}

if (highestRank == -1) {
return -1;
}
else{
int indexYOB = fileName.lastIndexOf("yob");
String year =  fileName.substring(indexYOB+3, indexYOB+7);
return Integer.parseInt(year);
}

}

//average rank of the name and gender over the selected files
public double getAverageRank(String name, String gender){
    
double total = 0;
int count = 0;
DirectoryResource dr = new DirectoryResource();
//itterate over selected files
for (File f : dr.selectedFiles()){
FileResource fr = new FileResource(f);
CSVParser parser = fr.getCSVParser(false);
int rankCurrent = rankOfNameinGivenParser(parser, name,gender);
if (rankCurrent != -1){ 
    total += rankCurrent;
    count ++;
}
}
if (count == 0){
    return -1;
}
else {
    double avg = total / count;
    return avg;
}
}

//total of the births ranked higher than given name
public int getTotalBirthsRankedHigher(int year, String name, String gender){
FileResource fr = new FileResource();
CSVParser parser = fr.getCSVParser(false); //because no header row
int total = 0;
for (CSVRecord row : parser){
String genderFile = row.get(1);
if (genderFile.equals(gender)){
    String nameFile = row.get(0);
    int births = Integer.parseInt(row.get(2));
    total += births;
    if (nameFile.equals(name)){
    total -= births;
    break;
}
}
}
return total;
}

public void testTotalNames(){
totalNames();
}

public void testGetRank(){
System.out.println("Rank of the name: " + getRank(2012 , "Frank" , "M"));
}

public void testGetName(){
System.out.println("Name of the rank: " + getName(2012 , 450 , "M"));
}

public void testWhatIsNameInYear(){
System.out.println("Susan was born in 1972, what would be the name if she were born in 2014:  " +whatIsNameInYear(1972, 2014, "Owen", "M"));    
}

public void testYearOfHighestRank(){
//System.out.println ("Mich is highest ranked in: "+ yearOfHighestRank("Mich" , "M"));
System.out.println ("Genevieve is highest ranked in: "+ yearOfHighestRank("Genevieve" , "F"));
//System.out.println ("Mason is highest ranked in: "+ yearOfHighestRank("Mason" , "M"));
}

public void testGetAverageRank(){
//System.out.println ("average rank of Mason: " + getAverageRank("Mason" , "M"));
//System.out.println ("average rank of Susan: " + getAverageRank("Susan" , "F"));
System.out.println ("average rank of Robert: " + getAverageRank("Robert" , "M"));
}

public void testGetTotalBirthsRankedHigher(){
System.out.println ("total number of births than Ethan: " +getTotalBirthsRankedHigher(2012, "Ethan", "M") );
System.out.println ("total number of births than Emily: " +getTotalBirthsRankedHigher(1990, "Emily", "F") );
System.out.println ("total number of births than Drew: " +getTotalBirthsRankedHigher(1990, "Drew", "M") );
}

}
