package databus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;

/**
 * Created by yehua.zyh on 2018/3/28.
 */
public class 贷款申请申请Test {

    public static void main(String[] args){



        Map<String,  Object> map  =  new HashMap<String,  Object>();
        map.put("apply_amt","100000");
        map.put("apply_date","2018-03-11 01:07:00");
        map.put("ar_no","20180311J1010111037848862475");
        map.put("cert_no","522132198901104939");
        map.put("cert_type","IDENTITY_CARD");
        map.put("credit_no","201712050080291247A");
        map.put("due_date","2018-09-17 00:00:00");
        map.put("encash_org","v_F51_SZBK");
        map.put("ext_info","{\"ENCASH_ORG_NAME\":\"重庆市蚂蚁商诚小额贷款有限公司及广州银行股份有限公司\",\"ALIPAY_LOGON_ID\":\"18885228700\",\"DEBTOR_LOGON_ID\":\"18885228700\",\"umidToken\":\"1G5Lxc1LOtq/sjUAAHnWF2hvAAAfjK6C\",\"loanPurpose\":\"dailyShopping\",\"ORG_IP_ID\":\"2088721715831984\",\"ORG_AR_NO\":\"20170906J1010111016888376002\",\"AUTH_CONT_EXIST\":\"true\",\"MAIN_REPAY_ACCOUNT_NO\":\"20888026381844710156\",\"firstUseLoan\":\"false\",\"CREDIT_AMOUNT_SNAPSHOT\":\"1650000\",\"identical\":\"2\",\"ENCASH_ORG_NAME_FOR_SHORT\":\"商诚小贷、广州银行\",\"FIRST_ENCASH_ORG_NAME\":\"重庆市蚂蚁商诚小额贷款有限公司\",\"ORG_AR_VERSION\":\"2018-02-27 19:19:27.908\",\"clientIp\":\"106.108.49.215\",\"AVAILABLE_AMOUNT_SNAPSHOT\":\"107024\",\"FIRST_ORG_IP_ID\":\"2088211080303244\",\"isFirstCreditSign\":\"false\"}");
        map.put("gmt_create","2018-03-11 01:07:00");
        map.put("gmt_modified","2018-03-11 01:08:27");
        map.put("grant_acct_no","6228481198674835278");
        map.put("grant_acct_type","BANK_DEBIT_CARD");
        map.put("apply_no","20180311101930181400000000004700592874699");
        map.put("loan_mode","4");
        map.put("pd_code","J1010100100000000004");
        map.put("pd_version","1.0007");
        map.put("repay_mode","1");
        map.put("risk_int_rate","0.16200000");
        map.put("status","USING");
        map.put("term","6");
        map.put("term_unit","M");
        map.put("user_id","2088802638184471");
        map.put("user_name","李啟维111111");

        UniformEvent  uniformEvent  =  new  UniformEvent();
        uniformEvent.setTopic("TP_DATABUS_DRC");
        uniformEvent.setEventCode("IDB_L_306273.antloan.cl_loan_apply");
        uniformEvent.setEventPayload(map);
        uniformEvent.getProperties().put("dbsyncTs",  System.currentTimeMillis()  /  1000  +  "12345678");
        uniformEvent.getProperties().put("ddDBType",  "MYSQL");
        uniformEvent.getProperties().put("ddOperationType",  "INSERT");
        uniformEvent.getProperties().put("ddTs",  System.currentTimeMillis()  /  1000);

        //********************
        Map<String,  Object> map1  =  new HashMap<String,  Object>();
        map1.put("apply_amt","100000");
        map1.put("apply_date","2018-03-11 01:07:00");
        map1.put("ar_no","20180311J1010111037848862475");
        map1.put("cert_no","522132198901104939");
        map1.put("cert_type","IDENTITY_CARD");
        map1.put("credit_no","201712050080291247A");
        map1.put("due_date","2018-09-17 00:00:00");
        map1.put("encash_org","v_F51_SZBK");
        map1.put("ext_info","{\"ENCASH_ORG_NAME\":\"重庆市蚂蚁商诚小额贷款有限公司及广州银行股份有限公司\",\"ALIPAY_LOGON_ID\":\"18885228700\",\"DEBTOR_LOGON_ID\":\"18885228700\",\"umidToken\":\"1G5Lxc1LOtq/sjUAAHnWF2hvAAAfjK6C\",\"loanPurpose\":\"dailyShopping\",\"ORG_IP_ID\":\"2088721715831984\",\"ORG_AR_NO\":\"20170906J1010111016888376002\",\"AUTH_CONT_EXIST\":\"true\",\"MAIN_REPAY_ACCOUNT_NO\":\"20888026381844710156\",\"firstUseLoan\":\"false\",\"CREDIT_AMOUNT_SNAPSHOT\":\"1650000\",\"identical\":\"2\",\"ENCASH_ORG_NAME_FOR_SHORT\":\"商诚小贷、广州银行\",\"FIRST_ENCASH_ORG_NAME\":\"重庆市蚂蚁商诚小额贷款有限公司\",\"ORG_AR_VERSION\":\"2018-02-27 19:19:27.908\",\"clientIp\":\"106.108.49.215\",\"AVAILABLE_AMOUNT_SNAPSHOT\":\"107024\",\"FIRST_ORG_IP_ID\":\"2088211080303244\",\"isFirstCreditSign\":\"false\"}");
        map1.put("gmt_create","2018-03-11 01:07:00");
        map1.put("gmt_modified","2018-03-11 01:08:27");
        map1.put("grant_acct_no","6228481198674835278");
        map1.put("grant_acct_type","BANK_DEBIT_CARD");
        map1.put("apply_no","201803111019301814000000000047005928746798");
        map1.put("loan_mode","4");
        map1.put("pd_code","J1010100100000000004");
        map1.put("pd_version","1.0007");
        map1.put("repay_mode","1");
        map1.put("risk_int_rate","0.16200000");
        map1.put("status","USING");
        map1.put("term","6");
        map1.put("term_unit","M");
        map1.put("user_id","2088802638184471");
        map1.put("user_name","李啟维22222");

        UniformEvent  uniformEvent1  =  new  UniformEvent();
        uniformEvent.setTopic("TP_DATABUS_DRC");
        uniformEvent.setEventCode("IDB_L_306273.antloan.cl_loan_apply");
        uniformEvent.setEventPayload(map1);
        uniformEvent.getProperties().put("dbsyncTs",  System.currentTimeMillis()  /  1000  +  "123456789");
        uniformEvent.getProperties().put("ddDBType",  "MYSQL");
        uniformEvent.getProperties().put("ddOperationType",  "INSERT");
        uniformEvent.getProperties().put("ddTs",  System.currentTimeMillis()  /  1000+100);


        List<UniformEvent> eventPayload  =  new ArrayList<UniformEvent>();
        eventPayload.add(uniformEvent);
        eventPayload.add(uniformEvent1);

        UniformEvent  masterUniformEvent  =  new  UniformEvent();
        masterUniformEvent.setTopic("TP_DATABUS_DRC");
        masterUniformEvent.setEventCode("IDB_L_306273.antloan.cl_loan_apply");
        masterUniformEvent.setEventPayload(eventPayload);
        // return  masterUniformEvent;
        System.out.println(JSON.toJSONString(masterUniformEvent));

    }

}