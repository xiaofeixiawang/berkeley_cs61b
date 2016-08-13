package list;

public class DListNode extends ListNode{
	protected DListNode prev;
	protected DListNode next;
	
	DListNode(Object i,DList l,DListNode p,DListNode n){
		item=i;
		myList=l;
		prev=p;
		next=n;
	}
	public Object getItem(){
		return item;
	}
	public boolean isValidNode(){
		return myList!=null;
	}
	public ListNode next() throws InvalidNodeException{
		if(!isValidNode()){
			throw new InvalidNodeException("next() called on invalid node");
		}
		return next;
	}
	public ListNode prev() throws InvalidNodeException{
		if(!isValidNode()){
			throw new InvalidNodeException("prev() called on invalid node");
		}
		return prev;
	}
	public void insertAfter(Object item) throws InvalidNodeException{
		if(!isValidNode()){
			throw new InvalidNodeException("insertAfter() called on invalid node");
		}
		DListNode n = ((DList)myList).newNode(item, (DList)myList, this, next);
		next.prev=n;
		next=n;
		myList.size++;
	}
	public void insertBefore(Object item) throws InvalidNodeException{
		if(!isValidNode()){
			throw new InvalidNodeException("insertBefore() called on invalid node");
		}
		DListNode n=((DList)myList).newNode(item, (DList)myList, this.prev, this);
		prev.next=n;
		prev=n;
		myList.size++;
	}
	public void remove() throws InvalidNodeException{
		if(!isValidNode()){
			throw new InvalidNodeException("remove() called on invalid node");
		}
		next.prev=prev;
		prev.next=next;
		myList.size--;
		myList=null;
		next=null;
		prev=null;
	}
}


















