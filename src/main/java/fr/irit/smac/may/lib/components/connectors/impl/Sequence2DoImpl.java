package fr.irit.smac.may.lib.components.connectors.impl;

import fr.irit.smac.may.lib.components.connectors.Sequence2;
import fr.irit.smac.may.lib.interfaces.Do;

public class Sequence2DoImpl extends Sequence2<Do> {

	@Override
	protected Do make_input() {
		return new Do() {

			@Override
			public void doIt() {
				requires().first().doIt();
				requires().second().doIt();
			}
		};
	}

}
