package xyz.brassgoggledcoders.steamagerevolution.entities.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created using Tabula 7.0.1
 */
public class ModelDrillingMinecart extends ModelBase {
    public ModelRenderer field_78154_a2;
    public ModelRenderer field_78154_a3;
    public ModelRenderer field_78154_a1;
    public ModelRenderer field_78154_a6;
    public ModelRenderer field_78154_a4;
    public ModelRenderer field_78154_a5;
    public ModelRenderer body;
    public ModelRenderer drill1;
    public ModelRenderer drill2;

    public ModelDrillingMinecart() {
        textureWidth = 64;
        textureHeight = 64;
        body = new ModelRenderer(this, 0, 28);
        body.setRotationPoint(10.0F, -1.0F, 0.0F);
        body.addBox(-20.0F, -4.0F, -10.0F, 20, 4, 16, 0.0F);
        field_78154_a4 = new ModelRenderer(this, 0, 0);
        field_78154_a4.setRotationPoint(0.0F, 4.0F, -7.0F);
        field_78154_a4.addBox(-8.0F, -5.0F, -1.0F, 16, 4, 2, 0.0F);
        setRotateAngle(field_78154_a4, 0.0F, 3.141592653589793F, 0.0F);
        field_78154_a3 = new ModelRenderer(this, 0, 0);
        field_78154_a3.setRotationPoint(9.0F, 4.0F, 0.0F);
        field_78154_a3.addBox(-8.0F, -5.0F, -1.0F, 16, 4, 2, 0.0F);
        setRotateAngle(field_78154_a3, 0.0F, 1.5707963267948966F, 0.0F);
        field_78154_a6 = new ModelRenderer(this, 44, 10);
        field_78154_a6.setRotationPoint(0.0F, 4.0F, 0.0F);
        field_78154_a6.addBox(-9.0F, -7.0F, -1.0F, 18, 14, 1, 0.0F);
        setRotateAngle(field_78154_a6, -1.5707963705062866F, 0.0F, 0.0F);
        field_78154_a5 = new ModelRenderer(this, 0, 0);
        field_78154_a5.setRotationPoint(0.0F, 4.0F, 7.0F);
        field_78154_a5.addBox(-8.0F, -5.0F, -1.0F, 16, 4, 2, 0.0F);
        field_78154_a1 = new ModelRenderer(this, 0, 10);
        field_78154_a1.setRotationPoint(0.0F, 4.0F, 0.0F);
        field_78154_a1.addBox(-10.0F, -8.0F, -1.0F, 20, 16, 2, 0.0F);
        setRotateAngle(field_78154_a1, 1.5707963705062866F, 0.0F, 0.0F);
        drill1 = new ModelRenderer(this, 0, 48);
        drill1.setRotationPoint(-5.0F, 0.0F, -16.0F);
        drill1.addBox(-3.0F, -6.0F, 0.0F, 6, 6, 6, 0.0F);
        drill2 = new ModelRenderer(this, 0, 48);
        drill2.setRotationPoint(5.0F, 0.0F, -16.0F);
        drill2.addBox(-3.0F, -6.0F, 0.0F, 6, 6, 6, 0.0F);
        field_78154_a2 = new ModelRenderer(this, 0, 0);
        field_78154_a2.setRotationPoint(-9.0F, 4.0F, 0.0F);
        field_78154_a2.addBox(-8.0F, -5.0F, -1.0F, 16, 4, 2, 0.0F);
        setRotateAngle(field_78154_a2, 0.0F, 4.71238898038469F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        body.render(f5);
        field_78154_a4.render(f5);
        field_78154_a3.render(f5);
        field_78154_a6.render(f5);
        field_78154_a5.render(f5);
        field_78154_a1.render(f5);
        drill1.render(f5);
        drill2.render(f5);
        field_78154_a2.render(f5);
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
