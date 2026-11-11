package com.example.sheriffhat;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SheriffHatMod.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SheriffHatMod.MOD_ID);

    public static final RegistryObject<Item> SHERIFF_HAT = ITEMS.register("sheriff_hat",
            () -> new SheriffHatItem(false, new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> SHERIFF_HAT_STAR = ITEMS.register("sheriff_hat_star",
            () -> new SheriffHatItem(true, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.sheriffhat"))
                    .icon(() -> new ItemStack(SHERIFF_HAT_STAR.get()))
                    .displayItems((params, out) -> {
                        out.accept(SHERIFF_HAT.get());
                        out.accept(SHERIFF_HAT_STAR.get());
                    })
                    .build());
}
