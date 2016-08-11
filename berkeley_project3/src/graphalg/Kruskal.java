/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import list.*;
import dict.*;
import sorting.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g){
	  Object[] vertices=g.getVertices();
	  LinkedQueue edges=new LinkedQueue();
	  WUGraph minSpanTree=new WUGraph();
	  for(Object vertex:vertices)minSpanTree.addVertex(vertex);
	  Object[] currentNeighborList;
	  int[] currentWeightList;
	  for(int counter=0;counter<vertices.length;counter++){
		  Neighbors current=g.getNeighbors(vertices[counter]);
		  currentNeighborList=current.neighborList;
		  currentWeightList=current.weightList;
		  for(int i=0;i<currentNeighborList.length;i++){
			  Edge e=new Edge(vertices[counter],currentNeighborList[i],currentWeightList[i]);
			  edges.enqueue(e);
		  }
	  }
	  ListSorts.quickSort(edges);
	  DisjointSets set = new DisjointSets(vertices.length);
	  HashTableChained verticesToIntegers = new HashTableChained(vertices.length);
	  for(int v=0;v<vertices.length;v++)verticesToIntegers.insert(vertices[v], v);
	  while(!edges.isEmpty()){
		  Edge e=new Edge();
		  try{
			  e=(Edge)edges.dequeue();
		  }catch(QueueEmptyException e1){
			  System.err.println(e1);
		  }
		  int u=(int) verticesToIntegers.find(e.getU()).value();
		  int v=(int) verticesToIntegers.find(e.getV()).value();
		  if(set.find(u)!=set.find(v)){
			  set.union(set.find(u), set.find(v));
			  minSpanTree.addEdge(e.getU(), e.getV(), e.getWeight());
		  }
	  }
	  return minSpanTree;
  }

}




















