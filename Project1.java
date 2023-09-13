//Arthur Hakobyan
//Project 1 Stable Marriage
//CS482
//2 October 2022

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Project1 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> mPref = new ArrayList<String>();
        ArrayList<String> fPref = new ArrayList<String>();
        ArrayList<String> currentMarriage = new ArrayList<String>();
        ArrayList<String> checkStable = new ArrayList<String>();
        ArrayList<String> marriageCheck = new ArrayList<String>();
        int nEdited = 0;
        File file = new File("src/input.txt");
        Scanner scan = new Scanner(file);
        int n = Integer.valueOf(scan.nextLine());

//Parses .txt file into appropriate arraylist variables.
        for(int i=0; i<n; i++)
        {
            mPref.add(scan.nextLine());
        }
        for(int i=0; i<n; i++)
        {
            fPref.add(scan.nextLine());
        }
        for(int i=0; i<n; i++)
        {
            currentMarriage.add(scan.nextLine());
        }
        //While marriage is unstable, loops variable through checkStability until stable matching is found
        checkStable = new ArrayList<String>(marriageCheck);
        marriageCheck = (checkStability(mPref, fPref, currentMarriage));
        while(!marriageCheck.contains("Stable") && nEdited < 100)
        {
            nEdited++;
            checkStable = marriageCheck;
            marriageCheck = (checkStability(mPref, fPref, marriageCheck));
        }
        System.out.println(nEdited);
        for(int i = 0; i< marriageCheck.size()-1; i++)
        {
            System.out.println(marriageCheck.get(i));
        }

    }
    public static ArrayList<String> checkStability(ArrayList<String> malePref, ArrayList<String> femPref, ArrayList<String> currentMarriage){
        int n = currentMarriage.size();
        int indexM = 0;
        int indexF = 0;
        int index = 0;
        boolean test = false;
        String marriage[];
        String mArr[];
        String fArr[];
        String fem;
        String male = "null";
        ArrayList<String> testMarriage = new ArrayList<String>(currentMarriage);
        boolean stable = true;

        for(int k = 0; k < n; k++) {
            if(stable){


                //Split couple
                marriage = currentMarriage.get(k).split(" ");
                //Split male
                mArr = malePref.get(k).split(" ");
                //Find index of current partner preference in male pref.
                indexM = Arrays.asList(mArr).indexOf(marriage[1]);


                //Check Male pref prefer female over their own partners

                for (int i = 0; i < indexM; i++) {
                    //Female with higher rank than current partner
                    fem = mArr[i];
                    for (int j = 0; j < n; j++) {
                        //Get female current partner
                        if (((currentMarriage.get(j)).substring(1).indexOf(fem)) > 0) {
                            male = String.valueOf(j + 1);
                        }
                    }
                    //Check if M preferred female prefers M over current husband
                    if ((femPref.get(Integer.parseInt(fem) - 1)).indexOf(marriage[0]) < (femPref.get(Integer.parseInt(fem) - 1)).indexOf(male)) {
                            if(stable) {
                                stable = false;
                                //Switches partners if marriage is unstable
                                testMarriage.remove(Integer.parseInt(marriage[0])-1);
                                testMarriage.add(Integer.parseInt(marriage[0])-1, marriage[0] + " " + fem);
                                index = Integer.parseInt(male) - 1;
                                testMarriage.remove(index);
                                testMarriage.add(index, male + " " + marriage[1]);
                            }
                    }

                }

                fArr = femPref.get(Integer.parseInt(marriage[1]) - 1).split(" ");
                indexF = Arrays.asList(fArr).indexOf(marriage[0]);

                for (int i = 0; i < indexF; i++) {
                    //Male with higher rank than current partner
                    male = fArr[i];
                    //That male's partner
                    fem = currentMarriage.get(Integer.parseInt(male) - 1).substring(2);

                    if ((malePref.get(Integer.parseInt(male) - 1)).indexOf(marriage[1]) < (malePref.get(Integer.parseInt(male) - 1)).indexOf(fem)) {
                        //Switches partners if marriage is unstable
                        if(stable) {
                        index = Integer.parseInt(male)-1;
                        testMarriage.remove(index);
                        testMarriage.add(index, male + " " + marriage[1]);
                        testMarriage.remove(Integer.parseInt(marriage[0])-1);
                        testMarriage.add(Integer.parseInt(marriage[0])-1, marriage[0] + fem);
                            stable = false;
                        }
                    }

                }
            }
        }

//If marriage is stable, function adds string "Stable" at the end of ArrayList and returns it to the main function.
        //Main function checks if Arraylist contains string "Stable". The main function loops CheckStability until "Stable" is found
        //or the loop has been exceeded 100x.
        if(stable == true)
        {
            testMarriage.add("Stable");
        }
        else {
        }
        return testMarriage;
    }
}
