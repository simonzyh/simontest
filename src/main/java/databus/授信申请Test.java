package databus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;

/**
 * Created by yehua.zyh on 2018/3/28.
 */
public class 授信申请Test {
//static String topic="TP_DATABUS_DRC";
//static String enentCode="IDB_L_98846.pcapplycore.pcac_apply";
    public static void main(String[] args){

        Map<String,  Object> map  =  new HashMap<String,  Object>();
        map.put("id","14076092");
        map.put("gmt_create","2018-04-10 00:01:07");
        map.put("gmt_modified","2018-04-10 00:01:10");
        map.put("user_id","2088812462670008");
        map.put("gmt_creator","pcac");
        map.put("gmt_modifier","pcac");
        map.put("apply_no","201804100124572300A");
        map.put("apply_status","APPROVAL");
        map.put("person_name","黄月明");
        map.put("cert_no","362427198910156218");
        map.put("amount",null);
        map.put("virtual_credit_type","CAR_MORTGAGE_CREDIT_APPLY");
        map.put("apply_date","2018-04-10 11:23:08");
        map.put("prod_id","J1010100001000001288");
        map.put("service_type","null");
        map.put("rel_prod","null");
        map.put("ext_no","2018041067b3af14-681f-446c-8866-505471c88f3200B");
        map.put("ext_json", "{\"ORDER_URL\":\"https://m.alipay.com/ZKOP1gM\",\"PUSH_URL\":\"/www/index.html\"," + "\"appraiseNo\":\"20180410006010020000080000808609\",\"assetNo\":\"20180410006010010000080000798029\"," + "\"creditSignature\":false,\"device_info\":{\"imei\":\"000000603400670\",\"imsi\":\"460030404077687\"," + "\"ip\":\"117.136.11.203\",\"mac\":\"02:00:00:00:00:00\",\"wifimac\":\"\"}," + "\"orgArNo\":\"20180410J1010111044331552001\"," + "\"verifyBizId\":\"lendweb67b3af14-681f-446c-8866-505471c88f32e2ed1e51034dfd0ff0eba09863de0984\"}");
            map.put("pre_env","N");

        map.put("refuse_code","null");
        map.put("ext_items","[{\"code\":\"VEHICLE_VALUE_MODEL\",\"extInfo\":\"赣D15P67\",\"policySelected\":true," + "\"requiredToDisplay\":true,\"selected\":true,\"userSelected\":true},{\"code\":\"CAR_VALUATION\"," + "\"policySelected\":true,\"requiredToDisplay\":true,\"selected\":false,\"userSelected\":false}]");
        map.put("org_info",    "{\"loanMode\":\"2\",\"orgAlipayAppId\":\"2017092208873994\"," + "\"orgAlipayUserId\":\"2088821443097295\",\"orgArNo\":\"20171025J1010111019933991001\"," + "\"orgCode\":\"TOUNA\",\"orgName\":\"前海众诚国际融资租赁（深圳）有限公司\",\"orgShortName\":\"前海众诚融资租赁\"," + "\"participants\":[{\"instCode\":\"TOUNA\",\"investRate\":1,\"orgAlipayAppId\":\"2017092208873994\"," + "\"orgName\":\"前海众诚国际融资租赁（深圳）有限公司\",\"orgPartnerId\":\"2088821443097295\"," + "\"orgSimpleName\":\"前海众诚融资租赁\",\"partner\":\"01\"}],\"signature\":\"EFABF7D26D54E6852A2053B12750C7BF\"}");
        map.put("mobile_no","15059841365");
        map.put("channel","210012");
        map.put("scene","CAR_MORTGAGE_LOAN");

        UniformEvent  uniformEvent  =  new  UniformEvent();
        uniformEvent.setTopic("TP_DATABUS_DRC");
        uniformEvent.setEventCode("IDB_L_98846.pcapplycore.pcac_apply");
        uniformEvent.setEventPayload(map);
        uniformEvent.getProperties().put("dbsyncTs",  System.currentTimeMillis()  /  1000  +  "12345678");
        uniformEvent.getProperties().put("ddDBType",  "MYSQL");
        uniformEvent.getProperties().put("ddOperationType",  "UPDATE");
        uniformEvent.getProperties().put("ddTs",  System.currentTimeMillis()  /  1000);

        //********************

        Map<String,  Object> map1  =  new HashMap<String,  Object>();
        map1.put("id","14076091");
        map1.put("gmt_create","2018-04-10 00:02:07");
        map1.put("gmt_modified","2018-04-10 00:13:11");
        map1.put("user_id","2088502261360005");
        map1.put("gmt_creator","pcac");
        map1.put("gmt_modifier","pcac");
        map1.put("apply_no","201804100124527500A");
        map1.put("apply_status","APPLY_FINISH");
        map1.put("person_name","刘雨");
        map1.put("cert_no","612727198910266730");
        map1.put("amount","0E-8");
        map1.put("virtual_credit_type","JB_PLATFORM_CREDIT_APPLY");
        map1.put("apply_date","2018-04-10 00:02:07");
        map1.put("prod_id","J1010100100000000004");
        map1.put("service_type","null");
        map1.put("rel_prod","null");
        map1.put("ext_no","2018041062c89629-c534-46f6-b841-bc8b322f5dd800B");
        map1.put("ext_json", "{\"PUSH_URL\":\"/www/jiebei/pages/openScene.htm?undefined\"," + "\"antLoanServiceArNo\":\"20180410J1010111044316896001\",\"creditSignature\":false," + "\"device_info\":{\"imei\":\"90i8dgyu98v5yqs\",\"imsi\":\"46000werl9vzvp9\",\"ip\":\"101.247.16.66\"," + "\"mac\":\"02:00:00:00:00:00\",\"wifimac\":\"\"},\"orgArNo\":\"20180410J1010111044316896001\"," + "\"orgAuthArNo\":\"20180410J1010111044316896001\"," + "\"verifyBizId\":\"lendweb62c89629-c534-46f6-b841-bc8b322f5dd81824fdb41d97f19d6b7a0ddac1966588\"}");
        map1.put("refuse_code","授信通过");
        map1.put("ext_items","null");
        map1.put("org_info",  "{\"loanMode\":\"2\",\"orgAlipayUserId\":\"2088511924976330\"," + "\"orgArNo\":\"20170522J1010111011682522004\",\"orgCode\":\"XABK\",\"orgName\":\"西安银行股份有限公司\"," + "\"orgShortName\":\"西安银行\",\"participants\":[{\"instCode\":\"XABK\",\"orgName\":\"西安银行股份有限公司\"," + "\"orgPartnerId\":\"2088511924976330\",\"orgSimpleName\":\"西安银行\",\"partner\":\"01\"}]," + "\"signature\":\"4AD7A2B23843BDF05FA45C5B8248D083\",\"zmAuthOrgFlag\":false}");
        map1.put("mobile_no","13629225230");
        map1.put("channel","2100");
        map1.put("scene","PLATFORM_CASH_LOAN");

        UniformEvent  uniformEvent1  =  new  UniformEvent();
        uniformEvent1.setTopic("TP_DATABUS_DRC");
        uniformEvent1.setEventCode("IDB_L_98846.pcapplycore.pcac_apply");
        uniformEvent1.setEventPayload(map1);
        uniformEvent1.getProperties().put("dbsyncTs",  System.currentTimeMillis()  /  1000  +  "123456789");
        uniformEvent1.getProperties().put("ddDBType",  "MYSQL");
        uniformEvent1.getProperties().put("ddOperationType",  "UPDATE");
        uniformEvent1.getProperties().put("ddTs",  System.currentTimeMillis()  /  1000+100);

        List<UniformEvent> eventPayload  =  new ArrayList<UniformEvent>();
        eventPayload.add(uniformEvent);
        eventPayload.add(uniformEvent1);

        UniformEvent  masterUniformEvent  =  new  UniformEvent();
        masterUniformEvent.setTopic("TP_DATABUS_DRC");
        masterUniformEvent.setEventCode("IDB_L_98846.pcapplycore.pcac_apply");
        masterUniformEvent.setEventPayload(eventPayload);
        System.out.println(JSON.toJSONString(masterUniformEvent));
       // return  masterUniformEvent;
    }

}