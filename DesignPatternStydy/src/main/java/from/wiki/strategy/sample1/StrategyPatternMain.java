package from.wiki.strategy.sample1;

public class StrategyPatternMain {

    public static void main(String[] args) {
        Customer a = new Customer(new CashStrategy());

        // Normal billing
        a.add(1.0, 1);

        // Start Happy Hour
        a.setStrategy(new CreditCardStrategy("1234-2345-3456-4567"));
        a.add(1.0, 2);

        // New Customer
        Customer b = new Customer(new CreditCardStrategy("9876-8765-7654-6543"));
        b.add(0.8, 1);
        // The Customer pays
        a.printBill();

        // End Happy Hour
        b.setStrategy(new CashStrategy());
        b.add(1.3, 2);
        b.add(2.5, 1);
        b.printBill();

    }
}
