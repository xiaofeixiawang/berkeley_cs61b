package list;

public class DListNode extends ListNode {
	protected DListNode prev;
	protected DListNode next;
	
	DListNode(Object i,DList l,DListNode p,DListNode n){
		item=i;
		myList=l;
		prev=p;
		next=n;
	}
	public boolean isValidNode(){
		return myList!=null;
	}
	public ListNode next(){
		if(!isValidNode()){
			throw new InvalidNodeException();
		}
		return next;
	}
	public ListNode prev(){
		if(!isValidNode()){
			throw new InvalidNodeException();
		}
		return prev;
	}
	public void insertAfter(Object item){
		if(!isValidNode()){
			throw new InvalidNodeException();
		}
		DListNode newNode = ((DList)myList).newNode(item,(DList)myList,this,next);
		next.prev=newNode;
		next=newNode;
		myList.size++;
	}
	public void insertBefore(Object item){
		if(!isValidNode()){
			throw new InvalidNodeException();
		}
		DListNode newNode = ((DList)myList).newNode(item,(DList)myList,prev,this);
		prev.next=newNode;
		prev=newNode;
		myList.size++;
	}
	public void remove(){
		if(!isValidNode()){
			throw new InvalidNodeException();
		}
		prev.next=next;
		next.prev=prev;
		myList.size--;
		myList=null;
		next=null;
		prev=null;
	}
}
