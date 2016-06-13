package fr.irit.smac.may.lib.components.connectors.impl;

import fr.irit.smac.may.lib.components.connectors.CAlternate;
import fr.irit.smac.may.lib.interfaces.Do;

public class AlternateDoImpl extends CAlternate<Do> {

	protected boolean doFirst = true;

	@Override
	protected Do make_input() {
		return new Do() {

			@Override
			public void doIt() {
				if (doFirst) {
					doFirst = false;
					requires().first().doIt();
				} else {
					doFirst = true;
					requires().second().doIt();
				}
			}
		};
	}
}
