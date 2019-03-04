package com.pinyougou.service;

import java.util.Map;

/**
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/3/4 10:31
 */
public interface ItemSearchService {

    Map<String,Object> search(Map<String,Object> params);
}
