package ooo.foooooooooooo.yep.mixin;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import ooo.foooooooooooo.yep.events.AdvancementCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public class PlayerAdvancementTrackerMixin {
    @Shadow
    public AdvancementProgress getProgress(Advancement advancement) {
        return null;
    }

    @Shadow
    private ServerPlayerEntity owner;

    @Inject(method = "grantCriterion", at = @At(value = "TAIL"))
    public void onAdvancement(Advancement advancement, String criterionName, CallbackInfoReturnable<Boolean> cir) {
        AdvancementProgress advancementProgress = this.getProgress(advancement);
        if (advancementProgress.isDone()) {
            AdvancementCallback.EVENT.invoker().getAdvancement(this.owner, advancement);
        }
    }
}
