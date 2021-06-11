package com.ls.repository;

import com.ls.pojo.Item;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by ls on 2019/10/10.
 */
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

  /**
   * 根据商品标题查询：如
   *
   * @param itemName
   * @return
   */
  List<Item> findByItemName(String itemName);

/**
   * 区间查找
   *
   * @param
   * @param
   * @return
   */
  List<Item> findByPriceBetween(Long minPrice, Long maxPrice);

  List<Item> findByCreateTimeBetween(Long start, Long end);


}
