import java.util.concurrent.TimeUnit;

public class Ai {
	
	private int move = 0;

	public void scoretracker(String[][] pos, String xoro){
        //2d array to store scores for computer
        int[][] score = new int[8][8];
        double[][] addedscore = new double[8][8];
        //temporary array to calculate move ahead
        String[][] temparray = new String[8][8];
        //each index is the difference between points won and points lost on the next move
        double[][] diffscore = new double[8][8];
        //sets every space in 2d array of score and addedscore are given their respective original scores
        for (int i = 0; i < 8; i++){
            for (int e = 0; e < 8; e++){
                score[i][e] = -100;
            }
        }
        for (int i = 0; i < 8; i++){
            for (int e = 0; e < 8; e++){
                addedscore[i][e] = 0;
            }
        }

        //scans every direction and calculates points won
        computerscan(pos, score, xoro);
        //takes into account better moves that may not get as many points
        scorecount(score, addedscore, pos, xoro);
        //calculates difference between current possible points won and the maximum points lost on the next move as a result of each possible move
        pointdiff(score, pos, addedscore, temparray, diffscore);

        //finds the largest difference in score
        double highestscore = -100;
        int index1 = 0;
        int index2 = 0;
        for (int a = 0; a < 8; a++){
            for (int b = 0; b < 8; b++) {
                if (diffscore[a][b] > highestscore){
                    highestscore = diffscore[a][b];
                    index1 = a;
                    index2 = b;
                }
            }
        }
        int gameover = 0;
        //gameover checker
        if (highestscore == -100){
        	gameover = 1;
        	Gameplay g = new Gameplay(1);
        	g.aiimplementation(pos, 0, 0, gameover);
        }

  
        move = 1;
        //input highest scoring move
        compinput(pos, index1, index2, xoro, gameover);
        
        

    }
    public void computerscan(String[][] pos, int[][] score, String xoro){
        //resets score to all -100 just in case
        for (int i = 0; i < 8; i++){
            for (int e = 0; e < 8; e++){
                score[i][e] = -100;
            }
        }

        for (int index1 = 0; index1 < 8; index1++) {
            for (int index2 = 0; index2 < 8; index2++) {
                if ((pos[index1][index2] == " ") || (pos[index1][index2] == ".")) {
                    int possiblemove = 0;

                    // scans north
                    int ncount = 0;
                    int nrand = 0;
                    for (int a = index2+2; a <= 7; a++){
                        if(pos[index1][a] == xoro) {
                            ncount = a;
                            nrand = 1;
                            break;
                        }
                    }
                    int nEmptyOrXCount = 2;
                    if (nrand > 0){
                        for (int b = index2+1; b < ncount; b++ ){
                            if ((pos[index1][b] == " ") || (pos[index1][b] == xoro) || (pos[index1][b] == ".")){
                                nEmptyOrXCount = 1;
                                break;
                            } else {
                                nEmptyOrXCount = 0;
                            }
                        }
                    }
                    if (nEmptyOrXCount == 0){
                        for (int b = index2+1; b < ncount; b++ ){
                            possiblemove++;
                        }
                    }

                    // scans south
                    int scount = 0;
                    int srand = 0;
                    for (int a = index2-2; a >= 0; a--){
                        if(pos[index1][a] == xoro) {
                            scount = a;
                            srand = 1;
                            break;
                        }
                    }
                    int sEmptyOrXCount = 2;
                    if (srand > 0){
                        for (int b = index2-1; b > scount; b-- ){
                            if ((pos[index1][b] == " ") || (pos[index1][b] == xoro) || (pos[index1][b] == ".")){
                                sEmptyOrXCount = 1;
                                break;
                            } else {
                                sEmptyOrXCount = 0;
                            }
                        }
                    }
                    if (sEmptyOrXCount == 0){
                        for (int b = index2-1; b > scount; b-- ){
                            possiblemove++;
                        }
                    }

                    // scans east
                    int ecount = 0;
                    int erand = 0;
                    for (int a = index1+2; a <= 7; a++){
                        if(pos[a][index2] == xoro) {
                            ecount = a;
                            erand = 1;
                            break;
                        }
                    }
                    int eEmptyOrXCount = 2;
                    if (erand > 0){
                        for (int b = index1+1; b < ecount; b++ ){
                            if ((pos[b][index2] == " ") || (pos[b][index2] == xoro) || (pos[b][index2] == ".")){
                                eEmptyOrXCount = 1;
                                break;
                            } else {
                                eEmptyOrXCount = 0;
                            }
                        }
                    }
                    if (eEmptyOrXCount == 0){
                        for (int b = index1+1; b < ecount; b++ ){
                            possiblemove++;
                        }
                    }

                    // scans west
                    int wcount = 0;
                    int wrand = 0;
                    for (int a = index1-2; a >= 0; a--){
                        if(pos[a][index2] == xoro) {
                            wcount = a;
                            wrand = 1;
                            break;
                        }
                    }
                    int wEmptyOrXCount = 2;
                    if (wrand > 0){
                        for (int b = index1-1; b > wcount; b-- ){
                            if ((pos[b][index2] == " ") || (pos[b][index2] == xoro) || (pos[b][index2] == ".")){
                                wEmptyOrXCount = 1;
                                break;
                            } else {
                                wEmptyOrXCount = 0;
                            }
                        }
                    }
                    if (wEmptyOrXCount == 0){
                        for (int b = index1-1; b > wcount; b-- ){
                            possiblemove++;
                        }
                    }

                    // scans northeast
                    if ((index1 <= 5) && (index2 <= 5)) {
                        int necount1a = index1 + 2;
                        int necount2a = index2 + 2;
                        int nehasx = 0;
                        for (int a = necount1a; a <= 7; a++) {
                            if ((pos[a][necount2a]) == xoro) {
                                necount1a = a;
                                nehasx = 1;
                                break;
                            }
                            if (necount2a == 7) {
                                break;
                            }
                            necount2a++;
                        }
                        if (nehasx == 1) {
                            int necount1b = index1 + 1;
                            int necount2b = index2 + 1;
                            int neEmptyOrXCount = 2;
                            for (int b = necount1b; b < necount1a; b++) {
                                if ((pos[b][necount2b] == " ") || (pos[b][necount2b] == xoro) || (pos[b][necount2b] == ".")) {
                                    neEmptyOrXCount = 1;
                                    break;
                                } else {
                                    neEmptyOrXCount = 0;
                                }
                                if (necount2b == necount2a - 1) {
                                    break;
                                }
                                necount2b++;
                            }
                            necount1b = index1 + 1;
                            necount2b = index2 + 1;
                            if (neEmptyOrXCount == 0) {
                                for (int b = necount1b; b < necount1a; b++) {
                                    possiblemove++;
                                    if (necount2b == necount2a - 1) {
                                        break;
                                    }
                                    necount2b++;
                                }
                            }
                        }
                    }

                    // scans northwest
                    if ((index1 >= 2) && (index2 <= 5)) {
                        int nwcount1a = index1 - 2;
                        int nwcount2a = index2 + 2;
                        int nwhasx = 0;
                        for (int a = nwcount1a; a >= 0; a--) {
                            if ((pos[a][nwcount2a]) == xoro) {
                                nwcount1a = a;
                                nwhasx = 1;
                                break;
                            }
                            if (nwcount2a == 7) {
                                break;
                            }
                            nwcount2a++;
                        }
                        if (nwhasx == 1) {
                            int nwcount1b = index1 - 1;
                            int nwcount2b = index2 + 1;
                            int nwEmptyOrXCount = 2;
                            for (int b = nwcount1b; b > nwcount1a; b--) {
                                if ((pos[b][nwcount2b] == " ") || (pos[b][nwcount2b] == xoro) || (pos[b][nwcount2b] == ".")) {
                                    nwEmptyOrXCount = 1;
                                    break;
                                } else {
                                    nwEmptyOrXCount = 0;
                                }
                                if (nwcount2b == nwcount2a - 1) {
                                    break;
                                }
                                nwcount2b++;
                            }
                            nwcount1b = index1 - 1;
                            nwcount2b = index2 + 1;
                            if (nwEmptyOrXCount == 0) {
                                for (int b = nwcount1b; b > nwcount1a; b--) {
                                    possiblemove++;
                                    if (nwcount2b == nwcount2a - 1) {
                                        break;
                                    }
                                    nwcount2b++;
                                }
                            }
                        }
                    }

                    //scans southeast
                    if ((index1 <= 5) && (index2 >= 2)) {
                        int secount1a = index1 + 2;
                        int secount2a = index2 - 2;
                        int sehasx = 0;
                        for (int a = secount1a; a <= 7; a++) {
                            if ((pos[a][secount2a]) == xoro) {
                                secount1a = a;
                                sehasx = 1;
                                break;
                            }
                            if (secount2a == 0) {
                                break;
                            }
                            secount2a--;
                        }
                        if (sehasx == 1) {
                            int secount1b = index1 + 1;
                            int secount2b = index2 - 1;
                            int seEmptyOrXCount = 2;
                            for (int b = secount1b; b < secount1a; b++) {
                                if ((pos[b][secount2b] == " ") || (pos[b][secount2b] == xoro) || (pos[b][secount2b] == ".")) {
                                    seEmptyOrXCount = 1;
                                    break;
                                } else {
                                    seEmptyOrXCount = 0;
                                }
                                if (secount2b == secount2a + 1) {
                                    break;
                                }
                                secount2b--;
                            }
                            secount1b = index1 + 1;
                            secount2b = index2 - 1;
                            if (seEmptyOrXCount == 0) {
                                for (int b = secount1b; b < secount1a; b++) {
                                    possiblemove++;
                                    if (secount2b == secount2a + 1) {
                                        break;
                                    }
                                    secount2b--;
                                }
                            }
                        }
                    }

                    // scans southwest
                    if ((index1 >= 2) && (index2 >= 2)) {
                        int swcount1a = index1 - 2;
                        int swcount2a = index2 - 2;
                        int swhasx = 0;
                        for (int a = swcount1a; a >= 0; a--) {
                            if ((pos[a][swcount2a]) == xoro) {
                                swcount1a = a;
                                swhasx = 1;
                                break;
                            }
                            if (swcount2a == 0) {
                                break;
                            }
                            swcount2a--;
                        }
                        if (swhasx == 1) {
                            int swcount1b = index1 - 1;
                            int swcount2b = index2 - 1;
                            int swEmptyOrXCount = 2;
                            for (int b = swcount1b; b > swcount1a; b--) {
                                if ((pos[b][swcount2b] == " ") || (pos[b][swcount2b] == xoro) || (pos[b][swcount2b] == ".")) {
                                    swEmptyOrXCount = 1;
                                    break;
                                } else {
                                    swEmptyOrXCount = 0;
                                }
                                if (swcount2b == swcount2a + 1) {
                                    break;
                                }
                                swcount2b--;
                            }
                            swcount1b = index1 - 1;
                            swcount2b = index2 - 1;
                            if (swEmptyOrXCount == 0) {
                                for (int b = swcount1b; b > swcount1a; b--) {
                                    possiblemove++;
                                    if (swcount2b == swcount2a + 1) {
                                        break;
                                    }
                                    swcount2b--;
                                }
                            }
                        }
                    }
                    if (possiblemove == 0){
                        possiblemove = -100;
                    }
                    //each index of score is given the number of possible flips (possible move num)
                    score[index1][index2] = possiblemove;
                }
            }
        }
    }
    public void scorecount(int[][] score, double[][] addedscore, String[][] pos, String xoro) {
        //every index of addedscore is set to its inital 0
        for (int i = 0; i < 8; i++){
            for (int e = 0; e < 8; e++){
                addedscore[i][e] = 0;
            }
        }

        //corners
        if (score[0][0] > 0) {
            addedscore[0][0] = 15;
        }
        if (score[0][7] > 0) {
            addedscore[0][7] = 15;
        }
        if (score[7][7] > 0) {
            addedscore[7][7] = 15;
        }
        if (score[7][0] > 0) {
            addedscore[7][0] = 15;
        }
        //westside
        for (int a = 0; a < 1; a++) {
            for (int b = 2; b < 6; b++) {
                if (score[a][b] > 0) {
                    addedscore[a][b] = 2;
                    if ((b == 2) || (b == 5)){
                        addedscore[a][b] = 2.5;
                    }
                }
            }
        }
        //westside interior
        for (int a = 2; a < 3; a++) {
            for (int b = 2; b < 6; b++) {
                if (score[a][b] > 0) {
                    addedscore[a][b] = 2;
                    if ((b == 2) || (b == 5)){
                        addedscore[a][b] = 2.5;
                    }
                }
            }
        }

        //northside
        for (int b = 7; b < 8; b++) {
            for (int a = 2; a < 6; a++) {
                if (score[a][b] > 0) {
                    addedscore[a][b] = 2;
                    if ((a == 2) || (a == 5)){
                        addedscore[a][b] = 2.5;
                    }
                }
            }
        }
        //northside interior
        for (int b = 5; b < 6; b++) {
            for (int a = 2; a < 6; a++) {
                if (score[a][b] > 0) {
                    addedscore[a][b] = 2;
                    if ((a == 2) || (a == 5)){
                        addedscore[a][b] = 2.5;
                    }
                }
            }
        }
        //eastside
        for (int a = 7; a < 8; a++) {
            for (int b = 2; b < 6; b++) {
                if (score[a][b] > 0) {
                    addedscore[a][b] = 2;
                    if ((b == 2) || (b == 5)){
                        addedscore[a][b] = 2.5;
                    }
                }
            }
        }
        //eastside interior
        for (int a = 5; a < 6; a++) {
            for (int b = 2; b < 6; b++) {
                if (score[a][b] > 0) {
                    addedscore[a][b] = 2;
                    if ((b == 2) || (b == 5)){
                        addedscore[a][b] = 2.5;
                    }
                }
            }
        }
        //southside
        for (int b = 0; b < 1; b++) {
            for (int a = 2; a < 6; a++) {
                if (score[a][b] > 0) {
                    addedscore[a][b] = 2;
                    if ((a == 2) || (a == 5)){
                        addedscore[a][b] = 2.5;
                    }
                }
            }
        }
        //southside interior
        for (int b = 2; b < 3; b++) {
            for (int a = 2; a < 6; a++) {
                if (score[a][b] > 0) {
                    addedscore[a][b] = 2;
                    if ((a == 2) || (a == 5)){
                        addedscore[a][b] = 2.5;
                    }
                }
            }
        }

        //opponent corner prevention
        if (pos[0][0] != xoro){
            addedscore[1][1] = -10;
            addedscore[0][1] = -3;
            addedscore[1][0] = -3;
        }
        if (pos[0][7] != xoro){
            addedscore[1][6] = -10;
            addedscore[0][6] = -3;
            addedscore[1][7] = -3;
        }
        if (pos[7][0] != xoro){
            addedscore[6][1] = -10;
            addedscore[7][1] = -3;
            addedscore[6][0] = -3;
        }
        if (pos[7][7] != xoro){
            addedscore[6][6] = -10;
            addedscore[7][6] = -3;
            addedscore[6][7] = -3;
        }
    }

