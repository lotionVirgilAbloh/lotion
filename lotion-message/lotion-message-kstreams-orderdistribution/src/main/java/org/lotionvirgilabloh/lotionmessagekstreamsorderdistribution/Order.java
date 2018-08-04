package org.lotionvirgilabloh.lotionmessagekstreamsorderdistribution;

public class Order {
    public String order;
    public Long generateTime;
    public Long distributedTime;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Long getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(Long generateTime) {
        this.generateTime = generateTime;
    }

    public Long getDistributedTime() {
        return distributedTime;
    }

    public void setDistributedTime(Long distributedTime) {
        this.distributedTime = distributedTime;
    }

}
