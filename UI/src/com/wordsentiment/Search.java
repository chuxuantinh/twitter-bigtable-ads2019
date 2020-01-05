package UI.src.com.wordsentiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String keyword = request.getParameter("keyword").trim();
		List<com.wordsentiment.SentimentItem> data = GetData(keyword);
		String result = SentimentArrayToJSON(data); 
		
		response.getWriter().append(result);
	}
	
	private String SentimentArrayToJSON(List<com.wordsentiment.SentimentItem> list)
	{
		String result = ""; 
		for (com.wordsentiment.SentimentItem item : list)
		{
			if (result.length() == 0)
			{
				result = item.ToJSON();
			}
			else
			{
				result += "," + item.ToJSON();
			}
		}
		result = "[" + result + "]";
		return result;
	}

	private List<com.wordsentiment.SentimentItem> GetData(String keyword)
	{
		List<com.wordsentiment.SentimentItem> result = new ArrayList<com.wordsentiment.SentimentItem>();
		result.add(new com.wordsentiment.SentimentItem("1234560", "abc", "en", -6.711458, 31.171311, 4));
		result.add(new com.wordsentiment.SentimentItem("1234561", "abc", "en", -79.371005, 37.995937, 4));
		result.add(new com.wordsentiment.SentimentItem("1234562", "abc", "en", -6.421784, 22.917660, 0));
		result.add(new com.wordsentiment.SentimentItem("1234563", "abc", "en", 10.277435, 29.992755, 4));
		result.add(new com.wordsentiment.SentimentItem("1234564", "abc", "en", -77.085848, 48.341456, 4));
		result.add(new com.wordsentiment.SentimentItem("1234565", "abc", "en", -95.894443, 32.398275, 2));
		result.add(new com.wordsentiment.SentimentItem("1234566", "abc", "en", -52.124910, -3.513706, 2));
		result.add(new com.wordsentiment.SentimentItem("1234567", "abc", "en", 2.191497, 43.196959, 4));
		result.add(new com.wordsentiment.SentimentItem("1234568", "abc", "en", 25.570404, 20.632517, 0));
		result.add(new com.wordsentiment.SentimentItem("1234569", "abc", "en", -67.593661, -0.879157, 0));
		result.add(new com.wordsentiment.SentimentItem("1234570", "abc", "en", 40.160248, 46.679398, 4));
		
		return result;				
	}
}
