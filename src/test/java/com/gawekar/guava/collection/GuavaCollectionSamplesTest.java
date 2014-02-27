package com.gawekar.guava.collection;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;

public class GuavaCollectionSamplesTest {

    /**
     * About Multiset : (for details see
     * http://code.google.com/p/guava-libraries
     * /wiki/NewCollectionTypesExplained) 1. This is like an ArrayList<E>
     * without an ordering constraint 2. Multiset Is Not A Map. (But is like a
     * Map<E, Integer>, with elements and counts. )
     */
    @Test
    public void multiSet() {
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("a");
        multiset.add("b");
        multiset.add("c", 2);

        TestCase.assertEquals(5, multiset.size());
        TestCase.assertEquals(2, multiset.count("a"));
        TestCase.assertEquals(1, multiset.count("b"));
        TestCase.assertEquals(2, multiset.count("c"));
    }

    @Test
    public void arrayListMultimap() {
        Multimap<Integer, String> multiMap = ArrayListMultimap.create();

        multiMap.put(100, "a");
        multiMap.put(100, "a");
        // Note size is 2
        TestCase.assertEquals(2, multiMap.size());
        TestCase.assertEquals(2, multiMap.get(100).size());

        // Remove one 'a'
        TestCase.assertTrue(multiMap.remove(100, "a"));
        TestCase.assertEquals(1, multiMap.size());

    }

    @Test
    public void hashMultimap() {
        Multimap<Integer, String> multiMap = HashMultimap.create();
        multiMap.put(100, "a");
        multiMap.put(100, "a");
        // Note size is 1
        TestCase.assertEquals(1, multiMap.size());
        multiMap.put(100, "b");
        TestCase.assertEquals(2, multiMap.size());
    }

    /**
     * For BiMap -> (key,value) has to be unique.
     */
    @Test(expected = IllegalArgumentException.class)
    public void biMap_duplicate_values_not_allowed() {
        BiMap<String, Integer> userId = HashBiMap.create();

        userId.put("aaa", 1001);
        userId.put("bbb", 1001);
    }

    @Test
    public void biMap() {
        BiMap<String, Integer> userIds = HashBiMap.create();

        userIds.put("aaa", 1001);
        userIds.put("bbb", 1002);

        TestCase.assertEquals(2, userIds.size());
        TestCase.assertEquals(new Integer(1001), userIds.get("aaa"));
        TestCase.assertEquals("aaa", userIds.inverse().get(new Integer(1001)));
    }

    @Test
    public void table() {
        Table<String, String, String> table = HashBasedTable.create();
        
        table.put("key1_x", "key2_x", "a");
        table.put("key1_y", "key2_x", "b");
        
        //table.put("key1_y", "key2_x", "y");
        
        System.out.println(table.column("key2_x"));
        System.out.println(table.row("key2_x"));
        

    }


}
