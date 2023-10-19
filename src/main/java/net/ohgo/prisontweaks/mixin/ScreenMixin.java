package net.ohgo.prisontweaks.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.ohgo.prisontweaks.PrisonTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractParentElement {
    @Inject(method = "getTooltipFromItem", at=@At(value="HEAD"), cancellable = true)
    private static void getTooltipFromItem(MinecraftClient client, ItemStack stack, CallbackInfoReturnable<List<Text>> cir) {
        String stackName = stack.getName().getString().toLowerCase().replaceAll("'", "").replaceAll(" ", "_");
        Text description = Text.translatable("§3item-guide description: " + PrisonTweaks.items.get(stackName));
        if (PrisonTweaks.items.containsKey(stackName)) {
            List<Text> tooltip = stack.getTooltip(client.player, client.options.advancedItemTooltips ? TooltipContext.Default.ADVANCED : TooltipContext.Default.BASIC);

            tooltip.add(description);

            cir.setReturnValue(tooltip);
        }
    }
}