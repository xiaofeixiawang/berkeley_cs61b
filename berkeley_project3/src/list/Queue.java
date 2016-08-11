package list;

public interface Queue {
	public int size();
	public boolean isEmpty();
	public void enqueue(Object item);
	public Object dequeue() throws QueueEmptyException;
	public Object front() throws QueueEmptyException;
}
