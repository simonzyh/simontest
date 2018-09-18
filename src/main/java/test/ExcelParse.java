package test;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ExcelParse {

   static  String barcode = "110900055," +
            "702000018," +
            "602200235," +
            "601300063," +
            "702000391," +
            "109520004," +
            "119120001," +
            "111320040," +
            "702000012," +
            "602300152," +
            "611120025," +
            "109110842," +
            "111320146," +
            "611100085," +
            "111320187," +
            "111620028," +
            "114410108," +
            "108120082," +
            "303220085," +
            "108210053," +
            "302510356," +
            "302420010," +
            "108210070," +
            "303210047," +
            "108110082," +
            "302440001," +
            "601200022," +
            "601200081," +
            "902201100," +
            "301630016," +
            "301620022," +
            "301660035," +
            "301630015," +
            "301650016," +
            "309100005," +
            "120200053," +
            "301610070,";

    public static void main(String[] args){
        String filePath = "/Users/ljf/zyh/goods.xlsx";

        List<List<String>> data=getData(filePath);

        for(List<String> row:data){
              String barCode=row.get(2);
            if(!barcode.contains(barCode)||StringUtils.isBlank(barCode)){
                continue;
            }
            String spid=row.get(1);
            String unit=row.get(7);
            String spec=row.get(5);
            Map<String,Object> attribute=new HashMap<>();
            attribute.put("spid",spid);
             // System.out.println("('"+data[0].trim()+"','"+data[1].trim()+"','"+data[0].trim()+"',0"+","+data[6].replace("¥","").replace("￥","").trim()+",now(),now(),'hnhys',"+data[9].trim()+",1,'"+data[4].trim()+"'),");
            System.out.println("update wx_goods set spec='"+spec+"',attribute='" + JSON.toJSONString(attribute) + "',unit='"+unit+"' where bar_code='" + barCode + "';");

        }
    }

    public static List<List<String>> getData(String filePath) {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<List<String>> list = null;
        String cellData = null;
        wb = readExcel(filePath);
        if (wb != null) {
            //用来存放表中数据
            list = new ArrayList<List<String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 1; i < rownum; i++) {
                List<String> rowdata = new ArrayList<>();
                row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < colnum; j++) {
                        cellData = "";
                        try {
                            cellData = row.getCell(j).getStringCellValue();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(row.getCell(j));
                        }
                        rowdata.add(cellData.trim());
                    }
                } else {
                    break;
                }
                 list.add(rowdata);
            }
        }
        return list;
    }


    //读取excel
    public static Workbook readExcel(String filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

}

