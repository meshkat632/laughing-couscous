package com.sixt.platform.interview;

public class Edge  {	
	
    private final Vertex source;
    private final Vertex destination;
    private final int weight;
    
    public Edge(Vertex source, Vertex destination, int weight) {        
        this.source = source;
        this.destination = destination;
        this.weight = weight;        
        this.source.addOutGoingEdge(this);
        this.destination.addIncomingEdge(this);
    }
    
    public Vertex getDestination() {
        return destination;
    }

    public Vertex getSource() {
        return source;
    }
    public int getWeight() {
        return weight;
    }
	
	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());		
		result = prime * result + ((source == null) ? 0 : source.hashCode());
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
		Edge other = (Edge) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}

	

    @Override
    public String toString() {
        return source.toString() + " -> " + destination.toString()+":"+weight;
    }
}