package fr.irit.smac.may.lib.components.connectors.impl;

import fr.irit.smac.may.lib.components.connectors.Sequence3;
import fr.irit.smac.may.lib.interfaces.Do;

public class Sequence3DoImpl extends Sequence3<Do> {

	@Override
	protected Do make_input() {
		return new Do() {

			@Override
			public void doIt() {
				requires().first().doIt();
				requires().second().doIt();
				requires().third().doIt();
			}
		};
	}

}
