import javax.swing.*;
import java.io.PipedOutputStream;

public abstract class Animal extends Organism{

    protected abstract void Replication(Position posOfStaticParent);

    protected void CheckReplication(Organism defender)
    {
        if(this.getAge() > AGE_TO_REPRODUCT && defender.getAge() > AGE_TO_REPRODUCT) {
            this.Replication(defender.getPosition());
            this.world.AddToActions("organizm gatunku " + this.getNameOfClass() + " rozmnożył się ");
            this.setAge(0);
            defender.setAge(0);
        }
    }

    Animal(World world, Position pos)
    {
        super(world, pos);
    }

    @Override
    protected void Action()
    {
        Position tmpPos = this.RandomisePos(1, false);
        if(tmpPos != NULLPOS)
        {
            Organism defender = this.GetOrganismOnFiled(tmpPos);
            if(this.getNameOfClass() != defender.getNameOfClass())
            {
                if(!(defender instanceof Ground)&& defender instanceof Animal)
                    this.world.AddToActions(this.getNameOfClass() + " atakuje " + defender.getNameOfClass());
                DecodeCollision(this.getPosition(), tmpPos, defender.Collision(this));
            }
            else
                this.CheckReplication(defender);
        }
    }

    @Override
    public Collisions Collision(Organism attacker)
    {
        this.world.AddToActions(attacker.getNameOfClass() + " atakuje " + this.getNameOfClass());
        if(this.getStrength() > attacker.getStrength())
        {
            return Collisions.DEATH;
        }
        else
        {
            this.world.AddToActions(this.getNameOfClass() + " umiera ");
            this.Die();
            return Collisions.MOVE;
        }
    }
}
