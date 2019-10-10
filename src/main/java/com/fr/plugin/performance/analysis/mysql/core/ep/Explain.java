package com.fr.plugin.performance.analysis.mysql.core.ep;

import java.util.*;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/30
 * Description:none
 */
public class Explain {
    private HashMap<Integer, List<Node<AbstractSelect>>> selects= new HashMap<>();
    private Tree selectTree;

    public Explain(List<HashMap> explain){
        final int[] i = {0};
        explain.forEach(l->{
            AbstractSelect s= SelectFactory.buildSelect(l);
            int treeOrder= s.getTreeOrder();
            if(selects.containsKey(treeOrder)){
                List<Node<AbstractSelect>> selectNodeList= selects.get(treeOrder);
                selectNodeList.add(new Node(s, i[0]));
            } else {
                List<Node<AbstractSelect>> selectNodeList= new ArrayList<>();
                selectNodeList.add(new Node(s, i[0]));
                selects.put(treeOrder, selectNodeList);
            }
            i[0]++;
        });
    }

    public Tree getSelectTree(){ return selectTree== null? selectTree=new Tree(): selectTree; }

    public String getTrackTree(){ return null;}

    private class Tree{
        private Tree(){
            selects.forEach((i, l)->{
                l.forEach(n->{
                    if(i== 1){
                        n.setParentId(-1);
                    }
                    if(!n.isEOFBranch()){
                        selects.get(Integer.valueOf(n.getDeriveGroup())).forEach(c->{
                            c.setParentId(n.getCurrentId());
                        });
                    }
                });
            });
        }
    }

    private class Node<S extends AbstractSelect>{
        private S select= null;
        private int currentId;
        private int parentId;

        private Node() {}

        private Node(S var, int var1) {
            this.select= var;
            this.setCurrentId(var1);
        }

        public int getCurrentId() {
            return currentId;
        }

        public void setCurrentId(int currentId) {
            this.currentId = currentId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        private boolean isEOFBranch() { return !select.getTable().getName().matches("<derived\\d+>"); }

        private String getDeriveGroup() {
            return select.getTable().getName().replace("<derived","").replace(">","");
        }
    }
}
