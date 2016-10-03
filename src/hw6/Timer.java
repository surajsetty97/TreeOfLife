package hw6;

import java.io.*;
import java.util.*;


public class Timer implements Runnable
{
	private boolean			timerFinished;
	private TreeOfLife		tree;
	
	
	Timer(File file)
	{		
		try
		{
			tree = new TreeOfLife(file);
		}
		catch (IOException x)
		{
			System.out.println("Couldn't load tree from file");
			System.exit(1);
		}
	}
	
	
	long countFindsFor10Seconds(boolean useRecursion)
	{
		Map<String, TreeNode> map = tree.getNameToNodeMap();
		ArrayList<TreeNode> nodesList = new ArrayList<>(map.values());
		
		long nFinds = 0;
		timerFinished = false;
		new Thread(this).start();
		
		while (!timerFinished)
		{
			int r = (int)(nodesList.size() * Math.random());
			String target = nodesList.get(r).getName();
			if (useRecursion)
				tree.getNodeForNameFromTree(target);
			else
				tree.getNodeForNameFromMap(target);
			nFinds++;
		}
		
		return nFinds;
	}
	
	
	public void run()
	{
		try
		{
			Thread.sleep(10 * 1000);
		}
		catch (InterruptedException x) { }
		timerFinished = true;
	}
	
	
	public static void main(String[] args)
	{
		System.out.println("START");
		File f = new File(args[0]);
		Timer timer = new Timer(f);
		System.out.println("Counting finds by recursive search for 10 seconds:");
		long nFinds = timer.countFindsFor10Seconds(true);
		System.out.println(" ==> " + nFinds);
		System.out.println("Counting finds by map search for 10 seconds:");
		nFinds = timer.countFindsFor10Seconds(false);
		System.out.println(" ==> " + nFinds);
		System.out.println("DONE");
	}
}
