package meew0.meals;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import meew0.meals.util.JsonConfigLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mod(modid = Meals.MODID, version = Meals.VERSION, name = Meals.NAME)
public class Meals {
    public static final String MODID = "meals";
    public static final String VERSION = "0.1";
    public static final String NAME = "Meals";

    public static List<MealBean> foodConfig;
    public static Configuration modConfig;

    public static boolean shouldLoadFood = true;
    public static boolean displayMealInfo = true;
    public static boolean wolvesLikeMeals = true;
    public static String mealUnlocalizedName = "meal";
    public static String foodConfigFileName = "Meals_FoodConfig.json";

    public static Logger log;

    public static Item meal;

    public static CreativeTabs mealsTab = new CreativeTabs("meals") {
        @Override
        public Item getTabIconItem() {
            return meal;
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        log = event.getModLog();

        modConfig = new Configuration(event.getSuggestedConfigurationFile());

        modConfig.load();

        shouldLoadFood = modConfig.get("general", "shouldLoadFood", true, "Determines whether the food configuration file should be loaded.").getBoolean(true);
        mealUnlocalizedName = modConfig.get("general", "mealUnlocalizedName", "meal", "The unlocalized name of the meal item. This is similar to the ID in 1.6 and should only be changed once.").getString();
        foodConfigFileName = modConfig.get("general", "foodConfigFileName", "Meals_FoodConfig.json", "The file name of the food config. Should be in the config/ directory.").getString();
        displayMealInfo = modConfig.get("general", "displayMealInfo", true, "Determines whether additional information should be displayed below a meal's item name.").getBoolean(true);
        wolvesLikeMeals = modConfig.get("general", "wolvesLikeMeals", true, "Determines whether wolves will eat meals.").getBoolean(true);

        modConfig.save();

        File foodCfgFile = new File(event.getModConfigurationDirectory(), foodConfigFileName);
        foodConfig = new JsonConfigLoader(foodCfgFile).load();

        for (MealBean m : foodConfig) {
            log.info("Found entry: " + m);
        }

        meal = new ItemMeal().setUnlocalizedName("meal").setHasSubtypes(true).setTextureName("meals:default").setCreativeTab(mealsTab);

        GameRegistry.registerItem(meal, "meal");

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        int i = -1;
        for (MealBean m : foodConfig) {
            i++;
            if (m.getRecipe() == null) continue;
            String[] recipe = m.getRecipe().split(",");
            ArrayList<Object> recipe2 = new ArrayList<Object>();
            if (recipe.length < 3 || (recipe.length % 2 != 1)) {
                log.error("Invalid recipe detected for food item " + m.getName());
                continue;
            }
            for (int j = 0; j < recipe.length; j++) {
                if (j > 2 && (j % 2 == 0)) {
                    String[] item = recipe[j].split(":");
                    int damage = 0;
                    if (item.length > 1) {
                        damage = Integer.parseInt(item[1]);
                    }
                    recipe2.add(new ItemStack(GameData.getItemRegistry().getObject(item[0]), 1, damage));
                } else if (j > 2 && (j % 2 == 1)) {
                    recipe2.add(recipe[j].charAt(0));
                } else {
                    recipe2.add(recipe[j]);
                }
            }
            GameRegistry.addShapedRecipe(new ItemStack(meal, m.getRecipeAmount(), i), recipe2.toArray());
        }
    }
}
