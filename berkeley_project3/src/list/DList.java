package list;

import java.util.Iterator;

public class DList extends List {
	protected DListNode head;
	
	protected DListNode newNode(Object item,DList list,DListNode prev,DListNode next){
		return new DListNode(item,list,prev,next);
	}
	public DList(){
		head=newNode(Integer.MIN_VALUE,null,null,null);
		head.next=head;
		head.prev=head;
		size=0;
	}
	public ListNode insertFront(Object item){
		DListNode node=newNode(item,this,head,head.next);
		head.next.prev=node;
		head.next=node;
		size++;
		return node;
	}
	public ListNode insertBack(Object item){
		DListNode node=newNode(item,this,head.prev,head);
		head.prev.next=node;
		head.prev=node;
		size++;
		return node;
	}
	public ListNode front(){
		return head.next;
	}
	public ListNode back(){
		return head.prev;
	}
	public Iterator<ListNode> iterator(){
		return new ListIterator(this);
	}
	public boolean contains(Object obj){
		for(ListNode node:this){
			if(node.item==obj)return true;
		}
		return false;
	}
	public DList copy(){
		DList copy = new DList();
		DListNode cur = head.next;
		while(cur!=head){
			copy.insertBack(cur.item);
			cur=cur.next;
		}
		return copy;
	}
	public String toString(){
		String res="[ ";
		DListNode cur=head.next;
		while(cur!=head){
			res+=cur.item+" ";
			cur=cur.next;
		}
		return res+"]";
	}
}
