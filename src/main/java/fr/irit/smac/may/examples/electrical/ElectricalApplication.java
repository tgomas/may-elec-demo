package fr.irit.smac.may.examples.electrical;

import fr.irit.smac.may.examples.electrical.behaviors.NodeBehaviorImpl;
import fr.irit.smac.may.examples.electrical.behaviors.ResistorBehaviorImpl;
import fr.irit.smac.may.examples.electrical.behaviors.UGeneratorBehaviorImpl;
import fr.irit.smac.may.examples.electrical.knowledges.NodeKnowledgeImpl;
import fr.irit.smac.may.examples.electrical.knowledges.ResistorKnowledgeImpl;
import fr.irit.smac.may.examples.electrical.knowledges.UGeneratorKnowledgeImpl;
import fr.irit.smac.may.examples.mas.Mas.Component;
import fr.irit.smac.may.examples.mas.MasImpl;
import fr.irit.smac.may.examples.mas.messaging.interfaces.Message;

/**
 * Hello world!
 *
 */
public class ElectricalApplication {
	public static void main(String[] args) {

		Component<Message> mas = new MasImpl<Message>().newComponent();

		mas.monitoring().filterAgents(s -> "R 5_5|5_6".equals(s) || "5_5".equals(s) || "1_1".equals(s)
				|| "10_10".equals(s) || "gen 20V".equals(s) || "gen 2".equals(s));

		// mas.factory().createAgent(
		// "gen 20V",
		// new UGeneratorBehavior(new UGeneratorKnowledge("gen 20V", "A",
		// "C", 20d)));
		// mas.factory()
		// .createAgent("A", new NodeBehavior(new NodeKnowledge("A")));
		// mas.factory()
		// .createAgent("B", new NodeBehavior(new NodeKnowledge("B")));
		// mas.factory()
		// .createAgent("C", new NodeBehavior(new NodeKnowledge("C")));
		// mas.factory()
		// .createAgent(
		// "R1",
		// new ResistorBehavior(new ResistorKnowledge("R1", "A",
		// "B", 5d)));
		// mas.factory()
		// .createAgent(
		// "R2",
		// new ResistorBehavior(new ResistorKnowledge("R2", "A",
		// "B", 5d)));
		// mas.factory()
		// .createAgent(
		// "R3",
		// new ResistorBehavior(new ResistorKnowledge("R3", "A",
		// "C", 5d)));

		int rows = 10;
		int cols = 10;
		mas.factory().createAgent("gen 20V",
				new UGeneratorBehaviorImpl(new UGeneratorKnowledgeImpl("gen 20V", "1_1", rows + "_" + cols, 20d)));
		mas.factory().createAgent("gen 2",
				new UGeneratorBehaviorImpl(new UGeneratorKnowledgeImpl("gen 2", "1_1", "5_5", 20d)));
		for (int i = 1; i <= rows; i++) {
			for (int j = 1; j <= cols; j++) {
				String nodeId = i + "_" + j;
				mas.factory().createAgent(nodeId, new NodeBehaviorImpl(new NodeKnowledgeImpl(nodeId)));
				if (i != rows) {
					final String firstTerminal = i + "_" + j;
					final String secondTerminal = (i + 1) + "_" + j;
					String resistorId = "R " + firstTerminal + "|" + secondTerminal;
					mas.factory().createAgent(resistorId, new ResistorBehaviorImpl(
							new ResistorKnowledgeImpl(resistorId, firstTerminal, secondTerminal, new Double(i + j))));
				}
				if (j != cols) {
					final String firstTerminal = i + "_" + j;
					final String secondTerminal = i + "_" + (j + 1);
					String resistorId = "R " + firstTerminal + "|" + secondTerminal;
					mas.factory().createAgent(resistorId, new ResistorBehaviorImpl(
							new ResistorKnowledgeImpl(resistorId, firstTerminal, secondTerminal, new Double(i + j))));
				}
			}
		}
	}
}
