# Checkout Kata (Java 8)

## Usage

Build and run tests:
```
mvn clean install
```

Run main method with a list of SKUs in the basket:
```
mvn compile exec:java -Dexec.mainClass="com.rntech.checkout.Main" -Dexec.args="A A A B B C D"
```

### Assumptions

Some assumptions/considerations:

* Assumed input is santised and well formed when running main class
* All prices in pence and Integer is big enough for our use case (no overflow)
* SKU was wrapped so type can easily be changed, Char would have worked but opted for String
* Functional style where appropriate
* Omitted javadoc
