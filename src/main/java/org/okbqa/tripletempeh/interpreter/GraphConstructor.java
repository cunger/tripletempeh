package org.okbqa.tripletempeh.interpreter;

import org.okbqa.tripletempeh.graph.Color;
import org.okbqa.tripletempeh.graph.Edge;
import org.okbqa.tripletempeh.graph.Graph;
import org.okbqa.tripletempeh.graph.Format;
import org.okbqa.tripletempeh.graph.Node;
import org.okbqa.tripletempeh.interpreter.grammar.DependenciesBaseListener;
import org.okbqa.tripletempeh.interpreter.grammar.DependenciesParser;

/**
 *
 * @author cunger
 */
public class GraphConstructor extends DependenciesBaseListener {
    
    Graph graph = new Graph();
            
    public GraphConstructor() {
    }
    
    @Override
    public void enterConll(DependenciesParser.ConllContext ctx) {
        graph.setFormat(Format.CONLL);
    }

    @Override
    public void enterStanford(DependenciesParser.StanfordContext ctx) {
        graph.setFormat(Format.STANFORD);
    }
    
    @Override
    public void exitConll(DependenciesParser.ConllContext ctx) {
        // conll : STRING STRING STRING STRING features STRING STRING (sheads) ;

        int i1 = Integer.parseInt(ctx.STRING(0).getText());
        int i2 = Integer.parseInt(ctx.STRING(4).getText());
        
        String form  = ctx.STRING(1).getText();
        String lemma = ctx.STRING(2).getText();
        String pos   = ctx.STRING(3).getText();
        String rel   = ctx.STRING(5).getText();
       
        graph.addNode(new Node(i1,form,lemma,pos));
        graph.addEdge(new Edge(Color.DEPENDENCY,i2,rel,i1));
    }
    
    @Override
    public void exitStanford(DependenciesParser.StanfordContext ctx) {
        // stanford : STRING '(' STRING '-' STRING ',' STRING '-' STRING ')' ;
        
        int i1 = Integer.parseInt(ctx.STRING(2).getText());
        int i2 = Integer.parseInt(ctx.STRING(4).getText());
        
        String form1 = ctx.STRING(1).getText();
        String form2 = ctx.STRING(3).getText();
        String rel   = ctx.STRING(0).getText();
       
        graph.addNode(new Node(i1,form1));
        graph.addNode(new Node(i2,form2));
        graph.addEdge(new Edge(Color.DEPENDENCY,i1,rel,i2));
    }
    
}
