package net.tazgirl.just_passing_through.packets;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.tazgirl.just_passing_through.packets.particle.ParticlePacketHandler;
import net.tazgirl.just_passing_through.packets.particle.ParticlePacketPayload;

@EventBusSubscriber
public class RegisterPackets
{
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event)
    {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                ParticlePacketPayload.TYPE,
                ParticlePacketPayload.PARTICLE_CODEC,
                ParticlePacketHandler::handleParticlePacket
        );
    }
}
