package net.tazgirl.just_passing_through.helpers;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.tazgirl.just_passing_through.entity.green_wizard.GreenWizardFuncs;

@EventBusSubscriber
public class QuickCommandTest
{
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event)
    {
        event.getDispatcher().register(
                net.minecraft.commands.Commands.literal("greenWizardScan")
                        .executes(context ->
                        {
                            GreenWizardFuncs.growNearby(context.getSource().getPlayer());
                            return 1;
                        })
        );
    }
}
