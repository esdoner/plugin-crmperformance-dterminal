package com.fr.plugin.performance.setup.table;

import com.fr.plugin.performance.execute.table.TableViewTypeA;
import com.fr.plugin.performance.util.read.PropertiesCacheReader;
import com.fr.plugin.performance.setup.threshold.Threshold;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuwh on 2018/12/26
 * Description: 配置数据集
 */
public final class SetupView extends TableViewTypeA {
    private Map dataSource = new HashMap();

    public SetupView(){
        //TODO 可能是用了单例的问题？模板上的数据集拉不到数，做模板的时候只能手动编辑下单元格，加个id列好拖出个模子,后面插件里debug下
        dataSource.putAll(PropertiesCacheReader.getInstance().readProperties(Threshold.PATH));
        dataSource.put("id","1");

        String[] columnNames = new String[dataSource.size()];
        Object[][] rows = {new String[dataSource.size()]};

        int index = 0;
        for(Map.Entry<String,String> var2 : (Set<Map.Entry>)dataSource.entrySet()){
            columnNames[index] = var2.getKey();
            rows[0][index] = var2.getValue();
            index++;
        }

        this.colNames = columnNames;
        this.rowData = rows;
    }
}
