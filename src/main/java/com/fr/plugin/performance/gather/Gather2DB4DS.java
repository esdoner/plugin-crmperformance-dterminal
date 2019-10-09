package com.fr.plugin.performance.gather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuwh on 2018/9/19
 * Description:none
 * @author yuwh
 */
public class Gather2DB4DS extends Gather2DB{
    private String path;
    private String paras;
    private String result;
    private String name;
    private String queryIndexStr;
    private String tTime;

    public Gather2DB4DS(Map var1, String var2, String var3, Map<String, String[]> var4, String var5) {
        super("performance2018");
        setContainerType("2DB4DS");
        path = String.valueOf(var1.get("cptName"));
        tTime = String.valueOf(var1.get("tTime"));
        result = var2;
        name = var3;
        queryIndexStr = makeIndex((HashMap) var4);
        paras = var5;
    }

    private String makeIndex(HashMap var5){
        ArrayList<Integer> indexXMin = new ArrayList<>(2);
        ArrayList<Integer> indexXMax = new ArrayList<>(2);
        Set<Map.Entry> var6 = var5.entrySet();
        if(var6.size() > 0 ) {
            for (Map.Entry<String, String[]> var7 : var6) {
                String[] var8 = var7.getValue();
                short i = 2;
                while (i + 1 <= var8.length) {
                    if (indexXMax.size() < i - 1 || indexXMin.size() < i - 1) {
                        indexXMax.add(Integer.parseInt(var8[i]));
                        indexXMin.add(Integer.parseInt(var8[i]));
                    }
                    if (Integer.parseInt(var8[i]) > indexXMax.get(i - 2)) {
                        indexXMax.set(i - 2, Integer.parseInt(var8[i]));
                    }
                    if (Integer.parseInt(var8[i]) < indexXMin.get(i - 2)) {
                        indexXMin.set(i - 2, Integer.parseInt(var8[i]));
                    }
                    i++;
                }
            }
            return indexXMin.toString().replaceAll("[\\[\\]]", "") + "," + indexXMax.toString().replaceAll("[\\[\\]]", "");
        }else{
            return "";
        }
    }

    @Override
    public boolean containerPrepare() {
        result = htmParser(result);
        queryIndexStr = queryIndexStr== ""? "0,0,0,0,0,0": queryIndexStr;
        paras = paras== ""? "null": "'"+paras+"'";
        result = result== ""? "null": "'"+result+"'";
        String fields = "test_paras,test_path,test_result,test_name,test_time,test_time_min,test_index1_min,test_index2_min,test_time_max,test_index1_max,test_index2_max,test_ttime";
        sqlString = "INSERT INTO cptanalysis_record("+fields+") VALUES ("+paras+",'"+path+"',"+result+",'"+name+"', UNIX_TIMESTAMP(),"+queryIndexStr+","+ tTime +")";
        if(sqlString.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void containerGather() {
        try {
            Object rst = conn.executeDML(sqlString);
            conFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String containerFeed() {
        return null;
    }
}
