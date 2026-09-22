package net.tazgirl.just_passing_through.entity.green_wizard.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.tazgirl.just_passing_through.JustPassingThrough;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber
public class GreenWizardChanceExclude
{
    static final ResourceLocation address = ResourceLocation.fromNamespaceAndPath("just_passing_through","green_wizard_chance_exclude.json");
    static final String blockAddress = "block";
    static final String chanceAddress = "chance";

    public static Map<Block, Float> blocks;


    @SubscribeEvent
    public static void onServerStart(ServerStartingEvent event)
    {
        blocks = getBlocks(event);
    }

    static Map<Block, Float> getBlocks(ServerStartingEvent event)
    {
        Map<Block, Float> returnMap = new HashMap<>();

        for(Resource resource : event.getServer().getResourceManager().getResourceStack(address))
        {
            try(InputStream inputStream = resource.open())
            {
                JsonObject jsonObject = new Gson().fromJson(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8), JsonObject.class);

                for(JsonElement element : jsonObject.asMap().values())
                {
                    if(element instanceof JsonObject container)
                    {
                        JsonElement blockElement = container.get(blockAddress);
                        JsonElement chanceElement = container.get(chanceAddress);

                        if(blockElement instanceof JsonPrimitive && chanceElement instanceof JsonPrimitive)
                        {
                            Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(blockElement.getAsString()));
                            Float chance = chanceElement.getAsFloat();

                            returnMap.put(block, chance);
                        }
                    }
                }

            }
            catch (IOException e)
            {
                JustPassingThrough.LOGGER.warn("Failed to read \"" + resource.toString() + "\" while collecting GreenWizardChanceExclusions");
            }
        }

        return returnMap;
    }
}
