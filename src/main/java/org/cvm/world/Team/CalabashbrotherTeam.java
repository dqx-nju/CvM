package org.cvm.world.Team;
import org.cvm.world.Buff.*;
import org.cvm.world.Character.*;
import org.cvm.world.Algorithm.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Collection;
public class CalabashbrotherTeam {
    static final int MaxTeamSkillNumber=10;
    private int teamSkillNumber;
    static final int MaxTeamAcitonNumber=15;
    private int teamActionnumber;
    static List<CalabashBrother> list;
    private Assault a;
    public CalabashbrotherTeam(){
        list=new ArrayList<CalabashBrother>();
        a=new Assault();
        CalabashBrother c1=new CalabashBrother(80,50,5,3,400,1);
        CalabashBrother c2=new CalabashBrother(40,40,5,7,300,2);
        CalabashBrother c3=new CalabashBrother(60,100,5,1,500,3);
        CalabashBrother c4=new CalabashBrother(70,60,5,3,350,4);
        CalabashBrother c5=new CalabashBrother(70,60,5,3,350,5);
        CalabashBrother c6=new CalabashBrother(55,55,5,10,350,6);
        CalabashBrother c7=new CalabashBrother(60,60,5,5,300,7);
        c1.posx=0;c1.posy=0;
        c2.posx=1;c2.posy=0;
        c3.posx=2;c3.posy=0;
        c4.posx=3;c4.posy=0;
        c5.posx=4;c5.posy=0;
        c6.posx=1;c6.posy=1;
        c7.posx=3;c7.posy=1;
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
        list.add(c5);
        list.add(c6);
        list.add(c7);
        teamActionnumber=MaxTeamAcitonNumber;
        teamSkillNumber=MaxTeamSkillNumber;
    }
    public void TeamNewTurn(){
        for(CalabashBrother c:list){
            c.newturn();
        }
        teamSkillNumber=MaxTeamSkillNumber;
        teamActionnumber=MaxTeamAcitonNumber;
    }
    public int[] getallpostion(){
        int[] pos=new int[7];
        for(int i=0;i<list.size();i++){
            pos[i]=list.get(i).posx*9+list.get(i).posy;
        }
        return pos;
    }

