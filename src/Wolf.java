public class Wolf extends Animal{
    private final int WOLF_STRENGTH = 9;
    private final int WOLF_INIT = 5;
    private final String WOLF_NAME = "Wilk";

    Wolf(World world, Position pos)
    {
        super(world, pos);
        this.setStrength(WOLF_STRENGTH);
        this.setInitiative(WOLF_INIT);
        this.setNameOfClass(WOLF_NAME);
        this.setSignOfClass(this.world.WOLF_SIGN);
    }

    @Override
    protected void Replication(Position posOfStaticParent)
    {
        Position tmpPos = this.RandomisePos(posOfStaticParent, 1, true);
        if(tmpPos != NULLPOS) {
            Wolf newOrg = new Wolf(this.world, tmpPos);
            this.world.AddOrgToTempList(newOrg);
        }
    }

    @Override
    public ColorM ColorOfClass()
    {
        return new ColorM(153, 153, 153);
    }

}
