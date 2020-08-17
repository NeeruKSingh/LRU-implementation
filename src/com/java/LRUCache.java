package com.java;

import java.util.HashMap;

class DDNode {
	int value;
	int key;
	DDNode left;
	DDNode right;
}
public class LRUCache {
	
	HashMap<Integer, DDNode> hashmap;
	static DDNode start;
	static DDNode end;
	int LRU_SIZE = 4; // Here i am setting 4 to test the LRU cache
						// implementation, it can make be dynamic
	public LRUCache() {
		hashmap = new HashMap<Integer, DDNode>();
	}
	
	public int getEntry(int key) {
		if (hashmap.containsKey(key)) // Key Already Exist, just update the
		{
			DDNode node = hashmap.get(key);
			removeNode(node);
			addAtTop(node);
			return node.value;
		}
		return -1;
	}
	public void putEntry(int key, int value) {
		if (hashmap.containsKey(key)) // Key Already Exist, just update the value and move it to top
		{
			DDNode node = hashmap.get(key);
			node.value = value;
			removeNode(node);
			addAtTop(node);
		} else {
			DDNode newnode = new DDNode();
			newnode.left = null;
			newnode.right = null;
			newnode.value = value;
			newnode.key = key;
			if (hashmap.size() > LRU_SIZE) // We have reached maxium size so need to make room for new element.
			{
				hashmap.remove(end.key);
				removeNode(end);				
				addAtTop(newnode);

			} else {
				addAtTop(newnode);
			}

			hashmap.put(key, newnode);
		}
	}
	
	
	
	public static void addAtTop(DDNode node) {
		node.right = start;
		node.left = null;
		if (start != null)
			start.left = node;
		start = node;
		if (end == null)
			end = start;
	}
	
	
	public void removeNode(DDNode node) {

		if (node.left != null) {
			node.left.right = node.right;
		} else {
			start = node.right;
		}

		if (node.right != null) {
			node.right.left = node.left;
		} else {
			end = node.left;
		}
	}

}
