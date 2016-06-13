package fr.irit.smac.may.examples.electrical.knowledges;

public class ResistorKnowledgeImpl extends DipoleKnowledgeImpl {

	public ResistorKnowledgeImpl(String id, String firstTerminal,
			String secondTerminal, Double resistor) {
		super(id, firstTerminal, secondTerminal);
		this.resistor = resistor;
	}

}
