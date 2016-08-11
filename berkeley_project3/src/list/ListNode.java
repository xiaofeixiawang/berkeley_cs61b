package list;

public abstract class ListNode {
	protected Object item;
	protected List myList;
	
	public boolean isValidNode(){
		return myList!=null;
	}
	public Object item(){
		return item;
	}
	public void setItem(Object item){
		this.item=item;
	}
	public abstract ListNode next();
	public abstract ListNode prev();
	public abstract void insertAfter(Object item);
	public abstract void insertBefore(Object item);
	public abstract void remove();
}
