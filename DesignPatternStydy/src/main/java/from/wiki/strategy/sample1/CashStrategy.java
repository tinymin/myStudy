package from.wiki.strategy.sample1;

// Normal billing from.wiki.strategy (unchanged price)
class CashStrategy implements BillingStrategy {
    @Override
    public double getActPrice(double rawPrice) {
        return rawPrice;
    }

    @Override
    public String pay() {
        return "현금 결제";
    }

}
