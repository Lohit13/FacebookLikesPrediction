import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.restfb.types.Post;
import com.restfb.types.User;

public class Fetcher {

	static String fetch(String token)
	{

		FacebookClient facebookClient = new DefaultFacebookClient(token);
		

		Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class);
		User user = facebookClient.fetchObject("me", User.class);
		String myname = user.getName();
		
		JsonObject obj = facebookClient.fetchObject("me/friends", JsonObject.class);

		Long totalCount = null;
		if (obj.has("summary")) {
		     totalCount = obj.getJsonObject("summary").getLong("total_count");
		}
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(".\\src\\" + user.getFirstName() + ".csv",false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String line = "";
		if (user.getGender().equals("male"))
			line += "M\n";
		else
			line += "F\n";
		
		String year = user.getBirthday().substring(6);
		
		line += (Integer.toString(2015 - Integer.valueOf(year)) + "\n");
		line += (totalCount + "\n");
		line += "TimeofCreation~ShareCount~PrivacyType~TypeofPost~MSG\n";
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		String dateInString = "31-Dec-2014";
		Date date = null;

		try {

			date = formatter.parse(dateInString);
			System.out.println(date);
			System.out.println(formatter.format(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int ctr=0;
		
		for (List<Post> myFeedConnectionPage : myFeed){
			if(ctr==1){break;}
			  for (Post post : myFeedConnectionPage){
				  if(ctr==1){break;}
				  if(post.getFrom().getName().equals(myname)){
					  if (post.getMessage() != null){
						  
						  String cleanMessage = post.getMessage().replace("\n", "").replace("\r", "");
						  JsonObject jsonObject = facebookClient.fetchObject(post.getId() + "/likes",
					                        JsonObject.class, Parameter.with("summary", true),
					                        Parameter.with("limit", 1));
					        long count = jsonObject.getJsonObject("summary").getLong(
					                "total_count");
						  
						  line += (post.getCreatedTime() +  "~" + post.getSharesCount() + "~" + count + "~" + post.getPrivacy().getDescription() + "~" + post.getType() + "~" + cleanMessage + "\n");
						  System.out.println(post);
					  }
					  if(post.getCreatedTime().before(date)){
						  ctr=1;
					  }
				  }
			  }
		}
		
		try {
			writer.write(line);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		 return line;
	}
}
