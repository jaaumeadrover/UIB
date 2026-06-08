/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package efficientsorting;

import com.sun.imageio.plugins.common.I18N;
import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author Jaume
 */
public class testing {
    public static void main(String[] args){
        TreeSet<Integer> tree=new TreeSet<Integer>();
        tree.add(10);
        tree.add(1);
        tree.add(3);
        tree.add(5);
        tree.add(15);
        tree.add(12);
        tree.add(16);
        Iterator it=tree.iterator();
        System.out.println(tree);
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
}
