package net.tazgirl.just_passing_through;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tazgirl.just_passing_through.entity.test_guy.TestGuy;

@EventBusSubscriber
public class Entities
{
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, JustPassingThrough.MODID);
    public static final DeferredHolder<EntityType<?>, EntityType<TestGuy>> TEST_GUY = register("test_guy",
            EntityType.Builder.<TestGuy>of(TestGuy::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

                    .sized(1f, 2f));


    // Start of user code block custom entities
    // End of user code block custom entities
    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryName, EntityType.Builder<T> entityTypeBuilder) {
        return REGISTRY.register(registryName, () -> (EntityType<T>) entityTypeBuilder.build(registryName));
    }

    @SubscribeEvent
    public static void init(RegisterSpawnPlacementsEvent event)
    {
        TestGuy.init(event);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event)
    {
        event.put(TEST_GUY.get(), TestGuy.createAttributes().build());
    }
}
