package com.mic.betterslimes.proxy;

import net.minecraft.util.text.translation.I18n;

public class CommonProxy implements IProxy {

	public String localize(String unlocalized, Object... args) {
		return I18n.translateToLocalFormatted(unlocalized, args);
	}
}
