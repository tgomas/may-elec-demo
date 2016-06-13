package fr.irit.smac.may.lib.components.connectors.impl;

import fr.irit.smac.may.lib.components.connectors.CToggle;
import fr.irit.smac.may.lib.interfaces.Do;

public class ToggleImpl<I> extends CToggle<I> {

	protected boolean returnFirst = true;

	@Override
	protected I make_output() {
		return returnFirst ? requires().first() : requires().second();
	}

	@Override
	protected Do make_toggle() {
		return new Do() {
			@Override
			public void doIt() {
				returnFirst = !returnFirst;
			}
		};
	}

}
