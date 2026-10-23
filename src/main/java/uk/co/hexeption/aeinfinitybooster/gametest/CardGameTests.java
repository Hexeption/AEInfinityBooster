//? if >=1.17 {
package uk.co.hexeption.aeinfinitybooster.gametest;

import appeng.blockentity.networking.WirelessAccessPointBlockEntity;
import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import appeng.menu.implementations.WirelessAccessPointMenu;
import appeng.menu.slot.RestrictedInputSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import uk.co.hexeption.aeinfinitybooster.AEInfinityBooster;
import uk.co.hexeption.aeinfinitybooster.config.AEInfinityBoosterConfig;
import uk.co.hexeption.aeinfinitybooster.setup.ModItems;

//? if >=1.20.5
import net.minecraft.world.level.GameType;
//? if <1.20
/*import net.minecraft.gametest.framework.GameTestAssertException;*/

//? if >=1.21.5 {
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;
//?} else {
/*import net.minecraft.gametest.framework.GameTest;
*///?}
//? if neoforge && <1.21.5 {
/*import net.neoforged.neoforge.gametest.GameTestHolder;
*///?}
//? if forge {
/*import net.minecraftforge.gametest.GameTestHolder;
*///?}

/**
 * Runs the mixins against a real AE2 Wireless Access Point.
 * From 1.21.5 each test also needs a matching data/aeinfinitybooster/test_instance/*.json.
 */
//? if >=1.21.5 {
@EventBusSubscriber(modid = AEInfinityBooster.MODID)
//?} else if forgeLike {
/*@GameTestHolder(AEInfinityBooster.MODID)
*///?}
public class CardGameTests {

    private static final BlockPos ACCESS_POINT = new BlockPos(1, 1, 1);

