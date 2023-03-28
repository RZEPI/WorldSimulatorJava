import javax.swing.event.HyperlinkListener;

public class Human extends Animal {
    final private int DEF_DURATION_OF_ABILITY = 6;
    private int abilityDuration = DEF_DURATION_OF_ABILITY;
    private int abilityCooldown = 0;
    private boolean IsAbilityOn = false;
    final private int HUMAN_STRENGTH = 5;
    final private int HUMAN_INIT = 4;
    final public String HUMAN_NAME = "Czlowiek";


    Human(World world, Position pos)
    {
        super(world, pos);
        this.setSignOfClass(this.world.HUMAN_SIGN);
        this.setNameOfClass(HUMAN_NAME);
        this.setStrength(HUMAN_STRENGTH);
        this.setInitiative(HUMAN_INIT);
    }

    private void ControlAbility()
    {
        if(this.IsAbilityOn)
        {
            if(this.abilityDuration != 0)
                this.abilityDuration--;
            else
            {
                this.world.AddToActions("Specjalna umiejetnosc skonczyla sie");
                this.IsAbilityOn = false;
                this.abilityCooldown = DEF_DURATION_OF_ABILITY;
            }

        }else
        {
            if(this.abilityCooldown != 0)
                this.abilityCooldown--;
        }

        if( this.abilityCooldown == 0)
            this.abilityDuration = DEF_DURATION_OF_ABILITY;
    }


    public void Action(Direction direction, boolean onAbility)
    {
        if(!onAbility) {
            Position newPos = new Position(this.getPosition().getX(), this.getPosition().getY());
            switch (direction) {
                case UP:
                    newPos.setY(this.getPosition().getY() - 1);
                    break;
                case DOWN:
                    newPos.setY(this.getPosition().getY() + 1);
                    break;
                case RIGHT:
                    newPos.setX(this.getPosition().getX() + 1);
                    break;
                case LEFT:
                    newPos.setX(this.getPosition().getX() - 1);
                    break;
                case NONE:
                    break;
            }
            if(CheckIfIsInBounds(newPos))
            {
                Organism defender = this.world.board[newPos.getX()][newPos.getY()];
                if (this.CheckIfIsInBounds(newPos)) {
                    if (defender instanceof Animal && !(defender instanceof Human))
                        this.world.AddToActions(this.getNameOfClass() + " atakuje " + defender.getNameOfClass());
                    if (!(defender instanceof Human))
                        DecodeCollision(this.getPosition(), newPos, defender.Collision(this));
                }
            }
        }else
        {
            if(abilityCooldown == 0 && !this.IsAbilityOn) {
                this.IsAbilityOn = true;
                this.world.AddToActions(this.getNameOfClass() + " uzywa umiejetnosci");
            }
        }
        this.ControlAbility();
    }

    @Override
    public Collisions Collision(Organism attacker)
    {
        if(IsAbilityOn)
            return Collisions.HUMAN_ALZURES_SHIELD;
        else
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

    @Override
    protected void Replication(Position posOfStaticParent)
    {

    }

    @Override
    public ColorM ColorOfClass()
    {
        return new ColorM(0, 0, 0);
    }
}
