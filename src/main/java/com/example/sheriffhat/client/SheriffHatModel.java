package com.example.sheriffhat.client;

import com.example.sheriffhat.SheriffHatMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

/**
 * Worn model. Cuboids here MUST match tools/generate_assets.py (which builds the
 * inventory item JSON models and the texture from the same numbers).
 * Origin = head pivot, head box spans y -8..0, front is -Z.
 */
public class SheriffHatModel extends Model {
    public static final ModelLayerLocation LAYER_PLAIN =
            new ModelLayerLocation(new ResourceLocation(SheriffHatMod.MOD_ID, "sheriff_hat"), "main");
    public static final ModelLayerLocation LAYER_STAR =
            new ModelLayerLocation(new ResourceLocation(SheriffHatMod.MOD_ID, "sheriff_hat_star"), "main");

    public final ModelPart hat;

    public SheriffHatModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.hat = root.getChild("hat");
    }

    public static LayerDefinition createLayer(boolean star) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        CubeListBuilder b = CubeListBuilder.create()
                // wide brim (18x18)
                .texOffs(0, 0).addBox(-9f, -7f, -9f, 18f, 1f, 18f)
                // brim curl: down at front/back, up at the sides
                .texOffs(0, 20).addBox(-7f, -6f, -9f, 14f, 1f, 1f)
                .texOffs(0, 20).addBox(-7f, -6f, 8f, 14f, 1f, 1f)
                .texOffs(0, 23).addBox(-9f, -8f, -7f, 1f, 1f, 14f)
                .texOffs(0, 23).addBox(8f, -8f, -7f, 1f, 1f, 14f)
                // lower crown (grown 0.6 so it clears the skin's hair layer)
                .texOffs(0, 40).addBox(-4f, -11f, -4f, 8f, 4f, 8f, new CubeDeformation(0.6f))
                // cattleman crease: two raised halves with a 1px groove between them
                .texOffs(0, 53).addBox(-3.5f, -12.5f, -3.5f, 3f, 2f, 7f)
                .texOffs(0, 53).addBox(0.5f, -12.5f, -3.5f, 3f, 2f, 7f)
                // gold campaign cord around the crown
                .texOffs(0, 63).addBox(-4f, -8.5f, -4f, 8f, 1f, 8f, new CubeDeformation(0.78f, 0.1f, 0.78f))
                // cord knot centred at the front + two V-shaped cord tails on the brim
                .texOffs(0, 73).addBox(-1.5f, -9f, -5.78f, 3f, 2f, 1f)
                .texOffs(8, 73).addBox(-1.5f, -8f, -7.8f, 1f, 1f, 2f)
                .texOffs(8, 73).addBox(-2.5f, -8f, -7.8f, 1f, 1f, 1f)
                .texOffs(8, 73).addBox(-2.5f, -8f, -8.8f, 1f, 1f, 1f)
                .texOffs(8, 73).addBox(0.5f, -8f, -7.8f, 1f, 1f, 2f)
                .texOffs(8, 73).addBox(1.5f, -8f, -7.8f, 1f, 1f, 1f)
                .texOffs(8, 73).addBox(1.5f, -8f, -8.8f, 1f, 1f, 1f);

        if (star) {
            // gold metal sheriff badge, centred above the cord knot
            b.texOffs(0, 78).addBox(-1.5f, -12f, -5.6f, 3f, 3f, 1f);
        }

        root.addOrReplaceChild("hat", b, PartPose.ZERO);
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack ps, VertexConsumer vc, int light, int overlay,
                               float r, float g, float b, float a) {
        hat.render(ps, vc, light, overlay, r, g, b, a);
    }
}