    //? if >=1.21.5 {
    @SubscribeEvent
    public static void registerTestFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> {
            registry.register(Identifier.fromNamespaceAndPath(AEInfinityBooster.MODID, "access_point_accepts_cards"), CardGameTests::accessPointAcceptsCards);
            registry.register(Identifier.fromNamespaceAndPath(AEInfinityBooster.MODID, "booster_slot_accepts_cards"), CardGameTests::boosterSlotAcceptsCards);
            registry.register(Identifier.fromNamespaceAndPath(AEInfinityBooster.MODID, "infinity_card_sets_unlimited_range"), CardGameTests::infinityCardSetsUnlimitedRange);
            registry.register(Identifier.fromNamespaceAndPath(AEInfinityBooster.MODID, "dimension_card_sets_unlimited_range"), CardGameTests::dimensionCardSetsUnlimitedRange);
            registry.register(Identifier.fromNamespaceAndPath(AEInfinityBooster.MODID, "card_leaves_other_forced_chunks_alone"), CardGameTests::cardLeavesOtherForcedChunksAlone);
        });
    }
    //?} else {
    /*// Forge and NeoForge expand "empty" to aeinfinitybooster:cardgametests.empty
    //? if fabric {
    /^private static final String TEMPLATE = "fabric-gametest-api-v1:empty";
    ^///?} else {
    /^private static final String TEMPLATE = "empty";
    ^///?}

    @GameTest(template = TEMPLATE)
    public void accessPointAcceptsCardsTest(GameTestHelper helper) {
        accessPointAcceptsCards(helper);
    }

    @GameTest(template = TEMPLATE)
    public void boosterSlotAcceptsCardsTest(GameTestHelper helper) {
        boosterSlotAcceptsCards(helper);
    }

    @GameTest(template = TEMPLATE)
    public void infinityCardSetsUnlimitedRangeTest(GameTestHelper helper) {
        infinityCardSetsUnlimitedRange(helper);
    }

    @GameTest(template = TEMPLATE)
    public void dimensionCardSetsUnlimitedRangeTest(GameTestHelper helper) {
        dimensionCardSetsUnlimitedRange(helper);
    }

    @GameTest(template = TEMPLATE)
    public void cardLeavesOtherForcedChunksAloneTest(GameTestHelper helper) {
        cardLeavesOtherForcedChunksAlone(helper);
    }
    *///?}

    // MixinWirelessBlockEntity: the access point's own inventory lets both cards in
    public static void accessPointAcceptsCards(GameTestHelper helper) {
        WirelessAccessPointBlockEntity accessPoint = placeAccessPoint(helper);

        assertTrue(helper, accessPoint.getInternalInventory().isItemValid(0, new ItemStack(ModItems.INFINITY_CARD.get())), "Access point rejected the Infinity Card");
        assertTrue(helper, accessPoint.getInternalInventory().isItemValid(0, new ItemStack(ModItems.DIMENSION_CARD.get())), "Access point rejected the Dimension Card");
        assertTrue(helper, accessPoint.getInternalInventory().isItemValid(0, new ItemStack(AEItems.WIRELESS_BOOSTER.asItem())), "Access point rejected AE2's Wireless Booster");
        assertFalse(helper, accessPoint.getInternalInventory().isItemValid(0, new ItemStack(Items.DIRT)), "Access point accepted dirt");
        helper.succeed();
    }

    // MixinRestrictedInputSlot: the booster slot in the access point's menu lets both cards in
    public static void boosterSlotAcceptsCards(GameTestHelper helper) {
        Slot boosterSlot = boosterSlot(openMenu(helper, placeAccessPoint(helper)));

        assertTrue(helper, boosterSlot.mayPlace(new ItemStack(ModItems.INFINITY_CARD.get())), "Booster slot rejected the Infinity Card");
        assertTrue(helper, boosterSlot.mayPlace(new ItemStack(ModItems.DIMENSION_CARD.get())), "Booster slot rejected the Dimension Card");
        helper.succeed();
    }

    // MixinWirelessMenu: an Infinity Card gives unlimited range at the configured drain
    public static void infinityCardSetsUnlimitedRange(GameTestHelper helper) {
        assertCardRange(helper, new ItemStack(ModItems.INFINITY_CARD.get()), 100L * AEInfinityBoosterConfig.INFINITY_CARD_DRAIN.get());
    }

    // MixinWirelessMenu: a Dimension Card gives unlimited range at the configured drain
    public static void dimensionCardSetsUnlimitedRange(GameTestHelper helper) {
        assertCardRange(helper, new ItemStack(ModItems.DIMENSION_CARD.get()), 100L * AEInfinityBoosterConfig.DIMENSION_CARD_DRAIN.get());
    }

    // MixinWirelessBlockEntity: the game test framework already forces this chunk, so taking the card out must not release it
    public static void cardLeavesOtherForcedChunksAlone(GameTestHelper helper) {
        WirelessAccessPointBlockEntity accessPoint = placeAccessPoint(helper);
        ServerLevel level = helper.getLevel();
        BlockPos pos = helper.absolutePos(ACCESS_POINT);
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;

        accessPoint.getInternalInventory().setItemDirect(0, new ItemStack(ModItems.INFINITY_CARD.get()));
        accessPoint.saveChanges();
        accessPoint.getInternalInventory().setItemDirect(0, ItemStack.EMPTY);
        accessPoint.saveChanges();

        // setChunkForced returns whether it changed anything, so false means the chunk was still forced
        assertFalse(helper, level.setChunkForced(chunkX, chunkZ, true), "Taking the card out released a chunk the access point didn't force");
        helper.succeed();
    }

    private static void assertCardRange(GameTestHelper helper, ItemStack card, long expectedDrain) {
        WirelessAccessPointBlockEntity accessPoint = placeAccessPoint(helper);
        accessPoint.getInternalInventory().setItemDirect(0, card);

        WirelessAccessPointMenu menu = openMenu(helper, accessPoint);
        menu.broadcastChanges();

        assertTrue(helper, menu.getRange() == Long.MAX_VALUE, "Expected unlimited range but got " + menu.getRange());
        assertTrue(helper, menu.getDrain() == expectedDrain, "Expected a drain of " + expectedDrain + " but got " + menu.getDrain());
        helper.succeed();
    }

    private static void assertTrue(GameTestHelper helper, boolean condition, String message) {
        //? if >=1.20 {
        helper.assertTrue(condition, message);
        //?} else {
        /*if (!condition) {
            throw new GameTestAssertException(message);
        }
        *///?}
    }

    private static void assertFalse(GameTestHelper helper, boolean condition, String message) {
        assertTrue(helper, !condition, message);
    }

    private static WirelessAccessPointBlockEntity placeAccessPoint(GameTestHelper helper) {
        helper.setBlock(ACCESS_POINT, AEBlocks.WIRELESS_ACCESS_POINT.block());
        return (WirelessAccessPointBlockEntity) helper.getLevel().getBlockEntity(helper.absolutePos(ACCESS_POINT));
    }

    // A plain mock player rather than a ServerPlayer, so AE2 doesn't try to sync the menu over a fake connection
    private static WirelessAccessPointMenu openMenu(GameTestHelper helper, WirelessAccessPointBlockEntity accessPoint) {
        //? if >=1.20.5 {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        //?} else
        /*Player player = helper.makeMockPlayer();*/
        return new WirelessAccessPointMenu(0, player.getInventory(), accessPoint);
    }

    private static Slot boosterSlot(WirelessAccessPointMenu menu) {
        return menu.slots.stream()
                .filter(slot -> slot instanceof RestrictedInputSlot)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Access point menu has no booster slot"));
    }
}
//?}
