/* WUGraph.java */

package graph;

import dict.*;
import list.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
	private HashTableChained vList;
	private HashTableChained eList;
	private DList keys;
	
  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph(){
	  vList = new HashTableChained();
	  eList = new HashTableChained();
	  keys = new DList();
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
	  return vList.size();
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
	  return eList.size();
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices(){
	  Object[] res = new Object[keys.length()];
	  try{
		  DListNode first = (DListNode)keys.front();
		  int pos=0;
		  while(first!=null){
			  try{
				  res[pos++]=((VertexPair)first.item()).object1;
				  first=(DListNode)first.next();
			  }catch(Exception e){
				  break;
			  }
		  }
	  }catch(Exception e){}
	  return res;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex){
	  if(vList.find(vertex)!=null)return;
	  VertexPair tmp = new VertexPair(vertex,new DList());
	  vList.insert(vertex, keys.insertBack(tmp));
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex){
	  	Entry entry=vList.remove(vertex);
	  	if(entry!=null){
	  		DListNode node = (DListNode)entry.value();
	  		DList list = (DList)((VertexPair)node.item()).object2;
		  	try{
		  		DListNode first=(DListNode)list.front();
		  		while(first!=null){
		  			try{
			  			VertexPair key = new VertexPair(vertex,first.item());
			  			Entry e = eList.find(key);
			  			if(e!=null){
			  				VertexPair pair=(VertexPair)((VertexPair)e.value()).object1;
			  				if(((DListNode)pair.object1).item().equals(first.item())){
			  					if(((DListNode)pair.object2).item().equals(first.item())){
			  						
			  					}else{
			  						((DListNode)pair.object2).remove();
			  					}
			  				}else{
			  					((DListNode)pair.object1).remove();
			  				}
			  				eList.remove(key);
			  			}
			  			first=(DListNode)first.next();
		  			}catch(Exception e){
		  				break;
		  			}
		  		}
		  		node.remove();
		  	}catch(Exception e){
		  	}
	  	}
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
	  return vList.find(vertex)!=null;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex){
	  Entry e=vList.find(vertex);
	  if(e==null)return 0;
	  DList list=(DList)((VertexPair)((DListNode)e.value()).item()).object2;
	  return list.length();
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex){
	  Neighbors res=null;
	  Entry entry = vList.find(vertex);
	  if(entry!=null){
		  DList neighbors = (DList)((VertexPair)((DListNode)entry.value()).item()).object2;
		  res=new Neighbors();
		  Object[] neighborList=new Object[neighbors.length()];
		  int[] weights=new int[neighbors.length()];
		  if(neighbors.length()==0)return null;
		  DListNode first = (DListNode)neighbors.front();
		  for(int i=0;i<neighbors.length();i++){
			  neighborList[i]=first.item();
			  VertexPair key=new VertexPair(vertex,first.item());
			  weights[i]=(Integer)((VertexPair)eList.find(key).value()).object2;
			  first=(DListNode)first.next();
		  }
		  res.neighborList=neighborList;
		  res.weightList=weights;
	  }
	  return res;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u.equals(v)) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight){
	  if(vList.find(u)==null||vList.find(v)==null)return;
	  if(eList.find(new VertexPair(u,v))!=null){
		  ((VertexPair)eList.find(new VertexPair(u,v)).value()).object2=weight;
	  }else{
		  VertexPair pair=new VertexPair(u,v);
		  DListNode first=(DListNode)((DList)((VertexPair)((DListNode)vList.find(u).value()).item()).object2).insertBack(v);
		  DListNode second=first;
		  if(u!=v){
			  second=(DListNode)((DList)((VertexPair)((DListNode)vList.find(v).value()).item()).object2).insertBack(u);
		  }
		  VertexPair option = new VertexPair(new VertexPair(first,second),weight);
		  eList.insert(pair, option);
	  }
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v){
	  if(eList.find(new VertexPair(u,v))==null)return;
	  VertexPair pair=(VertexPair)eList.remove(new VertexPair(u,v)).value();
	  VertexPair nodes=(VertexPair)pair.object1;
	  ((DListNode)nodes.object1).remove();
	  if(nodes.object1!=nodes.object2)((DListNode)nodes.object2).remove();
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v){
	  return eList.find(new VertexPair(u,v))!=null;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v){
	  Entry e=eList.find(new VertexPair(u,v));
	  if(e!=null){
		  VertexPair pair=(VertexPair)e.value();
		  return (Integer)pair.object2;
	  }
	  return 0;
  }

}
