# How to run the project:

The src folder contains compiled class files. You can just run the compiled class file with:

`java InventoryAllcator`

Or if you want to compile the class files on your own:

`javac InventoryAllocator.java` and then run the class file

`java InventoryAllocator`

You should be able to see messages like in your terminal running the test cases.

```
Test 1 passed!
Input: { apple: 1 }, [ { owd: { apple: 1 } } ]
Output: [ { owd: { apple: 1 } } ]

Test 2 passed!
Input: { apple: 1 }, [ { owd: { apple: 0 } } ]
Output: []

Test 3 passed!
Input: { apple: 10 }, [ { owd: { apple: 5 } }, { dm: { apple: 5 } } ]
Output: [ { owd: { apple: 5 } }, { dm: { apple: 5 } } ]

Test 4 passed!
Input: { banana: 5, orange: 5, apple: 5 }, [ { owd: { orange: 10, apple: 5 } }, { dm: { banana: 5, orange: 10 } } ]
Output: [ { owd: { orange: 5, apple: 5 } }, { dm: { banana: 5 } } ]

Test 5 passed!
Input: { banana: 5, orange: 5, apple: 5 }, [ { owd: { orange: 10, apple: 5 } }, { ma: { banana: 5, orange: 10 } }, null ]
Output: [ { owd: { orange: 5, apple: 5 } }, { ma: { banana: 5 } } ]

Test 6 passed!
Input: { banana: 5, orange: 5, apple: 5 }, []
Output: []

Test 7 passed!
Input: { banana: 5, orange: 5, apple: 5 }, [ { owd: { orange: 10, apple: 5 } }, { ma: { banana: 5, orange: 10 } }, null ]
Output: [ { owd: { orange: 5, apple: 5 } }, { ma: { banana: 5 } } ]

Test 8 passed!
Input: { banana: 5, orange: 5, apple: 5 }, [ null, null, null ]
Output: []

Test 9 passed!
Input: {}, [ { dm: { apple: 1 } } ]
Output: []

Test 10 passed!
Input: { orange: 1 }, [ { dm: { apple: 1 } } ]
Output: []

All tests passed!
```
