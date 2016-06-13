package fr.irit.smac.may.examples.electrical.knowledges;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import fr.irit.smac.may.examples.mas.knowledge.BasicKnowledgeImpl;
import fr.irit.smac.util.avt.AVT;
import fr.irit.smac.util.avt.AVTBuilder;
import fr.irit.smac.util.avt.Feedback;

public class NodeKnowledgeImpl extends BasicKnowledgeImpl {

	public Double worstPotentialCriticality = 0d;
	public Feedback potentialDirection;
	public AVT potential;
	public Collection<String> neighbors = new HashSet<String>();
	public Map<String, Double> intensities = new HashMap<String, Double>();

	public NodeKnowledgeImpl(String id) {
		super(id);
		this.potential = new AVTBuilder().deltaMin(.001).deltaMax(100).build();
	}

}
