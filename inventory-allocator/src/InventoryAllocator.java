import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryAllocator {
    static class Supplier {
        String name;
        Map<String, Integer> inventory;

        public Supplier(String n, Map<String, Integer> i) {
            name = n;
            inventory = i;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{ ");
            builder.append(name + ": " + mapToString(inventory));
            builder.append(" }");
            return builder.toString();
        }
    }

    static class Allocation {
        String supplier;
        Map<String, Integer> produces;

        public Allocation(String s, Map<String, Integer> p) {
            supplier = s;
            produces = p;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{ ");
            builder.append(supplier + ": " + mapToString(produces));
            builder.append(" }");
            return builder.toString();
        }
    }

    public static List<Allocation> getAllocation(Map<String, Integer> order, Supplier[] suppliers) {
        Map<String, Integer> copy = new HashMap<>(order);
        List<Allocation> res = new ArrayList<>();
        for (Supplier supplier: suppliers) {
            if (supplier == null) {
                continue;
            }
            Map<String, Integer> produces = new HashMap<>();
            for (String produce: supplier.inventory.keySet()) {
                if (copy.containsKey(produce)) {
                    int num = Math.min(copy.get(produce), supplier.inventory.get(produce));
                    copy.put(produce, copy.get(produce) - num);
                    produces.put(produce, num);
                    if (copy.get(produce) == 0) {
                        copy.remove(produce);
                    }
                }
            }
            if (!produces.isEmpty()) {
                res.add(new Allocation(supplier.name, produces));
            }
            if (copy.isEmpty()) {
                break;
            }
        }
        if (!copy.isEmpty()) {
            return new ArrayList<>();
        }
        return res;
    }

    public static String mapToString(Map<String, Integer> map) {
        if (map.isEmpty()) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{ ");
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            builder.append(entry.getKey() + ": " + entry.getValue() + ", ");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" }");
        return builder.toString();
    }

    public static String arrayToString(Object[] array) {
        if (array.length == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[ ");
        for (Object o: array) {
            if (o == null) {
                builder.append("null");
            } else {
                builder.append(o.toString());
            }
            builder.append(", ");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" ]");
        return builder.toString();
    }

    public static String listToString(List<Allocation> list) {
        if (list.isEmpty()) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[ ");
        for (Object o: list) {
            if (o == null) {
                builder.append("null");
            } else {
                builder.append(o.toString());
            }
            builder.append(", ");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" ]");
        return builder.toString();
    }

    private static void printInput(Map<String, Integer> order, Supplier[] suppliers) {
        System.out.println("Input: " + mapToString(order) + ", " + arrayToString(suppliers));
    }

    private static void printOutput(List<Allocation> allocations) {
        System.out.println("Output: " + listToString(allocations));
    }

    private static void printTest(Map<String, Integer> order, Supplier[] suppliers, List<Allocation> allocations, int testNum) {
        System.out.println("Test " + testNum + " passed!");
        printInput(order, suppliers);
        printOutput(allocations);
        System.out.println();
    }

    public static void test1() {
        // Input: { apple: 1 }, [{ name: owd, inventory: { apple: 1 } }]
        // Output: [{ owd: { apple: 1 } }]

        Map<String, Integer> order = new HashMap<>();
        order.put("apple", 1);
        Supplier[] suppliers = new Supplier[1];
        suppliers[0] = new Supplier("owd", new HashMap<>());
        suppliers[0].inventory.put("apple", 1);

        List<Allocation> allocations = getAllocation(order, suppliers);
        assert(allocations.size() == 1);
        assert(allocations.get(0).supplier.equals("owd"));
        assert(allocations.get(0).produces.size() == 1);
        assert(allocations.get(0).produces.get("apple") == 1);

        printTest(order, suppliers, allocations, 1);
    }

    public static void test2() {
        // Input: { apple: 1 }, [{ name: owd, inventory: { apple: 0 } }]
        // Output: []

        Map<String, Integer> order = new HashMap<>();
        order.put("apple", 1);
        Supplier[] suppliers = new Supplier[1];
        suppliers[0] = new Supplier("owd", new HashMap<>());
        suppliers[0].inventory.put("apple", 0);

        List<Allocation> allocations = getAllocation(order, suppliers);
        assert(allocations.size() == 0);

        printTest(order, suppliers, allocations, 2);
    }

    public static void test3() {
        // Input: { apple: 10 }, [{ name: owd, inventory: { apple: 5 } }, { name: dm, inventory: { apple: 5 }}]
        // Output: [{ owd: { apple: 5 } }, { dm: { apple: 5 }}]

        Map<String, Integer> order = new HashMap<>();
        order.put("apple", 10);
        Supplier[] suppliers = new Supplier[2];
        suppliers[0] = new Supplier("owd", new HashMap<>());
        suppliers[0].inventory.put("apple", 5);
        suppliers[1] = new Supplier("dm", new HashMap<>());
        suppliers[1].inventory.put("apple", 5);

        List<Allocation> allocations = getAllocation(order, suppliers);

        assert(allocations.size() == 2);
        assert(allocations.get(0).supplier.equals("owd"));
        assert(allocations.get(0).produces.size() == 1);
        assert(allocations.get(0).produces.get("apple") == 5);
        assert(allocations.get(1).supplier.equals("dm"));
        assert(allocations.get(1).produces.size() == 1);
        assert(allocations.get(1).produces.get("apple") == 5);

        printTest(order, suppliers, allocations, 3);
    }

    public static void test4() {
        // Input: { apple: 5, banana: 5, orange: 5 }, [ { name: owd, inventory: { apple: 5, orange: 10 } }, { name: dm, inventory: { banana: 5, orange: 10 } } ]
        // Output: [{ owd: { apple: 5, orange: 5 } }, { dm: { banana: 5 }}]

        Map<String, Integer> order = new HashMap<>();
        order.put("apple", 5);
        order.put("banana", 5);
        order.put("orange", 5);
        Supplier[] suppliers = new Supplier[2];
        suppliers[0] = new Supplier("owd", new HashMap<>());
        suppliers[0].inventory.put("apple", 5);
        suppliers[0].inventory.put("orange", 10);
        suppliers[1] = new Supplier("dm", new HashMap<>());
        suppliers[1].inventory.put("banana", 5);
        suppliers[1].inventory.put("orange", 10);

        List<Allocation> allocations = getAllocation(order, suppliers);

        assert(allocations.size() == 2);
        assert(allocations.get(0).supplier.equals("owd"));
        assert(allocations.get(0).produces.size() == 2);
        assert(allocations.get(0).produces.get("apple") == 5);
        assert(allocations.get(0).produces.get("orange") == 5);
        assert(allocations.get(1).supplier.equals("dm"));
        assert(allocations.get(1).produces.size() == 1);
        assert(allocations.get(1).produces.get("banana") == 5);

        printTest(order, suppliers, allocations, 4);
    }

    public static void test5() {
        // Input: { apple: 5, banana: 5, orange: 5 }, [ { name: owd, inventory: { apple: 5, orange: 10 } }
        //          , { name: dm, inventory: { banana: 5, orange: 10 } }, { name: ma, inventory: { banana: 5, orange: 10 } } ]
        // Output: [{ owd: { apple: 5, orange: 5 } }, { dm: { banana: 5 }}]

        Map<String, Integer> order = new HashMap<>();
        order.put("apple", 5);
        order.put("banana", 5);
        order.put("orange", 5);
        Supplier[] suppliers = new Supplier[3];
        suppliers[0] = new Supplier("owd", new HashMap<>());
        suppliers[0].inventory.put("apple", 5);
        suppliers[0].inventory.put("orange", 10);
        suppliers[1] = new Supplier("dm", new HashMap<>());
        suppliers[1].inventory.put("banana", 5);
        suppliers[1].inventory.put("orange", 10);
        suppliers[1] = new Supplier("ma", new HashMap<>());
        suppliers[1].inventory.put("banana", 5);
        suppliers[1].inventory.put("orange", 10);

        List<Allocation> allocations = getAllocation(order, suppliers);

        assert(allocations.size() == 2);
        assert(allocations.get(0).supplier.equals("owd"));
        assert(allocations.get(0).produces.size() == 2);
        assert(allocations.get(0).produces.get("apple") == 5);
        assert(allocations.get(0).produces.get("orange") == 5);
        assert(allocations.get(1).supplier.equals("dm"));
        assert(allocations.get(1).produces.size() == 1);
        assert(allocations.get(1).produces.get("banana") == 5);

        printTest(order, suppliers, allocations, 5);
    }

    public static void test6() {
        // Input: { apple: 5, banana: 5, orange: 5 }, []
        // Output: []

        Map<String, Integer> order = new HashMap<>();
        order.put("apple", 5);
        order.put("banana", 5);
        order.put("orange", 5);
        Supplier[] suppliers = new Supplier[0];

        List<Allocation> allocations = getAllocation(order, suppliers);

        assert(allocations.size() == 0);

        printTest(order, suppliers, allocations, 6);
    }

    public static void test7() {
        // Input: { apple: 5, banana: 5, orange: 5 }, [ { name: owd, inventory: { apple: 5, orange: 10 } }
        //          , { name: dm, inventory: { apple: 5, orange: 10 } }, { name: ma, inventory: { banana: 5, orange: 10 } } ]
        // Output: [{ owd: { apple: 5, orange: 5 } }, { ma: { banana: 5 }}]
        Map<String, Integer> order = new HashMap<>();
        order.put("apple", 5);
        order.put("banana", 5);
        order.put("orange", 5);
        Supplier[] suppliers = new Supplier[3];
        suppliers[0] = new Supplier("owd", new HashMap<>());
        suppliers[0].inventory.put("apple", 5);
        suppliers[0].inventory.put("orange", 10);
        suppliers[1] = new Supplier("dm", new HashMap<>());
        suppliers[1].inventory.put("apple", 5);
        suppliers[1].inventory.put("orange", 10);
        suppliers[1] = new Supplier("ma", new HashMap<>());
        suppliers[1].inventory.put("banana", 5);
        suppliers[1].inventory.put("orange", 10);

        List<Allocation> allocations = getAllocation(order, suppliers);

        assert(allocations.size() == 2);
        assert(allocations.get(0).supplier.equals("owd"));
        assert(allocations.get(0).produces.size() == 2);
        assert(allocations.get(0).produces.get("apple") == 5);
        assert(allocations.get(0).produces.get("orange") == 5);
        assert(allocations.get(1).supplier.equals("ma"));
        assert(allocations.get(1).produces.size() == 1);
        assert(allocations.get(1).produces.get("banana") == 5);

        printTest(order, suppliers, allocations, 7);
    }

    public static void test8() {
        // Input: { apple: 5, banana: 5, orange: 5 }, [ null, null, null ]
        // Output: []
        Map<String, Integer> order = new HashMap<>();
        order.put("apple", 5);
        order.put("banana", 5);
        order.put("orange", 5);
        Supplier[] suppliers = new Supplier[3];

        List<Allocation> allocations = getAllocation(order, suppliers);

        assert(allocations.size() == 0);

        printTest(order, suppliers, allocations, 8);
    }

    public static void test9() {
        // Input: {}, [{ name: dm, inventory: { apple: 1 }]
        // Output: []
        Map<String, Integer> order = new HashMap<>();
        Supplier[] suppliers = new Supplier[1];
        suppliers[0] = new Supplier("dm", new HashMap<>());
        suppliers[0].inventory.put("apple", 1);

        List<Allocation> allocations = getAllocation(order, suppliers);
        assert(allocations.size() == 0);

        printTest(order, suppliers, allocations, 9);
    }

    public static void test10() {
        // Input: { orange: 1 }, [{ name: dm, inventory: { apple: 1 }]
        // Output: []
        Map<String, Integer> order = new HashMap<>();
        order.put("orange", 1);
        Supplier[] suppliers = new Supplier[1];
        suppliers[0] = new Supplier("dm", new HashMap<>());
        suppliers[0].inventory.put("apple", 1);

        List<Allocation> allocations = getAllocation(order, suppliers);
        assert(allocations.size() == 0);

        printTest(order, suppliers, allocations, 10);
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        test8();
        test9();
        test10();
        System.out.println("All tests passed!");
    }
}
