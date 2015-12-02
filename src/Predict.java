

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.WebRequestor;
import com.restfb.types.Post;
import com.restfb.types.User;

/**
 * Servlet implementation class Predict
 */
@WebServlet("/Predict")
public class Predict extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	private FacebookClient.AccessToken getFacebookUserToken(String code, String redirectUrl) throws IOException {
	    String appId = "1695525277328506";
	    String secretKey = "8ff2fbd2ec24c1d327a769af6c0752b2";

	    WebRequestor wr = new DefaultWebRequestor();
	    WebRequestor.Response accessTokenResponse = wr.executeGet(
	            "https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&redirect_uri=" + redirectUrl
	            + "&client_secret=" + secretKey + "&code=" + code);

	    return DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody());
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Predict() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String code = request.getParameter("code");
		String redirectUrl = "http://localhost:8080/FacebookLikesPrediction/Predict";

		
		FacebookClient.AccessToken token = getFacebookUserToken(code, redirectUrl);
		String accessToken = token.getAccessToken();
		
		accessToken = "CAACEdEose0cBAKnUa3k0ls6jzCs0fY6FiUKz2M8AM9hQgJruLRQlA1V4ORHZCeLijQeGcdjubLr6JKDIBJnNsUk5oj9bpvEvjnLiMzwdzNwEWHhKUGOuDuNj5alCX0DYT5gAHsDi5iLzt4WgbKLOicX4HZC0SjpaRjv6T9W6pj4fNAPNR0u11U5GW7MjhCSBVhEpV3hgZDZD";
		String name = Fetcher.fetch(accessToken);
		
		Parse.makeFeatures(name);
		
		response.getWriter().write(name);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}



