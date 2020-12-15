package org.cvm.world.Character;
import org.cvm.world.Buff.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Creature {
    private int Attack;
    private int Armor;
    private int CriticalStrike;
    private int Missrate;
    private List<AttackBuff> attackBuffs;
    private List<ArmorBuff> armorBuffs;
    Creature(int Attack,int Armor,int CriticalStrike,int Missrate){
        this.Attack=Attack;
        this.Armor=Armor;
        this.CriticalStrike=CriticalStrike;
        this.Missrate=Missrate;
        armorBuffs=new ArrayList<ArmorBuff>();
        attackBuffs=new ArrayList<AttackBuff>();
    }
    public int getAttack(){
        return Attack;
    }

    public int getArmor() {
        return Armor;
    }

    public List<ArmorBuff> getArmorBuffs() {
        return armorBuffs;
    }

    public List<AttackBuff> getAttackBuffs() {
        return attackBuffs;
    }
    public int getCriticalStrike() {
        return CriticalStrike;
    }

    public int getMissrate() {
        return Missrate;
    }
    public void newAttackBuff(int Attackchange, int Attackbufftime){
        AttackBuff att=new AttackBuff(Attackchange,Attackbufftime);
        attackBuffs.add(att);
    }
    public void newArmorBuff(int Armorchange,int Armorbufftime){
        ArmorBuff arm=new ArmorBuff(Armorchange,Armorbufftime);
        armorBuffs.add(arm);
    }
    public void newturn(){
        Iterator<AttackBuff> iter1=attackBuffs.iterator();
        while (iter1.hasNext()){
            AttackBuff a=iter1.next();
            if(a.Attackbuffoverdue()){
                iter1.remove();
            }
        }
        Iterator<ArmorBuff>iter2=armorBuffs.iterator();
        while (iter2.hasNext()){
            ArmorBuff a=iter2.next();
            if(a.Armorbuffoverdue()){
                iter2.remove();
            }
        }
    }
}