    public void pointdiff(int[][] score, String[][] pos, double[][] addedscore, String[][] temparray, double[][] diffscore){
        //every index of diffscore is set to -100 initially
        for (int a = 0; a < 8; a++){
            for (int b = 0; b < 8; b++){
                diffscore[a][b] = -100;
            }
        }

        //temparray is given every value of pos (clones pos array)
        for (int a = 0; a < 8; a++){
            for (int b = 0; b < 8; b++){
                temparray[a][b] = pos[a][b];
            }
        }

        //temp score and tempaddedscore are clones of score and addedscore
        int[][] tempscore = new int[8][8];
        double[][] tempaddedscore = new double[8][8];
        for (int a = 0; a < 8; a++){
            for (int b = 0; b < 8; b++){
                tempscore[a][b] = score[a][b];
            }
        }
        for (int a = 0; a < 8; a++){
            for (int b = 0; b < 8; b++){
                tempaddedscore[a][b] = addedscore[a][b];
            }
        }

        System.out.println(" ");
        for (int a = 0; a < 8; a++){
            for (int b = 0; b < 8; b++) {
                double oscore = 0;
                double xhighestscore = -100;
                //System.out.println(score[a][b] + " " + addedscore[a][b]);
                if (score[a][b] > 0){
                    oscore = score[a][b] + addedscore[a][b];
                    compinput(temparray, a, b, "O", 0);
                    computerscan(temparray, tempscore, "X");
                    scorecount(tempscore, tempaddedscore, temparray, "X");
                    for (int c = 0; c < 8; c++){
                        for (int d = 0; d < 8; d++){
                            if (tempscore[c][d] + tempaddedscore[c][d] > xhighestscore){
                                xhighestscore = tempscore[c][d] + tempaddedscore[c][d];
                            }
                        }
                    }
                    diffscore[a][b] = oscore - xhighestscore;
                } else {
                    diffscore[a][b] = -100;
                    oscore = -100;
                    xhighestscore = 0;
                }
                //just for me to see calculations, delete later
                System.out.println("Whiteposscore - Blackhighestposscore (" + a + ", " + b + ")= " + oscore + " - " + xhighestscore + " = " + diffscore[a][b]);

                //resets each array
                for (int i = 0; i < 8; i++){
                    for (int e = 0; e < 8; e++){
                        tempscore[i][e] = score[i][e];
                    }
                }
                for (int j = 0; j < 8; j++){
                    for (int k = 0; k < 8; k++){
                        tempaddedscore[j][k] = addedscore[j][k];
                    }
                }
                for (int t = 0; t < 8; t++){
                    for (int v = 0; v < 8; v++){
                        temparray[t][v] = pos[t][v];
                    }
                }
            }
        }
    }

