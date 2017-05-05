/**
 * Created by yehua.zyh on 2017/2/9.
 */



import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * 供应商活动投放配置
 *
 */

public enum  SupplierPageFoderEnum {

    /********************************品牌特惠*********************************/
      tab1(1,"banner", "banner背景图片",2,true,""),
    logo1(1,"logo1", "品牌logo1",2,true,""),
    logo2(1,"logo2", "品牌logo2",2,false,""),

    /*******************************品牌特惠商业置换活动*********************************/





    /****************************************站内资源**********************************/
    //超市场景搜
    keywords(1,"keywords", "场景搜关键字",1, false, "{\"maxLength\":10}"),
    //pcBanner("pcBanner", "PC封面图", "{\"width\":290,\"height\":390,\"maxSize\":200}", true),
    appBanner(1,"appBanner", "场景搜无线封面图",2, true, "{\"width\":958,\"height\":496,\"maxSize\":200}"),
    //detail 下面活动
    detailContent(1,"detailContent", "detailbanner图",2, true, "{\"width\":372,\"height\":190}"),
    //热销banner
    content(1,"content", "热销banner图",2, true, "{\"width\":372,\"height\":568}"),
    //我的优惠券banner图
    actImg(1,"actImg", "优惠券列表banner图",2, true, "{\"width\":750,\"height\":370}"),
    //天猫场景搜无线
    pic_800_800(1,"pic_800_800", "天猫无线场景搜封面图片",2, true,""),
    pic_750_388(1,"pic_750_388", "天猫无线场景搜封面图片2",2, true,""),

    //天猫场景搜PC
    pic_290_390(1,"pic_290_390", "天猫PC场景搜封面图片",2, true,""),

    //猫客B版
    pic_heng(1,"pic_heng", "猫客B版横图",2, true,""),
    pic_fang(1,"pic_fang", "猫客B版方图",2, true,""),


    //猫客交易链路
    pic_220_270(1,"pic_220_270", "交易路径推荐活动封面图",2, true,"")
    ;
    /**
     * 栏目  对应  ColumnTypeEnum 枚举
     */
    @Getter
    private Integer column;
    /**
     * 素材名称
     */
    @Getter
    private String name;
    /**
     * 素材key
     */
    @Getter
    private String key;
    /**
     * 素材类型  1 str  2 img 3 link
     */
    @Getter
    private Integer type;
    /**
     * 素材描述
     */
    @Getter
    private String placeholder;
    /**
     * 是否能为空
     */
    @Getter
    private Boolean notNull;
    /**
     * 验证 规则
     */
    @Getter
    private String validate;
    SupplierPageFoderEnum(Integer column,String key,String name,Integer type,Boolean notNull,String validate) {
        this.column=column;
        this.name=name;
        this.key=key;
        this.type=type;
        this.notNull=notNull;
        this.validate=validate;
    }





    //根据栏目获取素材规范
    public static List<SupplierPageFoderEnum> getFodderNormByColumn(Integer column){
        List<SupplierPageFoderEnum> res=new ArrayList<SupplierPageFoderEnum>();
        for(SupplierPageFoderEnum fodderNorm:SupplierPageFoderEnum.values()){
            if(fodderNorm.getColumn().intValue()==column){
                res.add(fodderNorm);
            }
        }
        return res;
    }

}





