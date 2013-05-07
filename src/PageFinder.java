import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class PageFinder extends Thread  {

	private String protocol, websiteUrl, webpage = "";
	private int depth;
	private PageDownloadQueue pageQueue;

	public PageFinder(String protocol, String websiteUrl, String webpage, int depth, PageDownloadQueue pageQueue){

		this.protocol = protocol;
		this.websiteUrl = websiteUrl;
		this.webpage = webpage;
		this.depth = depth;
		this.pageQueue = pageQueue;

	}


	public void run(){
		try {

			Document doc = Jsoup.connect(protocol+websiteUrl+webpage).get();
			URI uri = new URI(protocol+websiteUrl+webpage);

			URI resultURI;

			Elements links = doc.select("a[href]");

			
			//add itself to download queue
			PageToDownload newPage = new PageToDownload(uri.toURL().toString());
			//pageQueue.pushNewPageToDownload(newPage);
			addToQueue(newPage);
			
			while(depth >0)
			{
				for (Element link : links) {
					
					resultURI=uri.resolve(link.attr("href"));

					if(resultURI.getAuthority() == uri.getAuthority())
					{	
						newPage = new PageToDownload(resultURI.toURL().toString());
						//pageQueue.pushNewPageToDownload(newPage);
						addToQueue(newPage);

						new PageFinder(newPage.getProtocol(), newPage.getWebsiteURL(), newPage.getWebpage(), this.depth -1, this.pageQueue).start();
					}

				}
				depth--;
			}

			Thread.yield();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}
	
	private synchronized void addToQueue(PageToDownload newPage)
	{
		pageQueue.pushNewPageToDownload(newPage);
	}

}
