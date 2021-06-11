package com.ls.base;

import com.ls.dao.es.ItemMapper;
import com.ls.repository.ItemRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ls
 * @date 2020/6/14 7:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBaseTest {

  @Autowired
  protected ElasticsearchTemplate elasticsearchTemplate;

  @Autowired
  protected ItemRepository itemRepository;
  @Autowired
  protected ItemMapper itemMapper;

}
