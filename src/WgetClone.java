import java.io.IOException;
import java.util.Set;

public class WgetClone {

	public static void main(String args[]) throws IOException {

		/*if (args.length != 1) 
		{
			outputUsage();
		}
		
		PageToDownload newPage = Helpers.ProcessURL(args[0]);
		*/
		
		String protocol = "http://";
		String websiteUrl = "www.dei.isep.ipp.pt";
		String webpage = "/~jcoelho/index.html";

		//String websiteUrl = "www.pagetutor.com";
		//String webpage = "/html_tutor/index.html";
		
		String saveFolder = "teste5";
		String filetype = "";
		int depth = 1;

		HttpRequest.host = websiteUrl;
		HttpRequest.port = 80;

		PageDownloadQueue pageQueue = new PageDownloadQueue();
		
		new PageFinder(protocol, websiteUrl, webpage, depth, pageQueue).start();
		//new PageFinder(newPage.getProtocol(), newPage.getWebsiteURL(), newPage.getWebpage(), depth, pageQueue).start();
		
		new PageHandler(saveFolder, pageQueue, filetype).start();
		new PageHandler(saveFolder, pageQueue, filetype).start();
		new PageHandler(saveFolder, pageQueue, filetype).start();
		new PageHandler(saveFolder, pageQueue, filetype).start();
		
		while(!pageQueue.getCanTerminate())
		{	

		}
		
		/*Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
		
		for(Thread t : threadArray)
		{
			if(t.getThreadGroup().getName().equals("main"))
				t.interrupt();
			//System.out.println(t.getState());
			//System.out.println(t.getThreadGroup().getName());
		}
		*/
		
		System.out.println("Download Finished!");
		System.exit(0);

	}
	
	private static void outputUsage() {
		System.out.println("USAGE: ");
		System.out.println("java -jar wgetjava.jar %URL OF HTML website to Download%  %depth% %filetype%");
	}
}


