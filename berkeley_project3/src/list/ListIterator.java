package list;

import java.util.Iterator;

public class ListIterator implements Iterator<ListNode>{
	ListNode cur;
	
	public ListIterator(List list){
		cur=list.front();
	}
	public boolean hasNext(){
		return cur.isValidNode()&&cur.next().isValidNode();
	}
	public ListNode next(){
		if(!cur.isValidNode())return null;
		ListNode tmp = cur;
		cur=cur.next();
		return tmp;
	}
	public void remove(){
		if(!cur.isValidNode())return;
		cur.remove();
	}
}
