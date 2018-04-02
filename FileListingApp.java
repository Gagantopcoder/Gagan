import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class File_listing_app {
	public static void expand(File directory,ArrayList al) {       										//This function is used to explore the directory  
		
		File files[]=directory.listFiles();
		
		for(int a=0;a<files.length;a++) {
			if(files[a].isFile()) {
				al.add(files[a].getName()+","+files[a].getAbsolutePath());
			}
			if(files[a].isDirectory()) {
				al.add(files[a].getName()+","+files[a].getAbsolutePath());
				expand(files[a],al);
			}
		
		}	 
	}
	
	public static void main(String[] args) {
		try { 
			Scanner scan =new Scanner(System.in);  
			System.out.println("Enter the Location of the file :");
			String location=scan.nextLine();
			File file=new File(location);																	  //This is the text file which contains the two urls
			String directory_to_search="";
			String csv_file=""; 
			FileReader in = new FileReader(file);
			int current_char ;
				  while((current_char =  in.read())!=10) {
					   char ch = (char)current_char;
					   directory_to_search = directory_to_search + ch; 
				  }
	
				  File dir =new File(directory_to_search.substring(0, directory_to_search.length()-1));   //This is the directory which have been provided by the file
				  while((current_char =  in.read())!=-1) {
					   char ch = (char)current_char;
					   csv_file = csv_file + ch;    
				  } 
				  File output = new File(csv_file);
				  ArrayList al=new ArrayList();
				  PrintStream writer =new PrintStream(output); 
				  expand(dir,al);																		//Here I recieved the arraylist which has been edited by the 
				  																						//expand method
				  Iterator irt =al.iterator();
					while(irt.hasNext()) {
						writer.println((String)irt.next());
					}
				  
		} catch (Exception e) {
			System.out.println("The location you have entered doesn't have the the file mentioned");  
		}
		
	}

}