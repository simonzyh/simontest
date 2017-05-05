package zookeeper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

/**
 * Description:业务目标配置明细
 *
 * @author 芯沙
 *         Date 2017-03-23
 */
public class PiovtTargerQuotaDetailDO   {
    // 主键
    @Getter
    @Setter
    private Long id;
    // 创建时间
    @Getter
    @Setter
    private Date gmtCreate;
    // 修改时间
    @Getter
    @Setter
    private Date gmtModified;
    /**
     * 扩展属性 k-v结构
     */
    private String attribute;

    private Map<String, Object> attributeMap;

    /**
     * getter for column 扩展属性 k-v结构
     */
    public String getAttribute() {
        return JSON.toJSONString(getAttributeMap());
    }

    /**
     * setter for column 扩展属性 k-v结构
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
        if (StringUtils.isBlank(attribute)||(!attribute.startsWith("{")&&!attribute.startsWith("["))) {
            return;
        }
        JSONObject jsonObject = JSON.parseObject(attribute);
        for (String key : jsonObject.keySet()) {
            getAttributeMap().put(key, jsonObject.get(key));
        }
    }

    public Map<String, Object> getAttributeMap() {
        if (null == attributeMap) {
            this.attributeMap = new HashMap<String, Object>();
        }
        return attributeMap;
    }

    public void setAttributeMap(Map<String, Object> attributeMap) {
        this.attributeMap = attributeMap;
    }

    /**
     * 业务指标ID
     */
    private Long targetQuotaId;

    /**
     * 类型 1 类目 2 商品
     */
    private Integer detailType;

    /**
     * 配置明细ID
     */
    private Long detailId;

    /**
     * 配置明细名称
     */
    private String detailName;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 地区
     */
    private String area;

    /**
     * 级别
     */
    private Integer detailLevel;

    /**
     * 地区级别  1 国家  2 逻辑区  3 省 4热门城市   5 城市 6 区
     */
    private Integer areaLevel;

    /**
     * getter for column 业务指标ID
     */
    public Long getTargetQuotaId() {
        return this.targetQuotaId;
    }

    /**
     * setter for column 业务指标ID
     */
    public void setTargetQuotaId(Long targetQuotaId) {
        this.targetQuotaId = targetQuotaId;
    }

    /**
     * getter for column 类型 1 类目 2 商品
     */
    public Integer getDetailType() {
        return this.detailType;
    }

    /**
     * setter for column 类型 1 类目 2 商品
     */
    public void setDetailType(Integer detailType) {
        this.detailType = detailType;
    }

    /**
     * getter for column 配置明细ID
     */
    public Long getDetailId() {
        return this.detailId;
    }

    /**
     * setter for column 配置明细ID
     */
    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    /**
     * getter for column 配置明细名称
     */
    public String getDetailName() {
        return this.detailName;
    }

    /**
     * setter for column 配置明细名称
     */
    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    /**
     * getter for column 开始时间
     */
    public Date getStartTime() {
        return this.startTime;
    }

    /**
     * setter for column 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * getter for column 结束时间
     */
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * setter for column 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * getter for column 地区
     */
    public String getArea() {
        return this.area;
    }

    /**
     * setter for column 地区
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * getter for column 级别
     */
    public Integer getDetailLevel() {
        return this.detailLevel;
    }

    /**
     * setter for column 级别
     */
    public void setDetailLevel(Integer detailLevel) {
        this.detailLevel = detailLevel;
    }

    /**
     * getter for column 地区级别  1 国家  2 逻辑区  3 省 4热门城市   5 城市 6 区
     */
    public Integer getAreaLevel() {
        return this.areaLevel;
    }

    /**
     * setter for column 地区级别  1 国家  2 逻辑区  3 省 4热门城市   5 城市 6 区
     */
    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }
}