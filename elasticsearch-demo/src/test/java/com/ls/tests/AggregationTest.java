package com.ls.tests;

import com.ls.base.EsBaseTest;
import com.ls.pojo.Item;
import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

/**
 * @author ls
 * @date 2020/6/14 7:56
 */
public class AggregationTest extends EsBaseTest {


    /****************聚合为桶*****/
    /**
     * 以品牌分组，求组内情况
     */
    @Test
    public void testAgg() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
            AggregationBuilders.terms("brands").field("brand.keyword"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository
            .search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        }

    }


    /**
     * 聚合查询上架和下架多少
     */
    @Test
    public void getCountStatus() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 添加一个新的聚合，聚合类型为terms，聚合名称为types，聚合字段为type
        Script script = new Script("doc['status'].value");
        NativeSearchQueryBuilder queryBuilder1 = queryBuilder
            .addAggregation(AggregationBuilders.terms("statusT").script(script));
        Page<Item> searchType = this.itemRepository.search(queryBuilder1.build());
        AggregatedPage<Item> aggregatedPage = (AggregatedPage<Item>) searchType;
        //解析
        StringTerms stringTerms = (StringTerms) aggregatedPage.getAggregation("statusT");
        List<Bucket> buckets = stringTerms.getBuckets();
        List<Item> result = new ArrayList<>();
    }

    /**
     * 分组和平均值计算
     */
    @Test
    public void testSubAgg() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
            AggregationBuilders.terms("brands").field("brand.keyword")
                .subAggregation(AggregationBuilders.avg("priceAvg").field("price"))
            // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository
            .search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");

            // 3.6.获取子聚合结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
        }

    }


}
