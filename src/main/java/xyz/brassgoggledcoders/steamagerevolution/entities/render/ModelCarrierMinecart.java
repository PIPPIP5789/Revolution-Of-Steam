package xyz.brassgoggledcoders.steamagerevolution.entities.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelMinecart - FreneticScribbler Created using Tabula 7.0.1
 */
public class ModelCarrierMinecart extends ModelBase {
    public ModelRenderer back;
    public ModelRenderer front;
    public ModelRenderer bottom;
    public ModelRenderer right;
    public ModelRenderer left;
    public ModelRenderer pillar1;
    public ModelRenderer pillar2;
    public ModelRenderer pillar3;
    public ModelRenderer pillar4;
    public ModelRenderer side1;
    public ModelRenderer side2;
    public ModelRenderer backdiag;
    public ModelRenderer backinner;
    public ModelRenderer frontdiag;
    public ModelRenderer frontinner;
    public ModelRenderer fill;

    public ModelCarrierMinecart() {
        textureWidth = 128;
        textureHeight = 64;
        right = new ModelRenderer(this, 0, 12);
        right.setRotationPoint(0.0F, -3.0F, -7.0F);
        right.addBox(-8.0F, -3.0F, -1.0F, 16, 3, 2, 0.0F);
        setRotateAngle(right, 0.0F, 3.141592653589793F, 0.0F);
        pillar1 = new ModelRenderer(this, 38, 0);
        pillar1.setRotationPoint(-10.0F, 3.0F, 6.0F);
        pillar1.addBox(0.0F, -6.0F, 0.0F, 2, 6, 2, 0.0F);
        side2 = new ModelRenderer(this, 2, 4);
        side2.setRotationPoint(0.0F, -1.0F, -4.0F);
        side2.addBox(-9.0F, -6.0F, -1.0F, 18, 11, 2, 0.0F);
        setRotateAngle(side2, -2.443460952792061F, 0.0F, 0.0F);
        pillar3 = new ModelRenderer(this, 52, 0);
        pillar3.setRotationPoint(-10.0F, 3.0F, -8.0F);
        pillar3.addBox(0.0F, -6.0F, 0.0F, 2, 6, 2, 0.0F);
        left = new ModelRenderer(this, 56, 26);
        left.setRotationPoint(0.0F, -3.0F, 7.0F);
        left.addBox(-8.0F, -3.0F, -1.0F, 16, 3, 2, 0.0F);
        pillar4 = new ModelRenderer(this, 44, 8);
        pillar4.setRotationPoint(8.0F, 3.0F, -8.0F);
        pillar4.addBox(0.0F, -6.0F, 0.0F, 2, 6, 2, 0.0F);
        backdiag = new ModelRenderer(this, 46, 10);
        backdiag.setRotationPoint(-8.9F, -5.5F, 0.0F);
        backdiag.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
        setRotateAngle(backdiag, -0.7853981633974483F, 0.0F, 0.0F);
        back = new ModelRenderer(this, 0, 0);
        back.setRotationPoint(-9.0F, -3.0F, 0.0F);
        back.addBox(-8.0F, -3.0F, -1.0F, 16, 3, 2, 0.0F);
        setRotateAngle(back, 0.0F, 4.71238898038469F, 0.0F);
        frontinner = new ModelRenderer(this, 0, 22);
        frontinner.setRotationPoint(8.1F, 0.0F, 0.0F);
        frontinner.addBox(-5.0F, -3.0F, -1.0F, 10, 3, 1, 0.0F);
        setRotateAngle(frontinner, 0.0F, 4.71238898038469F, 0.0F);
        pillar2 = new ModelRenderer(this, 44, 0);
        pillar2.setRotationPoint(8.0F, 3.0F, 6.0F);
        pillar2.addBox(0.0F, -6.0F, 0.0F, 2, 6, 2, 0.0F);
        frontdiag = new ModelRenderer(this, 96, 18);
        frontdiag.setRotationPoint(7.9F, -5.5F, 0.0F);
        frontdiag.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
        setRotateAngle(frontdiag, -0.7853981633974483F, 0.0F, 0.0F);
        backinner = new ModelRenderer(this, 0, 23);
        backinner.setRotationPoint(-9.1F, 0.0F, 0.0F);
        backinner.addBox(-5.0F, -3.0F, -1.0F, 10, 3, 1, 0.0F);
        setRotateAngle(backinner, 0.0F, 4.71238898038469F, 0.0F);
        bottom = new ModelRenderer(this, 72, 0);
        bottom.setRotationPoint(0.0F, 4.0F, 0.0F);
        bottom.addBox(-10.0F, -8.0F, -1.0F, 20, 16, 2, 0.0F);
        setRotateAngle(bottom, 1.5707963267948966F, 0.0F, 0.0F);
        side1 = new ModelRenderer(this, 72, 5);
        side1.setRotationPoint(0.0F, -1.0F, 4.0F);
        side1.addBox(-9.0F, -6.0F, -1.0F, 18, 11, 2, 0.0F);
        setRotateAngle(side1, 2.443460952792061F, 0.0F, 0.0F);
        front = new ModelRenderer(this, 0, 5);
        front.setRotationPoint(9.0F, -3.0F, 0.0F);
        front.addBox(-8.0F, -3.0F, -1.0F, 16, 3, 2, 0.0F);
        setRotateAngle(front, 0.0F, 1.5707963267948966F, 0.0F);
        fill = new ModelRenderer(this, 0, 27);
        fill.setRotationPoint(0.0F, -3.5F, 0.0F);
        fill.addBox(-8.0F, -6.0F, -1.0F, 16, 12, 2, 0.0F);
        setRotateAngle(fill, 1.5707963267948966F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        right.render(f5);
        pillar1.render(f5);
        side2.render(f5);
        pillar3.render(f5);
        left.render(f5);
        pillar4.render(f5);
        backdiag.render(f5);
        back.render(f5);
        frontinner.render(f5);
        pillar2.render(f5);
        frontdiag.render(f5);
        backinner.render(f5);
        bottom.render(f5);
        side1.render(f5);
        front.render(f5);
    }

    public void renderContents(float scale) {
        fill.render(scale);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
