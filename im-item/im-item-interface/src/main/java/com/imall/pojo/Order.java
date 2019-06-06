package com.imall.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_order")
public class Order {
    /**
     * 订单id
     */
    @Id
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 总金额，单位为分
     */
    @Column(name = "total_pay")
    private Long totalPay;

    /**
     * 实付金额。单位:分。如:20007，表示:200元7分
     */
    @Column(name = "actual_pay")
    private Long actualPay;

    @Column(name = "promotion_ids")
    private String promotionIds;

    /**
     * 支付类型，1、在线支付，2、货到付款
     */
    @Column(name = "payment_type")
    private Boolean paymentType;

    /**
     * 邮费。单位:分。如:20007，表示:200元7分
     */
    @Column(name = "post_fee")
    private Long postFee;

    /**
     * 订单创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 物流名称
     */
    @Column(name = "shipping_name")
    private String shippingName;

    /**
     * 物流单号
     */
    @Column(name = "shipping_code")
    private String shippingCode;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 买家留言
     */
    @Column(name = "buyer_message")
    private String buyerMessage;

    /**
     * 买家昵称
     */
    @Column(name = "buyer_nick")
    private String buyerNick;

    /**
     * 买家是否已经评价,0未评价，1已评价
     */
    @Column(name = "buyer_rate")
    private Boolean buyerRate;

    /**
     * 收获地址（省）
     */
    @Column(name = "receiver_state")
    private String receiverState;

    /**
     * 收获地址（市）
     */
    @Column(name = "receiver_city")
    private String receiverCity;

    /**
     * 收获地址（区/县）
     */
    @Column(name = "receiver_district")
    private String receiverDistrict;

    /**
     * 收获地址（街道、住址等详细地址）
     */
    @Column(name = "receiver_address")
    private String receiverAddress;

    /**
     * 收货人手机
     */
    @Column(name = "receiver_mobile")
    private String receiverMobile;

    /**
     * 收货人邮编
     */
    @Column(name = "receiver_zip")
    private String receiverZip;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 发票类型(0无发票1普通发票，2电子发票，3增值税发票)
     */
    @Column(name = "invoice_type")
    private Integer invoiceType;

    /**
     * 订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     */
    @Column(name = "source_type")
    private Integer sourceType;

    /**
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取总金额，单位为分
     *
     * @return total_pay - 总金额，单位为分
     */
    public Long getTotalPay() {
        return totalPay;
    }

    /**
     * 设置总金额，单位为分
     *
     * @param totalPay 总金额，单位为分
     */
    public void setTotalPay(Long totalPay) {
        this.totalPay = totalPay;
    }

    /**
     * 获取实付金额。单位:分。如:20007，表示:200元7分
     *
     * @return actual_pay - 实付金额。单位:分。如:20007，表示:200元7分
     */
    public Long getActualPay() {
        return actualPay;
    }

    /**
     * 设置实付金额。单位:分。如:20007，表示:200元7分
     *
     * @param actualPay 实付金额。单位:分。如:20007，表示:200元7分
     */
    public void setActualPay(Long actualPay) {
        this.actualPay = actualPay;
    }

    /**
     * @return promotion_ids
     */
    public String getPromotionIds() {
        return promotionIds;
    }

    /**
     * @param promotionIds
     */
    public void setPromotionIds(String promotionIds) {
        this.promotionIds = promotionIds;
    }

    /**
     * 获取支付类型，1、在线支付，2、货到付款
     *
     * @return payment_type - 支付类型，1、在线支付，2、货到付款
     */
    public Boolean getPaymentType() {
        return paymentType;
    }

