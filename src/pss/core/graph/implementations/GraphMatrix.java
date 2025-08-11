package pss.core.graph.implementations;

import pss.core.graph.Graph;
import pss.core.graph.model.ModelGraph;
import pss.core.graph.model.ModelMatrix;


public abstract class GraphMatrix extends Graph {
	public GraphMatrix() {
	}

	public GraphMatrix(String title) {
		super(title);
	}

	public ModelGraph createModelGraph() {
		return new ModelMatrix();
	}
	

}
