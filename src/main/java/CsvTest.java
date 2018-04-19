import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.ecs.wml.I;

/**
 * Created by yehua.zyh on 2018/3/20.
 */
public class CsvTest {

    public static void main(String[] args) throws Exception {

        CSVReader csvReader = new CSVReader(new FileReader(new File("/Users/yehua.zyh/Downloads/执行结果-11.txt")));
        List<String[]> datalist = csvReader.readAll();
        System.out.println(JSON.toJSONString(datalist));
        String[] key = datalist.get(0);
        String[] value = datalist.get(1);
        for (int i = 0; i < key.length; i++) {
            System.out.println("eventPayload.put(\"" + key[i] + "\",\"" + value[i] + "\")");
        }
        Map<String,String> eventPayload=new HashMap<String,String>();

        eventPayload.put("id","1407609");
        eventPayload.put("gmt_create","2018-03-23 21:52:01");
        eventPayload.put("gmt_modified","2018-03-23 21:53:51");
        eventPayload.put("user_id","2088422750545482");
        eventPayload.put("gmt_creator","pcac");
        eventPayload.put("gmt_modifier","pcac");
        eventPayload.put("apply_no","201803230142402948A");
        eventPayload.put("apply_status","APPLY_FINISH");
        eventPayload.put("person_name","邵玉华");
        eventPayload.put("cert_no","232303195805101021");
        eventPayload.put("amount","0E-8");
        eventPayload.put("virtual_credit_type","JB_PLATFORM_2_ADMIT_CREDIT_APPLY");
        eventPayload.put("apply_date","2018-03-23 21:52:01");
        eventPayload.put("prod_id","J1010100100000000004");
        eventPayload.put("service_type","null");
        eventPayload.put("rel_prod","null");
        eventPayload.put("ext_no","20180323fe7284bd-4c62-4a27-b4df-b173b66b2d3448B");
        eventPayload.put("ext_json",    "{\"PUSH_URL\":\"/www/jiebei/pages/openScene.htm?undefined\"," + "\"antLoanServiceV2ArNo\":\"20180325J1010111039206771797\",\"creditSignature\":false," + "\"device_info\":{\"imei\":\"\",\"imsi\":\"\",\"ip\":\"\",\"mac\":\"\",\"wifimac\":\"\"}," + "\"orgArNo\":\"20180325J1010111039206771797\",\"platformCreditNo\":\"2018032510145180\"," + "\"verifyBizId\":\"lendweb026bb9fc-f782-4b0a-a128-bae83fb0f21df51bdd5affa56cd6839ce3e9f0709a8a\"}");
        eventPayload.put("refuse_code","null");
        eventPayload.put("ext_items","null");
        eventPayload.put("org_info",    "{\"loanMode\":\"4\",\"orgAlipayUserId\":\"2088621779633525\"," + "\"orgArNo\":\"20180209J1010111037343624003\",\"orgCode\":\"V_MYBK_GLBK\"," + "\"orgName\":\"浙江网商银行股份有限公司和桂林银行股份有限公司\",\"orgShortName\":\"浙江网商银行,桂林银行\"," + "\"participants\":[{\"instCode\":\"MYBK\",\"investRate\":0.1,\"orgName\":\"浙江网商银行股份有限公司\"," + "\"orgPartnerId\":\"2088921950512727\",\"orgSimpleName\":\"浙江网商银行\",\"partner\":\"01\"," + "\"zmAuthOrgFlag\":false},{\"instCode\":\"GLBK\",\"investRate\":0.9,\"orgName\":\"桂林银行股份有限公司\"," + "\"orgPartnerId\":\"2088621779633525\",\"orgSimpleName\":\"桂林银行\",\"partner\":\"04\",\"zmAuthOrgFlag\":false}]," + "\"signature\":\"8177B469C64A6E14B171BDE929E0A9A3\",\"zmAuthOrgFlag\":false}");
        eventPayload.put("mobile_no","13303660565");
        eventPayload.put("channel","2100");
        eventPayload.put("scene","PLATFORM_CASH_LOAN");
    }
    public static void main1(String[] args) throws  Exception {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CSVReader csvReader=new CSVReader(new FileReader(new File("/Users/yehua.zyh/Downloads/执行结果-3.txt")));
        csvReader.readNext();
        List<String[]> datalist= csvReader.readAll();
        System.out.println(JSON.toJSONString(datalist));
        String insertSql="insert into pcip_credit_apply(gmt_create, gmt_modified, user_id, credit_apply_no ,"
            + "            cert_no, cert_type ,user_name, mobile_no, credit_type, pd_code, org_code,"
            + "            org_name, apply_date, status, result_msg, loan_mode, credit_no,"
            + "            zm_auth_flag, credit_amt, credit_int_rate, env, ext_info,org_code_pt ,scene ,channel )values (";
        List<String> fullsql=new ArrayList<>();
        for(String[] strArr:datalist){
            try {
                StringBuffer sql = new StringBuffer();
                sql.append(insertSql).append("'");

                JSONObject orgINfo = JSON.parseObject(strArr[22]);
                JSONObject extInfo = JSON.parseObject(strArr[17]);
                String gmt_create = strArr[1];
                sql.append(gmt_create).append("','");

                String gmt_modified = strArr[2];
                sql.append(gmt_modified).append("','");

                String user_id = strArr[3];
                sql.append(user_id).append("','");

                String credit_apply_no = strArr[6];
                sql.append(credit_apply_no).append("','");

                String cert_no = strArr[9];
                sql.append(cert_no).append("','");

                String cert_type = "IDENTITY_CARD";
                sql.append(cert_type).append("','");

                String user_NAME = strArr[8];
                sql.append(user_NAME).append("','");

                String mobile_no = strArr[23];
                sql.append(mobile_no).append("','");

                String credit_type = strArr[11];
                sql.append(credit_type).append("','");

                String pd_code = strArr[13];
                sql.append(pd_code).append("','");

                String org_code = orgINfo.getString("orgCode");
                sql.append(org_code).append("','");

                String org_name = orgINfo.getString("orgName");
                sql.append(org_name).append("','");

                String apply_date = strArr[12];
                sql.append(apply_date).append("','");

                String status = strArr[7];
                sql.append(status).append("','");

                String result_msg = strArr[19];
                sql.append(result_msg).append("','");

                String loan_mode = orgINfo.getString("loanMode");
                sql.append(loan_mode).append("','");

                String credit_no = extInfo.getString("platformCreditNo");
                sql.append(credit_no).append("','");

                String zm_auth_flag = "1";
                sql.append(zm_auth_flag).append("','");

                String credit_amt = "200000";
                sql.append(credit_amt).append("','");

                String credit_int_rate = "0.012";
                sql.append(credit_int_rate).append("','");

                String env = "PROD";
                sql.append(env).append("','");

                String ext_info = strArr[17];
                sql.append(ext_info).append("','");

                String org_code_pt = org_code.substring(org_code.lastIndexOf("_") + 1);
                sql.append(org_code_pt).append("','");

                String scene = strArr[23];
                sql.append(scene).append("','");

                String channel = strArr[24];
                sql.append(channel).append("') ;");
                System.out.println(sql.toString());
            }catch (Exception e){
                //System.out.println(JSON.toJSONString(strArr));
               // e.printStackTrace();
            }



        }


    }

