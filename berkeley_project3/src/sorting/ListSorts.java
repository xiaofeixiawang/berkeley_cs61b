package sorting;

import list.*;

public class ListSorts {
	private final static int SORTSIZE = 1000000;
	
	public static LinkedQueue makeQueueOfQueues(LinkedQueue q){
		LinkedQueue newQueue = new LinkedQueue();
		while(q.size()>0){
			try{
				LinkedQueue individualQueue = new LinkedQueue();
				individualQueue.enqueue(q.dequeue());
				newQueue.enqueue(individualQueue);
			}catch(QueueEmptyException e){
				System.err.println(e);
			}
		}
		return newQueue;
	}
	public static LinkedQueue mergeSortedQueues(LinkedQueue q1,LinkedQueue q2){
		LinkedQueue mergeQueue = new LinkedQueue();
		try{
			while(q1.size()>0&&q2.size()>0){
				Comparable first = (Comparable)q1.front();
				Comparable second = (Comparable)q2.front();
				if(first.compareTo(second)<=0){
					mergeQueue.enqueue(q1.dequeue());
				}else{
					mergeQueue.enqueue(q2.dequeue());
				}
			}
			while(q1.size()>0)mergeQueue.enqueue(q1.dequeue());
			while(q2.size()>0)mergeQueue.enqueue(q2.dequeue());
		}catch(QueueEmptyException e){
			System.err.println(e);
		}
		return mergeQueue;
	}
	public static void partition(LinkedQueue qIn,Comparable pivot,LinkedQueue qSmall,LinkedQueue qEquals,LinkedQueue qLarge){
		while(qIn.size()>0){
			try{
				Comparable element = (Comparable)qIn.dequeue();
				int compare = element.compareTo(pivot);
				if(compare==0)qEquals.enqueue(element);
				else if(compare<0)qSmall.enqueue(element);
				else qLarge.enqueue(element);
			}catch(QueueEmptyException e){
				System.err.println(e);
			}
		}
	}
	public static void mergeSort(LinkedQueue q){
		LinkedQueue queueOfQueues = makeQueueOfQueues(q);
		while(queueOfQueues.size()>1){
			try{
				LinkedQueue first = (LinkedQueue)queueOfQueues.dequeue();
				LinkedQueue second = (LinkedQueue)queueOfQueues.dequeue();
				LinkedQueue merged = mergeSortedQueues(first,second);
				queueOfQueues.enqueue(merged);
			}catch(QueueEmptyException e){
				System.err.println(e);
			}
		}
		if(queueOfQueues.size()>0){
			q.append((LinkedQueue)queueOfQueues.nth(1));
		}
	}
	public static void quickSort(LinkedQueue q){
		if(q.size()==0)return;
		int random=(int)(Math.random()*1.0*(q.size()-1));
		Comparable pivot = (Comparable)q.nth(random);
		LinkedQueue qSmall = new LinkedQueue();
		LinkedQueue qEquals = new LinkedQueue();
		LinkedQueue qLarge = new LinkedQueue();
		partition(q,pivot,qSmall,qEquals,qLarge);
		quickSort(qSmall);
		quickSort(qLarge);
		q.append(qSmall);
		q.append(qEquals);
		q.append(qLarge);
	}
	public static LinkedQueue makeRandom(int size){
		LinkedQueue q = new LinkedQueue();
		for(int i=0;i<size;i++){
			q.enqueue(new Integer((int)(size*Math.random())));
		}
		return q;
	}
}