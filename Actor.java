import java.util.Random;
public class Actor
{
    String name;
    int wounds, fighting, toughness, parry, armour, strength, damage, spirit;
    boolean shaken, wildCard;
    Actor foe;
    Actor[] combatants;//for simulator class
    Random dice = new Random();
    public Actor(String name, boolean wildCard,int fighting, int toughness, int parry, int armour, int strength, int damage, int spirit){
        this.name = name;
        this.wildCard = wildCard;
        this.fighting = fighting;
        this.toughness = toughness;
        this.parry = parry;
        this.armour = armour;
        this.strength = strength;
        this.damage = damage;
        this.spirit = spirit;
        wounds = 0;
        shaken = false;
    }

    public int test(int sides, int target){
        if(wildCard){
            int wildResult = (dice.nextInt(6)+1);
            wildResult += aceCheck(wildResult, 6, 0);
            if((wildResult - target) >=0)return wildResult;
        }
        int result = dice.nextInt(sides)+1;
        result+= aceCheck(result, sides, 0);
        if(((result-target)-wounds) >= 0) return result;
        return -1;
    }

    public int aceCheck(int result, int max, int current){
        int aceResult = 0, aceDice = 0;
        if(result == max){
            aceDice = dice.nextInt(6)+1;
            aceResult += aceDice;
            return aceCheck(aceResult, max, aceResult + current);
        }
        else{
            return current;
        }
    }

    public void turn(){
        //String returnString = "";
        if(shaken){
            shaken = test(spirit, 4)>=0?false:true;
        }
        if(!shaken){
            int fightingResult = test(fighting, foe.parry);
            int inflictedDam = totalDamage(fightingResult);
            //returnString += "Wounds Inflicted by "+name+" = "+inflictedDam/4;
            foe.giveWounds(inflictedDam);
        }
        //return returnString;
    }

    public int totalDamage(int testResult){
        int dealtDamage = 0;
        if(testResult >= 0){
            dealtDamage = strength + (dice.nextInt(damage)-1);
            if((testResult/4)>0){
                dealtDamage += (dice.nextInt(6)+1);
            }
            return dealtDamage - foe.parry;
        }
        return dealtDamage;
    }

    public boolean killCheck(){
        if(wounds > 3 && wildCard)return true;
        else if(wounds > 0 && !wildCard)return true;
        return false;
    }

    public void giveWounds(int damage){
        if(damage>0 && !shaken){
            shaken = true;
        }
        wounds += damage/4;
    }

    public void setFoe(Actor foe){
        this.foe = foe;
    }
}
