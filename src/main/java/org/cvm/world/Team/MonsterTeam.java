package org.cvm.world.Team;
import org.cvm.world.Buff.*;
import org.cvm.world.Character.*;
import org.cvm.world.Algorithm.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MonsterTeam {
    static final int MaxTeamSkillNumber=10;
    private int teamSkillNumber;
    static final int MaxTeamAcitonNumber=15;
    private int teamActionnumber;
    private Assault a;

    static List<Monster> list;
    MonsterTeam(){
        list=new ArrayList<Monster>();
        a=new Assault();
        Monster c1=new Monster(65,50,5,5,450,1);
        Monster c2=new Monster(35,70,5,7,350,2);
        Monster c3=new Monster(50,100,5,1,550,3);
        Monster c4=new Monster(60,60,5,2,400,4);
        Monster c5=new Monster(60,60,5,2,400,5);
        Monster c6=new Monster(40,65,5,7,400,6);
        Monster c7=new Monster(50,70,5,9,350,7);
        c1.posx=0;c1.posy=8;
        c2.posx=1;c2.posy=8;
        c3.posx=2;c3.posy=8;
        c4.posx=3;c4.posy=8;
        c5.posx=4;c5.posy=8;
        c6.posx=1;c6.posy=7;
        c7.posx=3;c7.posy=7;
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

    public int[] getallpostion(){
        int[] pos=new int[7];
        for(int i=0;i<list.size();i++){
            pos[i]=list.get(i).posx*9+list.get(i).posy;
        }
        return pos;
    }

    static void monsterDead(int No_x){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getNo_x()==No_x){
                list.remove(i);
                break;
            }
        }
    }

    public List<String> getinformation(int No_x){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getNo_x()==No_x){
                Monster c=list.get(i);
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

    public static void getattack(int No_x,int damage,Monster m){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getNo_x()==No_x){
                int HP=list.get(i).getHP();
                list.get(i).setHP(HP-damage);
                m=list.get(i);
                break;
            }
        }
    }

    public int[] moveup(int No_x){
        int[] a=new int[1];
        a[0]=-1;
        if(teamActionnumber<=0) return a;
        int i=0;
        Monster c=list.get(i);
        for(i=0;i< list.size();i++){
            if(list.get(i).getNo_x()==No_x) {
                c=list.get(i);
                break;
            }
        }
        if(i>=list.size()) return a;
        int x=c.posx;
        int y=c.posy;
        if(x-1<0) return a;
        if(CalabashbrotherTeam.haveCreature(x-1,y)||MonsterTeam.haveCreature(x-1,y)) return a;
        list.get(i).posx=x-1;
        teamActionnumber--;
        int[] arr=new int[3];
        arr[0]=1;//Team
        arr[1]=No_x;//monster number
        arr[2]=list.get(i).posx*9+list.get(i).posy;//position
        return arr;
    }
    public int[] movedown(int No_x){
        int[] a=new int[1];
        a[0]=-1;
        if(teamActionnumber<=0) return a;
        int i=0;
        Monster c=list.get(i);
        for(i=0;i< list.size();i++){
            if(list.get(i).getNo_x()==No_x) {
                c=list.get(i);
                break;
            }
        }
        if(i>=list.size()) return a;
        int x=c.posx;
        int y=c.posy;
        if(x+1>4) return a;
        if(CalabashbrotherTeam.haveCreature(x+1,y)||MonsterTeam.haveCreature(x+1,y)) return a;
        list.get(i).posx=x+1;
        teamActionnumber--;
        int[] arr=new int[3];
        arr[0]=1;//Team
        arr[1]=No_x;//monster number
        arr[2]=list.get(i).posx*9+list.get(i).posy;//position
        return arr;
    }
    public int[] moveleft(int No_x){
        int[] a=new int[1];
        a[0]=-1;
        if(teamActionnumber<=0) return a;
        int i=0;
        Monster c=list.get(i);
        for(i=0;i< list.size();i++){
            if(list.get(i).getNo_x()==No_x) {
                c=list.get(i);
                break;
            }
        }
        if(i>=list.size()) return a;
        int x=c.posx;
        int y=c.posy;
        if(y-1<0) return a;
        if(CalabashbrotherTeam.haveCreature(x,y-1)||MonsterTeam.haveCreature(x,y-1)) return a;
        list.get(i).posy=y-1;
        teamActionnumber--;
        int[] arr=new int[3];
        arr[0]=1;//Team
        arr[1]=No_x;//monster number
        arr[2]=list.get(i).posx*9+list.get(i).posy;//position
        return arr;
    }
    public int[] moveright(int No_x){
        int[] a=new int[1];
        a[0]=-1;
        if(teamActionnumber<=0) return a;
        int i=0;
        Monster c=list.get(i);
        for(i=0;i< list.size();i++){
            if(list.get(i).getNo_x()==No_x) {
                c=list.get(i);
                break;
            }
        }
        if(i>=list.size()) return a;
        int x=c.posx;
        int y=c.posy;
        if(y+1>8) return a;
        if(CalabashbrotherTeam.haveCreature(x,y+1)||MonsterTeam.haveCreature(x,y+1)) return a;
        list.get(i).posy=y+1;
        teamActionnumber--;
        int[] arr=new int[3];
        arr[0]=1;//Team
        arr[1]=No_x;//monster number
        arr[2]=list.get(i).posx*9+list.get(i).posy;//position
        return arr;
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
                list.get(i).newArmorBuff(armorchange, armorbufftime);
                return "Monster No."+No_x+" get Armor buff : change:"+ armorchange +" time:"+armorbufftime;
            }
        }
        return "";
    }
    public List<List<Integer>> Doattack(int No_x,boolean is_skill) {
        List<List<Integer>> A=new ArrayList<List<Integer>>();
        List<Integer> A_in=new ArrayList<Integer>();
        A_in.add(-1);
        A.add(A_in);
        List<List<Integer>> array=new ArrayList<List<Integer>>();
        int i = 0;
        for (i = 0; i < list.size(); i++) {
            if (list.get(i).getNo_x() == No_x) break;
        }
        if (i >= list.size()) return A;
        if(teamActionnumber<=0) return A;
        Monster c=list.get(i);
        if(is_skill && c.getSkillcost()>teamSkillNumber) return A;
        int x=c.posx;
        int y=c.posy;
        int[][] f=c.getField();
        List<Integer> entry=new ArrayList<Integer>();
        entry.add(2);//Team
        entry.add(No_x);//monster number
        if(is_skill) entry.add(2);
        else entry.add(1);//skill choice
        array.add(entry);
        if(is_skill) {
            for (int j = 0; j < f.length; j++) {
                if (CalabashbrotherTeam.haveCreature(x + f[j][0], y + f[j][1])) {
                    CalabashBrother m = CalabashbrotherTeam.getCal(x + f[j][0], y + f[j][1]);
                    Monster tmp=c;
                    tmp.setAttack(c.getSkillnumber());
                    int damage = a.DamageCaculate(tmp, m);
                    if (damage == -1){
                        entry=new ArrayList<Integer>();
                        entry.add(1);//mean attack
                        entry.add(m.getNo_x());//borther number
                        entry.add(damage);//damage,-1 equals miss;
                    }
                    else{
                        CalabashbrotherTeam.getattack(m.getNo_x(),damage,m);
                        entry=new ArrayList<Integer>();
                        entry.add(1);//mean attack
                        entry.add(m.getNo_x());//brother number
                        entry.add(damage);//damage
                        entry.add(m.getHP());//HP after damage
                        entry.add(m.getMAXHP());//Max HP
                        array.add(entry);
                        if(m.getHP()<=0) CalabashbrotherTeam.calabashbrotherDead(m.getNo_x());
                    }
                }
            }
            switch (No_x) {
                case 1:{
                    for(int j=0;j<f.length;j++){
                        int armorchange=-(int)(c.getArmor()*0.7);
                        if(CalabashbrotherTeam.haveCreature(x+f[j][0],y+f[j][1])){
                            CalabashBrother m = CalabashbrotherTeam.getCal(x + f[j][0], y + f[j][1]);
                            CalabashbrotherTeam.getArmorBuff(m.getNo_x(),armorchange,c.getSkillbufftime());
                            entry=new ArrayList<Integer>();
                            entry.add(2);//mean buff
                            entry.add(1);//mean team
                            entry.add(m.getNo_x());//creature number
                            entry.add(2);//mean attack(1)/armor(2) buff
                            entry.add(armorchange);
                            entry.add(c.getSkillbufftime());
                            array.add(entry);
                        }
                    }
                    break;
                }
                case 2: {
                    int attackchange=-(int)(c.getAttack()*0.4);
                    List<Integer> arr=new ArrayList<Integer>();
                    for(int j=1;j<=MonsterTeam.list.size();j++)
                        arr.add(j);
                    Collections.shuffle(arr);
                    for(int j=0;j<3&&j<arr.size();j++){
                        CalabashbrotherTeam.getAttackBuff(arr.get(j),attackchange,c.getSkillbufftime());
                        entry=new ArrayList<Integer>();
                        entry.add(2);//mean buff
                        entry.add(1);//mean team
                        entry.add(list.get(arr.get(j)).getNo_x());//creature number
                        entry.add(1);//mean attack(1)/armor(2) buff
                        entry.add(attackchange);
                        entry.add(c.getSkillbufftime());
                        array.add(entry);
                    }
                    break;
                }
                case 3: {
                    int armorchange=(int)(c.getArmor()*0.2);
                    list.get(i).newArmorBuff(armorchange,c.getSkillbufftime());
                    entry=new ArrayList<Integer>();
                    entry.add(2);//mean buff
                    entry.add(2);//mean team
                    entry.add(list.get(i).getNo_x());//creature number
                    entry.add(2);//mean attack(1)/armor(2) buff
                    entry.add(armorchange);
                    entry.add(c.getSkillbufftime());
                    array.add(entry);
                    break;
                }
                case 4: break;
                case 5: {
                    int attackchange=(int)(c.getAttack()*0.3);
                    list.get(i).newAttackBuff(attackchange,c.getSkillbufftime());
                    entry=new ArrayList<Integer>();
                    entry.add(2);//mean buff
                    entry.add(2);//mean team
                    entry.add(list.get(i).getNo_x());//creature number
                    entry.add(1);//mean attack(1)/armor(2) buff
                    entry.add(attackchange);
                    entry.add(c.getSkillbufftime());
                    array.add(entry);
                    break;
                }
                case 6: {
                    int armorchange=20;
                    for(int j=0;j<list.size();j++){
                        list.get(j).newArmorBuff(armorchange,c.getSkillbufftime());
                        entry=new ArrayList<Integer>();
                        entry.add(2);//mean buff
                        entry.add(2);//mean team
                        entry.add(list.get(j).getNo_x());//creature number
                        entry.add(2);//mean attack(1)/armor(2) buff
                        entry.add(armorchange);
                        entry.add(c.getSkillbufftime());
                        array.add(entry);
                    }
                    break;
                }
                case 7: {
                    teamActionnumber+=3;
                    break;
                }
                default:
                    break;
            }
        }
        else {
            if (CalabashbrotherTeam.haveCreature(c.posx, c.posy-1)) {
                CalabashBrother m = CalabashbrotherTeam.getCal(c.posx , c.posy-1);
                int damage = a.DamageCaculate(c, m);
                if (damage == -1){
                    entry=new ArrayList<Integer>();
                    entry.add(1);//mean attack
                    entry.add(m.getNo_x());//borther number
                    entry.add(damage);//damage,-1 equals miss;
                }
                else{
                    CalabashbrotherTeam.getattack(m.getNo_x(),damage,m);
                    entry=new ArrayList<Integer>();
                    entry.add(1);//mean attack
                    entry.add(m.getNo_x());//brother number
                    entry.add(damage);//damage
                    entry.add(m.getHP());//HP after damage
                    entry.add(m.getMAXHP());//Max HP
                    array.add(entry);
                    if(m.getHP()<=0) CalabashbrotherTeam.calabashbrotherDead(m.getNo_x());
                }
            }
        }
        return array;
    }

}
