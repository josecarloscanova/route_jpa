package com.toughtworks.path;

public class VertexDistance {

	private String nodeOrigin; 
	
	private String nodeTarget; 
	
	private int distance;

	public VertexDistance() {}
	
	public VertexDistance(String orig , String tgt , int dist)
	{ 
		this.nodeOrigin = orig; 
		this.nodeTarget = tgt; 
		this.distance = dist; 
	}
	
	public String getNodeOrigin() {
		return nodeOrigin;
	}

	public void setNodeOrigin(String nodeOrigin) {
		this.nodeOrigin = nodeOrigin;
	}

	public String getNodeTarget() {
		return nodeTarget;
	}

	public void setNodeTarget(String nodeTarget) {
		this.nodeTarget = nodeTarget;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "VertexDistance [nodeOrigin=" + nodeOrigin + ", nodeTarget=" + nodeTarget + ", distance=" + distance
				+ "]";
	} 
	
}
