package com.imall.page.service;

import org.springframework.stereotype.Service;

import java.util.Map;


public interface PageService {

    Map<String,Object> loadModel(Long id);
}
