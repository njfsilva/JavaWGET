import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class PageHandler extends Thread  {

	private String saveFolder = "";
	private PageDownloadQueue pageQueue;
	private String fileType;

	public PageHandler(String saveFolder, PageDownloadQueue pageQueue, String fileType){

		this.saveFolder = saveFolder;
		this.pageQueue = pageQueue;
		this.fileType = fileType;
	}

	public void run(){
		PageToDownload pageToProcess;

		while(true){

			try {
				
				Thread.sleep(2000);
				
				pageToProcess = pageQueue.pullPageToDownload();

				if(pageToProcess != null)
				{

					Document doc = Jsoup.connect(pageToProcess.getProtocol()+pageToProcess.getWebsiteURL()+pageToProcess.getWebpage()).get();


					File htmlFile = new File(saveFolder+pageToProcess.getWebpage());

					if(!htmlFile.exists() || !fileType.isEmpty())
					{
						//Process HTML resources
						Elements media = doc.select("[src]");
						Elements imports = doc.select("link[href]");
						String resourceURL = "";

						for (Element src : media) {

							resourceURL = src.absUrl("src").replace(pageToProcess.getProtocol()+pageToProcess.getWebsiteURL(), "");
							if(!fileType.isEmpty())
							{
								String[] split =  resourceURL.split("\\.");
								if(split.length > 0)
								{
									String fileExtension = split[split.length-1]; 
									if(fileType.toLowerCase().equals(fileExtension.toLowerCase()))
										HttpRequest.binaryFile(resourceURL, saveFolder, ".");
								}
							}	
							else
							HttpRequest.binaryFile(resourceURL, saveFolder, ".");
						}


						for (Element link : imports) {     	

							resourceURL = link.absUrl("href").replace(pageToProcess.getProtocol()+pageToProcess.getWebsiteURL(), "");
							if(!fileType.isEmpty())
							{
								String[] split =  resourceURL.split("\\.");
								if(split.length > 0)
								{
									String fileExtension = split[split.length-1]; 
									if(fileType.toLowerCase().equals(fileExtension.toLowerCase()))
										HttpRequest.binaryFile(resourceURL, saveFolder, ".");
								}
							}	
							else
							HttpRequest.binaryFile(resourceURL, saveFolder, ".");
						}
						
						//write HTML to disk
						if(fileType.toLowerCase().equals("html") || fileType.isEmpty())
						{
							if(!htmlFile.getParentFile().exists())
								htmlFile.getParentFile().mkdirs();
							BufferedWriter writer = new BufferedWriter(new FileWriter(saveFolder+pageToProcess.getWebpage()));
							writer.write(doc.html());
							writer.close();
						}

						System.out.println("Processed: " + pageToProcess.URL() +" thread: " + Thread.currentThread().getId());

						Thread.yield();
					}
					else
					{
						System.out.println("Skipped: " + pageToProcess.URL() + ", Page already Downloaded.");
					}
				}
			} 
				catch (IOException | InterruptedException e) {
					e.printStackTrace();
			}
		}
	}

}

