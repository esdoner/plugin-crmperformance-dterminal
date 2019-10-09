package com.fr.plugin.performance.analysis.mysql.core.ep;

import com.fr.decision.base.util.UUIDUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/30
 * Description:none
 */
public class Explain {
    private HashMap<Integer, AbstractSelect> selects= new HashMap<>();

    public Explain(List<HashMap> explain){
        explain.forEach(l->{
            AbstractSelect s= SelectFactory.buildSelect(l);
            selects.put(s.getTreeOrder(), s);
        });
    }

    public Tree getSelectTree(){
        return new Tree();
    }

    private class Tree implements Iterable<Branch>{
        private HashMap<String, Branch> branches= new HashMap<>();

        private Tree(){
            selects.forEach(s->{
                if(selects.get(s).getTreeOrder()== 1){
                    branchOut(new Node<>(s));
                } else {

                }
            });
        }

        private Branch branchOut(Node root){
            return branches.put(UUIDUtil.generate(), new Branch(root));
        }

        @NotNull
        @Override
        public Iterator<Branch> iterator() {
            return branches.values().iterator();
        }

        public Branch[] getTrack(){
            return null;
        }

        public void trackView(){}
    }

    private class Branch implements Iterable<Node>{
        private List<Node> nodes;

        private Branch(Node root){
            nodes= new ArrayList<>();
            add(root);
        }

        private Node getFirstNode(){ return nodes.get(0); }

        private void add(Node node){ nodes.add(node); }

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
                    Node node= nodes.get(i);
                    if(i>= nodes.size()-1){
                        hasNext= false;
                    } else {
                        i++;
                    }
                    return node;
                }

                @Override
                public void remove() { nodes.set(i, new Node()); }
            };
        }
    }

    private class Node<S extends AbstractSelect>{
        private S select;

        private Node(S var){ this.select= var;}
    }
}
