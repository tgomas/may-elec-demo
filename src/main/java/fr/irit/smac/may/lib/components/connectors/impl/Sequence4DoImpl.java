package fr.irit.smac.may.lib.components.connectors.impl;

import fr.irit.smac.may.lib.components.connectors.Sequence4;
import fr.irit.smac.may.lib.interfaces.Do;

public class Sequence4DoImpl extends Sequence4<Do> {

	@Override
	protected Do make_input() {
		return new Do() {

			@Override
			public void doIt() {
				requires().first().doIt();
				requires().second().doIt();
				requires().third().doIt();
				requires().forth().doIt();
			}
		};
	}

}
