public class Guarana extends Plant {
    private final String GUARANA_NAME = "Guarana";
    private String signOfClass = this.world.GUARANA_SIGN;

    Guarana(World world, Position pos)
    {
        super(world, pos);
        this.setStrength(DEF_PLANT_INIT);
        this.setInitiative(DEF_PLANT_STRENGTH);
        this.setNameOfClass(GUARANA_NAME);
        this.setSignOfClass(this.world.GUARANA_SIGN);
    }

    @Override
    protected void Replicate()
    {
        Position tmpPos = this.RandomisePos(1, true);
        if(tmpPos != NULLPOS) {
            Guarana newOrg = new Guarana(this.world, tmpPos);
            this.world.AddOrgToTempList(newOrg);
        }
    }

    @Override
    public ColorM ColorOfClass()
    {
        return new ColorM(204, 102, 0);
    }

    @Override
    public Collisions Collision(Organism attacker)
    {
        this.Die();
        return Collisions.GUARANA_BOOST;
    }
}
