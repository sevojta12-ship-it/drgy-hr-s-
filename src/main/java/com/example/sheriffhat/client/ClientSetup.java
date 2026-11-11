package com.example.sheriffhat.client;

import com.example.sheriffhat.ModItems;
import com.example.sheriffhat.SheriffHatMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(modid = SheriffHatMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SheriffHatModel.LAYER_PLAIN, () -> SheriffHatModel.createLayer(false));
        event.registerLayerDefinition(SheriffHatModel.LAYER_STAR, () -> SheriffHatModel.createLayer(true));
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(ModItems.SHERIFF_HAT.get(), () -> new SheriffHatRenderer(false));
        CuriosRendererRegistry.register(ModItems.SHERIFF_HAT_STAR.get(), () -> new SheriffHatRenderer(true));
    }
}