    public int getNo(int num){//返回葫芦娃参数No_X,范围1~7
        int x=num/9;//行
        int y=num%9;//列
        for(int i=0;i<list.size();i++){
            if(list.get(i).posx==x && list.get(i).posy==y){
                return list.get(i).getNo_x();
            }
        }
        return -1;
    }
    public List<String> getinformation(int No_x){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getNo_x()==No_x){
                CalabashBrother c=list.get(i);
                List<String> output=new ArrayList<String>();
                output.add("HP/MAXHP: "+c.getHP()+" / "+c.getMAXHP());
                output.add("criticalstrike: "+c.getCriticalStrike());
                output.add("missrate: "+c.getMissrate());
                output.add("attack: "+c.getAttack());
                output.add("skillname: "+c.getSkillname());
                output.add("skilldescription: "+ c.getSkilldescription());
                output.add("skillnumber: "+c.getSkillnumber());
                output.add("skillcost: " + c.getSkillcost());
                output.add("attackbuffs:");
                List<AttackBuff> att=c.getAttackBuffs();
                for(int j=0;j<att.size();j++){
                    output.add("buffnumber: "+att.get(i).getAttackChange()+" bufftime: "+att.get(i).getAttackbufftime());
                }
                output.add("armorbuffs:");
                List<ArmorBuff> arm=c.getArmorBuffs();
                for(int j=0;j<arm.size();j++)
                    output.add("buffnumber: "+arm.get(i).getArmorChange()+" bufftime: "+arm.get(i).getArmorbufftime());
                return output;
                }
            }
        List<String> output=new ArrayList<String>();
        return output;
    }

    public String moveup(int No_x){
        if(teamActionnumber<=0) return "";
        int i=0;
        CalabashBrother c=list.get(i);
        for(i=0;i< list.size();i++){
            if(list.get(i).getNo_x()==No_x) {
                c=list.get(i);
                break;
            }
        }
        if(i>=list.size()) return "";
        int x=c.posx;
        int y=c.posy;
        if(x-1<0) return "";
        //if(CalabashbrotherTeam.haveCreature(x-1,y)||MonsterTeam.haveCreature(x-1,y)) return "";
        list.get(i).posx=x-1;
        teamActionnumber--;
        return "CalabashBrother "+No_x+" move to position: ["+list.get(i).posx+","+list.get(i).posy+"]";
    }
    public String movedown(int No_x){
        if(teamActionnumber<=0) return "";
        int i=0;
        CalabashBrother c=list.get(i);
        for(i=0;i< list.size();i++){
            if(list.get(i).getNo_x()==No_x) {
                c=list.get(i);
                break;
            }
        }
        if(i>=list.size()) return "";
        int x=c.posx;
        int y=c.posy;
        if(x+1>4) return "";
        //if(CalabashbrotherTeam.haveCreature(x+1,y)||MonsterTeam.haveCreature(x+1,y)) return "";
        list.get(i).posx=x+1;
        teamActionnumber--;
        return "CalabashBrother "+No_x+" move to position: ["+list.get(i).posx+","+list.get(i).posy+"]";
    }
    public String moveleft(int No_x){
        if(teamActionnumber<=0) return "";
        int i=0;
        CalabashBrother c=list.get(i);
        for(i=0;i< list.size();i++){
            if(list.get(i).getNo_x()==No_x) {
                c=list.get(i);
                break;
            }
        }
        if(i>=list.size()) return "";
        int x=c.posx;
        int y=c.posy;
        if(y-1<0) return "";
        //if(CalabashbrotherTeam.haveCreature(x,y-1)||MonsterTeam.haveCreature(x,y-1)) return "";
        list.get(i).posy=y-1;
        teamActionnumber--;
        return "CalabashBrother "+No_x+" move to position: ["+list.get(i).posx+","+list.get(i).posy+"]";
    }
    public String moveright(int No_x){
        if(teamActionnumber<=0) return "";
        int i=0;
        CalabashBrother c=list.get(i);
        for(i=0;i< list.size();i++){
            if(list.get(i).getNo_x()==No_x) {
                c=list.get(i);
                break;
            }
        }
        if(i>=list.size()) return "";
        int x=c.posx;
        int y=c.posy;
        if(y+1>8) return "";
        //if(CalabashbrotherTeam.haveCreature(x,y+1)||MonsterTeam.haveCreature(x,y+1)) return "";
        list.get(i).posy=y+1;
        teamActionnumber--;
        return "CalabashBrother "+No_x+" move to position: ["+list.get(i).posx+","+list.get(i).posy+"]";
    }
    public static boolean haveCreature(int x,int y){
        for(int i=0;i<list.size();i++){
            if(list.get(i).posx==x && list.get(i).posy==y){
                return true;
            }
        }
        return false;
    }
    public static String getAttackBuff(int No_x,int attackchange,int attackbufftime){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getNo_x()==No_x){
                list.get(i).newAttackBuff(attackchange, attackbufftime);
                return "CalabashBrother "+No_x+" get Attack buff : change:"+ attackchange +" time:"+attackbufftime;
            }
        }
        return "";
    }
    public static String getArmorBuff(int No_x,int armorchange,int armorbufftime){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getNo_x()==No_x){
                list.get(i).newAttackBuff(armorchange, armorbufftime);
                return "CalabashBrother "+No_x+" get Armor buff : change:"+ armorchange +" time:"+armorbufftime;
            }
        }
        return "";
    }
    public List<String> Doattack(int No_x,boolean is_skill) {
        List<String> strs=new ArrayList<String>();
        int i = 0;
        for (i = 0; i < list.size(); i++) {
            if (list.get(i).getNo_x() == No_x) break;
        }
        if (i >= list.size()) return strs;
        if(teamActionnumber<=0) return strs;
        CalabashBrother c=list.get(i);
        if(is_skill && c.getSkillcost()>teamSkillNumber) return strs;
        int x=c.posx;
        int y=c.posy;
        int[][] f=c.getField();
        if(is_skill) strs.add("Calabashbrother "+No_x+" launch skill:"+ c.getSkillname());
        else strs.add("Calabashbrother "+No_x+" do normal attack");
        if(is_skill) {
            for (int j = 0; j < f.length; j++) {
                if (MonsterTeam.haveCreature(x + f[j][0], y + f[j][1])) {
                    Monster m = MonsterTeam.getMon(x + f[j][0], y + f[j][1]);
                    CalabashBrother tmp=c;
                    tmp.setAttack(c.getSkillnumber());
                    int damage = a.DamageCaculate(tmp, m);
                    if (damage == -1)
                        strs.add("Calabashbrother " + No_x + " attack Monster " + m.getNo_x() + " Miss");
                    else{
                        MonsterTeam.getattack(m.getNo_x(),damage,m);
                        strs.add("Calabashbrother " + No_x + " attack Monster " + m.getNo_x() + " Damage:" + damage+"Monster HP/MaxHP:"+ m.getHP()+" / "+m.getMAXHP());
                        if(m.getHP()<=0) MonsterTeam.monsterDead(m.getNo_x());
                    }
                }
            }
            switch (No_x) {
                case 1:break;
                case 2: {
                    for(int j=0;j<list.size();j++){
                        list.get(j).newAttackBuff(60,c.getSkillbufftime());
                    }
                    break;
                }
                case 3: {
                    list.get(i).newArmorBuff((int)(c.getArmor()*0.3),c.getSkillbufftime());
                    break;
                }
                case 4: break;
                case 5: {
                    for (int j = 0; j < f.length; j++) {
                        if (MonsterTeam.haveCreature(x + f[j][0], y + f[j][1])) {
                            Monster m = MonsterTeam.getMon(x + f[j][0], y + f[j][1]);
                            MonsterTeam.getArmorBuff(m.getNo_x(),-(int)(c.getArmor()*0.2),c.getSkillbufftime());
                        }

                    }
                }
                case 6: {
                    for(int j=0;j<list.size();j++){
                        list.get(j).newArmorBuff(15,c.getSkillbufftime());
                    }
                }
                case 7: {
                    List<Integer> arr=new ArrayList<Integer>();
                    for(int j=1;j<=MonsterTeam.list.size();j++)
                        arr.add(j);
                    Collections.shuffle(arr);
                    for(int j=0;j<2;j++)
                    {
                        MonsterTeam.getArmorBuff(j,-(int)(c.getArmor()*0.7),c.getSkillbufftime());
                    }
                }
                default:
                    break;
            }
        }
        else {
            if(MonsterTeam.haveCreature(c.posx,c.posy+1)){
                Monster m=MonsterTeam.getMon(c.posx,c.posy+1);
                int damage=a.DamageCaculate(c,m);
                if (damage == -1)
                    strs.add("Calabashbrother " + No_x + " attack Monster " + m.getNo_x() + " Miss");
                else{
                    MonsterTeam.getattack(m.getNo_x(),damage,m);
                    strs.add("Calabashbrother " + No_x + " attack Monster " + m.getNo_x() + " Damage:" + damage+"Monster HP/MaxHP:"+ m.getHP()+" / "+m.getMAXHP());
                    if(m.getHP()<=0) MonsterTeam.monsterDead(m.getNo_x());
                }
            }
        }
        return strs;
    }
}
