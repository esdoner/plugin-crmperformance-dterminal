package com.fr.plugin.performance.setup.threshold;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuwh on 2018/12/4
 * Description: 不断产生的残羹>>一个盘子>>一个桶子>>若干躲在桶子里的挑食的野猫
 */
public class ThresholdPlate{
    private Map<String,String> plate = new HashMap<>();
    private Map<String,Boolean> notes = new HashMap<>();

    public ThresholdPlate(){ }

    public void putLeavings(String var1,String var2){
        plate.put(var1,var2);
    }

    public void putLeavings(Map var){
        plate.putAll(var);
    }

    public void putNotes(String var1, boolean var2){
        notes.put(var1,var2);
    }

    public void putNotes(Map var){
        notes.putAll(var);
    }

    public Map getPlate(){ return this.plate; }

    public Map getNotes(){ return this.notes; }

    public Boolean getNotes(String var){ return this.notes.get(var);}

    public void failFilter(List<String> var){
        Set<Map.Entry<String,Boolean>> noteSet = notes.entrySet();
        for(Map.Entry<String,Boolean> note : noteSet){
            if(! note.getValue() && note.getKey() != "gameover"){
                var.add(note.getKey());
            }
        }
    }
}
