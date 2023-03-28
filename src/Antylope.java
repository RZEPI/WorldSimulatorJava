import javax.sql.rowset.FilteredRowSet;
import java.util.Random;

public class Antylope extends Animal{
    private final int ANTYLOPE_RANGE = 2;
    private final int ANTYLOPE_STRENGTH = 2;
    private final int ANTYLOPE_INIT = 1;
    private final int ANTYLOPE_ESCAPE_CHANCE = 2;//div 100 by this number
    private final String ANTYLOPE_NAME = "Antylopa";

    Antylope(World world, Position pos)
    {
        super(world, pos);
        this.setStrength(ANTYLOPE_STRENGTH);
        this.setInitiative(ANTYLOPE_INIT);
        this.setNameOfClass(ANTYLOPE_NAME);
        this.setSignOfClass(this.world.ANTYLOPE_SIGN);
    }

    @Override
    protected void Replication(Position posOfStaticParent)
    {
        Position tmpPos = this.RandomisePos( posOfStaticParent, 1, true);
        if(tmpPos != NULLPOS) {
            Antylope newOrg = new Antylope(this.world, tmpPos);
            this.world.AddOrgToTempList(newOrg);
        }
    }

    @Override
    protected void Action()
    {
        Position tmpPos = this.RandomisePos(ANTYLOPE_RANGE, false);
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

    @Override
    public ColorM ColorOfClass()
    {
        return new ColorM(255, 153, 102);
    }

    @Override
    public Collisions Collision(Organism attacker)
    {
        Random rand = new Random();
        int randNr = rand.nextInt(ANTYLOPE_ESCAPE_CHANCE);
        if(randNr == 0)
        {
            this.setPosition(this.RandomisePos(1, true));
            this.world.AddToActions(this.getNameOfClass() + " ucieka");
            return Collisions.MOVE;
        }else
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

}
