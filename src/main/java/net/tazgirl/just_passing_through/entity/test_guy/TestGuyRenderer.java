package net.tazgirl.just_passing_through.entity.test_guy;

import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import org.jetbrains.annotations.NotNull;

public class TestGuyRenderer extends MobRenderer<TestGuy, VillagerModel<TestGuy>>
{
    public TestGuyRenderer(EntityRendererProvider.Context context)
    {
        super(context, new VillagerModel<>(context.bakeLayer(ModelLayers.WANDERING_TRADER)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull TestGuy testGuy)
    {
        return ResourceLocation.parse("minecraft:textures/entity/villager/profession/nitwit.png");
    }
}
