import java.util.concurrent.*;
import java.util.*;

public class PageDownloadQueue {

	private static ConcurrentLinkedQueue<PageToDownload> pageQueue = new ConcurrentLinkedQueue<PageToDownload>();
	private static boolean canTerminate = false;
	private static final int emptyLimiter = 100;
	private static int emptyCounter = emptyLimiter;
	private static HashMap<String, PageToDownload> cyclicLinkMap = new HashMap<String, PageToDownload>();

	public synchronized void pushNewPageToDownload(PageToDownload page) 
	{
		if(!isCyclicLink(page))
		{
			try
			{
				pageQueue.add(page);
				cyclicLinkMap.put(page.URL(), page);
				System.out.println("Queued:" + page.URL());
			}
			catch(Exception ex)
			{
				System.out.println("Error adding page to queue: " + ex.getMessage());
			}
		}
	}
	
	public synchronized PageToDownload pullPageToDownload() 
	{
		PageToDownload page = null;
		
		try
		{
			page = pageQueue.remove();
			System.out.println("Pulled:" + page.URL());
		}
		catch(NoSuchElementException e)
		{
			emptyCounter--;
			
			if(pageQueue.isEmpty() && emptycounter <= 0)
			{
				canTerminate = true;
			}
		}
		return page;
	}

	public synchronized int getQueueSize()
	{
		return pageQueue.size();
	}
	public boolean getCanTerminate() {
		return canTerminate;
	}
	public void setCanTerminate(boolean canTerminate) {
		PageDownloadQueue.canTerminate = canTerminate;
	}
	
	public boolean isCyclicLink(PageToDownload page)
	{
		if(cyclicLinkMap.get(page.URL()) != null)
			return true;
		
		return false;
	}
	
}
