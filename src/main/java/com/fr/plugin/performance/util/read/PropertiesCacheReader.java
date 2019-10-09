package com.fr.plugin.performance.util.read;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by yuwh on 2018/10/22
 * Description:none
 * @author yuwh
 */
public class PropertiesCacheReader {
    private static PropertiesCacheReader ourInstance = new PropertiesCacheReader();
    private String filepath = PropertiesCacheReader.class.getResource("/").getPath().replace("WEB-INF/classes/", "");
    private Map<String, Map> proMap = new HashMap();

    private PropertiesCacheReader() { }

    public static PropertiesCacheReader getInstance() {
        return ourInstance;
    }

    /**
    * @params
    * @return java.util.Map
    * @description: 读取配置文件至内存返回Map
    */
    public Map readProperties(String path){
        //pro中没有就先载入
        if(! proMap.containsKey(path)) {
            this.loadPro2Map(path,proMap);
        }
        Map<String,String> resultMap = proMap.get(path);
        return resultMap;
    }

    /**
    * @params
    * @return java.lang.String
    * @description: 读取具体配置项
    */
    public String readProperties(String propath,String prokey){
        if(propath != "" && prokey != "") {
            try {
                return readProperties(propath).get(prokey).toString();
            } catch(RuntimeException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    protected Map<String, Map> readProperties(){ return proMap; }

    /**
    * @params
    * @return java.util.Map
    * @description: 仅将 配置文件 读入内存 指定的Map中，这里是proMap
    */
    private void loadPro2Map(String path, Map maps) {
        Map promap = new HashMap();
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(filepath+path);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            props.load(bufferedReader);
            Enumeration en = props.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String value = props.getProperty(key);
                promap.put(key, value);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            maps.put(path, promap);
        }
    }

    /**
    * @params
    * @return boolean
    * @description: setupPro的第一步——修改 配置文件 的指定属性,顺序会变的问题再说
    */
    private synchronized boolean writePro2File(String path, Map map) {
        try{
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(filepath+path);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            props.load(bufferedReader);
            for(Map.Entry<String,String> et : (Set<Map.Entry>)map.entrySet()){
                props.setProperty(et.getKey(),et.getValue());
            }
            FileOutputStream fos = new FileOutputStream(filepath+path,false);
            props.store(fos,"");
            fos.close();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {

        }
        return true;
    }

    /**
    * @params
    * @return boolean
    * @description: setupPro的第二步——修改 配置文件 之后注销并更新 内存 中的proMap
    */
    private synchronized boolean reloadPro2Map(String path) {
        Map proMap_c = new HashMap();

        if(proMap != null) {proMap_c.putAll(proMap);}
        loadPro2Map(path,proMap_c);
        if(proMap_c == null){
            return false;
        } else {
            Map tmp = proMap;
            proMap = proMap_c;
            tmp.clear();
        }
        return true;
    }

    /**
    * @return void
    * @description: 更新指定配置文件的制定属性，两步完成
    */
    public void setupPro(String path, Map map){
        if(writePro2File(path,map)){
            if(reloadPro2Map(path)){
                System.out.println("PropertiesMap Reload Successfully");
            } else {
                System.out.println("Reload To Memory Failed");
            }
        } else {
            System.out.println("Write To Properties Failed");
        }
    }

    /**
    * @return
    * @description: 匹配出不同的配置的改变
    */
    public static Map ProCompare(String path, Map map, Boolean StrictMod) {
        Set<Map.Entry> var1 = map.entrySet();
        Map var3 = PropertiesCacheReader.getInstance().readProperties(path);
        String var4;
        String var5;
        Map var6 = new HashMap();
        var6.putAll(map);
        for(Map.Entry<String, String> var2:var1){
            var4 = var2.getKey();
            var5 = var2.getValue();
            if( var3.containsKey(var4) ){
                if( var3.get(var4).equals(var5) ){
                    var6.remove(var4);
                }
            } else if(StrictMod) {
                 var6.remove(var4);
            }
        }
        return var6;
    }
}
