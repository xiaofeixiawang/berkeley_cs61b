package dict;

public class ListNode {
	protected Entry entry;
	protected ListNode next;
	
	public ListNode(Entry entry,ListNode next){
		this.entry=entry;
		this.next=next;
	}
	
	public ListNode(Entry entry){
		this(entry,null);
	}
}
