package com.java;

import java.util.HashMap;

public class LRUCache {

	private HashMap<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>();
	private int count;
	private int capacity;
	private DLinkedNode head, tail;

	public LRUCache(int capacity) {
		this.count = 0;
		this.capacity = capacity;

		head = new DLinkedNode();
		head.pre = null;

		tail = new DLinkedNode();
		tail.next = null;

		head.next = tail;
		tail.pre = head;
	}

	/**
	 * Always add the new node right after head;
	 */
	private void addNode(DLinkedNode node) {

		node.pre = head;
		node.next = head.next;

		head.next.pre = node;
		head.next = node;
	}

	/**
	 * Remove an existing node from the linked list.
	 */
	private void removeNode(DLinkedNode node) {
		node.pre.next = node.next;
		node.next.pre = node.pre;
	}

	/**
	 * Move certain node in between to the head.
	 */
	private void moveToHead(DLinkedNode node) {
		removeNode(node);
		addNode(node);
	}

// pop the current tail. 
	private DLinkedNode popTail() {
		DLinkedNode res = tail.pre;
		removeNode(res);
		return res;
	}

	public int get(int key) {

		DLinkedNode node = cache.get(key);
		if (node == null) {
			return -1; // should raise exception here.
		}

		// move the accessed node to the head;
		moveToHead(node);

		return node.value;
	}

	public void put(int key, int value) {
		DLinkedNode node = cache.get(key);

		if (node == null) {

			DLinkedNode newNode = new DLinkedNode();
			newNode.key = key;
			newNode.value = value;

			cache.put(key, newNode);
			addNode(newNode);

			++count;

			if (count > capacity) {
				// pop the tail
				DLinkedNode tail = popTail();
				cache.remove(tail.key);
				--count;
			}
		} else {
			// update the value.
			node.value = value;
			moveToHead(node);
		}
	}

	class DLinkedNode {
		int key;
		int value;
		DLinkedNode pre;
		DLinkedNode next;
	}

}