import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

public class Parse {
	
	static String name;
	static String msgs;
	
	static List<Row> matrix = new ArrayList<Row>();
	
	
	static int getPrivacy(String privacy){
		if(privacy.equals("Public")){
			return 0;
		}
		if(privacy.equals("Friends")){
			return 1;
		}
		if(privacy.equals("Your Friends")){
			return 2;
		}
		if(privacy.equals("Friends of Friends")){
			return 3;
		}
		return 0;
	}
	
	static int getType(String type){
		if(type.equals("link")){
			return 0;
		}
		if(type.equals("photo")){
			return 1;
		}
		if(type.equals("event")){
			return 2;
		}
		if(type.equals("video")){
			return 3;
		}
		return 0;
	}
	
	static double getWordPower(String msg){
		double wp = 0;
		
		String[] words = msg.split(" ");
		
		for(String word : words){
			wp += TFIDF.tfidf(word, msg);
		}
		wp = Math.floor(wp * 10000) / 10000;
		return wp;
	}
	
	
	public static void readAll(){
		
		int gender = 0, age = 0, nof = 0;
		
		BufferedReader reader=null;

		try {
			String loc = ".\\" + name + ".csv";
			reader = new BufferedReader(new FileReader(loc));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line = "";
		
		try{
			//Gender
			line = reader.readLine();
			if (line.equals("M")){
				gender = 0;
			}
			if (line.equals("F")){
				gender = 1;
			}
			//Age
			line = reader.readLine();
			age = Integer.valueOf(line);
			//No. of friends
			line = reader.readLine();
			nof = Integer.valueOf(line);
			
			//Faltu heading line
			line = reader.readLine();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			while( (line=reader.readLine())!=null ){
				String[] parts = line.split("~");
				//time
				int time = Integer.valueOf(parts[0].substring(parts[0].length()-17, parts[0].length()-15));
				//sharecount
				int sharec = Integer.valueOf(parts[1]);
				//Likes
				int likes = Integer.valueOf(parts[2]);
				//Privacy
				int privacy = getPrivacy(parts[3]);
				//Type
				int type = getType(parts[4]);
				//Wordpower
				double wp = getWordPower(parts[5]);
				Row row = new Row(gender,age,nof,time,sharec,privacy,type,wp,likes);
				matrix.add(row);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			reader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void writeAll(){
		FileWriter writer = null;
		try {
			String loc = ".\\" + name + "_features.csv";
			writer = new FileWriter(loc,false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String line = "";
		
		for(Row row : matrix){
			line += row.gender + "," + row.age + "," + row.nof + "," + row.time + "," + row.sharec + "," + row.privacy + "," + row.type + "," + row.wordpower + "," + row.likes + "\n";
		}
		
		try {
			writer.write(line);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		name = "Kush";
		
		TFIDF.readAllMsgs(name);
		
		readAll();
		writeAll();
	}
}
