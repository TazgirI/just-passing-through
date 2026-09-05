package net.tazgirl.just_passing_through.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GreenWizardScan
{
    static int radius = 32;
    static int radiusDepth = 8;
    static Vec3 radiusVec = new Vec3(radius, radiusDepth, radius);

    public static List<BlockPos> getPoses(LivingEntity wizard)
    {
        Level level = wizard.level();

        if(level.isClientSide)
        {
            return new ArrayList<>();
        }

        Vec3 wizardPos = wizard.getPosition(0);

        Vec3 lowerBound = wizardPos.subtract(radiusVec);
        Vec3 upperBound = wizardPos.add(radiusVec);

        return AreaScanner.scanArea(lowerBound, upperBound, state -> state.getBlock() instanceof BonemealableBlock, (ServerLevel) level);
    }
}
