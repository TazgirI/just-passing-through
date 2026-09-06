package net.tazgirl.just_passing_through;

import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.tazgirl.just_passing_through.entity.test_guy.TestGuyRenderer;

@EventBusSubscriber
public class EntityRenderers
{
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(Entities.TEST_GUY.get(), TestGuyRenderer::new);
    }
}
