package from.wiki.strategy.sample1;

public interface BillingStrategy {
    double getActPrice(double rawPrice);

    String pay();
}
