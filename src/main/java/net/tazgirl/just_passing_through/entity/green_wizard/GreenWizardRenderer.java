package net.tazgirl.just_passing_through.entity.green_wizard;

import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GreenWizardRenderer extends MobRenderer<GreenWizard, VillagerModel<GreenWizard>>
{
    public GreenWizardRenderer(EntityRendererProvider.Context context)
    {
        super(context, new VillagerModel<>(context.bakeLayer(ModelLayers.WANDERING_TRADER)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GreenWizard greenWizard)
    {
        return ResourceLocation.parse("minecraft:textures/entity/villager/profession/nitwit.png");
    }
}
