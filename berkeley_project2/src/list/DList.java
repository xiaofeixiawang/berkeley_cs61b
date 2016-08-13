package list;

public class DList extends List{
	protected DListNode head;
	
	protected DListNode newNode(Object item,DList list,DListNode prev,DListNode next){
		return new DListNode(item,list,prev,next);
	}
	public DList(){
		head=newNode(null,null,null,null);
		head.prev=head;
		head.next=head;
	}
	public void insertFront(Object item){
		DListNode n=newNode(item,this,head,head.next);
		head.next.prev=n;
		head.next=n;
		size++;
	}
	public void insertBack(Object item){
		DListNode n=newNode(item,this,head.prev,head);
		head.prev.next=n;
		head.prev=n;
		size++;
	}
	public ListNode front(){
		return head.next;
	}
	public ListNode back(){
		return head.prev;
	}
	public boolean within(int[] n){
		DListNode current=(DListNode)front();
		while(current.isValidNode()){
			if((((int[])(current.item))[0]==n[0])&&(((int[])(current.item))[1]==n[1])){
				return true;
			}
			current=(DListNode)current.next;
		}
		return false;
	}
	public String toString(){
		String result="[ ";
		DListNode current=head.next;
		while(current!=head){
			result=result+current.item+" ";
			current=current.next;
		}
		return result+"]";
	}
}
















