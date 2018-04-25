import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang.StringUtils;

/**
 * Created by yehua.zyh on 2018/4/19.
 */
public class FileTest {

    public static void main(String[] args)throws   Exception{
        System.out.println(Double.parseDouble("0E-8"));
        PrintWriter pw=new PrintWriter("/Users/yehua.zyh/Downloads/init.sql");
        List<Map<String,String>> creditApply=readData("/Users/yehua.zyh/Downloads/pcac_apply.csv");
        List<Map<String,String>> pcac_approval=readData("/Users/yehua.zyh/Downloads/pcac_approval.csv");
        List<Map<String,String>> loan=readData("/Users/yehua.zyh/Downloads/cl_loan_apply.csv");

        Map<String,Map<String,String>> approvalMap=new HashMap<>();
        pcac_approval.stream().forEach(e->{
            if("JB_PLATFORM_CREDIT_APPLY_APPROVAL,JB_PLATFORM_2_ADMIT_APPLY_APPROVAL,JB_PLATFORM_2_PRE_ADMIT_APPLY_APPROVAL".contains(e.get("approval_type").toString())){
                approvalMap.put(e.get("apply_no"),e);

            }

        }) ;

        Map<String,Map<String,String>> creditMap=new HashMap<>();
        creditApply.stream().forEach(e->{
            if(e.containsKey("prod_id")&& "J1010100100000000004".equals(e.get("prod_id").toString())){
                creditMap.put(e.get("apply_no"),e);
            }

        }) ;

         Map<String,String> userIdCerditNo=new HashMap<>();

        //生产授信申请数据插入语句
         StringBuffer creditSql=new StringBuffer();
         for(Map.Entry<String,Map<String,String>> data:creditMap.entrySet()){
             creditSql.append("insert into pcip_credit_apply( gmt_create,  gmt_modified,  user_id, "
                 + " credit_apply_no,  cert_no,  cert_type,  user_name,  mobile_no,  credit_type, "
                 + " pd_code,  org_code,  org_name,  apply_date,  status,  result_msg,  loan_mode,"
                 + "  credit_no,  zm_auth_flag,  credit_amt,  credit_int_rate,  env,  ext_info, "
                 + " channel,  scene,  org_code_pt) values(");
             String applyNo=data.getKey();
             Map<String,String> credit=data.getValue();
             Map<String,String> approval=approvalMap.get(applyNo);
             try {
                 String result_msg = null;
                 String credit_no = null;
                 String zm_auth_flag = null;
                 String credit_amt = null;
                 String credit_int_rate = null;


                 if (null != approval) {

                     JSONObject approvalExt = JSON.parseObject(hexStringToString(approval.get("ext_info") + ""));
                     if(approvalExt==null){
                         approvalExt=new JSONObject();
                     }
                     JSONObject creditInfo = JSON.parseObject(approvalExt.get("creditInfo") + "");
                     if(creditInfo==null){
                         creditInfo=new JSONObject();
                     }
                     JSONObject amount = JSON.parseObject(creditInfo.get("amount") + "");
                     JSONObject orgInfo = JSON.parseObject(creditInfo.get("orgInfoDTO") + "");
                     if(orgInfo==null){
                         orgInfo=new JSONObject();
                     }
                     result_msg = approval.get("fail_remark") + "";
                     if (null != amount) {
                         credit_amt = amount.get("cent") + "";
                     }
                     credit_no = creditInfo.get("platformCreditNo") + "";
                     credit_int_rate = creditInfo.get("annualIntRate") + "";
                     zm_auth_flag = orgInfo.get("zmAuthOrgFlag") + "";
                 }
                 // ,service_type,rel_prod,ext_no,ext_json,pre_env,refuse_code,
                 // gather_items,ext_items,org_info,,channel,scene,item_code,_dsql_guid
                 JSONObject orgInfo = JSON.parseObject(credit.get("org_info").toString());
                 String orgCode = orgInfo.get("orgCode") + "";
                 String orgName = orgInfo.get("orgName") + "";
                 String loan_mode = orgInfo.getString("loanMode");
                 creditSql.append("'" + credit.get("gmt_create") + "',")
                     .append("'" + credit.get("gmt_modified") + "',").append("'" + credit.get("user_id") + "',").append(
                     "'" + credit.get("apply_no") + "',")
                     .append("'" + credit.get("cert_no") + "',").append("'IDENTITY_CARD',").append(
                     "'" + credit.get("person_name") + "',")
                     .append("'" + credit.get("mobile_no") + "',").append(
                     "'" + credit.get("virtual_credit_type") + "',").append("'" + credit.get("prod_id") + "',")
                     .append("'" + orgCode + "',").append("'" + orgName + "',").append(
                     "'" + credit.get("apply_date") + "',")
                     .append("'" + credit.get("apply_status") + "',").append("'" + result_msg + "',").append(
                     loan_mode + ",")
                     .append("'" + credit_no + "',").append("'" + zm_auth_flag + "',").append(credit_amt + ",")
                     .append(credit_int_rate + ",'PROD',").append("'" + credit.get("ext_json") + "',")
                     .append("'" + credit.get("channel") + "',").append("'" + credit.get("scene") + "',")
                     .append("'" + getOrgCodePt(orgCode) + "'").append(");");


                 userIdCerditNo.put(credit.get("user_id")+orgCode,credit_no);


                 pw.println(creditSql.toString());
                 creditSql = new StringBuffer();
             }catch (Exception e){
                 e.printStackTrace();
                 System.out.println(credit);
                 System.out.println(approval);

             }
         }

        System.out.println( );
        System.out.println( );
        System.out.println( );
        System.out.println( );



        //生成贷款申请数据插入语句
        StringBuffer loanSql=new StringBuffer();

        //INSERT INTO `pcip_loan_apply` VALUES
        // ('2017-06-01 11:47:47', '2017-06-01 13:35:38',
        // '2088202865567762', '2017060110193018140000000000760000346371',
        // '20170601J1010111000110037762', '652824196404186417',
        // 'IDENTITY_CARD', 'qljuxs',
        // 'J1010100100000000001', '1.0020',
        // '5000', '5', 'D', '0.000000', '6', '20882028655677620156',
        // 'ALIPAY_BALANCE', 'QXMXT044', '2017-06-01 11:47:47',
        // 'CLEAR', '2017-06-06 00:00:00', '', '',
        // null, 'PROD',
        // '{\"ALIPAY_LOGON_ID\":\"15886626810\",\"AVAILABLE_AMOUNT_SNAPSHOT\":\"2000000\",\"CREDIT_AMOUNT_SNAPSHOT\":\"2000000\",\"DEBTOR_LOGON_ID\":\"15886626810\",\"ENCASH_ORG_NAME\":\"厦门国际信托有限公司\",\"MAIN_REPAY_ACCOUNT_NO\":\"20882028655677620156\",\"ORG_AR_NO\":\"20160818J1010110000010945006\",\"ORG_AR_VERSION\":\"2016-08-18 17:41:45.134\",\"ORG_IP_ID\":\"20889939999999531156\",\"clientIp\":\"10.15.17.65\",\"firstUseLoan\":\"true\",\"identical\":\"1\",\"isFirstCreditSign\":\"true\",\"umidToken\":\"YFLvMnWq/P7laWmPFWEIJZrBSUdhcKQv\"}',
        // 'QXMXT044', '厦门国际信托有限公司');
        for(Map<String,String> data:loan) {
            if(!"J1010100100000000004".equals(data.get("pd_code"))){
                continue;
            }
            if(!userIdCerditNo.containsKey(data.get("user_id")+data.get("encash_org"))){
                continue;
            }
            String serditNo=userIdCerditNo.get(data.get("user_id")+data.get("encash_org"));
            JSONObject extInfo=JSON.parseObject(data.get("ext_info"));
            if(extInfo==null){
                extInfo=new JSONObject();
            }
            loanSql.append("INSERT INTO `pcip_loan_apply` VALUES (");
            loanSql.append("'"+data.get("gmt_create")+"',").append("'"+data.get("gmt_modified")+"',")
                .append("'"+data.get("user_id")+"',").append("'"+data.get("apply_no")+"',")
                .append("'"+data.get("ar_no")+"',").append("'"+data.get("cert_no")+"',")
                .append("'IDENTITY_CARD',").append("'"+data.get("user_name")+"',")
                .append("'"+data.get("pd_code")+"',").append("'"+data.get("pd_version")+"',")
                .append(""+data.get("apply_amt")+",").append(""+data.get("term")+",").append("'D',").append(""+Double.parseDouble(data.get("risk_int_rate"))+",").append("'"+data.get("repay_mode")+"',")
                .append("'"+data.get("grant_acct_no")+"',").append("'"+data.get("grant_acct_type")+"',")
                .append("'"+data.get("encash_org")+"',").append("'"+data.get("apply_date")+"',")
                .append("'"+data.get("status")+"',")
                .append("'"+data.get("due_date")+"',").append("'"+data.get("credit_no")+"',")
                .append("'"+data.get("loan_mode")+"',").append("'"+extInfo.get("loanPurpose")+"',")

                .append("'PROD',").append("'"+data.get("ext_info")+"',")
                .append("'"+getOrgCodePt(data.get("encash_org")+"',")).append("'"+extInfo.getString("ENCASH_ORG_NAME")+"');");
            pw.println(loanSql.toString());
            loanSql=new StringBuffer();


        }
        pw.flush();
        pw.close();

//,,,,,,
        // ,, ,policy_code,,
        // ,,,,channel,scene,
        // out_order_no,ext_info,,,
        // acct_no,,,cert_type,,cnl_ev_code,
        // biz_ev_code,biz_pd_code,cnl_pd_code,ev_code,cnl_no,
        // discount_info,bank_ext_info,partner_alias_name,partner_id,
        // partner_discount_ratio,out_biz_no,partner_app_id,
        // partner_ar_no,partner_ar_gmt_vrsn,pre_discount_int_rate,
        // partner_refund_times,seller_discount_ratio,seller_id,
        // ,,biz_feature,term_unit,need_prepayment_fee,
        // sign_contract_list,guarantee_inst,guarantee_fee_rate,first_term_merge_day,step,_dsql_guid



    }
    public static String getOrgCodePt(String orgCode) {
        if (StringUtils.isBlank(orgCode)) {
            return null;
        }
        if(!orgCode.contains("_")){
            return orgCode;
        }
        return orgCode.substring(orgCode.lastIndexOf("_") + 1);
    }

