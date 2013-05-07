import java.util.concurrent.*;
import java.util.*;

public class PageDownloadQueue {

	private static ConcurrentLinkedQueue<PageToDownload> pageQueue = new ConcurrentLinkedQueue<PageToDownload>();
	private boolean canTerminate = false;
	private final int emptyLimiter = 100;
	private int emptyCounter = emptyLimiter;

	public synchronized void pushNewPageToDownload(PageToDownload page) {
		pageQueue.add(page);
		System.out.println("Queued:" + page.URL());

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
			if(pageQueue.isEmpty())
			{
				emptyCounter--;
				if(emptyCounter<=0)
				{
					canTerminate = true;
				}
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
		this.canTerminate = canTerminate;
	}

}
