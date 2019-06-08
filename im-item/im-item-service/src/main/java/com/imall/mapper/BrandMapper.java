package com.imall.mapper;

import com.imall.pojo.Brand;

import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand>, SelectByIdListMapper<Brand, Long>{
}