package com.ls.tests;

import com.ls.base.EsBaseTest;
import com.ls.pojo.Item;
import java.util.List;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

/**
 * Created by ls on 2020/6/22. es的高级查询和api
 */
public class EsHighTest extends EsBaseTest {

  @Test
  public void testScroll() {
    // 创建查询条件对象
    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
// 拼接查询条件
    queryBuilder.must(QueryBuilders.termQuery("status", 1));
    //假设查询状态为1的
// 创建查询对象
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices("item")//索引名
        .withTypes("_doc")//类型名
        .withQuery(queryBuilder)// 查询条件对象
        .withPageable(PageRequest.of(0, 1000))//从0页开始查，每页1000个结果
        .build();

// 滚动查询
    ScrolledPage<Item> scroll = (ScrolledPage<Item>) elasticsearchTemplate.
        startScroll(3000, searchQuery, Item.class);

// 判断是否有内容
    while (scroll.hasContent()) {
      List<Item> content = scroll.getContent();
      System.out.println(content);
      // 业务逻辑省略
      //取下一页，scrollId在es服务器上可能会发生变化，需要用最新的。发起continueScroll请求会重新刷新快照保留时间
      scroll = (ScrolledPage<Item>) elasticsearchTemplate.
          continueScroll(scroll.getScrollId(), 3000, Item.class);
    }

// 最后释放查询
    elasticsearchTemplate.clearScroll(scroll.getScrollId());
  }
}
