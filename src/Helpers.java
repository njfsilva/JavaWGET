import java.util.ArrayList;


public class Helpers {
	
	static PageToDownload ProcessURL(String url)
	{
		PageToDownload newPage = new PageToDownload();
		
		newPage.setURL(url);
		
		String[] splitURL = newPage.URL().split("/");
		ArrayList<String> listURL = new ArrayList<String>();
		
		for(int i = 0; i<splitURL.length; i++)
		{
			listURL.add(splitURL[i]);
		}
		
		newPage.setProtocol(listURL.get(0));
		
		listURL.remove(newPage.getProtocol());
		
		newPage.setProtocol(newPage.getProtocol()+"//");
		
		newPage.setWebsiteURL(listURL.get(1));
		
		listURL. remove(newPage.getWebsiteURL());
		
		for(String s : listURL)
		{
			if(!s.isEmpty() && s!=null)
			newPage.setWebpage(newPage.getWebpage() + "/"+s);
		}
		
		return newPage;
	}

}
