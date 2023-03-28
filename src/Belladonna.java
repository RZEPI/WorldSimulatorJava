import javax.swing.*;

public class Belladonna extends Plant{
    private final int BELLADONNA_STRENGTH = 99;
    private final String BELLADONNA_NAME = "Wilcza jagoda";

    Belladonna(World world, Position pos)
    {
        super(world, pos);
        this.setStrength(BELLADONNA_STRENGTH);
        this.setInitiative(DEF_PLANT_INIT);
        this.setNameOfClass(BELLADONNA_NAME);
        this.setSignOfClass(this.world.BELLADONA_SIGN);
    }

    @Override
    protected void Replicate()
    {
        Position tmpPos = this.RandomisePos(1, true);
        if(tmpPos != NULLPOS) {
            Belladonna newOrg = new Belladonna(this.world, tmpPos);
            this.world.AddToActions("organizm gatunku " + this.getNameOfClass() + " rozmnożył się ");
            this.world.AddOrgToTempList(newOrg);
        }
    }

    @Override
    public ColorM ColorOfClass()
    {
        return new ColorM(255, 0, 102);
    }

    @Override
    public Collisions Collision(Organism attacker)
    {
        this.world.AddToActions(attacker.getNameOfClass() + " zjada wilcze jagody");
        this.Die();
        return Collisions.DEATH;
    }
}
