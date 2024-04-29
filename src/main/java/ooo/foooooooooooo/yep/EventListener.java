package ooo.foooooooooooo.yep;

import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import ooo.foooooooooooo.yep.messages.AdvancementMessage;
import ooo.foooooooooooo.yep.messages.DeathMessage;

public class EventListener {
    @SubscribeEvent
    public static void onDeathEvent(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
            String username = player.getDisplayName().getString();
            String message = getComponentText(event.getSource().getDeathMessage(player)).replace(username + " ", "");

            PluginMessenger.sendMessage(player, new DeathMessage(message));
        }
    }

    @SubscribeEvent
    public static void onAdvancementEvent(AdvancementEvent event) {
        AdvancementDisplay display = event.getAdvancement().getDisplay();

        if (display == null) {
            Yep.LOGGER.trace("Ignoring unsent display");
            return;
        }

        if (display.shouldAnnounceToChat()) {
            String title = getComponentText(display.getTitle());
            String description = getComponentText(display.getDescription());

            PluginMessenger.sendMessage((ServerPlayerEntity) event.getPlayer(), new AdvancementMessage(title, description));
        }
    }

    private static String getComponentText(Text component) {
        return component.getString();
    }
}
