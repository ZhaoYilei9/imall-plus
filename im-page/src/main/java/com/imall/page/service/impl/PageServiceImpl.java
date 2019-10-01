package com.imall.page.service.impl;

import com.imall.enums.ExceptionEnum;
import com.imall.exception.ImException;
import com.imall.page.client.BrandClient;
import com.imall.page.client.CategoryClient;
import com.imall.page.client.GoodsClient;
import com.imall.page.client.SpecificationClient;
import com.imall.page.service.PageService;
import com.imall.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Override
    public Map<String, Object> loadModel(Long id) {
        Spu spu = goodsClient.querySpuById(id);
        //上架未上架，则不应该查询到商品详情信息，抛出异常
        if (!spu.getSaleable()) {
            throw new ImException(ExceptionEnum.GOODS_NOT_SALEABLE);
        }
        List<Category> categories = categoryClient.queryCategoriesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        List<SpecGroup> specs = specificationClient.getSpecsByCid(spu.getCid3());
        Map<String,Object> model = new HashMap<>();

        model.put("brand", brand);
        model.put("categories", categories);
        model.put("spu", spu);
        model.put("skus", spu.getSkus());
        model.put("detail", spu.getSpuDetail());
        model.put("specs", specs);
        return model;
    }
}
