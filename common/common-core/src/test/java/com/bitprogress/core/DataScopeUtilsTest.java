package com.bitprogress.core;

import com.bitprogress.util.DataScopeUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class DataScopeUtilsTest {

    public static void main(String[] args) {
        testOnFullLink();
        testOnFullLinkWithSet();
        testOnUpperLink();
        testOnUpperLinkWithSet();
        testOnLowerLink();
        testOnLowerLinkWithSet();
        testGetFullLink();
        testGetCompressFullLink();
        testGetUpperLink();
        testGetLowerLink();
        testCompressDataScopes();
        testCompressDataScopesWithPredicate();
    }

    public static void testOnFullLink() {
        System.out.println("Testing onFullLink...");
        assertTrue(DataScopeUtils.onFullLink("A1B2", "A1"), "Test 1 Failed");
        assertTrue(DataScopeUtils.onFullLink("A1", "A1B2"), "Test 2 Failed");
        assertFalse(DataScopeUtils.onFullLink("A1B2C3", "A2"), "Test 3 Failed");
        assertFalse(DataScopeUtils.onFullLink("A1", "A2B2C3"), "Test 4 Failed");
        System.out.println("All tests passed for onFullLink.");
    }

    public static void testOnFullLinkWithSet() {
        System.out.println("Testing onFullLinkWithSet...");
        Set<String> referenceDataScopes = Set.of("A1B2", "B2C3");
        assertTrue(DataScopeUtils.onFullLink(referenceDataScopes, "A1"), "Test 1 Failed");
        assertTrue(DataScopeUtils.onFullLink(referenceDataScopes, "B2"), "Test 2 Failed");
        assertFalse(DataScopeUtils.onFullLink(referenceDataScopes, "A2"), "Test 3 Failed");
        System.out.println("All tests passed for onFullLinkWithSet.");
    }

    public static void testOnUpperLink() {
        System.out.println("Testing onUpperLink...");
        assertTrue(DataScopeUtils.onUpperLink("A1B2", "A1"), "Test 1 Failed");
        assertFalse(DataScopeUtils.onUpperLink("A1", "A1B2"), "Test 2 Failed");
        assertFalse(DataScopeUtils.onUpperLink("A1B2C3", "A2"), "Test 3 Failed");
        assertFalse(DataScopeUtils.onUpperLink("A1", "A2B2C3"), "Test 4 Failed");
        System.out.println("All tests passed for onUpperLink.");
    }

    public static void testOnUpperLinkWithSet() {
        System.out.println("Testing onUpperLinkWithSet...");
        Set<String> referenceDataScopes = Set.of("A1B2", "B2C3");
        assertTrue(DataScopeUtils.onUpperLink(referenceDataScopes, "A1"), "Test 1 Failed");
        assertTrue(DataScopeUtils.onUpperLink(referenceDataScopes, "B2"), "Test 2 Failed");
        assertFalse(DataScopeUtils.onUpperLink(referenceDataScopes, "A2"), "Test 3 Failed");
        System.out.println("All tests passed for onUpperLinkWithSet.");
    }

    public static void testOnLowerLink() {
        System.out.println("Testing onLowerLink...");
        assertTrue(DataScopeUtils.onLowerLink("A1", "A1B2"), "Test 1 Failed");
        assertFalse(DataScopeUtils.onLowerLink("A1B2", "A1"), "Test 2 Failed");
        assertFalse(DataScopeUtils.onLowerLink("A1B2C3", "A2"), "Test 3 Failed");
        assertFalse(DataScopeUtils.onLowerLink("A1", "A2B2C3"), "Test 4 Failed");
        System.out.println("All tests passed for onLowerLink.");
    }

    public static void testOnLowerLinkWithSet() {
        System.out.println("Testing onLowerLinkWithSet...");
        Set<String> referenceDataScopes = Set.of("A1", "B2");
        assertTrue(DataScopeUtils.onLowerLink(referenceDataScopes, "A1B2"), "Test 1 Failed");
        assertTrue(DataScopeUtils.onLowerLink(referenceDataScopes, "B2C3"), "Test 2 Failed");
        assertFalse(DataScopeUtils.onLowerLink(referenceDataScopes, "A2"), "Test 3 Failed");
        System.out.println("All tests passed for onLowerLinkWithSet.");
    }

    public static void testGetFullLink() {
        System.out.println("Testing getFullLink...");
        Set<String> dataScopes = Set.of("A1", "A2", "A1B2", "A1B2C3", "A2B2", "A1B1C3");
        Set<String> expected = Set.of("A1", "A1B2", "A1B2C3");
        assertEquals(expected, DataScopeUtils.getFullLink("A1B2", dataScopes), "Test 1 Failed");
        System.out.println("All tests passed for getFullLink.");
    }

    public static void testGetCompressFullLink() {
        System.out.println("Testing getCompressFullLink...");
        Set<String> dataScopes = Set.of("A1", "A2", "A1B2", "A1B2C3", "A2B2", "A1B1C3");
        Set<String> expected = Set.of("A1");
        assertEquals(expected, DataScopeUtils.getCompressFullLink("A1B2", dataScopes), "Test 1 Failed");
        System.out.println("All tests passed for getCompressFullLink.");
    }

    public static void testGetUpperLink() {
        System.out.println("Testing getUpperLink...");
        Set<String> dataScopes = Set.of("A1", "A2", "A1B2", "A1B2C3", "A2B2", "A1B1C3");
        Set<String> expected = Set.of("A1", "A1B2");
        assertEquals(expected, DataScopeUtils.getUpperLink("A1B2", dataScopes), "Test 1 Failed");
        System.out.println("All tests passed for getUpperLink.");
    }

    public static void testGetLowerLink() {
        System.out.println("Testing getLowerLink...");
        Set<String> dataScopes = Set.of("A1", "A2", "A1B2", "A1B2C3", "A2B2", "A1B1C3");
        Set<String> expected = Set.of("A1B2", "A1B2C3");
        assertEquals(expected, DataScopeUtils.getLowerLink("A1B2", dataScopes), "Test 1 Failed");
        System.out.println("All tests passed for getLowerLink.");
    }

    public static void testCompressDataScopes() {
        System.out.println("Testing compressDataScopes...");
        Set<String> dataScopes1 = Set.of("A1B2", "A2B3", "A3B2", "A3B1");
        Set<String> dataScopes2 = Set.of("A1", "A2", "A1B2", "A1B2C3", "A2B2", "A1B1C3", "A3B2C3");
        Set<String> expected = Set.of("A1", "A2", "A3B2", "A3B1");
        assertEquals(expected, DataScopeUtils.compressDataScopes(dataScopes1, dataScopes2), "Test 1 Failed");
        System.out.println("All tests passed for compressDataScopes.");
    }

    public static void testCompressDataScopesWithPredicate() {
        System.out.println("Testing compressDataScopesWithPredicate...");
        Set<String> dataScopes1 = Set.of("A1B2", "A2B3", "A3B2", "A3B1");
        Set<String> dataScopes2 = Set.of("A1", "A2", "A1B2", "A1B2C3", "A2B2", "A1B1C3", "A3B2C3");
        Set<String> expected = Set.of("A1");
        Predicate<String> predicate = dataScope -> DataScopeUtils.onFullLink("A1", dataScope);
        assertEquals(expected, DataScopeUtils.compressDataScopes(predicate, dataScopes1, dataScopes2), "Test 1 Failed");
        System.out.println("All tests passed for compressDataScopesWithPredicate.");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(Set<String> expected, Set<String> actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + " Expected: " + expected + " but was: " + actual);
        }
    }

    private static void assertEquals(Set<String> expected, Set<String> actual) {
        assertEquals(expected, actual, "Test Failed");
    }
}
