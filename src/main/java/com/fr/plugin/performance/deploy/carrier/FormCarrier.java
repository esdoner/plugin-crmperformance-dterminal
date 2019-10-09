package com.fr.plugin.performance.deploy.carrier;

import com.fr.plugin.performance.deploy.Assign;
import com.fr.plugin.performance.deploy.Deploy;
import com.fr.plugin.performance.deploy.unit.*;
import com.fr.plugin.performance.deploy.unit.form.Form;
import com.fr.plugin.performance.deploy.unit.form.Input;
import com.fr.plugin.performance.exception.BadCheckException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019.08.01
 * Description:InputCarrier, 非 final 可继承
 * //TODO 有未处理的异常抛出
 */
public class FormCarrier implements Deploy, Assign {
    private Map<String, Unit> units = new HashMap<String, Unit>();

    public FormCarrier() { }

    /**
     * 添加单元
     * @return
     */
    @Override
    public Deploy addUnit(Unit unit) {
        units.put(unit.getID(), unit);
        return this;
    }

    /**
     * 删除单元
     * @return
     */
    @Override
    public Deploy removeUnit(Unit unit) {
        units.remove(unit.getID());
        return this;
    }

    /**
     * 单元数量获取
     * @return
     */
    @Override
    public int getUnitNumber() { return units.size(); }

    /**
     * 单元质量获取
     * @return
     */
    @Override
    public int getQuality() {
        /* TODO 这里直接0 因为还没有用上*/
        return 0;
    }

    /**
     * 根据 UnitID 获取 Unit
     * @param id
     * @return
     */
    @Override
    public Unit getUnit(String id) {
        return units.get(id);
    }

    @Override
    public void assign(Map parts) {
        units.forEach((k, v)->{
            if(v.getClass().isAnnotationPresent(DeployUnit.class)){
                Field[] fields= v.getClass().getDeclaredFields();
                Arrays.stream(fields).forEach(f->{
                    StringBuffer tmpFld= new StringBuffer(f.getName());
                    StringBuffer tmpMed;
                    if(f.isAnnotationPresent(Input.class)){
                        Input input= f.getAnnotation(Input.class);
                        if(parts.containsKey(input.name())){
                            if(input.accessClazz().isAssignableFrom(parts.get(input.name()).getClass())){
                                tmpMed= new StringBuffer("set").append(tmpFld.substring(0, 1).toUpperCase()+tmpFld.substring(1));
                                try {
                                    Method m= v.getClass().getDeclaredMethod(tmpMed.toString(), input.accessClazz());
                                    m.setAccessible(true);
                                    m.invoke(v, parts.get(input.name()));
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                throw new BadCheckException("Wrong type:"+parts.get(input.name()).getClass());
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 查找内容
     * @param alias1
     * @param alias2
     * @return
     */
    @Override
    public Object find(String alias1, String alias2) {
        final Object[] result = {null};
        units.forEach((k,v)->{
            if(v.getClass().isAnnotationPresent(DeployUnit.class)){
                Form form= v.getClass().getAnnotation(Form.class);
                if(form.name().equals(alias1)){
                    Class clazz= v.getClass();
                    if(clazz.isAnnotationPresent(DeployUnit.class)){
                        Field[] fields = clazz.getDeclaredFields();
                        Arrays.stream(fields).forEach(f->{
                            StringBuffer tmpFld= new StringBuffer(f.getName());
                            StringBuffer tmpMed;
                            if(f.isAnnotationPresent(Input.class)) {
                                Input input = f.getAnnotation(Input.class);
                                if(input.name().equals(alias2)){
                                    tmpMed= new StringBuffer("get").append(tmpFld.substring(0, 1).toUpperCase()+tmpFld.substring(1));
                                    try {
                                        Method m= clazz.getMethod(tmpMed.toString());
                                        m.setAccessible(true);
                                        result[0] = m.invoke(v);
                                    } catch (NoSuchMethodException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
        return result[0];
    }

    /**
     * Carrier 这个模块的测试
     */
    /**public static void main(String[] args) {
        com.fr.plugin.performance.deploy.unit.form.BookUnit book= new com.fr.plugin.performance.deploy.unit.form.BookUnit();
        com.fr.plugin.performance.deploy.unit.form.UserUnit user= new com.fr.plugin.performance.deploy.unit.form.UserUnit();
        FormCarrier a= new FormCarrier();
        a.addUnit(book);
        a.addUnit(user);
        Map b= new HashMap();
        b.put("BookPath", "123");
        b.put("BookMainOption", "write");
        b.put("BookParasHolder", new HashMap());
        b.put("UserID", "yuwh");
        b.put("UserPassword", "yuwh123");
        a.assign(b);
        a.find("BookInformation", "BookPath");
    }*/
}
