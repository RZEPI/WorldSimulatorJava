import java.util.Random;

public class Milt extends Plant {
    private final int NUM_OF_TRIES_TO_REPLICATE = 3;
    private final String MILT_NAME = "Mlecz";
    private String signOfClass = this.world.MILT_SIGN;

    Milt(World world, Position pos)
    {
        super(world, pos);
        this.setStrength(DEF_PLANT_STRENGTH);
        this.setInitiative(DEF_PLANT_INIT);
        this.setNameOfClass(MILT_NAME);
        this.setSignOfClass(this.world.MILT_SIGN);
    }

    @Override
    protected void Replicate()
    {
        Position tmpPos = this.RandomisePos(1, true);
        if(CheckIfIsInBounds(tmpPos)) {
            this.world.AddToActions("organizm gatunku " + this.getNameOfClass() + " rozmnożył się ");
            Milt newOrg = new Milt(this.world, tmpPos);
            this.world.AddOrgToTempList(newOrg);
        }
    }

    @Override
    public void Action()
    {
        for(int i = 0; i < NUM_OF_TRIES_TO_REPLICATE; i++)
        {
            Random rand = new Random();
            int randNr = rand.nextInt(PLANT_CHANCE_TO_REPLICATE);
            if (randNr == 1) {
                this.Replicate();
            }
        }
    }

    @Override
    public Collisions Collision(Organism attacker)
    {
        this.world.AddToActions(attacker.getNameOfClass() + " zjada " + this.getNameOfClass());
        this.Die();
        return Collisions.MOVE;
    }

    @Override
    public ColorM ColorOfClass()
    {
        return new ColorM(255, 255, 0);
    }

}
