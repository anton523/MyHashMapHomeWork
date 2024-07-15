import java.util.LinkedList;
/**
 * Собственная реализация HashMap с реализованными методами put, get, remove.
 * Реализовано увеличение массива бакетов при достижении порога значений в мапе.
 * Коллизии решаются при помощи метода цепочек.
 */
class Node<K, V> { // Вложенный вспомогательный класс, имеет поля хэш, ключ, значение.
    int hash;
    K key;
    V value;
    public Node(int hash, K key, V value) {
        this.hash= hash;
        this.key = key;
        this.value = value;
    }
}

public class MyHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 2;
    private final double loadFactor;
    private int threshold;
    private int size;
    private LinkedList<Node<K, V>>[] buckets;

    /**
     * Конструктор с передачей значений количества бакетов и лоадфактора(среднее значение элементов в каждом бакете при
     * превышении которого идёт увеличение количетсва бакетов в 2 раза)
     * Создаёт массив бакетов(связных списков).
     */
    public MyHashMap(int capacity, double loadFactor){
        buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++){
            buckets[i] = new LinkedList<>();
        }
        this.loadFactor = loadFactor;
        this.threshold = (int) (capacity * loadFactor);
        this.size = 0;
    }
    /**
     * Конструктор без передачи значений, использует значения заданные по умолчанию.
     */
    public MyHashMap(){
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public void put(K key, V value) {
        int index = hash(key); // Вычисляем индекс бакета с помощью метода hash
        for (Node<K, V> node : buckets[index]) { // Проходимся по элементам бакета(нодам) и действуем по условию,
            if (node.key.equals(key)){ // если ключи одинаковые, то перезапизываем значение по этому ключу.
                node.value = value;
                return;
            }
        }
        buckets[index].add(new Node<>(key.hashCode(), key, value)); // В случае если совпадений не было, создаём новый элемент бакета.
        size++;
        if (size > threshold) { // Если количество элементов в мапе превышает порог, то вызывается метод resize
            resize(2 * buckets.length);
        }
    }

    public V get(K key){
        int index = hash(key);
        for (Node<K, V> node :buckets[index]){ // Проходимся по элементам бакета(нодам) и действуем по условию,
            if (node.key.equals(key)){ // если ключи одинаковые, возвращаем значение соответствующее этому ключу, иначе возвращаем null.
                return node.value;
            }
        }
        return null;
    }

    public void remove(K key) {
        int index = hash(key);
        for (Node<K, V> node :buckets[index]) { // Проходимся по элементам бакета(нодам) и действуем по условию,
            if (node.key.equals(key)) { // если ключи одинаковые, удаляем этот элемент бакета.
                buckets[index].remove(node);
                size--;
                return;
            }
        }
    }
    private void resize(int newCapacity) {
        LinkedList<Node<K, V>>[] newBuckets = new LinkedList[newCapacity]; // Создаём новый увеличенный массив бакетов.
        for (int i = 0; i < newCapacity; i++) { // Заполняем его связными списками.
            newBuckets[i] = new LinkedList<>();
        }
        for (LinkedList<Node<K, V>> bucket : buckets) { // Заполняем бакеты нового массива значениями из старого, пересчитывая их индексы для равномерного распределения.
            for (Node<K, V> node : bucket) {
                int index = Math.abs(node.hash % newCapacity);
                newBuckets[index].add(node);
            }
        }
        buckets = newBuckets;
        threshold = (int) (newCapacity * loadFactor); // Перечитываем порог для увеличения массива.
    }

    private int hash(K key){ // Метод для получения случайного индекса в массиве посредством вычисления Хэша по ключу.
        return Math.abs(key.hashCode() % buckets.length);
    }
}
