package com.fr.plugin.performance.execute;

import com.fr.plugin.performance.exception.BadCheckException;
import com.fr.plugin.performance.exception.ReadableException;
import com.fr.plugin.performance.exception.ValidityException;
import com.fr.plugin.transform.ExecuteFunctionRecord;
import com.fr.plugin.transform.FunctionRecorder;
import com.fr.script.AbstractFunction;

import java.lang.reflect.Method;

import static com.fr.plugin.performance.util.read.ClassInfoReader.*;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2018/12/27
 * Description:所有Executable Action 的入口
 */
@FunctionRecorder(localeKey="FS_PLUGIN_PERFORMANCE-DTERMINAL")
public final class CommonExecutor extends AbstractFunction {
    private final static String FIND_IN= getPackName(CommonExecutor.class, false)+ ".action";
    private final static String FIND_METHOD_NAME= "fetchResult";
    private String className= null;
    private Object[] objects;

    public CommonExecutor(){ }

    @Override
    @ExecuteFunctionRecord
    public String run(Object[] objects) {
        String result= "";
        try {
            validate(objects);
            this.objects = new Object[objects.length-1];
            System.arraycopy(objects, 1, this.objects, 0, objects.length-1);
            if(className!= null) {
                Class clazz= Class.forName(className);
                Method method= clazz.getDeclaredMethod(FIND_METHOD_NAME, Object[].class);
                method.setAccessible(true);
                Object executableAction= clazz.newInstance();
                result = String.valueOf(method.invoke(executableAction, (Object) this.objects));
            } else {
                throw new ReadableException("Unexpected error:the action name and class was missing");
            }
        } catch (ValidityException e) {
            result= e.getReadableMessage();
            e.printStackTrace();
        } catch (BadCheckException e) {
            result= e.getReadableMessage();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 确认下action
     * @param var
     * @throws ValidityException
     * @throws BadCheckException
     */
    private void validate(Object[] var) throws ValidityException, BadCheckException {
        if(var== null || var[0]== null){
            throw new ValidityException("An action name is required");
        } else {
            className= FIND_IN+ "."+ var[0];
            try{
                Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new BadCheckException("The given action name is not found");
            }
        }
    }
}
