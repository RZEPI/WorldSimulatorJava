public class Grass extends Plant{
    private final String GRASS_NAME = "Trawa";

    Grass(World world, Position pos)
    {
        super(world, pos);
        this.setStrength(DEF_PLANT_STRENGTH);
        this.setInitiative(DEF_PLANT_INIT);
        this.setNameOfClass(GRASS_NAME);
        this.setSignOfClass(this.world.GRASS_SIGN);
    }

    @Override
    protected void Replicate()
    {
        Position tmpPos = this.RandomisePos(1, true);
        if(tmpPos != NULLPOS) {
            this.world.AddToActions("organizm gatunku " + this.getNameOfClass() + " rozmnożył się ");
            Grass newOrg = new Grass(this.world, tmpPos);
            this.world.AddOrgToTempList(newOrg);
        }
    }

    @Override
    public ColorM ColorOfClass()
    {
        return new ColorM(0, 255, 0);
    }

    @Override
    public Collisions Collision(Organism attacker)
    {
        this.world.AddToActions(attacker.getNameOfClass() + " zjada " + this.getNameOfClass());
        this.Die();
        return Collisions.MOVE;
    }
}
