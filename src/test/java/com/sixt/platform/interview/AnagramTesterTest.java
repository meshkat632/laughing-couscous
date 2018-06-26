package com.sixt.platform.interview;

import static org.junit.Assert.*;

import org.junit.Test;

public class AnagramTesterTest {
	
	@Test (expected = AssertionError.class)
	public void test_01() {		
		AnagramTester.isAnagram(null, null);		
	}
	
	@Test (expected = AssertionError.class)
	public void test_02()  {
		AnagramTester.isAnagram(null, "");		
	}
	
	@Test (expected = AssertionError.class)
	public void test_03()  {
		AnagramTester.isAnagram("", null);		
	}
	
	@Test
	public void test_04()  {
		assertFalse(AnagramTester.isAnagram("", "asdf"));		
	}
	
	@Test
	public void test_05()  {
		assertFalse(AnagramTester.isAnagram("asdf", ""));		
	}
	
	@Test(expected = AssertionError.class)
	public void test_06()  {
		assertFalse(AnagramTester.isAnagram("asdf", null));		
	}
	
	@Test(expected = AssertionError.class)
	public void test_07()  {
		assertFalse(AnagramTester.isAnagram(null, "asdf"));		
	}
	
	@Test
	public void test_08()  {
		assertFalse(AnagramTester.isAnagram("a", "b"));		
	}
	
	@Test
	public void test_09()  {
		assertTrue(AnagramTester.isAnagram("", ""));		
	}
	
	@Test
	public void test_10()  {
		assertTrue(AnagramTester.isAnagram("a", "a"));		
	}
	@Test
	public void test_11()  {
		assertTrue(AnagramTester.isAnagram("abc", "abc"));		
	}
	
	@Test
	public void test_12()  {
		assertTrue(AnagramTester.isAnagram("abc", "cba"));		
	}
	
	@Test
	public void test_13()  {
		assertTrue(AnagramTester.isAnagram("aaa", "aaa"));		
	}	
	
	
	@Test
	public void test_14()  {
		assertFalse(AnagramTester.isAnagram("aaa", "aa"));		
	}
	
	@Test
	public void test_15()  {
		assertTrue(AnagramTester.isAnagram("anagram", "nagaram"));		
	}
	
	@Test
	public void test_16()  {
		assertFalse(AnagramTester.isAnagram("anagra", "nagaram"));		
	}
	@Test
	public void test_17()  {
		assertFalse(AnagramTester.isAnagram("anagram", "nagara"));		
	}

}
