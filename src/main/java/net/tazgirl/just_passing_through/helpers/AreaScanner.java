package net.tazgirl.just_passing_through.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AreaScanner
{
    public static List<BlockPos> scanArea(Vec3 lowerBound, Vec3 upperBound, Function<BlockState, Boolean> tester, ServerLevel level)
    {
        List<BlockPos> returnList = new ArrayList<>();

        BlockPos testPos;

        double xPos = lowerBound.x;
        double yPos = lowerBound.y;
        double zPos = lowerBound.z;

        while (zPos <= upperBound.z)
        {
            while   (yPos <= upperBound.y)
            {
                while (xPos <= upperBound.x)
                {
                    testPos = BlockPos.containing(xPos, yPos, zPos);
                    if (tester.apply(level.getBlockState(testPos)))
                    {
                        returnList.add(testPos);
                    }
                    xPos++;
                }
                yPos++;
            }
            zPos++;
        }

        return returnList;
    }
}
