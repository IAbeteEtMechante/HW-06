package space.harbour.java.hw5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;



public class MyHashMapTest {
    private MyHashMap<String, String> contacts;

    @Before
    public void createHashmap() {
        contacts = new MyHashMap<String, String>();
    }

    //test01: adding a pair
    @Test
    public void addingOnePair() {
        contacts.put("Karim", "123-456-789");
        assertEquals(contacts.size(), 1);
        assertTrue(contacts.containsKey("Karim"));
        assertTrue(contacts.containsValue("123-456-789"));
    }

    //test02: removing a pair
    @Test
    public void removingOnePair() {
        contacts.put("Patricia", "123-456-789");
        contacts.remove("Patricia");

        assertEquals(contacts.size(), 0);
        assertFalse(contacts.containsKey("Patricia"));
        assertFalse(contacts.containsValue("123-456-789"));
    }

    //test03: changing value by key
    @Test
    public void changeValueByKey() {
        contacts.put("Duc", "123-456-789");
        contacts.put("Duc", "888-888-888");
        assertFalse(contacts.containsValue("123-456-789"));
        assertTrue(contacts.containsValue("888-888-888"));


    }

    //test04: retrieving a value by non-existent key
    @Test
    public void retrieveValueFromNonExistentKey() {
        assertNull(contacts.get("Pierre"));
        contacts.put("Ahmed", "123-456-789");
        assertNull(contacts.get("Pierre"));
        contacts.put("Vasilii", "888-888-888");
        assertNull(contacts.get("Pierre"));
    }

    //test05: adding a pair with key = null
    @Test (expected = NullPointerException.class)
    public void addNullKey() {
        contacts.put("Ahmed", "123-456-789");
        contacts.put(null, "987-654-321");

    }

    //test06: adding a pair with value = null
    @Test
    public void addNullValue() {
        contacts.put("Ahmed", "123-456-789");
        contacts.put("Pierre", null);
        assertNull(contacts.get("Pierre"));
    }

    //test07: adding many pairs then removing them and checking that map is empty
    @Test
    public void addAndRemoveManyPairs() {
        for (int i = 0; i < 10; i++) {
            contacts.put("Student" + String.valueOf(i), "123-456-" + String.valueOf(i));
        }
        for (int i = 0; i < 10; i++) {
            contacts.remove("Student" + String.valueOf(i));
        }
        assertTrue(contacts.isEmpty());

    }

    //test08: general correctness of size()
    @Test
    public void size() {
        for (int i = 0; i < 100; i++) {
            contacts.put("Ahmed" + String.valueOf(i), String.valueOf(i));
        }
        assertEquals(contacts.size(), 100);
    }

    //test09:  extreme conditions for size()
    @Test
    public void sizeForBigOrSmallHashMap() {
        //empty one
        assertEquals(contacts.size(), 0);

        //one element
        contacts.put("Ahmed", "123-456-789");
        assertEquals(contacts.size(), 1);

        //big one
        for (int i = 1; i < 100_000; i++) {
            contacts.put("Student" + String.valueOf(i), String.valueOf(i));
        }
        assertEquals(contacts.size(), 100_000);
    }

    //test10 : check isEmpty() method
    @Test
    public void isEmpty() {
        assertEquals(contacts.size(), 0);
    }

    //test11 : check containsKey() method
    @Test
    public void containsKey() {
        contacts.put("Alejandro", "888-888-888");
        assertTrue(contacts.containsKey("Alejandro"));
        assertFalse(contacts.containsKey("Pierre"));
    }

    //test12 : check containsValue() method
    @Test
    public void containsValue() {
        contacts.put("Sarah", "888-888-888");
        assertTrue(contacts.containsValue("888-888-888"));
        assertFalse(contacts.containsKey("123-456-789"));
    }

    //test13 : check get() method
    @Test
    public void get() {
        contacts.put("Vasilii", "888-888-888");
        assertEquals(contacts.get("Vasilii"), "888-888-888");

    }

    //test14: check what happens if we try to get a value for a key not present
    @Test
    public void getInexistentKey() {
        contacts.put("Alex", "888-888-888");
        assertNull(contacts.get("Pierre"));

    }

    //test15: general correctness of put function
    @Test
    public void put() {
        contacts.put("Berend", "123-456-789");
        assertTrue(contacts.containsKey("Berend"));
        assertTrue(contacts.containsValue("123-456-789"));
        assertFalse(contacts.containsKey("Pierre"));
        assertFalse(contacts.containsValue("888-888-888"));
    }


    //test16 : we want to test put function a bit more, because we had issues on it
    //         at HW-03. Let's try to put many keys.
    @Test
    public void putBigAmountOfKeys() {
        for (int i = 0; i < 10000; i++) {
            contacts.put("Student" + String.valueOf(i), "123-456-" + String.valueOf(i));
        }
        for (int i = 0; i < 10000; i++) {
            assertTrue(contacts.containsKey("Student" + String.valueOf(i)));
            assertTrue(contacts.containsValue("123-456-" + String.valueOf(i)));
        }
    }

