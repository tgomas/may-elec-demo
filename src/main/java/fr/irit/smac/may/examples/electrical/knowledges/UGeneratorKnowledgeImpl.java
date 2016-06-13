package fr.irit.smac.may.examples.electrical.knowledges;

public class UGeneratorKnowledgeImpl extends DipoleKnowledgeImpl {

	public UGeneratorKnowledgeImpl(String id, String first, String second,
			Double tension) {
		super(id, first, second);
		this.tension = tension;
	}

	@Override
	public void computeUfromV() {
		// do nothing, it's a fixed U generator
	}
}
