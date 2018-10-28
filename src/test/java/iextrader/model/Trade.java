package iextrader.model;

public class Trade {
    private final Float price;
    private final Integer size;
    private final Integer tradeId;
    private final Long timestamp;

    public Trade(Float price, Integer size, Integer tradeId, Long timestamp) {
        this.price = price;
        this.size = size;
        this.tradeId = tradeId;
        this.timestamp = timestamp;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
