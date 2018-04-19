package databus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yehua.zyh on 2018/4/9.
 */
public class 授信审批 {

    public Object test(){



        Map<String,  Object> map  =  new HashMap<String,  Object>();
        map.put("id","1386128");
        map.put("gmt_create","2018-04-10 00:01:10");
        map.put("gmt_modified","2018-04-10 00:01:11");
        map.put("user_id","2088812462670008");
        map.put("gmt_creator","pcac");
        map.put("gmt_modifier","pcac");
        map.put("approval_no","201804100124456400P");
        map.put("approval_type","CAR_MORTGAGE_CREDIT_APPROVAL");
        map.put("status","USER_UNCONFIRMED");
        map.put("result","FINISHED");
        map.put("fail_code",null);
        map.put("fail_remark",null);
        map.put("ext_info","{\"bfCreditAmt\":61000.00,\"creditInfo\":\"{\\\"accessible\\\":true," + "\\\"amount\\\":{\\\"amount\\\":61000.00,\\\"cent\\\":6100000,\\\"centFactor\\\":100," + "\\\"currency\\\":{\\\"currencyCode\\\":\\\"CNY\\\",\\\"displayName\\\":\\\"人民币\\\"," + "\\\"symbol\\\":\\\"￥\\\"},\\\"currencyCode\\\":\\\"CNY\\\"},\\\"annualIntRate\\\":0.18," + "\\\"commonAccessible\\\":true,\\\"dailyRate\\\":0.00050000,\\\"promoted\\\":false," + "\\\"repayModes\\\":[\\\"1\\\"],\\\"specificAccessible\\\":false}\"}");
        map.put("pre_env","N");
        map.put("apply_no","201804100124572300A");
        map.put("amount","61000.00000000");

        UniformEvent  uniformEvent  =  new  UniformEvent();
        uniformEvent.setTopic("TP_DATABUS_DRC");
        uniformEvent.setEventCode("IDB_L_98838.pcapplycore.pcac_approval");
        uniformEvent.setEventPayload(map);
        uniformEvent.getProperties().put("dbsyncTs",  System.currentTimeMillis()  /  1000  +  "12345678");
        uniformEvent.getProperties().put("ddDBType",  "MYSQL");
        uniformEvent.getProperties().put("ddOperationType",  "INSERT");
        uniformEvent.getProperties().put("ddTs",  System.currentTimeMillis()  /  1000);

        //********************

        Map<String,  Object> map1  =  new HashMap<String,  Object>();
        map1.put("id","1386128");
        map1.put("gmt_create","2018-04-10 00:02:07");
        map1.put("gmt_modified","2018-04-10 00:13:11");
        map1.put("user_id","2088502261360005");
        map1.put("gmt_creator","pcac");
        map1.put("gmt_modifier","pcac");
        map1.put("approval_no","201804100124456500P");
        map1.put("approval_type","JB_PLATFORM_CREDIT_APPLY_APPROVAL");
        map1.put("status","DONE");
        map1.put("result","FINISHED");
        map1.put("fail_code","0000");
        map1.put("fail_remark","授信通过");
        map1.put("ext_info","{\"bfCreditAmt\":0.00,\"creditInfo\":\"{\\\"accessible\\\":true," + "\\\"amount\\\":{\\\"amount\\\":5400.00,\\\"cent\\\":540000,\\\"centFactor\\\":100," + "\\\"currency\\\":{\\\"currencyCode\\\":\\\"CNY\\\",\\\"displayName\\\":\\\"人民币\\\"," + "\\\"symbol\\\":\\\"￥\\\"},\\\"currencyCode\\\":\\\"CNY\\\"},\\\"annualIntRate\\\":0.18000," + "\\\"commonAccessible\\\":false,\\\"creditPeriodNum\\\":12,\\\"creditPeriodUnit\\\":\\\"M\\\"," + "\\\"failCode\\\":\\\"0000\\\",\\\"failRemark\\\":\\\"授信通过\\\"," + "\\\"orgInfoDTO\\\":{\\\"loanMode\\\":\\\"2\\\",\\\"orgAlipayUserId\\\":\\\"2088511924976330\\\"," + "\\\"orgArNo\\\":\\\"20170522J1010111011682522004\\\",\\\"orgCode\\\":\\\"XABK\\\"," + "\\\"orgName\\\":\\\"西安银行股份有限公司\\\",\\\"orgShortName\\\":\\\"西安银行\\\"," + "\\\"participants\\\":[{\\\"instCode\\\":\\\"XABK\\\",\\\"orgName\\\":\\\"西安银行股份有限公司\\\"," + "\\\"orgPartnerId\\\":\\\"2088511924976330\\\",\\\"orgSimpleName\\\":\\\"西安银行\\\"," + "\\\"partner\\\":\\\"01\\\"}],\\\"signature\\\":\\\"4AD7A2B23843BDF05FA45C5B8248D083\\\"," + "\\\"zmAuthOrgFlag\\\":false},\\\"platformCreditNo\\\":\\\"20180410001107006911523290267691\\\"," + "\\\"promoted\\\":false,\\\"repayModes\\\":[\\\"1\\\"],\\\"specificAccessible\\\":false}\"}");
        map1.put("pre_env","N");
        map1.put("apply_no","201804100124527500A");
        map1.put("amount","5400.00000000");

        UniformEvent  uniformEvent1  =  new  UniformEvent();
        uniformEvent1.setTopic("TP_DATABUS_DRC");
        uniformEvent1.setEventCode("IDB_L_98838.pcapplycore.pcac_approval");
        uniformEvent1.setEventPayload(map1);
        uniformEvent1.getProperties().put("dbsyncTs",  System.currentTimeMillis()  /  1000  +  "123456789");
        uniformEvent1.getProperties().put("ddDBType",  "MYSQL");
        uniformEvent1.getProperties().put("ddOperationType",  "INSERT");
        uniformEvent1.getProperties().put("ddTs",  System.currentTimeMillis()  /  1000+100);

        List<UniformEvent> eventPayload  =  new ArrayList<UniformEvent>();
        eventPayload.add(uniformEvent);
        eventPayload.add(uniformEvent1);

        UniformEvent  masterUniformEvent  =  new  UniformEvent();
        masterUniformEvent.setTopic("TP_DATABUS_DRC");
        masterUniformEvent.setEventCode("IDB_L_98838.pcapplycore.pcac_approval");
        masterUniformEvent.setEventPayload(eventPayload);

        return  masterUniformEvent;
    }
}