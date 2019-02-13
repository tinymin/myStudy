package from.wiki.strategy.sample1;

// Strategy for Happy hour (50% discount)
class CreditCardStrategy implements BillingStrategy {
    String cardNo;

    public CreditCardStrategy(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public double getActPrice(double rawPrice) {
        return rawPrice*0.5;
    }

    @Override
    public String pay() {
        return "카드 결제 - " + cardNo;
    }
}
