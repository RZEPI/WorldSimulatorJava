public class Ground extends Organism{

    public Ground(World world, Position pos)
    {
        super(world, pos);
    }
    @Override
    public ColorM ColorOfClass()
    {
        return new ColorM();
    }
    @Override
    public void Action()
    {
        return;
    }
    @Override
    public Collisions Collision(Organism attacker)
    {
        return Collisions.MOVE;
    }
}
