package meew0.meals;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Created by meew0 on 29.05.14.
 */
public class ItemMeal extends ItemFood {
    public static IIcon defaultIcon;

    public ItemMeal() {
        super(0, 0.f, false);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        defaultIcon = iconRegister.registerIcon("meals:default");
        for(int i = 0; i < Meals.foodConfig.size(); i++) {
            MealBean m = Meals.foodConfig.get(i);
            Meals.foodConfig.get(i).setIcon(iconRegister.registerIcon((m.getTexture() == null) ? "meals:default" : m.getTexture()));
        }
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        if(damage >= Meals.foodConfig.size()) {
            return defaultIcon;
        }
        return Meals.foodConfig.get(damage).getIcon();
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if(stack.getItemDamage() >= Meals.foodConfig.size()) {
            return "missingno";
        }
        return Meals.foodConfig.get(stack.getItemDamage()).getName();
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
        if(Meals.displayMealInfo) {
            if(stack.getItemDamage() >= Meals.foodConfig.size()) {
                return;
            }

            MealBean m = Meals.foodConfig.get(stack.getItemDamage());

            list.add("Restores " + (((float) m.getHunger()) / 2.f) + " hunger bars");
            list.add("Saturation: " + m.getSaturation());
            list.add("Use duration: " + m.getUseDuration());
        }
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < Meals.foodConfig.size(); i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public int func_150905_g(ItemStack stack) {
        if(stack.getItemDamage() >= Meals.foodConfig.size()) {
            return 0;
        }
        return Meals.foodConfig.get(stack.getItemDamage()).getHunger();
    }

    @Override
    public float func_150906_h(ItemStack stack) {
        if(stack.getItemDamage() >= Meals.foodConfig.size()) {
            return 0;
        }
        return Meals.foodConfig.get(stack.getItemDamage()).getSaturation();
    }

    @Override
    public boolean isWolfsFavoriteMeat() {
        return Meals.wolvesLikeMeals;
    }
}
