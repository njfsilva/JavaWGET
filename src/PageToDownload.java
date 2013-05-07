import java.util.ArrayList;


public class PageToDownload {
	
	private String URL, protocol, websiteURL, webpage = "";
	
	public PageToDownload(String URL)
	{
		this.URL = URL;
		
		String[] splitURL = this.URL.split("/");
		ArrayList<String> listURL = new ArrayList<String>();
		
		for(int i = 0; i<splitURL.length; i++)
		{
			listURL.add(splitURL[i]);
		}
		
		this.protocol = listURL.get(0);
		listURL.remove(this.protocol);
		this.protocol+="//";
		
		this.websiteURL = listURL.get(1);
		listURL. remove(this.websiteURL);
		
		for(String s : listURL)
		{
			if(!s.isEmpty() && s!=null)
			this.webpage += "/"+s;
		}
		
	}
	
	public PageToDownload(){}
	
	public String URL()
	{
		return URL;
	}
	
	public void setURL(String url)
	{
		this.URL = url;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getWebsiteURL() {
		return websiteURL;
	}

	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}

	public String getWebpage() {
		return webpage;
	}

	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}

}
