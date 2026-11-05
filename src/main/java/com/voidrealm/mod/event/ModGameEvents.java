package com.voidrealm.mod.event;

import com.voidrealm.mod.Config;
import com.voidrealm.mod.VoidRealm;
import com.voidrealm.mod.registry.ModArmorMaterials;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

/** Hieu ung khi mac du bo giap. Chi chay moi 1 giay tren may chu de nhe may. */
@EventBusSubscriber(modid = VoidRealm.MODID)
public class ModGameEvents {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide || player.tickCount % 20 != 0 || !Config.ARMOR_SET_BONUS.get()) {
            return;
        }

        if (wearsFullSet(player, ModArmorMaterials.VOID_STEEL)) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 0, true, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 0, true, false, true));
        }

        if (wearsFullSet(player, ModArmorMaterials.PHASE_CLOTH)) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 0, true, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 60, 0, true, false, true));
        }
    }

    private static boolean wearsFullSet(Player player, DeferredHolder<ArmorMaterial, ArmorMaterial> material) {
        for (ItemStack stack : player.getArmorSlots()) {
            if (!(stack.getItem() instanceof ArmorItem armor) || armor.getMaterial().value() != material.get()) {
                return false;
            }
        }
        return true;
    }

    private ModGameEvents() {
    }
}
