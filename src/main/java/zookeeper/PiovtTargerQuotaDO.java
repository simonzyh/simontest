package zookeeper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

/**
 * Description:业务目标指标设置
 *
 * @author 芯沙
 *         Date 2017-03-23
 */
public class PiovtTargerQuotaDO  {
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
     * 目标ID
     */
    private Long targerId;

    /**
     * 指标编码
     */
    private String code;

    /**
     * 权重
     */
    private Double quotaWgight;
    @Getter
    @Setter

    private List<PiovtTargerQuotaDetailDO> piovtTargerQuotaDetailDOList;

    /**
     * getter for column 目标ID
     */
    public Long getTargerId() {
        return this.targerId;
    }

    /**
     * setter for column 目标ID
     */
    public void setTargerId(Long targerId) {
        this.targerId = targerId;
    }

    /**
     * getter for column 指标编码
     */
    public String getCode() {
        return this.code;
    }

    /**
     * setter for column 指标编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * getter for column 权重
     */
    public Double getQuotaWgight() {
        return this.quotaWgight;
    }

    /**
     * setter for column 权重
     */
    public void setQuotaWgight(Double quotaWgight) {
        this.quotaWgight = quotaWgight;
    }

}