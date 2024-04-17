package xyz.brassgoggledcoders.steamagerevolution.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelClockworkWings extends ModelBiped {
    private final ModelRenderer shape1;
    private final ModelRenderer shape2;
    private final ModelRenderer shape3;
    private final ModelRenderer shape4;
    private final ModelRenderer shape5;
    private final ModelRenderer shape6;
    private final ModelRenderer shape7;
    private final ModelRenderer shape8;
    private final ModelRenderer shape9;
    private final ModelRenderer shape10;
    private final ModelRenderer shape11;
    private final ModelRenderer shape12;
    private final ModelRenderer shape13;
    private final ModelRenderer shape15;
    private final ModelRenderer shape14;

    public ModelClockworkWings(float f) {
        super(f, 0, 64, 32);
        shape1 = new ModelRenderer(this);
        shape1.addBox(0F, 0F, 0F, 4, 4, 2);
        shape1.setRotationPoint(-2F, 0F, 2F);
        // shape1.setRotationPoint(0F, 0F, 0F);
        shape1.mirror = true;
        shape2 = new ModelRenderer(this, 12, 0);
        shape2.addBox(0F, 0F, 0F, 3, 1, 1);
        shape2.setRotationPoint(1.5F, 0.5F, 2.5F);
        shape2.mirror = true;
        setRotation(shape2, 0F, 0F, -0.4363323F);
        shape3 = new ModelRenderer(this, 12, 0);
        shape3.addBox(0F, 0F, 0F, 3, 1, 1);
        shape3.setRotationPoint(1.5F, 2.5F, 2.5F);
        shape3.mirror = true;
        setRotation(shape3, 0F, 0F, -0.4363323F);
        shape6 = new ModelRenderer(this, 0, 6);
        shape6.addBox(0F, 0F, 0F, 1, 6, 1);
        shape6.setRotationPoint(4F, -0.8F, 2.5F);
        shape6.mirror = true;
        shape7 = new ModelRenderer(this, 4, 6);
        shape7.addBox(0F, 0F, 0F, 4, 1, 1);
        shape7.setRotationPoint(4F, -1F, 2.5F);
        shape7.mirror = true;
        setRotation(shape7, 0F, 0F, -0.1745329F);
        shape4 = new ModelRenderer(this, 12, 2);
        shape4.addBox(-3F, 0F, 0F, 3, 1, 1);
        shape4.setRotationPoint(-1.5F, 0.5F, 2.5F);
        shape4.mirror = true;
        setRotation(shape4, 0F, 0F, 0.4363323F);
        shape5 = new ModelRenderer(this, 12, 2);
        shape5.addBox(-3F, 0F, 0F, 3, 1, 1);
        shape5.setRotationPoint(-1.5F, 2.5F, 2.5F);
        shape5.mirror = true;
        setRotation(shape5, 0F, 0F, 0.4363323F);
        shape8 = new ModelRenderer(this, 0, 6);
        shape8.addBox(0F, 0F, 0F, 1, 6, 1);
        shape8.setRotationPoint(-5F, -0.8F, 2.5F);
        shape8.mirror = true;
        shape9 = new ModelRenderer(this, 20, 0);
        shape9.addBox(-4F, 0F, 0F, 4, 1, 1);
        shape9.setRotationPoint(-4F, -1F, 2.5F);
        shape9.mirror = true;
        setRotation(shape9, 0F, 0F, 0.1745329F);
        shape10 = new ModelRenderer(this, 32, 0);
        shape10.addBox(0F, 0F, 0F, 4, 10, 0);
        shape10.setRotationPoint(4F, -1F, 3F);
        shape10.mirror = true;
        shape11 = new ModelRenderer(this, 32, 0);
        shape11.addBox(0F, 0F, 0F, 4, 10, 0);
        shape11.setRotationPoint(-8F, -1F, 3F);
        shape11.mirror = true;
        shape12 = new ModelRenderer(this, 6, 10);
        shape12.addBox(0F, 0F, 0F, 3, 3, 0);
        shape12.setRotationPoint(4F, 9F, 3F);
        shape12.mirror = true;
        shape13 = new ModelRenderer(this, 6, 10);
        shape13.addBox(0F, 0F, 0F, 3, 3, 0);
        shape13.setRotationPoint(-7F, 9F, 3F);
        shape13.mirror = true;
        shape15 = new ModelRenderer(this, 20, 6);
        shape15.addBox(0F, 0F, 0F, 2, 2, 0);
        shape15.setRotationPoint(4F, 12F, 3F);
        shape15.mirror = true;
        // shape14.mirror = true;
        shape14 = new ModelRenderer(this, 20, 6);
        shape14.addBox(0F, 0F, 0F, 2, 2, 0);
        shape14.setRotationPoint(-6F, 12F, 3F);
        shape14.mirror = true;
        // shape14.mirror = false;

        bipedBody.addChild(shape1);
        bipedBody.addChild(shape2);
        bipedBody.addChild(shape3);
        bipedBody.addChild(shape6);
        bipedBody.addChild(shape7);
        bipedBody.addChild(shape4);
        bipedBody.addChild(shape5);
        bipedBody.addChild(shape8);
        bipedBody.addChild(shape9);
        bipedBody.addChild(shape10);
        bipedBody.addChild(shape11);
        bipedBody.addChild(shape12);
        bipedBody.addChild(shape13);
        bipedBody.addChild(shape15);
        bipedBody.addChild(shape14);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
