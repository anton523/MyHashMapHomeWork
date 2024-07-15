import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyHashMapTest {
    private final MyHashMap<String, Integer> map = new MyHashMap<>();
    @Test
    public void testPutGet(){
        map.put("Key1", 1);
        Assertions.assertEquals(1, map.get("Key1"));
    }
    @Test
    public void testRemove(){
        map.put("Key1", 1);
        map.remove("Key1");
        Assertions.assertNull(map.get("Key1"));
    }
    @Test
    public void testOverwrite(){
        map.put("Key1", 1);
        map.put("Key1", 2);
        Assertions.assertEquals(2, map.get("Key1"));
    }
    @Test
    public void testResize(){
        for (int i = 0; i < 10000; i++) {
            map.put("Key" + i, i);
        }
        for (int i = 0; i < 10000; i++) {
            Assertions.assertEquals(i, map.get("Key" + i));
        }
    }
}
