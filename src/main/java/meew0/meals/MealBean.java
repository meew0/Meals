package meew0.meals;

import net.minecraft.util.IIcon;

/**
 * Created by meew0 on 29.05.14.
 */
public class MealBean {
    private String name;
    private int hunger;
    private float saturation;
    private int useDuration;
    private String texture;
    private IIcon icon;
    private String recipe;
    private int recipeAmount;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public int getUseDuration() {
        return useDuration;
    }

    public void setUseDuration(int useDuration) {
        this.useDuration = useDuration;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "MealBean[" + name + "," + hunger + "," + saturation + "," + useDuration + "," + texture + "," + recipe + "]";
    }

    public IIcon getIcon() {
        return icon;
    }

    public void setIcon(IIcon icon) {
        this.icon = icon;
    }

    public int getRecipeAmount() {
        return recipeAmount;
    }

    public void setRecipeAmount(int recipeAmount) {
        this.recipeAmount = recipeAmount;
    }
}
