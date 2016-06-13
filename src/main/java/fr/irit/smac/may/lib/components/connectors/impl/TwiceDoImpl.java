package fr.irit.smac.may.lib.components.connectors.impl;

import fr.irit.smac.may.lib.components.connectors.CTwice;
import fr.irit.smac.may.lib.interfaces.Do;

public class TwiceDoImpl extends CTwice<Do> {

	@Override
	protected Do make_input() {
		return new Do() {
			@Override
			public void doIt() {
				requires().output().doIt();
				requires().output().doIt();
			}
		};
	}
}
