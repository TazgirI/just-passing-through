package net.tazgirl.just_passing_through.packets.particle;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public record ParticlePacketPayload(String particle, Vector3f position, Vector3f velocity) implements CustomPacketPayload
{

    public static final CustomPacketPayload.Type<ParticlePacketPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.parse("just_passing_through:particle_payload"));

    public static final StreamCodec<ByteBuf, ParticlePacketPayload> PARTICLE_CODEC = StreamCodec.composite
            (
                    ByteBufCodecs.STRING_UTF8,
                    ParticlePacketPayload::particle,
                    ByteBufCodecs.VECTOR3F,
                    ParticlePacketPayload::position,
                    ByteBufCodecs.VECTOR3F,
                    ParticlePacketPayload::velocity,
                    ParticlePacketPayload::new
            );

    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }


}
