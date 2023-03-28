public class SosHogweed extends Plant {

    private final int SOSHOGWEED_STRENGTH = 10;
    private final String SOSHOGWEED_NAME = "Barszcz sosnowskiego";

    SosHogweed(World world, Position pos)
    {
        super(world, pos);
        this.setStrength(SOSHOGWEED_STRENGTH);
        this.setInitiative(DEF_PLANT_INIT);
        this.setNameOfClass(SOSHOGWEED_NAME);
        this.setSignOfClass(this.world.SOSHOGWEED_SIGN);
    }

    @Override
    public void Action()
    {
        for(int i = 0; i < 8; i++)
        {
            Organism orgInRange;
            Position tmpPos = this.DecodePosition(this.getPosition(), i, 1);
            if(CheckIfIsInBounds(tmpPos)) {
                orgInRange = this.world.board[tmpPos.getX()][tmpPos.getY()];
                if (orgInRange instanceof Animal) {
                    this.world.AddToActions(orgInRange.getNameOfClass() + " znalazł się w zasięgu barszczu sosnowskiego i umiera");
                    orgInRange.Die();
                }
            }
        }
    }

    @Override
    protected void Replicate()
    {
        Position tmpPos = this.RandomisePos(1, true);
        if(tmpPos != NULLPOS) {
            this.world.AddToActions("organizm gatunku " + this.getNameOfClass() + " rozmnożył się ");
            SosHogweed newOrg = new SosHogweed(this.world, tmpPos);
            this.world.AddOrgToTempList(newOrg);
        }
    }

    @Override
    public ColorM ColorOfClass()
    {
        return new ColorM(204, 255, 102);
    }

    @Override
    public Collisions Collision(Organism attacker)
    {
        this.world.AddToActions(attacker.getNameOfClass() + " zjada " + this.getNameOfClass());
        this.Die();
        return Collisions.DEATH;
    }
}
