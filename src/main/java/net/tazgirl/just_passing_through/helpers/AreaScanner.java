package net.tazgirl.just_passing_through.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class AreaScanner
{
    public static List<BlockPos> scanArea(Vec3 lowerBound, Vec3 upperBound, BiFunction<BlockState, BlockPos, Boolean> tester, ServerLevel level)
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
                    if (tester.apply(level.getBlockState(testPos), testPos))
                    {
                        returnList.add(testPos);
                    }
                    xPos++;
                }
                xPos = lowerBound.x;
                yPos++;
            }
            yPos = lowerBound.y;
            zPos++;
        }

        return returnList;
    }

    public static void scanAndExecuteArea(Vec3 lowerBound, Vec3 upperBound, Function<BlockState, Boolean> tester, ServerLevel level, Consumer<BlockPos> consumer)
    {
        BlockPos testPos;
        BlockState testState;

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
                    testState = level.getBlockState(testPos);

                    if (tester.apply(testState))
                    {
                        consumer.accept(testPos);
                    }

                    xPos++;
                }
                xPos = lowerBound.x;
                yPos++;
            }
            yPos = lowerBound.y;
            zPos++;
        }
    }
}
