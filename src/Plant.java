import java.util.Random;

public abstract class Plant extends Organism{
    protected final int DEF_PLANT_INIT = 0;
    protected final int DEF_PLANT_STRENGTH = 0;
    protected final int PLANT_CHANCE_TO_REPLICATE = 33;//div 100 by this number

    Plant(World world, Position pos)
    {
        super(world, pos);
    }

    protected abstract void Replicate();
    public void Action()
    {
        Random rand = new Random();
        int randNr = rand.nextInt(PLANT_CHANCE_TO_REPLICATE);
        if(randNr == 1)
        {
            this.Replicate();
        }
    }
    public abstract Collisions Collision(Organism attacker);
}