    static List<Map<String,String>> readData(String file)throws Exception{

        CSVReader csvReader = new CSVReader(new FileReader(new File(file)));
        List<String[]> datalist = csvReader.readAll();

        List<Map<String,String>> res=new ArrayList<>();

        String[] key= datalist.get(0);
        for(int i=1;i<datalist.size();i++){
            Map<String, String> map = new HashMap<>();
            String[] data = datalist.get(i);

            try {
                for (int j = 0; j < data.length; j++) {
                    map.put(key[j], data[j]);
                }
                res.add(map);
            }catch (Exception e){

                System.out.println(JSON.toJSONString(map.get("approval_no")));

             }
        }
        return res;
     }


    static List<Map<String,String>> readDataFromSql(String file)throws Exception{

        BufferedReader br=new BufferedReader(new FileReader(new File(file)));
        List<Map<String,String>> res=new ArrayList<>();

        String line=null;
        String[] key=null;
        while((line=br.readLine())!=null){
            if(line.contains("INSERT INTO")){
                if(key==null){
                    key=line.substring(line.indexOf("(")+1,line.indexOf(")")).split(",");
                    System.out.println(JSON.toJSONString(key));
                }
                continue;
            }

            String[] data=line.substring(line.indexOf("(")+1,line.indexOf(")")).split(",'");
            Map<String, String> map = new HashMap<>();

            try {
                for (int j = 0; j < data.length; j++) {
                    map.put(key[j], data[j]);
                }
                res.add(map);
            }catch (Exception e){
                 e.printStackTrace();
                System.out.println(JSON.toJSONString(data));

            }

        }


        return res;
    }


    public static void main1(String[] args) throws Exception {
        String data=hexStringToString("7B226266437265646974416D74223A32303030302E30302C22637265646974496E666F223A227B5C2261636365737369626C655C223A747275652C5C22616D6F756E745C223A7B5C22616D6F756E745C223A32303030302E30302C5C2263656E745C223A323030303030302C5C2263656E74466163746F725C223A3130302C5C2263757272656E63795C223A7B5C2263757272656E6379436F64655C223A5C22434E595C222C5C22646973706C61794E616D655C223A5C22E4BABAE6B091E5B8815C222C5C2273796D626F6C5C223A5C22EFBFA55C227D2C5C2263757272656E6379436F64655C223A5C22434E595C227D2C5C22636F6D6D6F6E41636365737369626C655C223A747275652C5C2270726F6D6F7465645C223A66616C73652C5C22737065636966696341636365737369626C655C223A66616C73657D227D");
        JSONObject obj=JSON.parseObject(data);
        System.out.println(obj);
     }

    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

}

