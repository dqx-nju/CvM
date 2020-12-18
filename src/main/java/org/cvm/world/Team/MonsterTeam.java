package org.cvm.world.Team;
import org.cvm.world.Buff.*;
import org.cvm.world.Character.*;
import org.cvm.world.Algorithm.*;
import java.util.ArrayList;
import java.util.List;

public class MonsterTeam {
    static final int MaxTeamSkillNumber=10;
    private int teamSkillNumber;
    static final int MaxTeamAcitonNumber=10;
    private int teamActionnumber;
    static List<Monster> list;
    MonsterTeam(){
        list=new ArrayList<Monster>();
        Monster c1=new Monster(80,50,5,3,400,1);
        Monster c2=new Monster(40,40,5,7,300,2);
        Monster c3=new Monster(60,100,5,1,500,3);
        Monster c4=new Monster(70,60,5,3,350,4);
        Monster c5=new Monster(70,60,5,3,350,5);
        Monster c6=new Monster(55,55,5,10,350,6);
        Monster c7=new Monster(60,60,5,5,300,7);
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
        for(Monster c:list){
            c.newturn();
        }
        teamSkillNumber=MaxTeamSkillNumber;
        teamActionnumber=MaxTeamAcitonNumber;
    }
    public static Monster getMon(int x, int y) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).posx == x && list.get(i).posy == y) {
                Monster c = list.get(i);
                return c;
            }
        }
        Monster m=new Monster(0,0,0,0,0,0);
        return m;
    }

    public int getNo(int num){//返回葫芦娃参数No_X,范围1~7
        int x=num%9;//行
        int y=num-x;//列
        for(int i=0;i<list.size();i++){
            if(list.get(i).posx==x && list.get(i).posy==y){
                return list.get(i).getNo_x();
            }
        }
        return -1;
    }

    public String moveup(int No_x){
        int i=0;
        Monster c=list.get(i);
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
        if(CalabashbrotherTeam.haveCreature(x-1,y)||MonsterTeam.haveCreature(x-1,y)) return "";
        else list.get(i).posx=x-1;
        return "Monster No."+No_x+" move to position: ["+list.get(i).posx+","+list.get(i).posy+"]";
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
                return "Monster No."+No_x+" get Attack buff : change:"+ attackchange +" time:"+attackbufftime;
            }
        }
        return "";
    }
    public static String getArmorBuff(int No_x,int armorchange,int armorbufftime){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getNo_x()==No_x){
                list.get(i).newAttackBuff(armorchange, armorbufftime);
                return "Monster No."+No_x+" get Armor buff : change:"+ armorchange +" time:"+armorbufftime;
            }
        }
        return "";
    }
}
