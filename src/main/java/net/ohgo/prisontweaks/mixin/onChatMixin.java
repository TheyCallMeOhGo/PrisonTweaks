package net.ohgo.prisontweaks.mixin;

import net.ohgo.prisontweaks.PrisonTweaks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.text.DecimalFormat;

@Mixin(MessageHandler.class)
public class onChatMixin {
    @Inject(method = "onGameMessage", at = @At("RETURN"), cancellable = true)
    public void onGameMessage(Text message, boolean overlay, CallbackInfo ci) {
        DecimalFormat df = new DecimalFormat("0.00");
        if(message.toString().contains("Level:")) {
            String[] uncut = message.toString().split(":", 2);
            String[] cut = uncut[1].split(" ", 2);
            String[] cut1 = cut[1].split("}", 2);
            PrisonTweaks.level = Integer.parseInt(cut1[0]);
            MinecraftClient.getInstance().player.sendMessage(Text.of("§3PrisonTweaks set level to " + PrisonTweaks.level));
            PrisonTweaks.levelSet = true;
        } else if(message.toString().contains("Experience:")) {
            String[] uncut = message.toString().split(":", 2);
            String[] cut = uncut[1].split(" ", 2);
            String[] cut1 = cut[1].split("/", 2);
            String[] cut2 = cut1[0].split("\\[", 2);
            PrisonTweaks.currentXp = Integer.parseInt(cut2[1]);
            //int current = Integer.parseInt(cut2[1]);
            String[] cut3 = cut1[1].split("]", 2);
            PrisonTweaks.nextLevelXp = Integer.parseInt(cut3[0]);
            //int outOf = Integer.parseInt(cut3[0]);
            MinecraftClient.getInstance().player.sendMessage(Text.of("§3PrisonTweaks set current XP to " + PrisonTweaks.currentXp));
            MinecraftClient.getInstance().player.sendMessage(Text.of("§3PrisonTweaks set next level XP to " + PrisonTweaks.nextLevelXp));
            PrisonTweaks.xpSet = true;
        }
        if(!PrisonTweaks.levelSet || !PrisonTweaks.xpSet) {
            return;
        } else {
            if (message.toString().contains("Fished")) {
                String[] uncut = message.toString().split("\\+", 2);
                String[] cut = uncut[1].split(" ", 2);
                int xp = Integer.parseInt(cut[0]);
                if((PrisonTweaks.currentXp + xp) < PrisonTweaks.nextLevelXp) {
                    PrisonTweaks.currentXp += xp;
                    MinecraftClient.getInstance().player.sendMessage(Text.of("§3Prison Tweaks: Current Fishing XP: " + PrisonTweaks.currentXp + "/" + PrisonTweaks.nextLevelXp + " " + df.format((((double)PrisonTweaks.currentXp / (double)PrisonTweaks.nextLevelXp) * 100)) + "% to next level"));
                } else {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("§3Prison Tweaks: run /fish stats again to refresh"));
                }
            }
        }
    }
}
