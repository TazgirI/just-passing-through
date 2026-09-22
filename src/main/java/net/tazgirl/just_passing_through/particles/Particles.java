package net.tazgirl.just_passing_through.particles;

import net.minecraft.client.particle.SuspendedTownParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tazgirl.just_passing_through.JustPassingThrough;

@EventBusSubscriber
public class Particles
{
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(Registries.PARTICLE_TYPE, JustPassingThrough.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GREEN_WIZARD_GROW = REGISTRY.register("green_wizard_grow", () -> new SimpleParticleType(false));

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event)
    {
        event.registerSpriteSet(GREEN_WIZARD_GROW.get(), GreenWizardGrowParticle.Provider::new);
    }
}