    //test17 : we also want to be sure that if we put another value on the same key
    //          the old value will be replaced
    @Test
    public void putDifferentValueOnSameKey() {
        contacts.put("Victor", "888-888-888");
        contacts.put("Victor", "123-456-789");
        assertEquals(contacts.get("Victor"), "123-456-789");
        assertFalse(contacts.containsValue("888-888-888"));
        assertTrue(contacts.containsValue("123-456-789"));
    }


    //test18 : we want to be sure that we can differentiate capital letters
    @Test
    public void putSameNameDifferentCase() {
        contacts.put("Parshad", "888-888-888");
        contacts.put("parshad", "123-456-789");
        assertEquals(contacts.get("parshad"), "123-456-789");
        assertEquals(contacts.get("Parshad"), "888-888-888");
    }

    //test19: check putAll method
    @Test
    public void putAll() {
        for (int i = 0; i < 10000; i++) {
            contacts.put("Student" + String.valueOf(i), "123-456-" + String.valueOf(i));
        }
        MyHashMap<String, String> copyOfContacts = new MyHashMap<>();
        copyOfContacts.putAll(contacts);

        for (int i = 0; i < 10000; i++) {
            assertEquals(copyOfContacts.get("Student" + String.valueOf(i)),
                    "123-456-" + String.valueOf(i));
        }
        assertEquals(copyOfContacts.size(), 10000);


    }

    //test20 :check clear method
    @Test
    public void clear() {
        for (int i = 0; i < 10000; i++) {
            contacts.put("Student" + String.valueOf(i), "123-456-" + String.valueOf(i));
        }
        contacts.clear();
        assertEquals(contacts.size(), 0);
        assertTrue(contacts.isEmpty());
    }

    //test21
    @Test
    public void keySet() {
        for (int i = 0; i < 10000; i++) {
            contacts.put("Student" + String.valueOf(i), "123-456-" + String.valueOf(i));
        }
        Set<String> mySet = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            mySet.add("Student" + String.valueOf(i));
        }

        assertEquals(contacts.keySet(), mySet);
    }

    //test22
    @Test
    public void values() {
        for (int i = 0; i < 10000; i++) {
            contacts.put("Student" + String.valueOf(i), "123-456-" + String.valueOf(i));
        }
        Collection<String> myCollection = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            myCollection.add("123-456-" + String.valueOf(i));
        }

        assertEquals(contacts.values(), myCollection);
    }

    //test23: test entrySet() method
    //@Test
    //public void entrySet() {
    //    for (int i = 0; i < 10000; i++) {
    //        contacts.put("Student" + String.valueOf(i), "123-456-" + String.valueOf(i));
    //    }
    //    MyHashMap.Pair<String, String> pair = new MyHashMap.Pair<String, String>();
    //    for (int i = 0; i < 10000; i++) {
    //        pair.setKey("Student" + String.valueOf(i));
    //        pair.setValue("123-456-" + String.valueOf(i));
    //        assertTrue(contacts.entrySet().contains(pair));
    //    }
    //}

    //test24: check what happens if we clear() an empty hashmap
    @Test
    public void clearWhenEmpty() {
        contacts.clear();
        assertTrue(contacts.isEmpty());
        assertEquals(contacts.size(), 0);
    }


    //test25: check what happens if we clear() an empty hashmap 10000 times
    @Test
    public void clearWhenEmptyManyTimes() {
        for (int i = 0; i < 10_000; i++) {
            contacts.clear();
        }
        assertTrue(contacts.isEmpty());
        assertEquals(contacts.size(), 0);
    }

    //test26: check that when we remove pairs with the same value, we keep the keys we didn't remove
    @Test
    public void removeValueForDifferentKey() {
        contacts.put("Patricia", "123-456-789");
        contacts.put("Duc", "123-456-789");
        contacts.remove("Patricia");
        assertEquals(contacts.get("Duc"), "123-456-789");
    }

    //test27: check that if we add several times the same key value pair, it is
    //          stored exactly one time
    @Test
    public void addSamePairManyTimes() {

        for (int i = 0; i < 10000; i++) {
            contacts.put("Karim", "111-111-111");
        }
        assertEquals(contacts.size(), 1);
        assertEquals(contacts.get("Karim"), "111-111-111");
        assertTrue(contacts.containsValue("111-111-111"));
        assertTrue(contacts.containsKey("Karim"));
    }

    //test28: check that nothing happens if we apply putAll on the same Hashmap
    @Test
    public void putAllOnItself() {
        for (int i = 0; i < 10; i++) {
            contacts.put("Student" + String.valueOf(i), "123-456-" + String.valueOf(i));
        }
        contacts.putAll(contacts);

        assertEquals(contacts.size(), 10);

        for (int i = 0; i < 10; i++) {
            assertEquals(contacts.get("Student" + String.valueOf(i)),
                    "123-456-" + String.valueOf(i));
        }

    }

}