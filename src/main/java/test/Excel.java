/**
 * @Title: Excel.java
 * @Package: com.vipshop.coupon.util
 * @Description: TODO
 * @author 张业华
 * @date 2014-1-22 上午11:13:34
 */

package test;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;


/**
 * @author 张业华
 * @Description 利用xml生成excel文件
 * @date 2014-1-22 上午11:13:34
 */

public class Excel {

    /**
     * 每一页的数据限制
     */
    private static final int sheet_data_size = 51000;

    /**
     * 表格的换行
     */
    private static final String BR = "&#10;";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 当前行
     */
    private int currentRow = 0;

    /**
     * xml流
     */
    private PrintWriter xmlPrint;

    /**
     * Creates a new instance of Excel. Description
     *
     * @param filePath
     * @throws java.io.FileNotFoundException
     */
    public Excel(OutputStream out) throws Exception {
        logger.info("开始写入excel文件");
        xmlPrint = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
        xmlPrint.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        xmlPrint.println("<?mso-application progid=\"Excel.Sheet\"?>");
        xmlPrint
                .println("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"   xmlns:o=\"urn:schemas-microsoft-com:office:office\"   xmlns:x=\"urn:schemas-microsoft-com:office:excel\"   xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"    xmlns:html=\"http://www.w3.org/TR/REC-html40\">");
        xmlPrint.println("<DocumentProperties xmlns=\"urn:schemas-microsoft-com:office:office\">");
        xmlPrint.println("<Version>11.9999</Version>");
        xmlPrint.println("</DocumentProperties>");
        xmlPrint.println("<ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\">");
        xmlPrint.println("<WindowHeight>10005</WindowHeight>");
        xmlPrint.println("<WindowWidth>10005</WindowWidth>");
        xmlPrint.println("<WindowTopX>120</WindowTopX>");
        xmlPrint.println("<WindowTopY>135</WindowTopY>");
        xmlPrint.println("<ActiveSheet>1</ActiveSheet>");
        xmlPrint.println("<ProtectStructure>False</ProtectStructure>");
        xmlPrint.println("<ProtectWindows>False</ProtectWindows>");
        xmlPrint.println("</ExcelWorkbook>");
        xmlPrint.println("<Styles>");
        xmlPrint.println("<Style ss:ID=\"Default\" ss:Name=\"Normal\">");
        xmlPrint.println("<Alignment ss:Vertical=\"Center\"/>");
        xmlPrint.println("<Borders/> ");
        xmlPrint.println(" <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"12\"/>");
        xmlPrint.println("<Interior/> ");
        xmlPrint.println("<NumberFormat/>");
        xmlPrint.println(" <Protection/>");
        xmlPrint.println("</Style>");
        xmlPrint.println("<Style ss:ID=\"s23\">");
        xmlPrint.println("<Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"12\" ss:Color=\"#0000FF\"   ss:Bold=\"1\"/>");
        xmlPrint.println("<Interior ss:Color=\"#C0C0C0\" ss:Pattern=\"Solid\"/> ");
        xmlPrint.println("</Style>");
        xmlPrint.println("</Styles> ");
        xmlPrint.println("");
        xmlPrint.flush();

    }

    /**
     * @param sheetName   sheet 名称
     * @param columnCount 列数
     * @param rowCount    条数
     * @Description 打开一个新的sheet
     * @author 张业华
     */
    public synchronized void createSheet(String sheetName, int columnCount, int rowCount) {
        logger.info("打开新的sheet sheetName=" + sheetName + " columnCount=" + columnCount + " rowCount=" + rowCount);
        currentRow = 0;// 新的sheet 条数为0
        xmlPrint.println("<Worksheet ss:Name=\"" + sheetName + "\">");
        xmlPrint.println("<Table ss:ExpandedColumnCount=\"" + columnCount + "\" ss:ExpandedRowCount=\"" + rowCount
                + "\" x:FullColumns=\"1\"   x:FullRows=\"1\" ss:DefaultColumnWidth=\"200\" ss:DefaultRowHeight=\"16.5\">");
        xmlPrint.flush();
    }

    /**
     * @Description 结束当前sheet
     * @author 张业华
     */
    public synchronized void endSheet() {
        logger.info("结束当前sheet ");
        xmlPrint.println("</Table> ");
        xmlPrint.println("</Worksheet>");
        xmlPrint.flush();

    }

    /**
     * @param data
     * @Description 追加数据到上一次创建的sheet 每一个sheet只写入5万条数据 多余的数据不写入
     * @author 张业华
     */
    public synchronized void writeData(List<String[]> dataList) {
        logger.info("写入数据 当前row= " + currentRow);
        if (null == dataList || dataList.size() == 0) return;
        for (String[] data : dataList) {
            // 超过限定条数返回
            if (currentRow > sheet_data_size) return;
            currentRow++;
            xmlPrint.println("<Row ss:AutoFitHeight=\"0\">");
            for (String cell : data) {
                xmlPrint.println("<Cell><Data ss:Type=\"String\">" + (StringUtils.isBlank(cell) ? "" : cell.replaceAll("\n", BR))
                        + "</Data></Cell> ");
            }
            xmlPrint.println("</Row> ");
        }
        xmlPrint.flush();

    }

    /**
     * @Description 结束excel文件写入
     * @author 张业华
     */
    public synchronized void endWorkbook() {
        logger.info("结束 excel 写入 ");
        xmlPrint.println("</Workbook>  ");
        xmlPrint.flush();
        xmlPrint.close();
    }

}
