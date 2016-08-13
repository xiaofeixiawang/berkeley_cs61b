package list;

public class SList extends List{
	protected SListNode head;
	protected SListNode tail;
	
	protected SListNode newNode(Object item,SListNode next){
		return new SListNode(item,this,next);
	}
	public SList(){
		head=null;
		tail=null;
		size=0;
	}
	public void insertFront(Object item){
		head=newNode(item,head);
		if(size==0)tail=head;
		size++;
	}
	public void insertBack(Object item){
		if(head==null){
			head=newNode(item,null);
			tail=head;
		}else{
			tail.next=newNode(item,null);
			tail=tail.next;
		}
		size++;
	}
	public ListNode front(){
		if(head==null){
			SListNode node = newNode(null,null);
			node.myList=null;
			return node;
		}else{
			return head;
		}
	}
	public ListNode back(){
		if(tail==null){
			SListNode node=newNode(null,null);
			node.myList=null;
			return node;
		}else{
			return tail;
		}
	}
	public String toString(){
		String result="[ ";
		SListNode current=head;
		while(current!=null){
			result=result+current.item+" ";
			current=current.next;
		}
		return result+"]";
	}
}
