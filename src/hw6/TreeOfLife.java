package hw6;

import java.io.*;
import java.util.*;


public class TreeOfLife 
{
	private TreeNode					root;
	private HashMap<String, TreeNode>	nameToNode;
	
	
	//
	// This ctor is private, so it can only be called from this class. It
	// is just for you to test your code using main().
	//
	private TreeOfLife()
	{
		root = new TreeNode("Life");
		nameToNode = new HashMap<>();
	}
	
	
	//
	// Constructs a TreeOfLife instance, adding 1 leaf node for every
	// line in the input file.
	//
	public TreeOfLife(File f) throws IOException
	{
		root = new TreeNode("Life");
		nameToNode = new HashMap<>();
		
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		//Adds every line of the file
		while(br.readLine()!=null)
		{
			this.add(br.readLine());
		}
		br.close();
		fr.close();
		
		// Create a FileReader that reads from f, and a BufferedReader
		// that reads from the FileReader. Call add() for each input line.
	}
	
	
	//
	// Format: 7 names, separated by commas.
	// Example: Metazoa,Chordata,Ascidiacea,Enterogona,Didemnidae,Didemnum,vexillum
	// (Note: "Metazoa" means animals.)
	//
	private void add(String line)
	{
		// Split the comma-separated names into an array of names. See API page for
		// split() method of java.lang.String. If the input string didn't contain
		// exactly 7 names, throw an IllegalArgumentException with a useful message.

		String[] names = line.split(",");
		if(names.length!=7)
		{
			throw new IllegalArgumentException("Expected: 7 names. Given: " + names.length + " names.");
		}
		TreeNode node = root;
		TreeNode childNode = null;	
		for (String s: names)
		{
			if(node.getChildWithName(s)!=null)
			{
				node = node.getChildWithName(s);
			}
			else
			{
				node = node.addChild(s);
				nameToNode.put(s,node);
			}
			
			
			// If node has a child named s, just set node to that child node.
			//
			// If node does not have a child named s:
			// 1)  Give node a child named s.
			// 2)  Set node to that child node.
			// 3)  Make an entry in the nameToNode map, from s to the child node.
			//
			// In either case, every time through this loop, node will be 1
			// level lower in the tree.
		}
	}
	
	
	//
	// This method has been written for you. Be able to write it yourself for the final.
	//
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		recurseToString(root, sb, "");
		return sb.toString();
	}
	
	
	private void recurseToString(TreeNode node, StringBuilder sb, String indent)
	{
		sb.append("\n");
		sb.append(indent);
		sb.append(node.getName());
		indent += "  ";
		for (TreeNode child: node.getChildren())
			recurseToString(child, sb, indent);			
	}
	
	
	Map<String, TreeNode> getNameToNodeMap()
	{
		return nameToNode;
	}
	
	
	//
	// The fast way to find a node, given its name. 
	//
	TreeNode getNodeForNameFromMap(String name)
	{
		return nameToNode.get(name);
	}
	
	
	//
	// The slow way to find a node, given its name. Recurses through the tree.
	//
	// If this class didn't have the nameToNode map, it would require half as
	// much memory, but node searches would be done this way. How much slower
	// is this kind of search? 
	//
	TreeNode getNodeForNameFromTree(String name)
	{
		return recurseGetNodeForNameFromTree(name, root);
	}
	
	
	private TreeNode recurseGetNodeForNameFromTree(String name, TreeNode node)
	{
		if (node.getName().equals(name))
			return node;
		
		for (TreeNode childNode: node.getChildren())
		{
			TreeNode foundNode = recurseGetNodeForNameFromTree(name, childNode);
			if (foundNode != null)
				return foundNode;
		}
		
		return null;
	}
	
	
	// After initial testing, add at least 3 more strings to this array.
	private static String[] TEST_STRINGS =
	{
		"A,B,C,D,E,F,G",
		"A,B,C,D,E,F,GGG",
		"A,B,C,WWW,XXX,YYY,ZZZ"
	};
	
	
	public static void main(String[] args)
	{
		System.out.println("START");
		
		  		 
		File f = new File(args[0]);
		try
		{
			TreeOfLife tree = new TreeOfLife(f);
			System.out.println(tree);
		}
		catch (IOException x)
		{
			System.out.println("Trouble");
			// If your program throws IOException, add code here to
			// print the exception's message and stack trace.
		}
		
		
		
		System.out.println("DONE");
	}
}
