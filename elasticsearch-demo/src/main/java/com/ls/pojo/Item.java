package com.ls.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;


/**
 * <p>
 * 商品表
 * </p>
 *
 * @author jobob
 * @since 2019-10-10
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "Item对象", description = "商品表")
@TableName("tb_item")
@Document(indexName = "item", type = "_doc", shards = 1, replicas = 0)
public class Item {

  public Item(String itemName, String sellPoint, Double price, Integer num, String brand,
      String image, Long cid, Integer status) {
    this.itemName = itemName;
    this.sellPoint = sellPoint;
    this.price = price;
    this.num = num;
    this.brand = brand;
    this.image = image;
    this.cid = cid;
    this.status = status;
  }

  public Item() {
    super();
  }

  private static final long serialVersionUID = 1L;
  @ApiModelProperty("id")
  @TableId(type = IdType.AUTO)
  private Long id;
  @ApiModelProperty(value = "商品标题")
  private String itemName;

  @ApiModelProperty(value = "商品卖点")
  private String sellPoint;

  @ApiModelProperty(value = "商品价格，单位为：分")
  private Double price;

  @ApiModelProperty(value = "库存数量")
  private Integer num;
  @ApiModelProperty(value = "品牌")
  private String brand;

  @ApiModelProperty(value = "商品图片")
  private String image;

  @ApiModelProperty(value = "所属类目，叶子类目")
  private Long cid;

  @ApiModelProperty(value = "商品状态，1-正常，2-下架，3-删除")
  private Integer status;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "更新时间")
  private Date modifyTime;


}
