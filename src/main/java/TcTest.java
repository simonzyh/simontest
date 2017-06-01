import com.alibaba.fastjson.JSON;
import com.taobao.beehive.scenes.client.ClientInfo;
import com.taobao.beehive.scenes.client.TC;
import com.taobao.beehive.scenes.client.bootstrap.TcClient;
import com.taobao.beehive.scenes.client.top.dto.RequestContext;
import com.taobao.beehive.scenes.client.top.dto.TopResult;
import com.taobao.beehive.scenes.client.top.dto.content.BaseContentDTO;
import com.taobao.hsf.hsfunit.HSFEasyStarter;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by yehua.zyh on 2016/9/12.
 */
public class TcTest {
public static void main(String[] args) throws  Exception {
    BufferedReader bmd=new BufferedReader(new InputStreamReader(new FileInputStream(new File("d:\\bmd.txt"))));
    List<String>  bmditem=new ArrayList<>();
    String readline=null;
    while((readline=bmd.readLine())!=null){
        bmditem.add(readline);
    }

    BufferedReader all=new BufferedReader(new InputStreamReader(new FileInputStream(new File("d:\\allitem.txt"))));
    PrintWriter pw=new PrintWriter("d:\\hmd.txt");
    List<String>  hmdItem=new ArrayList<>();

    while((readline=all.readLine())!=null){
         if(bmditem.contains(readline)){
             continue;
         }
        hmdItem.add(readline);
        pw.println(readline);

    }
     pw.flush();
}

}
