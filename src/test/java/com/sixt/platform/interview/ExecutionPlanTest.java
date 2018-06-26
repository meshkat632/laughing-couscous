package com.sixt.platform.interview;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

public class ExecutionPlanTest {
	

	@Test
	public void test_01() {
		Graph graph = Graph.buildGraphFromEdgeList(Arrays.asList("AB", "AD"));	
		assertEquals("B D A", graph.getExecutionPlan().toString());
	}
	

	@Test
	public void test_02() {
		Graph graph = Graph.buildGraphFromEdgeList(Arrays.asList("AB"));	
		assertEquals("B A", graph.getExecutionPlan().toString());
	}
	
	@Test
	public void test_03() {
		Graph graph = Graph.buildGraphFromEdgeList(Arrays.asList("AB", "BC", "CD"));	
		assertEquals("D C B A", graph.getExecutionPlan().toString());
	}
	
	@Test
	public void test_04() {
		Graph graph = Graph.buildGraphFromEdgeList(Arrays.asList("AB", "BC", "CD", "DE"));	
		assertEquals("E D C B A", graph.getExecutionPlan().toString());
	}
	
	
	@Test
	public void test_05() {
		Graph graph = Graph.buildGraphFromEdgeList(Arrays.asList("AB", "BC", "CD", "DE", "BE"));	
		assertEquals("E D C B A", graph.getExecutionPlan().toString());	
	}
	
	@Test
	public void test_06() {
		Graph graph = Graph.buildGraphFromEdgeList(Arrays.asList("AB", "BE", "BC", "CD", "DE" ));	
		assertEquals("E D C B A", graph.getExecutionPlan().toString());	
	}
	
	@Test
	public void test_07() {
		Graph graph = Graph.buildGraphFromEdgeList(Arrays.asList("AB", "BE", "BC", "CD", "DE","CE", "AE" ));		
		assertEquals("E D C B A", graph.getExecutionPlan().toString());	
	}

	@Test
	public void test_08() {
		Graph graph = Graph.buildGraphFromEdgeList(Arrays.asList("AB", "AC", "AD", "BE", "CE","DE"));	
		assertEquals("E B C D A", graph.getExecutionPlan().toString());	
	}
	
	
	@Test
	public void test_09() {
		Graph graph = Graph.buildGraphFromEdgeList(Arrays.asList("BE", "CE","DE", "AB", "AC", "AD"));	
		assertEquals("E B C D A", graph.getExecutionPlan().toString());	
	}
	 
	@Test 
	public void test_10() {		
		
		VulnerabilityScript script1 = new VulnerabilityScript(1, new ArrayList<>());
		VulnerabilityScript script2 = new VulnerabilityScript(2, new ArrayList<>());
		VulnerabilityScript script3 = new VulnerabilityScript(3, new ArrayList<>());
		VulnerabilityScript script4 = new VulnerabilityScript(4, new ArrayList<>());
		
		VulnerabilityScript script5 = new VulnerabilityScript(5, Arrays.asList(1,2));
		VulnerabilityScript script6 = new VulnerabilityScript(6, Arrays.asList(3,4));
		VulnerabilityScript script7 = new VulnerabilityScript(7, new ArrayList<>());		
		VulnerabilityScript script8 = new VulnerabilityScript(8, Arrays.asList(5,6, 7));	

		
		
		Graph graph =  Graph.buildGraphFromVulnerabilityScriptList(Arrays.asList(script1, script2, script3, script4,script5, script6, script7, script8));
		
		assertEquals(Arrays.asList(script1, script2, script3, script4,script5, script6, script7, script8), graph.getExecutionPlan().toStream().collect(Collectors.toList()));		
					
	}
		

}
