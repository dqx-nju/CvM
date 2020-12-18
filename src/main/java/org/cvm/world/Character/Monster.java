package org.cvm.world.Character;

public class Monster extends Creature{
    public int posx;
    public int posy;
    private int No_x;
    private String skillname;
    private int[][] field;
    private int skillnumber;
    private int skillcost;
    private int skillbufftime;
    public Monster(int attack,int armor,int criticalstrike,int missrate,int HP,int number){
        super(attack,armor,criticalstrike,missrate,HP);
        No_x=number;
        posx=-1;
        posy=-1;
        switch (number){
            case 1:{
                skillname="一";//对前方一名角色造成伤害并大幅度削弱正前方三格内所有敌方的护甲，持续一回合,减甲倍率0.7
                field=new int[][]{{0,1},{0,2},{0,3}};
                this.skillnumber=(int)(attack*1.5);
                skillbufftime=1;
                skillcost=3;
                break;
            }
            case 2:{
                skillname="二";//随机中幅度削弱敌方3人的物理攻击力一回合，减攻倍率为0.4
                field=new int[][]{{-1,1},{0,1},{1,1},{-1,2},{0,2},{1,2}};
                skillnumber=60;
                skillbufftime=1;
                skillcost=2;
                break;
            }
            case 3:{
                skillname="三";//小幅度削弱单体的攻击力，并小幅度提升自身防御力，持续两回合,倍率为0.2
                field=new int[][]{{0,1}};
                skillnumber=(int)(attack*0.8);
                skillbufftime=2;
                skillcost=2;
                break;
            }
            case 4:{
                skillname="四";//范围aoe伤害
                field=new int[][]{{1,1},{-1,1},{0,1},{0,2},{1,2},{-1,2}};
                skillnumber=(int)(attack*1.3);
                skillbufftime=0;
                skillcost=3;
                break;
            }
            case 5:{
                skillname="五";//范围aoe伤害，并中幅度提升自身攻击力，持续2会和
                field=new int[][]{{1,1},{-1,1},{0,1},{0,2},{1,2},{-1,2}};
                skillnumber=(int)(attack*1.2);
                skillbufftime=1;
                skillcost=3;
                break;
            }
            case 6:{
                skillname="六";//常规攻击，自身闪避率提升，持续两回合
                field=new int[][]{{0,1}};
                skillnumber=(int)(missrate*0.2);
                skillbufftime=2;
                skillcost=2;
                break;
            }
            case 7:{
                skillname="七";//使用技能后，我方恢复3点行动力
                field=new int[][]{{0,1}};
                skillnumber=(int)(attack*1.2);
                skillbufftime=1;
                skillcost=5;
                break;
            }
            default:break;
        }
    }

    public int getNo_x() {
        return No_x;
    }
    public int[][] getField() {
        return field;
    }
}
