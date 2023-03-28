import java.util.Random;

public class Fox extends Animal {
    private final int FOX_STRENGTH = 3;
    private final int FOX_INIT = 7;
    private final String FOX_NAME = "Lis";

    Fox(World world, Position pos)
    {
        super(world, pos);
        this.setSignOfClass(this.world.FOX_SIGN);
        this.setStrength(FOX_STRENGTH);
        this.setInitiative(FOX_INIT);
        this.setNameOfClass(FOX_NAME);
    }

    @Override
    protected void Replication(Position posOfStaticParent) {
        Position tmpPos = this.RandomisePos(posOfStaticParent, 1, true);
        if(tmpPos != NULLPOS) {
            Fox newOrg = new Fox(this.world, tmpPos);
            this.world.AddOrgToTempList(newOrg);
        }
    }

    @Override
    protected void Action()
    {
        Position tmpPos = this.RandomisePos(1, false);
        if (tmpPos != NULLPOS) {
            Organism defender = this.GetOrganismOnFiled(tmpPos);
            if (this.getNameOfClass() != defender.getNameOfClass())
            {
                if(defender.getStrength() > this.getStrength())
                {
                    this.Action();
                }else
                {
                    if(defender instanceof Animal)
                        this.world.AddToActions(this.getNameOfClass() + " atakuje " + defender.getNameOfClass());
                    DecodeCollision(this.getPosition(), tmpPos, defender.Collision(this));
                }
            }
            else
                this.CheckReplication(defender);
        }
    }

    @Override
    public ColorM ColorOfClass()
    {
        return new ColorM(255, 153, 0);
    }

    @Override
    public Collisions Collision(Organism attacker)
    {
        if(this.getStrength() < attacker.getStrength())
        {
            this.world.AddToActions(this.getNameOfClass() + " umiera");
            this.Die();
            return Collisions.MOVE;
        }else
            return Collisions.DEATH;
    }
}