    /**
     * 设置支付类型，1、在线支付，2、货到付款
     *
     * @param paymentType 支付类型，1、在线支付，2、货到付款
     */
    public void setPaymentType(Boolean paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * 获取邮费。单位:分。如:20007，表示:200元7分
     *
     * @return post_fee - 邮费。单位:分。如:20007，表示:200元7分
     */
    public Long getPostFee() {
        return postFee;
    }

    /**
     * 设置邮费。单位:分。如:20007，表示:200元7分
     *
     * @param postFee 邮费。单位:分。如:20007，表示:200元7分
     */
    public void setPostFee(Long postFee) {
        this.postFee = postFee;
    }

    /**
     * 获取订单创建时间
     *
     * @return create_time - 订单创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置订单创建时间
     *
     * @param createTime 订单创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取物流名称
     *
     * @return shipping_name - 物流名称
     */
    public String getShippingName() {
        return shippingName;
    }

    /**
     * 设置物流名称
     *
     * @param shippingName 物流名称
     */
    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    /**
     * 获取物流单号
     *
     * @return shipping_code - 物流单号
     */
    public String getShippingCode() {
        return shippingCode;
    }

    /**
     * 设置物流单号
     *
     * @param shippingCode 物流单号
     */
    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取买家留言
     *
     * @return buyer_message - 买家留言
     */
    public String getBuyerMessage() {
        return buyerMessage;
    }

    /**
     * 设置买家留言
     *
     * @param buyerMessage 买家留言
     */
    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    /**
     * 获取买家昵称
     *
     * @return buyer_nick - 买家昵称
     */
    public String getBuyerNick() {
        return buyerNick;
    }

    /**
     * 设置买家昵称
     *
     * @param buyerNick 买家昵称
     */
    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    /**
     * 获取买家是否已经评价,0未评价，1已评价
     *
     * @return buyer_rate - 买家是否已经评价,0未评价，1已评价
     */
    public Boolean getBuyerRate() {
        return buyerRate;
    }

    /**
     * 设置买家是否已经评价,0未评价，1已评价
     *
     * @param buyerRate 买家是否已经评价,0未评价，1已评价
     */
    public void setBuyerRate(Boolean buyerRate) {
        this.buyerRate = buyerRate;
    }

    /**
     * 获取收获地址（省）
     *
     * @return receiver_state - 收获地址（省）
     */
    public String getReceiverState() {
        return receiverState;
    }

    /**
     * 设置收获地址（省）
     *
     * @param receiverState 收获地址（省）
     */
    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    /**
     * 获取收获地址（市）
     *
     * @return receiver_city - 收获地址（市）
     */
    public String getReceiverCity() {
        return receiverCity;
    }

    /**
     * 设置收获地址（市）
     *
     * @param receiverCity 收获地址（市）
     */
    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    /**
     * 获取收获地址（区/县）
     *
     * @return receiver_district - 收获地址（区/县）
     */
    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    /**
     * 设置收获地址（区/县）
     *
     * @param receiverDistrict 收获地址（区/县）
     */
    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }

    /**
     * 获取收获地址（街道、住址等详细地址）
     *
     * @return receiver_address - 收获地址（街道、住址等详细地址）
     */
    public String getReceiverAddress() {
        return receiverAddress;
    }

    /**
     * 设置收获地址（街道、住址等详细地址）
     *
     * @param receiverAddress 收获地址（街道、住址等详细地址）
     */
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    /**
     * 获取收货人手机
     *
     * @return receiver_mobile - 收货人手机
     */
    public String getReceiverMobile() {
        return receiverMobile;
    }

    /**
     * 设置收货人手机
     *
     * @param receiverMobile 收货人手机
     */
    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    /**
     * 获取收货人邮编
     *
     * @return receiver_zip - 收货人邮编
     */
    public String getReceiverZip() {
        return receiverZip;
    }

    /**
     * 设置收货人邮编
     *
     * @param receiverZip 收货人邮编
     */
    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    /**
     * 获取收货人
     *
     * @return receiver - 收货人
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * 设置收货人
     *
     * @param receiver 收货人
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * 获取发票类型(0无发票1普通发票，2电子发票，3增值税发票)
     *
     * @return invoice_type - 发票类型(0无发票1普通发票，2电子发票，3增值税发票)
     */
    public Integer getInvoiceType() {
        return invoiceType;
    }

    /**
     * 设置发票类型(0无发票1普通发票，2电子发票，3增值税发票)
     *
     * @param invoiceType 发票类型(0无发票1普通发票，2电子发票，3增值税发票)
     */
    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * 获取订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     *
     * @return source_type - 订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     */
    public Integer getSourceType() {
        return sourceType;
    }

    /**
     * 设置订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     *
     * @param sourceType 订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     */
    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }
}