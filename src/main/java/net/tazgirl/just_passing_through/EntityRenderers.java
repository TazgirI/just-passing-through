package net.tazgirl.just_passing_through;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.tazgirl.just_passing_through.entity.green_wizard.GreenWizardRenderer;

@EventBusSubscriber
public class EntityRenderers
{
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(Entities.GREEN_WIZARD.get(), GreenWizardRenderer::new);
    }
}
