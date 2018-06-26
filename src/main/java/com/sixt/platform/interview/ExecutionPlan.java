package com.sixt.platform.interview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class ExecutionPlan {

	private Map<Integer, List<Vertex>> executionPlans = new HashMap<>();

	public Optional<Integer> getIndex(Vertex vertex) {
		return this.executionPlans.keySet().stream().filter(key -> {
			return this.executionPlans.get(key).contains(vertex);
		}).findFirst();

	}

	public void add(Integer index, Vertex vertex) {

		Optional<Integer> oldIndex = getIndex(vertex);
		if (oldIndex.isPresent()) {
			if (oldIndex.get() > index) {
				return;
			} else {
				List<Vertex> vertices = this.executionPlans.get(oldIndex.get());
				vertices.remove(vertex);
			}
		}
		List<Vertex> vertices = this.executionPlans.get(index);
		if (vertices == null) {
			vertices = new ArrayList<>();
			this.executionPlans.put(index, vertices);

		}
		vertices.add(vertex);
	}

	public List<Object> getExecutionPlan() {
		List<Object> ret = new ArrayList<>();
		executionPlans.keySet().stream().sorted((o1, o2) -> {
			return o2 - o1;
		}).map(item -> executionPlans.get(item)).forEach(item -> {
			item.forEach(script -> {
				ret.add(script.getPayload());
			});
		});

		return ret;
	}

	public Stream<Object> toStream() {
		return getExecutionPlan().stream();
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		toStream().forEach(item -> ret.append(item.toString() + " "));
		return ret.toString().trim();
	}
 
}
