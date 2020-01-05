package UI.src.com.wordsentiment;

public class SentimentItem {
	public String IdStr;
	public String Text;
	public String Lang;
	public Double Longtitude;
	public Double Latitude;
	public Integer Sentiment;
	
	public SentimentItem(String idstr, String text, String lang, Double longtitude, Double latitude, Integer sentiment)
	{
		this.IdStr = idstr;
		this.Text = text;
		this.Lang = lang;
		this.Longtitude = longtitude;
		this.Latitude = latitude;
		this.Sentiment = sentiment;
	}
	
	public String ToJSON()
	{
		return "{ \"IdStr\" : \"" + IdStr + "\"," +
				 "\"Text\" : \"" + Text + "\"," +
				 "\"Lang\" : \"" + Lang + "\"," +
				 "\"Longtitude\" : " + Longtitude + "," +
				 "\"Latitude\" : " + Latitude + "," +
				 "\"Sentiment\" : " + Sentiment + " }";
	}	
}
