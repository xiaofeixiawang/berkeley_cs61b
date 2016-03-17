/* HashTableChained.java */

package dict;

import java.util.ArrayList;

/**
 * HashTableChained implements a Dictionary as a hash table with chaining. All
 * objects used as keys must have a valid hashCode() method, which is used to
 * determine which bucket of the hash table an entry is stored in. Each object's
 * hashCode() is presumed to return an int between Integer.MIN_VALUE and
 * Integer.MAX_VALUE. The HashTableChained class implements only the compression
 * function, which maps the hash code to a bucket in the table's range.
 *
 * DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

	/**
	 * Place any data fields here.
	 **/
	private int size, bucket;
	private ArrayList<ListNode> buckets;

	/**
	 * Construct a new empty hash table intended to hold roughly sizeEstimate
	 * entries. (The precise number of buckets is up to you, but we recommend
	 * you use a prime number, and shoot for a load factor between 0.5 and 1.)
	 **/

	public HashTableChained(int sizeEstimate) {
		size = 0;
		bucket = sizeEstimate;
		buckets = new ArrayList<ListNode>();
		for (int i = 0; i < bucket; i++) {
			buckets.add(new ListNode(new Entry()));
		}
		// Your solution here.
	}

	/**
	 * Construct a new empty hash table with a default size. Say, a prime in the
	 * neighborhood of 100.
	 **/

	public HashTableChained() {
		size = 0;
		bucket = 100;
		buckets = new ArrayList<ListNode>();
		for (int i = 0; i < bucket; i++) {
			buckets.add(new ListNode(new Entry()));
		}
		// Your solution here.
	}

	/**
	 * Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
	 * to a value in the range 0...(size of hash table) - 1.
	 *
	 * This function should have package protection (so we can test it), and
	 * should be used by insert, find, and remove.
	 **/

	int compFunction(int code) {
		return code % bucket;
	}

	/**
	 * Returns the number of entries stored in the dictionary. Entries with the
	 * same key (or even the same key and value) each still count as a separate
	 * entry.
	 * 
	 * @return number of entries in the dictionary.
	 **/

	public int size() {
		return size;
	}

	/**
	 * Tests if the dictionary is empty.
	 *
	 * @return true if the dictionary has no entries; false otherwise.
	 **/

	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Create a new Entry object referencing the input key and associated value,
	 * and insert the entry into the dictionary. Return a reference to the new
	 * entry. Multiple entries with the same key (or even the same key and
	 * value) can coexist in the dictionary.
	 *
	 * This method should run in O(1) time if the number of collisions is small.
	 *
	 * @param key
	 *            the key by which the entry can be retrieved.
	 * @param value
	 *            an arbitrary object.
	 * @return an entry containing the key and value.
	 **/

	public Entry insert(Object key, Object value) {
		int num = compFunction(key.hashCode());
		ListNode tmp = buckets.get(num);
		Entry entry = new Entry();
		entry.key = key;
		entry.value = value;
		tmp.next = new ListNode(entry, tmp.next);
		size++;
		return entry;
	}

	public String toString() {
		String out = "";
		for (int i = 0; i < bucket; i++) {
			out += "the entry in " + i + " is ";
			ListNode tmp = buckets.get(i);
			while (tmp.next != null) {
				out += tmp.next.entry.value + "/";
				tmp = tmp.next;
			}
			out += "\n";
		}
		return out;
	}

	/**
	 * Search for an entry with the specified key. If such an entry is found,
	 * return it; otherwise return null. If several entries have the specified
	 * key, choose one arbitrarily and return it.
	 *
	 * This method should run in O(1) time if the number of collisions is small.
	 *
	 * @param key
	 *            the search key.
	 * @return an entry containing the key and an associated value, or null if
	 *         no entry contains the specified key.
	 **/

	public Entry find(Object key) {
		int num = compFunction(key.hashCode());
		ListNode tmp = buckets.get(num);
		while (tmp.next != null) {
			if (tmp.next.entry.key.equals(key)) {
				return tmp.next.entry;
			}
			tmp = tmp.next;
		}
		return null;
	}

	/**
	 * Remove an entry with the specified key. If such an entry is found, remove
	 * it from the table and return it; otherwise return null. If several
	 * entries have the specified key, choose one arbitrarily, then remove and
	 * return it.
	 *
	 * This method should run in O(1) time if the number of collisions is small.
	 *
	 * @param key
	 *            the search key.
	 * @return an entry containing the key and an associated value, or null if
	 *         no entry contains the specified key.
	 */

	public Entry remove(Object key) {
		int num = compFunction(key.hashCode());
		ListNode tmp = buckets.get(num);
		while (tmp.next != null) {
			if (tmp.next.entry.key.equals(key)) {
				ListNode out = tmp.next;
				tmp.next = tmp.next.next;
				size--;
				return out.entry;
			}
			tmp = tmp.next;
		}
		// Replace the following line with your solution.
		return null;
	}

	/**
	 * Remove all entries from the dictionary.
	 */
	public void makeEmpty() {
		for (int i = 0; i < bucket; i++) {
			buckets.set(i, new ListNode(new Entry()));
		}
		size = 0;
	}

	public double collision() {
		int num = 0;
		for (int i = 0; i < bucket; i++) {
			ListNode tmp=buckets.get(i);
			if (tmp.next != null) {
				tmp=tmp.next;
				while(tmp.next!=null){
					num++;
					tmp=tmp.next;
				}
			}
		}
		return ((double) num) / bucket;
	}
}
