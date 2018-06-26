package com.sixt.platform.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Graph {

	private List<Vertex> vertices;
	private List<Edge> edges;

	Graph() {
		this.vertices = new ArrayList<>();
		this.edges = new ArrayList<>();
	}

	private boolean hasEdge(Edge edge) {
		return edges.contains(edge);
	}

	private void addVertex(Vertex vertex) {
		if (vertices.contains(vertex)) {
			System.out.println("vertex:" + vertex + " already exits.");
		} else
			vertices.add(vertex);
	}

	private void addEdge(Edge edge) {
		if (hasEdge(edge)) {
			System.out.println("edge:" + edge + " already exits.");
		} else {
			Vertex source = edge.getSource();
			Vertex destination = edge.getDestination();
			if (!vertices.contains(source)) {
				addVertex(source);
				source.addOutGoingEdge(edge);
			}
			if (!vertices.contains(destination)) {
				addVertex(destination);
				destination.addIncomingEdge(edge);
			}

			edges.add(edge);
		}
	}

	private Vertex getVertexWithName(String name) {
		return vertices.stream().filter(vertex -> vertex.getName().equals(name)).findAny().orElse(null);
	}

	private void addEdge(String source, String destination, int distance) {
		Vertex sourceVertext = getVertexWithName(source);
		Vertex destinationVertext = getVertexWithName(destination);
		if (sourceVertext == null) {
			sourceVertext = new Vertex(source);
			sourceVertext.setPayload(source);
		}
		if (destinationVertext == null) {
			destinationVertext = new Vertex(destination);
			destinationVertext.setPayload(destination);
		}
		addEdge(new Edge(sourceVertext, destinationVertext, distance));
	}

	private void addEdge(VulnerabilityScript source, VulnerabilityScript destination) {
		Vertex sourceVertext = getVertexWithName(source.getScriptId() + "");
		Vertex destinationVertext = getVertexWithName(destination.getScriptId() + "");
		if (sourceVertext == null) {
			sourceVertext = new Vertex(source.getScriptId() + "");
			sourceVertext.setPayload(source);
		}
		if (destinationVertext == null) {
			destinationVertext = new Vertex(destination.getScriptId() + "");
			destinationVertext.setPayload(destination);
		}
		addEdge(new Edge(sourceVertext, destinationVertext, 0));
	}

	@Override
	public String toString() {
		return edges.stream()
				.map(edge -> edge.getSource().getName() + "" + edge.getDestination().getName() + "" + edge.getWeight())
				.reduce((edge1, edge2) -> edge1 + ", " + edge2).orElse("");
	}

	/*
	 * builder methods.
	 */

	public static Graph buildGraphFromEdgeList(List<String> edgesAsStr) {

		Graph graph = new Graph();
		edgesAsStr.forEach(edge -> {
			if (!edge.matches("[A-Z]{2}")) {
				throw new IllegalArgumentException(
						"invalid format for edge input. e.g AB or CD. Please confirm to the regex [A-Z]{2}");
			}
			String source = edge.charAt(0) + "";
			String destination = edge.charAt(1) + "";
			int distance = 0;
			graph.addEdge(source, destination, distance);
		});
		return graph;
	}

	public static Graph buildGraphFromVulnerabilityScriptList(List<VulnerabilityScript> scripts) {

		Graph graph = new Graph();
		scripts.forEach(script -> {
			int sourceId = script.getScriptId();
			script.getDependencies().forEach(destination -> {
				Optional<VulnerabilityScript> sourceScript = getScriptWithId(scripts, sourceId);
				Optional<VulnerabilityScript> dependentScript = getScriptWithId(scripts, destination);
				if (sourceScript.isPresent() && dependentScript.isPresent())
					graph.addEdge(sourceScript.get(), dependentScript.get());
			});
		});
		return graph;
	}

	private static Optional<VulnerabilityScript> getScriptWithId(List<VulnerabilityScript> scripts, Integer scriptId) {
		return scripts.stream().filter(item -> item.getScriptId() == scriptId).findAny();
	}

	private void buildExecutionPlan(Vertex vertex, int index, ExecutionPlan executionPlan) {
		executionPlan.add(index, vertex);

		vertex.getOutgoingEdges().stream().forEach(edge -> {
			buildExecutionPlan(edge.getDestination(), index + 1, executionPlan);
		});

	}

	public ExecutionPlan getExecutionPlan() {
		ExecutionPlan executionPlan = new ExecutionPlan();
		vertices.stream().filter(item -> item.getIncomingEdges().isEmpty()).forEach(item -> {
			buildExecutionPlan(item, 0, executionPlan);
		});
		return executionPlan;

	}
}
