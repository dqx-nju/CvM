package org.cvm.world.Character;

public class CalabashBrother extends Creature{
    public int posx;
    public int posy;
    private String skillname;
    private int[][] field;
    private int skillnumber;
    private int skillbufftime;
    CalabashBrother(int attack,int armor,int criticalstrike,int missrate,int number,int skillnumber){
        super(attack,armor,criticalstrike,missrate);
        switch (number){
            case 1:{
                skillname="一";
                field=new int[1][2];
                field[0][0]=0;
                field[0][1]=1;
                this.skillnumber=attack;
                break;
            }
            case 2:{
                skillname="二";
                field=new int[][]{{-1,1},{0,1},{1,1},{-1,2},{0,2},{1,2}};
                skillnumber=60;
                skillbufftime=3;
                break;
            }
            case 3:break;
            case 4:break;
            case 5:break;
            case 6:break;
            case 7:break;
            default:break;
        }
    }

}
