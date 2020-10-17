
public class Simulator
{
    private Actor first, second;
    int simulations;
    public void simulate(int simulations)
    {
        this.simulations = simulations;

        int firstWins = 0, secondWins = 0;
        long startTime = System.nanoTime();
        
        while(simulations > 0){
            Actor[] actors = testActors();
            first = actors[0];
            second = actors[1];
            
            first.setFoe(second);
            second.setFoe(first);
            while(true){
                if(first.foe.killCheck()){
                    firstWins++;
                    break;
                }
                else{ 
                    first.turn(); 
                }
                if(first.killCheck()){
                    secondWins++;
                    break;
                }
                else{ 
                    first.foe.turn();
                }
            }
            
            flip();
            simulations--;
        }
        long endTime = System.nanoTime();
        System.out.println(first.name+" Wins = "+firstWins+"("+Float.toString(getPercent(firstWins, this.simulations)).substring(0,4)+"%)");
        System.out.println(second.name+" Wins = "+secondWins+"("+Float.toString(getPercent(secondWins, this.simulations)).substring(0,4)+"%)");
        System.out.println("Time Taken: " + ((endTime - startTime) / 1000000000) + " Seconds.");
    }
    
    private void flip(){
        Actor secondTemp = second;
        second = first;
        first = secondTemp;
    }

    public Actor[] testActors(){
        first = new Actor("Fighter 1", true, 8, 5, 6, 8, 6, 6, 8);
        second = new Actor("Fighter 2", true, 8, 5, 6, 8, 6, 6, 8);
        return new Actor[] {first, second};
    }

    public Actor createActor(String name, boolean wildCard, int fighting, int toughness, int parry, int armour, int strength, int weaponDamage, int spirit){
        return new Actor(name, wildCard, fighting, toughness, parry, armour, strength, weaponDamage, spirit);
    }

    private float getPercent(int input,int battleCount){
        float percent = (input * 100.0f) / battleCount;
        return percent;
    }
}
