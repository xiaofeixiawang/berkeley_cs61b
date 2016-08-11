package graphalg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Edge implements Comparable{
	protected Object u;
	protected Object v;
	protected int weight;
	
	public Edge(Object u,Object v,int weight){
		this.u=u;
		this.v=v;
		this.weight=weight;
	}
	public Edge(){
		this(null,null,0);
	}
	public Object getU(){
		return u;
	}
	public Object getV(){
		return v;
	}
	public int getWeight(){
		return weight;
	}
	public int compareTo(Object o){
		Edge e =(Edge)o;
		return getWeight()-e.getWeight();
	}
	public int hashCode(){
		int vertices = Math.abs(u.hashCode()-v.hashCode()-weight);
		return vertices;
	}
	public boolean equals(Edge e){
		return (e.getU().equals(getU())&&e.getV().equals(getV())&&e.getWeight()==getWeight());
	}
	public String toString(){
		return getU().toString()+" "+getV().toString()+" "+getWeight();
	}
}
