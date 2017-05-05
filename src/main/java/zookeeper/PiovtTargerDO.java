package zookeeper;

/**
 * Created by yehua.zyh on 2017/3/23.
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

/**
 * Description:业务目标配置信息
 *
 * @author 芯沙
 *         Date 2017-03-23
 */
public class PiovtTargerDO  {
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
     * 业务类型 猫超6000
     */
    private Integer bizType;

    /**
     * 1 总体目标 2 临时业务目标
     */
    private Integer targerType;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建小二
     */
    private String createUser;

    /**
     * 状态 -1 删除 0 草稿 1 待审核 10 通过 11 取消
     */
    private Integer status;

    @Getter
    @Setter
    private List<PiovtTargerQuotaDO> piovtTargerQuotaDOList;

    /**
     * getter for column 业务类型 猫超6000
     */
    public Integer getBizType() {
        return this.bizType;
    }

    /**
     * setter for column 业务类型 猫超6000
     */
    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    /**
     * getter for column 1 总体目标 2 临时业务目标
     */
    public Integer getTargerType() {
        return this.targerType;
    }

    /**
     * setter for column 1 总体目标 2 临时业务目标
     */
    public void setTargerType(Integer targerType) {
        this.targerType = targerType;
    }

    /**
     * getter for column 名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * setter for column 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for column 创建小二
     */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * setter for column 创建小二
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * getter for column 状态 0 草稿 1 待审核 10 通过 11 取消
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * setter for column 状态 0 草稿 1 待审核 10 通过 11 取消
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public static void main(String[] args){
        List<TTT> salesPlanPieVOs=new ArrayList<>();
        salesPlanPieVOs.add(new TTT(10));
        salesPlanPieVOs.add(new TTT(13));

        salesPlanPieVOs.add(new TTT(102));
        salesPlanPieVOs.add(new TTT(1));

        salesPlanPieVOs.sort((e1, e2) -> (e1.getValue() - e2.getValue()));
        System.out.println(JSON.toJSONString(salesPlanPieVOs));
        System.out.println(longToIp(3394060020L));

    }
    static class TTT{
        public TTT(int value){
            this.value=value;
        }
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
    public static String longToIp(long ip) {
        StringBuilder result = new StringBuilder(15);

        for (int i = 0; i < 4; i++) {

            result.insert(0,Long.toString(ip & 0xff));

            if (i < 3) {
                result.insert(0,'.');
            }

            ip = ip >> 8;
        }
        return result.toString();
    }

    //ip = 3232235778
    public String longToIp2(long ip) {

        return ((ip >> 24) & 0xFF) + "."
            + ((ip >> 16) & 0xFF) + "."
            + ((ip >> 8) & 0xFF) + "."
            + (ip & 0xFF);
    }


}
