import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Turtle extends  Animal {
    private final int STRENGTH_TO_KILL_TURTLE = 5;
    private final int TURTLE_STRENGTH = 2;
    private final int TURTLE_INIT = 1;
    private final int TURTLE_MOVE_CHANCE = 4;//div 100 by this number
    private final String TURTLE_NAME = "Zlow";

    Turtle(World world, Position pos)
    {
        super(world, pos);
        this.setStrength(TURTLE_STRENGTH);
        this.setInitiative(TURTLE_INIT);
        this.setNameOfClass(TURTLE_NAME);
        this.setSignOfClass(this.world.TURTLE_SIGN);
    }

    @Override
    protected void Replication(Position posOfStaticParent)
    {
        Position tmpPos = this.RandomisePos(posOfStaticParent, 1, true);
        if(tmpPos != NULLPOS) {
            Turtle newOrg = new Turtle(this.world, tmpPos);
            this.world.AddOrgToTempList(newOrg);
        }
    }

    @Override
    protected void Action()
    {
        Random random = new Random();
        int randNr = random.nextInt(TURTLE_MOVE_CHANCE);
        if(randNr == 0)
        {
            Position tmpPos = this.RandomisePos(1, false);
            if (tmpPos != NULLPOS) {
                Organism defender = this.GetOrganismOnFiled(tmpPos);
                if (this.getNameOfClass() != defender.getNameOfClass())
                {
                    if(defender instanceof Animal)
                        this.world.AddToActions(this.getNameOfClass() + " atakuje " + defender.getNameOfClass());
                    DecodeCollision(this.getPosition(), tmpPos, defender.Collision(this));
                }
                else
                    this.CheckReplication(defender);
            }
        }
    }

    @Override
    public ColorM ColorOfClass()
    {
        return new ColorM(0, 51, 0);
    }

    @Override
    public Collisions Collision(Organism attacker)
    {
        if(attacker.getStrength() > STRENGTH_TO_KILL_TURTLE)
        {
            this.world.AddToActions(this.getNameOfClass() + " umiera");
            this.Die();
            return Collisions.MOVE;
        }else
            return Collisions.TURTLE_COUNTER;
    }
}
