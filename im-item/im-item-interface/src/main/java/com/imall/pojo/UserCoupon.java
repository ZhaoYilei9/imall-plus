package com.imall.pojo;

import javax.persistence.*;

@Table(name = "tb_user_coupon")
public class UserCoupon {
    /**
     * 每张优惠券的编号
     */
    @Id
    @Column(name = "coupon_code")
    private Long couponCode;

    /**
     * 优惠卷id（coupon表的id）
     */
    @Column(name = "coupon_id")
    private Long couponId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 是否使用，0、未使用  1、已使用  2、已过期
     */
    private String status;

    /**
     * 获取每张优惠券的编号
     *
     * @return coupon_code - 每张优惠券的编号
     */
    public Long getCouponCode() {
        return couponCode;
    }

    /**
     * 设置每张优惠券的编号
     *
     * @param couponCode 每张优惠券的编号
     */
    public void setCouponCode(Long couponCode) {
        this.couponCode = couponCode;
    }

    /**
     * 获取优惠卷id（coupon表的id）
     *
     * @return coupon_id - 优惠卷id（coupon表的id）
     */
    public Long getCouponId() {
        return couponId;
    }

    /**
     * 设置优惠卷id（coupon表的id）
     *
     * @param couponId 优惠卷id（coupon表的id）
     */
    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取是否使用，0、未使用  1、已使用  2、已过期
     *
     * @return status - 是否使用，0、未使用  1、已使用  2、已过期
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置是否使用，0、未使用  1、已使用  2、已过期
     *
     * @param status 是否使用，0、未使用  1、已使用  2、已过期
     */
    public void setStatus(String status) {
        this.status = status;
    }
}