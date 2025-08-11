package pss.core.graph.implementations;

import pss.core.graph.Graph;
import pss.core.graph.model.ModelGraph;
import pss.core.graph.model.ModelMatrixAndLine;


public abstract class GraphMatrixAndLine extends Graph {
	
	public GraphMatrixAndLine() {
	}
	
	public GraphMatrixAndLine(String title) {
		super(title);
	}
	
	public ModelGraph createModelGraph() {
		return new ModelMatrixAndLine();
	}


	
}
