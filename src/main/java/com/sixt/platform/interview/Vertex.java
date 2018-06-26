package com.sixt.platform.interview;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	private String name;
	private Object payload;

	private List<Edge> outgoingEdges = new ArrayList<>();
	private List<Edge> incomingEdges = new ArrayList<>();

	public Vertex(String name) {		
		this.name = name;
		this.outgoingEdges = new ArrayList<>();
		this.incomingEdges = new ArrayList<>();
	}
	

	public String getName() {
		return name;
	}

	public List<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}

	public List<Edge> getIncomingEdges() {
		return incomingEdges;
	}

	public void addOutGoingEdge(Edge edge) {
		if(!outgoingEdges.contains(edge))
			outgoingEdges.add(edge);		
	}

	public void addIncomingEdge(Edge edge) {
		if(!incomingEdges.contains(edge))
			incomingEdges.add(edge);
	}

	 
	@Override
	public String toString() {
		return name;

	}

	public void setPayload(Object payload) {
		this.payload = payload;  
		
	}
	public Object getPayload() {
		return this.payload;  
		
	}

}