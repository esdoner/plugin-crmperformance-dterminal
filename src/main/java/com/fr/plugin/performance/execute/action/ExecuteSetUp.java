package com.fr.plugin.performance.execute.action;

import com.fr.plugin.performance.deploy.carrier.FormCarrier;
import com.fr.plugin.performance.execute.action.ac.ReleaseActionCarrier;
import com.fr.plugin.performance.util.read.PropertiesCacheReader;
import com.fr.plugin.performance.setup.bucket.ThresholdBucket;
import com.fr.plugin.performance.setup.threshold.Threshold;
import com.fr.script.AbstractFunction;
import static com.fr.plugin.performance.util.read.EscapeStrReader.unescape;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuwh on 2018/12/17
 * Description:none
 * @author yuwh
 */
public class ExecuteSetUp extends AbstractFunction implements Executable{
    private FormCarrier AC= new ReleaseActionCarrier();

    ExecuteSetUp(){ }

    @Override
    public Object run(Object[] objects) {
        return fetchResult(objects);
    }

    @Override
    public String fetchResult(Object[] objects){
        String[] a = unescape(objects[0].toString().trim()).split(";");
        Map b = new HashMap();
        for (int i = 1;i < a.length; i++){
            String[] c = a[i].split(",");
            if (c[0].isEmpty() || c[1].isEmpty()) {
                return "0";
            } else {
                b.put(c[0] ,c[1]);
            }
        }

        Map d = PropertiesCacheReader.ProCompare(Threshold.PATH,b,true);
        //先要把文件中的，内存中的 配置 更新，用PropertiesReader.setupPro
        if(d.size() > 0) {
            PropertiesCacheReader.getInstance().setupPro(Threshold.PATH, d);
            //用ThresholdBucket.reset();注意是clear()了的
            ThresholdBucket.getInstance().reset();
        }
        return "1";
    }
}
