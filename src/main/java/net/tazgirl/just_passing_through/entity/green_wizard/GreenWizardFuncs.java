package net.tazgirl.just_passing_through.entity.green_wizard;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.tazgirl.just_passing_through.entity.green_wizard.data.GreenWizardChanceExclude;
import net.tazgirl.just_passing_through.entity.green_wizard.data.GreenWizardExclude;
import net.tazgirl.just_passing_through.helpers.AreaScanner;
import net.tazgirl.just_passing_through.packets.particle.ParticlePacketPayload;
import org.joml.Vector3f;

import java.util.List;
import java.util.Random;

@EventBusSubscriber
public class GreenWizardFuncs
{
    static final int radius = 24;
    static final int radiusDepth = 8;
    static final Vec3 radiusVec = new Vec3(radius, radiusDepth, radius);
    static final int tickDiv = 3;
    static int range = 64 * 64;

    static List<BlockPos> executionList;
    static ServerLevel executionLevel = null;

    public static boolean safeToRitual()
    {
        return executionLevel == null;
    }

    public static void growNearby(LivingEntity wizard)
    {
        if(wizard.level().isClientSide )
        {
            return;
        }

        executionLevel = (ServerLevel) wizard.level();

        Vec3 wizardPos = wizard.getPosition(0);

        Vec3 lowerBound = wizardPos.subtract(radiusVec);
        Vec3 upperBound = wizardPos.add(radiusVec);

        executionList = AreaScanner.scanArea(lowerBound, upperBound,
                (state, blockPos) ->
                {
                    boolean initial = state.getBlock() instanceof BonemealableBlock bonemealableBlock && bonemealableBlock.isValidBonemealTarget(executionLevel, blockPos, state) && !GreenWizardExclude.blocks.contains(state.getBlock());

                    if (initial)
                    {
                        return !GreenWizardChanceExclude.blocks.containsKey(state.getBlock()) || new Random().nextFloat(0, 1) <= GreenWizardChanceExclude.blocks.get(state.getBlock());
                    }

                    return false;
                } , executionLevel)
        ;
        // Start ritual mode in mob

    }

    @SubscribeEvent
    static void runExecutionList(ServerTickEvent.Post event)
    {
        if(executionLevel == null || executionList.isEmpty() || event.getServer().getTickCount() % tickDiv != 0)
        {
            return;
        }

        BlockPos blockPos = executionList.remove(new Random().nextInt(0, executionList.size()));

        if(executionLevel.getBlockState(blockPos).getBlock() instanceof BonemealableBlock bonemealableBlock)
        {
            bonemealableBlock.performBonemeal(executionLevel, executionLevel.random, blockPos, executionLevel.getBlockState(blockPos));
            bonemealParticleEffect(blockPos);
        }
        if(executionList.isEmpty())
        {
            // End ritual mode in mob
            executionLevel = null;
        }
    }

    static void bonemealParticleEffect(BlockPos blockPos)
    {
        Vec3 bottomCentre = blockPos.getBottomCenter();
        
        sendParticleWithinRange(bottomCentre, new ParticlePacketPayload("GREEN_BURST", new Vector3f((float) bottomCentre.x, (float) bottomCentre.y, (float) bottomCentre.z), new Vector3f(0, 0.01f, 0)))

        executionLevel.playSound(null, blockPos, SoundEvents.COMPOSTER_READY, SoundSource.NEUTRAL, 1, 1.1f + new Random().nextFloat(-0.05f,0.05f));
    }

    static void fireDirectionParticle(Vec3 mobPos, Vec3 targetPos)
    {
        Vec3 sourcePos = mobPos.add(0, 2, 0);

        Vec3 direction = targetPos.subtract(mobPos);

        sendParticleWithinRange(sourcePos, new ParticlePacketPayload("GREEN_BURST"), new Vector3f((float) sourcePos.x, (float) sourcePos.y, (float) sourcePos.z), new Vector3f((float) direction.x, (float) direction.y, (float) direction.z))
    
        executionLevel.playSound(null, BlockPos.comtaining(mobPos), SoundEvents.POTION_READY, SoundSource.NEUTRAL, 1, 0.8f + new Random().nextFloat(-0.1,0.1))
    }

    static void sendParticleWithinRange(Vec3 pos, ParticlePacketPayload payload)
    {
        for(ServerPlayer player : executionLevel.players())
        {
            if(player.distanceToSqr(pos) <= range)
            {
                player.connection.send();
            }
        }
    }
}
