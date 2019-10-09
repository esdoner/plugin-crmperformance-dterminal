package com.fr.plugin.performance.execute.action;

import com.fr.plugin.performance.deploy.carrier.FormCarrier;
import com.fr.plugin.performance.execute.action.ac.ReleaseActionCarrier;
import com.fr.plugin.performance.file.release.CptFileRelease;
import com.fr.plugin.performance.file.release.FileRelease;
import com.fr.plugin.performance.util.read.PropertiesCacheReader;

import java.util.HashMap;
import java.util.Map;

import static com.fr.plugin.performance.util.read.EscapeStrReader.unescape;

/**
 * @author yuwh
 * Created by yuwh on 2018/10/10
 * Description:none
 */
public class ExecuteRelease implements Executable{
    private String[] strings;
    private FormCarrier AC= new ReleaseActionCarrier();

    @Override
    public String fetchResult(Object[] objects){
        strings  = unescape(objects);
        /** 在配置文件中取出和更新相关的配置值*/
        Map var1  = PropertiesCacheReader.getInstance().readProperties(FileRelease.RELEASE_PROPERTIES);

        Map option = new HashMap();
        /** option*/
        option.put("mkdir",true);
        option.put("onlyIO",true);
        option.put("bak4bak", !Boolean.parseBoolean(objects[3].toString()));
        option.put("reason", strings[4]);
        /** 模板更新路径*/
        option.put("SpecifiedCPTReleasePath", var1.get("SpecifiedCPTReleasePath").toString());
        /** 模板备份路径*/
        option.put("SpecifiedCPTBackUpPath", var1.get("SpecifiedCPTBackUpPath").toString());
        /** origin*/
        option.put("GitRemoteOriginName", var1.get("GitRemoteOriginName").toString());
        /** branch*/
        option.put("GitRemoteBranchName", var1.get("GitRemoteBranchName").toString());
        /** 仓库根目录，包含.git文件夹*/
        option.put("GitRepositoryRoot", var1.get("GitRepositoryRoot").toString());
        /** 模板默认路径截断，用于非 reportlets 仓库，可为空*/
        option.put("DefaultPathPrefix", var1.get("DefaultPathPrefix").toString());

        Map paras= new HashMap();
        paras.put("BookPath", strings[0]);
        paras.put("UserID", strings[1]);
        paras.put("UserPassword", strings[2]);
        paras.put("OptionName", "ReleaseOptions");
        paras.put("OptionMap", option);
        AC.assign(paras);

        CptFileRelease release = new CptFileRelease( AC );
        release.stepShift(6);
        release.releaseStop();
        return release.ResultReport;
    }
}
