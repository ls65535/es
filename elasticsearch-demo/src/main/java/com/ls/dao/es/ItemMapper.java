package com.ls.dao.es;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ls.pojo.Item;


/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-10-10
 */
public interface ItemMapper extends BaseMapper<Item> {

  Integer selectCountById();

}
