package list;

public class SListNode {
	Object item;
	SListNode next;
	
	SListNode(Object obj){
		item=obj;
		next=null;
	}
	SListNode(Object obj,SListNode next){
		item=obj;
		this.next=next;
	}
}
