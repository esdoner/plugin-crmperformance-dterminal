package com.fr.plugin.performance.analysis.mysql.core.ep;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/30
 * Description:none
 */
public class Explain {
    private List<AbstractSelect> selects;

    public Explain(List<HashMap> explain){
        explain.forEach(s->{
            selects.add(new AbstractSelect() {});
        });
    }

    public Tree getSelectTrack(){
        return new Tree();
    }

    private class Tree implements Iterable<Branch>{
        private HashMap<Integer, Branch> branches= new HashMap<>();

        @NotNull
        @Override
        public Iterator<Branch> iterator() {
            return branches.values().iterator();
        }
    }

    private abstract class Branch implements Iterable<Node>{
        private Node[] nodes;

        private Node getFirstNode(){ return nodes[0]; }

        @NotNull
        @Override
        public Iterator<Node> iterator() {
            return new Iterator<Node>(){
                int i= 0;
                boolean hasNext= true;

                @Override
                public boolean hasNext() { return hasNext; }

                @Override
                public Node next() {
                    Node node= nodes[i];
                    if(i>= nodes.length-1){
                        hasNext= false;
                    } else {
                        i++;
                    }
                    return node;
                }

                @Override
                public void remove() { nodes[i]= new Node(); }
            };
        }
    }

    private class Node<S extends AbstractSelect>{
        private S object;
    }
}
