package net.tazgirl.just_passing_through.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class GreenWizardGrowParticle extends SuspendedTownParticle
{
    private static final int wiggleLoop = 40;
    private static final int wiggleHalfwayPoint = wiggleLoop / 2;
    private static final float wiggleSpeed = 0.01f;

    private final Vec3 wiggle;

    protected GreenWizardGrowParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        Random random = new Random();
        wiggle = new Vec3(random.nextFloat(-1, 2), 0, random.nextFloat(-1, 2));
    }

    @Override
    public void move(double x, double y, double z)
    {
        int progressTick = this.lifetime % wiggleLoop;

        Vec3 movement = new Vec3(x, 10 * y, z);

        if(progressTick < wiggleHalfwayPoint)
        {
            movement = movement.add(wiggle.multiply(wiggleSpeed, 0, wiggleSpeed));
        }
        else
        {
            movement = movement.add(wiggle.multiply(-wiggleSpeed, 0, -wiggleSpeed));

        }

        this.setBoundingBox(this.getBoundingBox().move(movement));
        this.setLocationFromBoundingbox();
    }

    public static class Provider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites)
        {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            GreenWizardGrowParticle particle = new GreenWizardGrowParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.setColor(1f, 1f, 1f);
            particle.setSize(particle.bbWidth / 2, particle.bbHeight / 2);
            particle.hasPhysics = true;
            particle.pickSprite(this.sprites);

            return particle;
        }
    }
}

