package net.ohgo.prisontweaks;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrisonTweaks implements ModInitializer {
	public static final String MOD_ID = "prisontweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int nextLevelXp = 0;

	public static int currentXp = 0;

	public static int level = 0;

	public static boolean levelSet = false;

	public static boolean xpSet = false;

	@Override
	public void onInitialize() {
		LOGGER.info("Prison Tweaks");
	}
}
