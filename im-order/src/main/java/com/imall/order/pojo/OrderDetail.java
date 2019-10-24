package com.imall.order.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_order_detail")
public class OrderDetail {
    /**
     * 订单详情id 
     */
    @Id
    private Long id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * sku商品id
     */
    @Column(name = "sku_id")
    private Long skuId;

    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品动态属性键值集
     */
    @Column(name = "own_spec")
    private String ownSpec;

    /**
     * 价格,单位：分
     */
    private Long price;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 获取订单详情id 
     *
     * @return id - 订单详情id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置订单详情id 
     *
     * @param id 订单详情id 
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * 获取sku商品id
     *
     * @return sku_id - sku商品id
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * 设置sku商品id
     *
     * @param skuId sku商品id
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * 获取购买数量
     *
     * @return num - 购买数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置购买数量
     *
     * @param num 购买数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取商品标题
     *
     * @return title - 商品标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置商品标题
     *
     * @param title 商品标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取商品动态属性键值集
     *
     * @return own_spec - 商品动态属性键值集
     */
    public String getOwnSpec() {
        return ownSpec;
    }

    /**
     * 设置商品动态属性键值集
     *
     * @param ownSpec 商品动态属性键值集
     */
    public void setOwnSpec(String ownSpec) {
        this.ownSpec = ownSpec;
    }

    /**
     * 获取价格,单位：分
     *
     * @return price - 价格,单位：分
     */
    public Long getPrice() {
        return price;
    }

    /**
     * 设置价格,单位：分
     *
     * @param price 价格,单位：分
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * 获取商品图片
     *
     * @return image - 商品图片
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置商品图片
     *
     * @param image 商品图片
     */
    public void setImage(String image) {
        this.image = image;
    }
}