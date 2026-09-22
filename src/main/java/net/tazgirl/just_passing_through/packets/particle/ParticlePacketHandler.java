package net.tazgirl.just_passing_through.packets.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.tazgirl.just_passing_through.particles.Particles;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public class ParticlePacketHandler implements IPayloadHandler<ParticlePacketPayload>
{
    public static void handleParticlePacket(ParticlePacketPayload particlePacketPayload, IPayloadContext iPayloadContext)
    {
        SimpleParticleType particleType = null;
        Random random = new Random();

        switch(particlePacketPayload.particle())
        {
            case "GREEN_BURST" ->
                    {
                        particleType = ParticleEnum.GREEN.type;

                        for(int i = 0; i < 20; i++)
                        {
                            Minecraft.getInstance().level.addParticle(particleType, particlePacketPayload.position().x + random.nextDouble(-0.05, 0.05), particlePacketPayload.position().y + random.nextDouble(-0.05, 0.7), particlePacketPayload.position().z + random.nextDouble(-0.05, 0.05),
                                    particlePacketPayload.velocity().x + random.nextDouble(-0.05, 0.05), particlePacketPayload.velocity().y + 1 + random.nextDouble(-0.05, 100), particlePacketPayload.velocity().z+ random.nextDouble(-0.05, 0.05));
                        }
                    }
            default ->
            {
                return;
            }
        }


    }

    @Override
    public void handle(ParticlePacketPayload particlePacketPayload, IPayloadContext iPayloadContext)
    {
        handleParticlePacket(particlePacketPayload, iPayloadContext);
    }

    enum ParticleEnum
    {
        GREEN(Particles.GREEN_WIZARD_GROW.get());

        public final SimpleParticleType type;

        ParticleEnum(SimpleParticleType type)
        {
            this.type = type;
        }
    }
}
