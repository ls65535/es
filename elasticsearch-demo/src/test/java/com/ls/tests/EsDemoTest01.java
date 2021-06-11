package com.ls.tests;

import com.ls.base.EsBaseTest;
import com.ls.pojo.Item;
import com.ls.utils.DateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

/**
 * Created by ls on 2019/10/10.
 */
public class EsDemoTest01 extends EsBaseTest {

  /**
   * 存入单条
   */
  @Test
  public void testSave() {

    Item item = itemMapper.selectById(19l);
    //itemRepository.save(item);
  }

  /**
   * 保存多条
   */
  @Test
  public void testSaveAll() {
    List<Long> ids = new ArrayList<>();
    ids.add(1L);
    ids.add(2L);
    ids.add(3L);
    List<Item> items = itemMapper.selectBatchIds(ids);
    for (Item item : items) {
      System.out.println(item);
    }
    //itemRepository.saveAll(items);
  }

  /**
   * 创建索引，会根据Item类的@Document注解信息来创建
   */
  @Test
  public void testCreateIndex() {
    elasticsearchTemplate.createIndex(Item.class);
  }

  /**
   * 删除索引
   */
  @Test
  public void testDeleteIndex() {
    elasticsearchTemplate.deleteIndex(Item.class);
  }

  /**
   * 删除所有的()
   */
  @Test
  public void testDeleteAll() {
    itemRepository.deleteAll();
  }

  /**
   * 保存多条
   */
  @Test
  public void testSaveAllList() {

    List<Item> items = itemMapper.selectList(null);
    for (Item item : items) {
      System.out.println(item);
    }
    itemRepository.saveAll(items);
  }

  /**
   * 查找所有的
   */
  @Test
  public void testFindAll() {
    Iterable<Item> all = itemRepository.findAll();

    for (Item item : all) {
      System.out.println(item);
    }
  }

  /**
   * 配置映射，会根据Item类中的id、Field等字段来自动完成映射
   */
  @Test
  public void testPutMapping() {
    elasticsearchTemplate.putMapping(Item.class);
  }

  /**
   * 查询按价格排序从高到低
   */
  @Test
  public void testSort() {
    Iterable<Item> all = itemRepository.findAll(Sort.by("price").descending());

    for (Item item : all) {
      System.out.println(item);
    }
  }


  /**
   * 自定义方法查询，方法名要遵循命名规则，方法名要符合一定的约定
   */

  @Test
  public void testFindByTitle() {
    List<Item> item = itemRepository.findByItemName("三");

    for (Item item1 : item) {

      System.out.println(item1);
    }
  }


  /**
   * 价格区间查询
   */
  @Test
  public void testFindByPriceBetween() {
    List<Item> item = itemRepository.findByPriceBetween(3000L, 6000L);
    for (Item item1 : item) {

      System.out.println(item1);
    }
  }


  /**
   * matchQuery底层采用的是词条匹配查询
   */
  @Test
  public void testMatchQuery() {

    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
    queryBuilder.withQuery(QueryBuilders.matchQuery("itemName", "奔驰"));
    Page<Item> page = itemRepository.search(queryBuilder.build());
    long totalElements = page.getTotalElements();
    System.out.println("共有：" + totalElements);
    for (Item item : page) {
      System.out.println(item);
    }


  }

  /**
   * 词条匹配查询
   */
  @Test
  public void testTermQuery() {
    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

    queryBuilder.withQuery(QueryBuilders.termQuery("price", 3000));

    Page<Item> page = itemRepository.search(queryBuilder.build());

    for (Item item : page) {
      System.out.println(item);
    }
  }

  /**
   * 删除
   */
  @Test
  public void delete() {
    itemRepository.deleteAll();
  }

  /**
   *判断查询
   */
  @Test
  public void testBooleanQuery() {
    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
    queryBuilder
        .withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("itemName", "华为"))
            .must(QueryBuilders.matchQuery("num", 100)));
    Page<Item> page = itemRepository.search(queryBuilder.build());

    for (Item item : page) {
      System.out.println(item);
    }
  }

  /**
   * 模糊查询,最多错2次
   */
  @Test
  public void testFuzzyQuery() {
    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
    queryBuilder.withQuery(QueryBuilders.fuzzyQuery("itemName", "华"));

    Page<Item> page = itemRepository.search(queryBuilder.build());

    for (Item item : page) {
      System.out.println(item);
    }

  }

  /**
   * 模糊查询
   */
  @Test
  public void  testMatchPhraseQuery(){

    int pageNum = 0;
    int pageSize = 11;
    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
    queryBuilder.withQuery(QueryBuilders.matchPhraseQuery("itemName", "华"));
    queryBuilder.withPageable(PageRequest.of(pageNum, pageSize));
    Page<Item> page = itemRepository.search(queryBuilder.build());
    List<Item> data = page.get().collect(Collectors.toList());
    System.out.println(data);
  }



  @Test
  public void testPage() {

    int pageNum = 0;
    int pageSize = 11;

    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

    queryBuilder.withQuery(QueryBuilders.matchQuery("cid", 3));

    queryBuilder.withPageable(PageRequest.of(pageNum, pageSize));

    Page<Item> page = itemRepository.search(queryBuilder.build());

    for (Item item : page) {
      System.out.println(item);
    }

  }

  /**
   * -----------------时间的查询--------------------------
   */
  @Test
  public void testData() {
    long start = DateUtil.string2Date("2015-03-08").getTime();

    long end = DateUtil.string2Date("2015-03-09").getTime();
    List<Item> item = itemRepository.findByCreateTimeBetween(start, end);
    for (Item item1 : item) {

      System.out.println(item1);
    }
  }

  /**
   * 查询时间类型的格式
   */
  @Test
  public void testRangeQuery() {
    RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("created");
    long start = DateUtil.string2Date("2015-03-08").getTime();

    long end = DateUtil.string2Date("2015-03-09").getTime();
    rangeQueryBuilder.gt(start);
    rangeQueryBuilder.lt(end);
    NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
    NativeSearchQuery build = queryBuilder.withQuery(rangeQueryBuilder).build();
    List<Item> list = elasticsearchTemplate.queryForList(build, Item.class);
    System.out.println(list);


  }

}