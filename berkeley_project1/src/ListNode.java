
public class ListNode {

	public int[] run;
	public ListNode prev;
	public ListNode next;

	ListNode(int length) {
		run = new int[4];
		run[0] = length;
		prev = null;
		next = null;
	}

	ListNode(int red, int green, int blue, int length) {
		run = new int[4];
		run[0] = length;
		run[1] = red;
		run[2] = green;
		run[3] = blue;
		prev = null;
		next = null;
	}

	public void setLength(int length){
		run[0]=length;
	}
	
	public void setPixel(int red,int green,int blue){
		run[1]=red;
		run[2]=green;
		run[3]=blue;
	}
}
