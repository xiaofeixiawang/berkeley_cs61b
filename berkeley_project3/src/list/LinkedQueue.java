package list;

public class LinkedQueue implements Queue{
	private SListNode head;
	private SListNode tail;
	private int size;
	
	public LinkedQueue(){
		size=0;
		head=null;
		tail=null;
	}
	public int size(){
		return size;
	}
	public boolean isEmpty(){
		return size==0;
	}
	public void enqueue(Object item){
		if(head==null){
			head=new SListNode(item);
			tail=head;
		}else{
			tail.next=new SListNode(item);
			tail=tail.next;
		}
		size++;
	}
	public Object dequeue() throws QueueEmptyException{
		if(head==null){
			throw new QueueEmptyException();
		}
		Object o = head.item;
		head = head.next;
		size--;
		if(size==0)tail=null;
		return o;
	}
	public Object front() throws QueueEmptyException{
		if(head==null)throw new QueueEmptyException();
		return head.item;
	}
	public Object nth(int n){
		SListNode node = head;
		for(;n>1;n--){
			node=node.next;
		}
		return node.item;
	}
	public void append(LinkedQueue q){
		if(head==null)head=q.head;
		else tail.next=q.head;
		if(q.head!=null)tail=q.tail;
		size=size+q.size;
		q.head=null;
		q.tail=null;
		q.size=0;
	}
	public String toString(){
		String out = "[ ";
		try{
			for(int i=0;i<size();i++){
				out+=front()+" ";
				enqueue(dequeue());
			}
		}catch(QueueEmptyException e){
			System.err.println("Error: attempt to dequeue from empty queue.");
		}
		return out+"]";
	}
}
