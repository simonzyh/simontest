package test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExceptionTest {
    static int MAX_BATCH_SIZE = 2;

    public static void main(String[] args) throws Exception {
        File f = new File("d:\\q.xls");
        FileOutputStream outputStream = new FileOutputStream(f);
        Excel excel = new Excel(outputStream);
        excel.createSheet("测试", 2, 20000);
        List<String[]> data = new ArrayList<String[]>();
        data.add(new String[]{"订单编号", "区域"});
        for (int i = 0; i < 19999; i++) {
            data.add(new String[]{"2252371204190512", "华东"});
        }
        excel.writeData(data);
        excel.endSheet();
        excel.endWorkbook();
    }


}

