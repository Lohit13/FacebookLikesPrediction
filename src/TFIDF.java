import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TFIDF {
	static String msgs;
	
	static List<String> posts = new ArrayList<String>();
	
	static{
		msgs = "";
	}
	
	//Reads all the post content
	public static void readAllMsgs(String name){
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
			//Age
			line = reader.readLine();
			//No. of friends
			line = reader.readLine();
			//Faltu heading line
			line = reader.readLine();
			while( (line=reader.readLine())!=null ){
				String[] parts = line.split("~");
				msgs += parts[5] + "\n";
				posts.add(parts[5]);
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
	
	public static double tf(String word, String msg){
		int count = 0;
		String words[] = msg.split(" ");
		for(String w : words){
			if(w.equalsIgnoreCase(word)){
				count+=1;
			}
		}
		return count/words.length;
	}
	
	public static int n_contain(String word){
		int count = 0;
		
		for(String post : posts){
			String words[] = post.split(" ");
			for(String w : words){
				if(w.equalsIgnoreCase(word)){
					count+=1;
					break;
				}
			}
		}
		
		return count;
	}
	
	public static double idf(String word){
		return Math.log((double)posts.size()/(1+n_contain(word)));
	}
	
	public static double tfidf(String word, String msg){
		return tf(word,msg) + idf(word);
	}
	
	
}
