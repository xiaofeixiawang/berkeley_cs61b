package list;

import java.util.Iterator;

public abstract class List implements Iterable<ListNode>{
	protected int size;
	
	public boolean isEmpty(){
		return size==0;
	}
	public int length(){
		return size;
	}
	public abstract ListNode insertFront(Object item);
	public abstract ListNode insertBack(Object item);
	public abstract ListNode front();
	public abstract ListNode back();
	public abstract boolean contains(Object obj);
	public abstract Iterator<ListNode> iterator();
	public abstract String toString();
}
