package dict;

import list.DList;
import list.ListNode;

public class HashTableChained implements Dictionary {
	protected DList[] buckets;
	protected int size;
	protected static final int COMP_A = 3;
	protected static final int COMP_B = 5;
	
	private static boolean isPrime(int n){
		if(n%2==0)return false;
		for(int divisor=3;divisor<Math.sqrt(n);divisor+=2){
			if(n%divisor==0)return false;
		}
		return true;
	}
	private static int primeAfter(int n){
		if(n%2==0)n--;
		while(!isPrime(n))n+=2;
		return n;
	}
	
	public HashTableChained(int sizeEstimate){
		sizeEstimate=Math.max(1, sizeEstimate);
		size=0;
		int prime=primeAfter(sizeEstimate);
		buckets=new DList[prime];
		for(int i=0;i<prime;i++){
			buckets[i]=new DList();
		}
	}
	public HashTableChained(){
		size=0;
		buckets=new DList[101];
		for(int i=0;i<101;i++){
			buckets[i]=new DList();
		}
	}
	private static int mod(int a,int b){
		a=a%b;
		if(a<0)a+=b;
		return a;
	}
	int compFunction(int code){
		int n=buckets.length;
		int p=primeAfter(2*n);
		return mod(mod(COMP_A*code+COMP_B,p),n);
	}
	public int size(){
		return size;
	}
	public boolean isEmpty(){
		return size()==0;
	}
	public Entry insert(Object key,Object value){
		Entry entry = new Entry();
		entry.key=key;
		entry.value=value;
		int n=compFunction(key.hashCode());
		DList list=buckets[n];
		list.insertBack(entry);
		size++;
		return entry;
	}
	public static ListNode findNode(DList list,Object key){
		ListNode node=list.front();
		while(node.isValidNode()){
			Entry entry=(Entry)node.item();
			if(key.equals(entry.key()))return node;
			node=node.next();
		}
		return null;
	}
	public Entry find(Object key){
		int n=compFunction(key.hashCode());
		DList list=buckets[n];
		ListNode node=findNode(list,key);
		if(node==null)return null;
		return (Entry)node.item();
	}
	public Entry remove(Object key){
		int n=compFunction(key.hashCode());
		DList list=buckets[n];
		ListNode node=findNode(list,key);
		if(node==null)return null;
		Entry entry=(Entry)node.item();
		node.remove();
		size--;
		return entry;
	}
	public void makeEmpty(){
		size=0;
		for(int i=0;i<buckets.length;i++){
			buckets[i]=new DList();
		}
	}
}
