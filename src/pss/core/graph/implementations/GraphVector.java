package pss.core.graph.implementations;

import pss.core.graph.Graph;
import pss.core.graph.model.ModelGraph;
import pss.core.graph.model.ModelVector;


public abstract class GraphVector extends Graph {

	public GraphVector() {
	}
	
	public GraphVector(String title) {
		super(title);
	}

	public ModelGraph createModelGraph() {
		return new ModelVector();
	}
	
	
}