    private Object getEnv(){
        Map<String,Object> mapData=new HashMap<>();

        String extInfo = (String) mapData.get("ext_info");

        Map<String,Object> extInfoMap=JSON.parseObject(extInfo,Map.class);
        if(!extInfoMap.containsKey("creditInfo")){
            return null;
        }
        Map<String,Object> creditInfo=JSON.parseObject(extInfoMap.get("creditInfo").toString(),Map.class);
        if(null==creditInfo||!creditInfo.containsKey("orgInfoDTO")){
            return null;
        }
        Map<String,Object> orgInfoDTO=JSON.parseObject(creditInfo.get("orgInfoDTO").toString(),Map.class);
        if(null==orgInfoDTO||!orgInfoDTO.containsKey("orgCode")){
            return null;
        }
        String orgCode=  orgInfoDTO.get("orgCode").toString();
        if(!"V_MYBK".equals(orgCode)){
            return orgCode;
        }
        List<Map> participants=JSON.parseArray(orgInfoDTO.get("participants").toString(),Map.class);
        if(null==participants){
            return null;
        }
        for(Map map:participants){
           if(map==null|| !map.containsKey("instCode")){
               continue;
           }
           String instCode=map.get("instCode").toString();
           if("MYBK".equals(instCode)){
               continue;
           }
           orgCode+="_"+instCode;
           break;
        }
       return orgCode;
    }
}