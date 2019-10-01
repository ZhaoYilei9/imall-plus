package com.imall.cart.service.impl;

import com.imall.auth.entity.UserInfo;
import com.imall.auth.utils.ObjectUtils;
import com.imall.cart.interceptor.UserInterceptor;
import com.imall.cart.pojo.Cart;
import com.imall.cart.service.CartService;
import com.imall.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServicceImpl implements CartService {
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    private static final String KEY_PREFIX = "cart:uid:";

    public void addCart(Cart cart) {

        UserInfo loginUser = UserInterceptor.getLoginUser();
        String key = KEY_PREFIX + loginUser.getId().toString();
        String hashKey = cart.getSkuId().toString();
        BoundHashOperations<String, Object, Object>  hashOperations = redisTemplate.boundHashOps(key);
        if (hashOperations.hasKey(hashKey)){
            Cart cartInRedis = JsonUtils.parse(hashOperations.get(hashKey).toString(), Cart.class);
            cart.setNum(cart.getNum() + cartInRedis.getNum());
        }
        hashOperations.put(hashKey,JsonUtils.serialize(cart));
    }

    @Override
    public List<Cart> getCarts() {
        UserInfo loginUser = UserInterceptor.getLoginUser();
        String key = KEY_PREFIX + loginUser.getId().toString();
        if (!redisTemplate.hasKey(key)){
            return null;
        }
        BoundHashOperations ops = redisTemplate.boundHashOps(key);
        List<Object> values = ops.values();
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }
        List<Cart> carts = values.stream().map(o -> JsonUtils.parse(JsonUtils.serialize(o), Cart.class)).collect(Collectors.toList());

        return carts;
    }
}
