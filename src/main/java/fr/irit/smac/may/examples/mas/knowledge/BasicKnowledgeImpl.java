package fr.irit.smac.may.examples.mas.knowledge;

/**
 * a basic knowledge to be extended by the agents knowledges
 *
 */
public class BasicKnowledgeImpl implements Knowledge {

	/**
	 * The agent name
	 */
	protected String id;

	public BasicKnowledgeImpl(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}
}
