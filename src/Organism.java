import java.lang.String;
import java.lang.Object;
import java.util.Random;

public abstract class Organism {
    protected final int AGE_TO_REPRODUCT = 10;
    public final Position NULLPOS = new Position(-1231, -213);
    public World world;
    private int strength;
    private int initiative;
    private int age = 0;
    private boolean alive = true;
    private String nameOfClass;
    private String signOfClass;
    abstract public ColorM ColorOfClass();
    private Position position;

    public Organism(World world, Position pos)
    {
        this.world = world;
        this.position = pos;
        this.age = 0;
    }

    public int getAge() {
        return age;
    }

    public int getStrength() {
        return strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public Position getPosition() {
        return position;
    }

    public String getNameOfClass() {
        return nameOfClass;
    }

    public String getSignOfClass() {
        return signOfClass;
    }

    public boolean IsAlive()
    {
        return alive;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public void setNameOfClass(String nameOfClass){this.nameOfClass = nameOfClass;}

    public void setSignOfClass(String signOfClass) {
        this.signOfClass = signOfClass;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public abstract Collisions Collision(Organism attacker);
    protected abstract void Action();

    protected void Die()
    {
        this.alive = false;
        if(CheckIfIsInBounds(this.getPosition()))
            this.world.board[this.getPosition().getX()][this.getPosition().getY()] = new Ground(this.world, this.getPosition());
    }

    protected Position DecodePosition(Position posToChange, int num, int dist)
    {
        Position tmpPos = new Position(posToChange.getX(), posToChange.getY());
        switch (num)
        {
            case 0:
                tmpPos.setY(tmpPos.getY() - dist);
                break;
            case 1:
                tmpPos.setX(tmpPos.getX() + dist);
                tmpPos.setY(tmpPos.getY() - dist);
                break;
            case 2:
                tmpPos.setX(tmpPos.getX() + dist);
                break;
            case 3:
                tmpPos.setX(tmpPos.getX() + dist);
                tmpPos.setY(tmpPos.getY() + dist);
                break;
            case 4:
                tmpPos.setY(tmpPos.getY() + dist);
                break;
            case 5:
                tmpPos.setX(tmpPos.getX() - dist);
                tmpPos.setY(tmpPos.getY() + dist);
                break;
            case 6:
                tmpPos.setX(tmpPos.getX() -dist);
            case 7:
                tmpPos.setY(tmpPos.getY() - dist);
                tmpPos.setX(tmpPos.getX() - dist);
                break;
        }
        return tmpPos;
    }
    protected boolean CheckIfIsInBounds(Position pos)
    {
        if(world.getMeas().getX() > pos.getX() && world.getMeas().getY() > pos.getY() && pos.getY() >= 0 && pos.getX() >= 0)
            return true;
        else
            return false;
    }

    protected Position RandomisePos( int dist, boolean free)
    {
        Position outPos;
        Random rand = new Random();
        int randNr = rand.nextInt(8);
        outPos = DecodePosition(this.getPosition(), randNr, dist);
        if(CheckIfIsInBounds(outPos))
        {
            if(!free)
                return outPos;
            else
            {
                int i = 0;
                while(i < 7)
                {
                    if(CheckIfIsInBounds(outPos))
                    {
                        if (this.world.board[outPos.getX()][outPos.getY()] instanceof Ground)
                            return outPos;
                        else
                        {
                            randNr = (randNr + 1) % 8;
                            outPos = DecodePosition(this.getPosition(), randNr, dist);
                            i++;
                        }
                    }else
                        i++;
                }
                return NULLPOS;
            }
        }
        else
        {
            int i = 0;
            while(i < 7) {
                randNr = (randNr + 1) % 8;
                outPos = DecodePosition(this.getPosition(), randNr, dist);
                if(CheckIfIsInBounds(outPos))
                    return outPos;
                else
                    i++;
            }
            return NULLPOS;
        }
    }

    protected Position RandomisePos(Position pos, int dist, boolean free)
    {
        Position outPos;
        Random rand = new Random();
        int randNr = rand.nextInt(8);
        outPos = DecodePosition(pos, randNr, dist);
        if(CheckIfIsInBounds(outPos))
        {
            if(!free)
                return outPos;
            else
            {
                int i = 0;
                while(i < 7)
                {
                    if (this.world.board[outPos.getX()][outPos.getY()] instanceof Ground)
                        return outPos;
                    else
                    {
                        randNr = (randNr + 1) % 8;
                        outPos = DecodePosition(pos, randNr, dist);
                        i++;
                    }
                }
                return NULLPOS;
            }
        }
        else
        {
            int i = 0;
            while(i < 7) {
                randNr = (randNr + 1) % 8;
                outPos = DecodePosition(pos, randNr, dist);
                if(CheckIfIsInBounds(outPos))
                    return outPos;
                else
                    i++;
            }
            return NULLPOS;
        }
    }

    protected void DecodeCollision(Position prevPos, Position pos, Collisions col)
    {
        switch (col) {
            case MOVE -> {
                this.setPosition(pos);
                this.world.board[pos.getX()][pos.getY()] = this;
                this.world.board[prevPos.getX()][prevPos.getY()] = new Ground(this.world, prevPos);
            }
            case DEATH -> {
                this.world.board[prevPos.getX()][prevPos.getY()] = new Ground(this.world, prevPos);
                this.world.AddToActions(this.getNameOfClass() + " umiera");
                this.Die();
            }
            case GUARANA_BOOST -> {
                this.setPosition(pos);
                this.world.board[pos.getX()][pos.getY()] = this;
                this.world.board[prevPos.getX()][prevPos.getY()] = new Ground(this.world, prevPos);
                this.setStrength(this.getStrength() + 3);
                this.world.AddToActions(this.getNameOfClass() + " zjadł guaranę i otrzymuje +3 do siły");
            }
            case TURTLE_COUNTER -> this.world.AddToActions(this.getNameOfClass() + " został odbity przez żłówia");
            case HUMAN_ALZURES_SHIELD -> {
                Position newPos = this.RandomisePos(1, true);
                if (newPos != NULLPOS) {
                    Organism defender = this.GetOrganismOnFiled(newPos);
                    Collisions newCol = defender.Collision(this);
                    this.DecodeCollision(pos, newPos, newCol);
                }
                this.world.AddToActions(this.getNameOfClass() + " został odbity przez Tarczę Alzure'a");
            }
        }
    }

    protected Organism GetOrganismOnFiled(Position pos)
    {
        return world.board[pos.getX()][pos.getY()];
    }

}