    public void compinput(String[][] pos, int index1, int index2, String xoro, int gameover){

        //inputs highest score coordinate as 'O'
        pos[index1][index2] = xoro;

        // scans north
        int ncount = 0;
        int nrand = 0;
        for (int a = index2+2; a <= 7; a++){
            if(pos[index1][a] == xoro) {
                ncount = a;
                nrand = 1;
                break;
            }
        }
        int nEmptyOrXCount = 2;
        if (nrand > 0){
            for (int b = index2+1; b < ncount; b++ ){
                if ((pos[index1][b] == " ") || (pos[index1][b] == xoro) || (pos[index1][b] == ".")){
                    nEmptyOrXCount = 1;
                    break;
                } else {
                    nEmptyOrXCount = 0;
                }
            }
        }
        if (nEmptyOrXCount == 0){
            for (int b = index2+1; b < ncount; b++ ){
                pos[index1][b] = xoro;
            }
        }

        // scans south
        int scount = 0;
        int srand = 0;
        for (int a = index2-2; a >= 0; a--){
            if(pos[index1][a] == xoro) {
                scount = a;
                srand = 1;
                break;
            }
        }
        int sEmptyOrXCount = 2;
        if (srand > 0){
            for (int b = index2-1; b > scount; b-- ){
                if ((pos[index1][b] == " ") || (pos[index1][b] == xoro) || (pos[index1][b] == ".")){
                    sEmptyOrXCount = 1;
                    break;
                } else {
                    sEmptyOrXCount = 0;
                }
            }
        }
        if (sEmptyOrXCount == 0){
            for (int b = index2-1; b > scount; b-- ){
                pos[index1][b] = xoro;
            }
        }

        // scans east
        int ecount = 0;
        int erand = 0;
        for (int a = index1+2; a <= 7; a++){
            if(pos[a][index2] == xoro) {
                ecount = a;
                erand = 1;
                break;
            }
        }
        int eEmptyOrXCount = 2;
        if (erand > 0){
            for (int b = index1+1; b < ecount; b++ ){
                if ((pos[b][index2] == " ") || (pos[b][index2] == xoro) || (pos[b][index2] == ".")){
                    eEmptyOrXCount = 1;
                    break;
                } else {
                    eEmptyOrXCount = 0;
                }
            }
        }
        if (eEmptyOrXCount == 0){
            for (int b = index1+1; b < ecount; b++ ){
                pos[b][index2] = xoro;
            }
        }

        // scans west
        int wcount = 0;
        int wrand = 0;
        for (int a = index1-2; a >= 0; a--){
            if(pos[a][index2] == xoro) {
                wcount = a;
                wrand = 1;
                break;
            }
        }
        int wEmptyOrXCount = 2;
        if (wrand > 0){
            for (int b = index1-1; b > wcount; b-- ){
                if ((pos[b][index2] == " ") || (pos[b][index2] == xoro) || (pos[b][index2] == ".")){
                    wEmptyOrXCount = 1;
                    break;
                } else {
                    wEmptyOrXCount = 0;
                }
            }
        }
        if (wEmptyOrXCount == 0){
            for (int b = index1-1; b > wcount; b-- ){
                pos[b][index2] = xoro;
            }
        }

        // scans northeast
        if ((index1 <= 5) && (index2 <= 5)) {
            int necount1a = index1 + 2;
            int necount2a = index2 + 2;
            int nehasx = 0;
            for (int a = necount1a; a <= 7; a++) {
                if ((pos[a][necount2a]) == xoro) {
                    necount1a = a;
                    nehasx = 1;
                    break;
                }
                if (necount2a == 7) {
                    break;
                }
                necount2a++;
            }
            if (nehasx == 1) {
                int necount1b = index1 + 1;
                int necount2b = index2 + 1;
                int neEmptyOrXCount = 2;
                for (int b = necount1b; b < necount1a; b++) {
                    if ((pos[b][necount2b] == " ") || (pos[b][necount2b] == xoro) || (pos[b][necount2b] == ".")) {
                        neEmptyOrXCount = 1;
                        break;
                    } else {
                        neEmptyOrXCount = 0;
                    }
                    if (necount2b == necount2a - 1) {
                        break;
                    }
                    necount2b++;
                }
                necount1b = index1 + 1;
                necount2b = index2 + 1;
                if (neEmptyOrXCount == 0) {
                    for (int b = necount1b; b < necount1a; b++) {
                        pos[b][necount2b] = xoro;

                        if (necount2b == necount2a - 1) {
                            break;
                        }
                        necount2b++;
                    }
                }
            }
        }

        // scans northwest
        if ((index1 >= 2) && (index2 <= 5)) {
            int nwcount1a = index1 - 2;
            int nwcount2a = index2 + 2;
            int nwhasx = 0;
            for (int a = nwcount1a; a >= 0; a--) {
                if ((pos[a][nwcount2a]) == xoro) {
                    nwcount1a = a;
                    nwhasx = 1;
                    break;
                }
                if (nwcount2a == 7) {
                    break;
                }
                nwcount2a++;
            }
            if (nwhasx == 1) {
                int nwcount1b = index1 - 1;
                int nwcount2b = index2 + 1;
                int nwEmptyOrXCount = 2;
                for (int b = nwcount1b; b > nwcount1a; b--) {
                    if ((pos[b][nwcount2b] == " ") || (pos[b][nwcount2b] == xoro) || (pos[b][nwcount2b] == ".")) {
                        nwEmptyOrXCount = 1;
                        break;
                    } else {
                        nwEmptyOrXCount = 0;
                    }
                    if (nwcount2b == nwcount2a - 1) {
                        break;
                    }
                    nwcount2b++;
                }
                nwcount1b = index1 - 1;
                nwcount2b = index2 + 1;
                if (nwEmptyOrXCount == 0) {
                    for (int b = nwcount1b; b > nwcount1a; b--) {
                        pos[b][nwcount2b] = xoro;
                        if (nwcount2b == nwcount2a - 1) {
                            break;
                        }
                        nwcount2b++;
                    }
                }
            }
        }

        //scans southeast
        if ((index1 <= 5) && (index2 >= 2)) {
            int secount1a = index1 + 2;
            int secount2a = index2 - 2;
            int sehasx = 0;
            for (int a = secount1a; a <= 7; a++) {
                if ((pos[a][secount2a]) == xoro) {
                    secount1a = a;
                    sehasx = 1;
                    break;
                }
                if (secount2a == 0) {
                    break;
                }
                secount2a--;
            }
            if (sehasx == 1) {
                int secount1b = index1 + 1;
                int secount2b = index2 - 1;
                int seEmptyOrXCount = 2;
                for (int b = secount1b; b < secount1a; b++) {
                    if ((pos[b][secount2b] == " ") || (pos[b][secount2b] == xoro) || (pos[b][secount2b] == ".")) {
                        seEmptyOrXCount = 1;
                        break;
                    } else {
                        seEmptyOrXCount = 0;
                    }
                    if (secount2b == secount2a + 1) {
                        break;
                    }
                    secount2b--;
                }
                secount1b = index1 + 1;
                secount2b = index2 - 1;
                if (seEmptyOrXCount == 0) {
                    for (int b = secount1b; b < secount1a; b++) {
                        pos[b][secount2b] = xoro;
                        if (secount2b == secount2a + 1) {
                            break;
                        }
                        secount2b--;
                    }
                }
            }
        }

        // scans southwest
        if ((index1 >= 2) && (index2 >= 2)) {
            int swcount1a = index1 - 2;
            int swcount2a = index2 - 2;
            int swhasx = 0;
            for (int a = swcount1a; a >= 0; a--) {
                if ((pos[a][swcount2a]) == xoro) {
                    swcount1a = a;
                    swhasx = 1;
                    break;
                }
                if (swcount2a == 0) {
                    break;
                }
                swcount2a--;
            }
            if (swhasx == 1) {
                int swcount1b = index1 - 1;
                int swcount2b = index2 - 1;
                int swEmptyOrXCount = 2;
                for (int b = swcount1b; b > swcount1a; b--) {
                    if ((pos[b][swcount2b] == " ") || (pos[b][swcount2b] == xoro) || (pos[b][swcount2b] == ".")) {
                        swEmptyOrXCount = 1;
                        break;
                    } else {
                        swEmptyOrXCount = 0;
                    }
                    if (swcount2b == swcount2a + 1) {
                        break;
                    }
                    swcount2b--;
                }
                swcount1b = index1 - 1;
                swcount2b = index2 - 1;
                if (swEmptyOrXCount == 0) {
                    for (int b = swcount1b; b > swcount1a; b--) {
                        pos[b][swcount2b] = xoro;
                        if (swcount2b == swcount2a + 1) {
                            break;
                        }
                        swcount2b--;
                    }
                }
            }
        }
        
        Gameplay gp = new Gameplay(1);
        if (move == 1 && gameover == 0) {
        	gp.aiimplementation(pos, index1, index2, gameover);
        }

    }
}
