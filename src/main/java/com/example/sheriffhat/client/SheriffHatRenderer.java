package com.example.sheriffhat.client;

import com.example.sheriffhat.SheriffHatMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class SheriffHatRenderer implements ICurioRenderer {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SheriffHatMod.MOD_ID, "textures/entity/sheriff_hat.png");

    private final SheriffHatModel model;

    public SheriffHatRenderer(boolean star) {
        this.model = new SheriffHatModel(Minecraft.getInstance().getEntityModels()
                .bakeLayer(star ? SheriffHatModel.LAYER_STAR : SheriffHatModel.LAYER_PLAIN));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack, SlotContext slotContext, PoseStack poseStack,
            RenderLayerParent<T, M> parent, MultiBufferSource buffer, int light,
            float limbSwing, float limbSwingAmount, float partialTicks,
            float ageInTicks, float netHeadYaw, float headPitch) {

        // copy the wearer's head pose (rotation + sneak offset) onto the hat
        ICurioRenderer.followHeadRotations(slotContext.entity(), model.hat);

        VertexConsumer vc = ItemRenderer.getArmorFoilBuffer(buffer,
                RenderType.armorCutoutNoCull(TEXTURE), false, stack.hasFoil());
        model.renderToBuffer(poseStack, vc, light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
    }
}
