package com.imall.mapper;

import com.imall.pojo.Sku;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface SkuMapper extends Mapper<Sku> , SelectByIdListMapper<Sku, Long> {
}