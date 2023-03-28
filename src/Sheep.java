public class Sheep extends Animal{
    private final int SHEEP_STRENGTH = 4;
    private final int SHEEP_INIT = 4;
    private final String SHEEP_NAME = "Owca";

    Sheep(World world, Position pos)
    {
        super(world, pos);
        this.setStrength(SHEEP_STRENGTH);
        this.setInitiative(SHEEP_INIT);
        this.setNameOfClass(SHEEP_NAME);
        this.setSignOfClass(this.world.SHEEP_SIGN);
    }

    @Override
    protected void Replication(Position posOfStaticParent)
    {
        Position tmpPos = this.RandomisePos( posOfStaticParent, 1, true);
        if(tmpPos != NULLPOS) {
            Sheep newOrg = new Sheep(this.world, tmpPos);
            this.world.AddOrgToTempList(newOrg);
        }
    }

    @Override
    public ColorM ColorOfClass()
    {
        return new ColorM(102, 102, 153);
    }

}
