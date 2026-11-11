package com.example.sheriffhat;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SheriffHatMod.MOD_ID)
public class SheriffHatMod {
    public static final String MOD_ID = "sheriffhat";

    public SheriffHatMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(bus);
        ModItems.TABS.register(bus);
    }
}
