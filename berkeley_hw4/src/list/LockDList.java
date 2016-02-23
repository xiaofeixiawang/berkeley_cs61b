/* DList.java */

package list;

/**
 * A DList is a mutable doubly-linked list ADT. Its implementation is
 * circularly-linked and employs a sentinel (dummy) node at the head of the
 * list.
 *
 * DO NOT CHANGE ANY METHOD PROTOTYPES IN THIS FILE.
 */

public class LockDList extends DList {

	protected LockDListNode newNode(Object item, DListNode prev, DListNode next) {
		return new LockDListNode(item, prev, next);
	}

	public LockDList(){
		  head=newNode(0,null,null);
		  head.next=head;
		  head.prev=head;
		  size=0;
	}
	
	public void lockNode(DListNode node) {
		((LockDListNode) node).locked = true;
	}

	public void remove(DListNode node) {
		LockDListNode node2 = (LockDListNode) node;
		if (node2.locked)
			return;
		if (node == null)
			return;
		node.next.prev = node.prev;
		node.prev.next = node.next;
		size--;
		// Your solution here.
	}

	public static void main(String[] args) {
		DList test = new LockDList();
		System.out.println(test);
		test.insertFront(1);
		System.out.println(test);
		test.insertFront(0);
		System.out.println(test);
		test.insertBack(2);
		System.out.println(test);
		test.insertBack(5);
		System.out.println(test);
		test.insertAfter(3, test.head.next.next.next);
		System.out.println(test);
		test.insertBefore(4, test.head.prev);
		System.out.println(test);
		System.out.println("test front(); " + test.front().item);
		System.out.println("test back(); " + test.back().item);
		System.out.println("test isEmpty(); " + test.isEmpty());
		System.out.println("test length(); " + test.length());
		System.out.println("test next(); " + test.next(test.head.next).item);
		System.out.println("test prev(); " + test.prev(test.head.prev).item);
		((LockDList) test).lockNode(test.head.next);
		test.remove(test.head.next);
		System.out.println("test remove(); ");
		System.out.println(test);
	}
}
