package com.example.healthcertification.ui.MyActivity_Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaloryCalculate {

    private boolean first_step = false;
    private boolean second_step = false;
    private boolean third_step = false;
    private boolean forth_step = false;
    private boolean fifth_step = false;
    private boolean sixth_step = false;
    private boolean seventh_step = false;
    private String Starttime = null;
    private String Endtime = null;


    public void Calory(float speed) throws ParseException {
        SimpleDateFormat transFormat = new SimpleDateFormat("a hh:mm:ss");
        long duration = 0;
        long weight = 80;
        long calory = 0;
        FileStore fileStore = new FileStore();

        if(speed<=1.57&&speed>1) {
            first_step = true;
            if(Starttime == null && second_step == false && third_step == false && forth_step == false && fifth_step == false && sixth_step == false && seventh_step == false)
                Starttime = CurrentTime();
            else if(second_step || third_step || forth_step || fifth_step || sixth_step || seventh_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(second_step){
                    calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                    second_step = false;
                }else if(third_step){
                    calory = (long) (weight * duration * 5 * 17.5/60000000);
                    third_step = false;
                }else if(forth_step){
                    calory = (long) (weight * duration * 8 * 17.5/60000000);
                    forth_step = false;
                }else if(fifth_step){
                    calory = (long) (weight * duration * 10 * 17.5/60000000);
                    fifth_step = false;
                }else if(sixth_step){
                    calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                    sixth_step = false;
                }else if(seventh_step){
                    calory = (long) (weight * duration * 14 * 17.5/60000000);
                    seventh_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if(speed<=1.78&&speed>1.57) {
            second_step = true;
            if(Starttime == null && first_step == false && third_step == false && forth_step == false && fifth_step == false && sixth_step == false && seventh_step == false)
                Starttime = CurrentTime();
            else if(first_step || third_step || forth_step || fifth_step || sixth_step || seventh_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(first_step){
                    calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                    first_step = false;
                }else if(third_step){
                    calory = (long) (weight * duration * 5 * 17.5/60000000);
                    third_step = false;
                }else if(forth_step){
                    calory = (long) (weight * duration * 8 * 17.5/60000000);
                    forth_step = false;
                }else if(fifth_step){
                    calory = (long) (weight * duration * 10 * 17.5/60000000);
                    fifth_step = false;
                }else if(sixth_step){
                    calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                    sixth_step = false;
                }else if(seventh_step){
                    calory = (long) (weight * duration * 14 * 17.5/60000000);
                    seventh_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if(speed<=2.23&&speed>1.78) {
            third_step = true;
            if(Starttime == null && first_step == false && second_step == false && forth_step == false && fifth_step == false && sixth_step == false && seventh_step == false)
                Starttime = CurrentTime();
            else if(first_step || second_step || forth_step || fifth_step || sixth_step || seventh_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(first_step){
                    calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                    first_step = false;
                }else if(second_step){
                    calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                    second_step = false;
                }else if(forth_step){
                    calory = (long) (weight * duration * 8 * 17.5/60000000);
                    forth_step = false;
                }else if(fifth_step){
                    calory = (long) (weight * duration * 10 * 17.5/60000000);
                    fifth_step = false;
                }else if(sixth_step){
                    calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                    sixth_step = false;
                }else if(seventh_step){
                    calory = (long) (weight * duration * 14 * 17.5/60000000);
                    seventh_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if(speed<=2.68&&speed>2.23) {
            forth_step = true;
            if(Starttime == null && first_step == false && second_step == false && third_step == false && fifth_step == false && sixth_step == false && seventh_step == false)
                Starttime = CurrentTime();
            else if(first_step || second_step || third_step || fifth_step || sixth_step || seventh_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(first_step){
                    calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                    first_step = false;
                }else if(second_step){
                    calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                    second_step = false;
                }else if(third_step){
                    calory = (long) (weight * duration * 5 * 17.5/60000000);
                    third_step = false;
                }else if(fifth_step){
                    calory = (long) (weight * duration * 10 * 17.5/60000000);
                    fifth_step = false;
                }else if(sixth_step){
                    calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                    sixth_step = false;
                }else if(seventh_step){
                    calory = (long) (weight * duration * 14 * 17.5/60000000);
                    seventh_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if(speed<=3.05&&speed>2.68) {
            fifth_step = true;
            if(Starttime == null && first_step == false && second_step == false && third_step == false && forth_step == false && sixth_step == false && seventh_step == false)
                Starttime = CurrentTime();
            else if(first_step || second_step || third_step || forth_step || sixth_step || seventh_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(first_step){
                    calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                    first_step = false;
                }else if(second_step){
                    calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                    second_step = false;
                }else if(third_step){
                    calory = (long) (weight * duration * 5 * 17.5/60000000);
                    third_step = false;
                }else if(forth_step){
                    calory = (long) (weight * duration * 8 * 17.5/60000000);
                    forth_step = false;
                }else if(sixth_step){
                    calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                    sixth_step = false;
                }else if(seventh_step){
                    calory = (long) (weight * duration * 14 * 17.5/60000000);
                    seventh_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if(speed<=3.62&&speed>3.05) {
            sixth_step = true;
            if(Starttime == null && first_step == false && second_step == false && third_step == false && forth_step == false && fifth_step == false && seventh_step == false)
                Starttime = CurrentTime();
            else if(first_step || second_step || third_step || forth_step || fifth_step || seventh_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(first_step){
                    calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                    first_step = false;
                }else if(second_step){
                    calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                    second_step = false;
                }else if(third_step){
                    calory = (long) (weight * duration * 5 * 17.5/60000000);
                    third_step = false;
                }else if(forth_step){
                    calory = (long) (weight * duration * 8 * 17.5/60000000);
                    forth_step = false;
                }else if(fifth_step){
                    calory = (long) (weight * duration * 10 * 17.5/60000000);
                    fifth_step = false;
                }else if(seventh_step){
                    calory = (long) (weight * duration * 14 * 17.5/60000000);
                    seventh_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if(speed<=4.17&&speed>3.62) {
            seventh_step = true;
            if(Starttime == null && first_step == false && second_step == false && third_step == false && forth_step == false && fifth_step == false && sixth_step == false)
                Starttime = CurrentTime();
            else if(first_step || second_step || third_step || forth_step || fifth_step || sixth_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(first_step){
                    calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                    first_step = false;
                }else if(second_step){
                    calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                    second_step = false;
                }else if(third_step){
                    calory = (long) (weight * duration * 5 * 17.5/60000000);
                    third_step = false;
                }else if(forth_step){
                    calory = (long) (weight * duration * 8 * 17.5/60000000);
                    forth_step = false;
                }else if(fifth_step){
                    calory = (long) (weight * duration * 10 * 17.5/60000000);
                    fifth_step = false;
                }else if(sixth_step){
                    calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                    sixth_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if((speed<=1.57||speed>4.17)&&Starttime!=null){
            Endtime = CurrentTime();
            duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
            if(first_step){
                calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                first_step = false;
            }else if(second_step){
                calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                second_step = false;
            }else if(third_step){
                calory = (long) (weight * duration * 5 * 17.5/60000000);
                third_step = false;
            }else if(forth_step){
                calory = (long) (weight * duration * 8 * 17.5/60000000);
                forth_step = false;
            }else if(fifth_step){
                calory = (long) (weight * duration * 10 * 17.5/60000000);
                sixth_step = false;
            }else if(sixth_step){
                calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                sixth_step = false;
            }else if(seventh_step){
                calory = (long) (weight * duration * 14 * 17.5/60000000);
                seventh_step = false;
            }
            fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
            Endtime = null;
            Starttime = null;
        }
    }

    private String CurrentTime() {
        Date today = new Date();
        SimpleDateFormat time = new SimpleDateFormat("a hh:mm:ss");
        return time.format(today);
    }

    private String CurrentDate() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        return date.format(today);
    }
}
