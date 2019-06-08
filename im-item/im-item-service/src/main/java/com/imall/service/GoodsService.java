package com.imall.service;

import java.util.List;

import com.imall.common.pojo.PageResult;
import com.imall.pojo.Spu;

public interface GoodsService {

	PageResult<Spu> spuList(String key, Boolean saleable, Integer page, Integer rows);

}
