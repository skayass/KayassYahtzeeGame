// CSC 151: YAHTZEE FINAL PROJECT.
// Name: Samia Kayass.
// Date: 04/27/2022.


import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;



abstract class Game {

    //
    // Constants
    //
    // Default value when a category has not been used.
    public final static int SCORE_NO_VALUE = -1;

    // Use the current time as a seed for the random number generator.
    private final long seed = (new java.util.Date()).getTime();
    private final Random generator = new Random(seed);
    //
    // End Constants
    //

    //
    // Properties
    //
    // Stores the character used to make a border around display messages.
    // Default is *.
    private String borderChar = "*";

    // Stores the text to indicate the current turn number.
    private String turnLabel = "Turn #";

    // Stores the error message for all invalid input.
    private String invalidInputMessage = "*** Invalid input ***";

    // Stores the maximum display width for output.
    private int displayWidth = 70;

    // Array to store the score values of each category.
    private final int[] scores;

    // Tracker for number of turns.
    private int turnCount = 0;

    // Property turnOver is used as the loop control variable for the
    //    "turn" do-loop.
    // It becomes true under the following conditions:
    //    * The user enters EXIT_RESPONSE to exit the program.
    //    * The user forgoes remaining rolls of the dice and ends the turn.
    //    * The user consumes the maximum number of rolls of the dice.
    private boolean turnOver = false;

    // Property gameExit is used as the loop control variable to exit the
    //    program when the user enters EXIT_RESPONSE.
    private boolean gameExit = false;

    // Property gameComplete is used as the loop control variable to exit
    //    the program when the user scores all categories on the scorecard
    //    which signifies a complete game.
    private boolean gameComplete = false;
    //
    // End Properties
    //

    //
    // Setters and Getters
    //
    public String getBorderChar() {
        return borderChar;
    }

    public void setBorderChar(String borderChar) {
        this.borderChar = borderChar;
    }

    public String getTurnLabel() {
        return turnLabel;
    }

    public void setTurnLabel(String turnLabel) {
        this.turnLabel = turnLabel;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public int getScore(int index) {
        return scores[index];
    }

    public void setScore(int index, int value) {
        scores[index] = value;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public String getInvalidInputMessage() {
        return invalidInputMessage;
    }

    public void setInvalidInputMessage(String invalidInputMessage) {
        this.invalidInputMessage = invalidInputMessage;
    }

    public boolean isTurnOver() {
        return turnOver;
    }

    public void setTurnOver(boolean turnOver) {
        this.turnOver = turnOver;
    }

    public boolean isGameExit() {
        return gameExit;
    }

    public void setGameExit(boolean gameExit) {
        this.gameExit = gameExit;
    }

    public boolean isGameComplete() {
        return gameComplete;
    }

    public void setGameComplete(boolean gameComplete) {
        this.gameComplete = gameComplete;
    }

    private Random getGenerator() {
        return generator;
    }
    //
    // End Setters and Getters
    //

    // Constructor for abstract Game class.
    public Game(int numberOfScores) {

        // Define the number of elements in the array property "scores".
        scores = new int[numberOfScores];

        // Initialize all elements in array property "scores" to CONSTANT SCORE_NO_VALUE.
        Arrays.fill(scores, SCORE_NO_VALUE);
    }

    //
    // Methods
    //
    // Generates a random integer in the range of the valid numbers on a die.
    // Essentially, this rolls one die.
    public final int getRandomInt() {
        return (getGenerator().nextInt(Yahtzee.MAX_NUMBER_ON_DIE) + 1);
    }

}

class Yahtzee extends Game {

    //
    // Constants
    //
    // There are 14 categories to store with respect to points.
    final static int NUMBER_OF_CATEGORIES = 14;

    // There are only 13 categories when determining if the game is complete.
    // Yahtzee Bonus is excluded in this count because it is not a category
    //    that can be chosen by the player.
    final static int NUMBER_OF_CATEGORIES_TO_COMPLETE_GAME = 13;

    // This is the array index (remember zero-based) for the sixth (upper
    //    bound) category in the upper section of the scorecard.
    final static int UPPER_CATEGORY_UPPER_BOUND_INDEX = 5;

    // This is the array index (remember zero-based) for the fourteenth (upper
    //    bound) category in the lower section of the scorecard.
    final static int LOWER_CATEGORY_UPPER_BOUND_INDEX = 13;

    // The number of dice used in the game.
    final static int NUMBER_OF_DICE = 5;

    // These are the array indices (remember zero-based) for each of the
    //    categories in the scorecard.
    // Although Yahtzee Bonus is not considered a full-fledged category
    //    because the player cannot directly choose it, the category still
    //    needs a place to store the points in array property "scores".
    final static int ACES_INDEX = 0;
    final static int TWOS_INDEX = 1;
    final static int THREES_INDEX = 2;
    final static int FOURS_INDEX = 3;
    final static int FIVES_INDEX = 4;
    final static int SIXES_INDEX = 5;
    final static int THREE_KIND_INDEX = 6;
    final static int FOUR_KIND_INDEX = 7;
    final static int FULL_HOUSE_INDEX = 8;
    final static int SMALL_STRAIGHT_INDEX = 9;
    final static int LARGE_STRAIGHT_INDEX = 10;
    final static int YAHTZEE_INDEX = 11;
    final static int CHANCE_INDEX = 12;
    final static int YAHTZEE_BONUS_INDEX = 13;

    // The index position in the 2-D array that stores a die face number.
    // The 2-D array that is referenced by this index is used to track how
    //    many of the five rolled dice have a certain die face number.
    final static int DIE_NUMBER_INDEX = 0;

    // The index position in the 2-D array that stores the count of dice that
    //    are showing a certain die face number.
    // The 2-D array that is referenced by this index is used to track how
    //    many of the five rolled dice have a certain die face number.
    final static int DIE_COUNT_INDEX = 1;

    // Used to create the 2-D array mentioned above. If the needs of the
    //    program are such that a higher dimension array is necessary, then
    //    this constant can simply be changed to match the array dimension.
    final static int DIE_COUNT_COLUMN_SIZE = 2;

    // Number of dice needed to form a small straight.
    final static int NUMBER_IN_SMALL_STRAIGHT = 4;

    // Number of dice needed to form a large straight.
    final static int NUMBER_IN_LARGE_STRAIGHT = 5;

    // To achieve a Full House, the five dice must form a pair and
    //    three-of-a-kind. Therefore, CONSTANTS for the values of
    //    2 and 3 are declared here in order to be used later to determine
    //    if the dice combination is configured into two groups; each
    //    group having the same value as all members of the group but
    //    having a different value than the another group.
    // We must distinguish between the Full House configuration and
    //    a Yahtzee.
    final static int FULL_HOUSE_NUMBER_IN_GROUP_1 = 2;
    final static int FULL_HOUSE_NUMBER_IN_GROUP_2 = 3;

    // This constant stores the number of different values on the dice faces.
    // The random number generator produces a number between 0.0 and 1.0 but
    //    not including 1.0
    // This constant is used with the random number generator to place the
    //    random numbers into the range of 1-6.
    final static int MAX_NUMBER_ON_DIE = 6;

    // Menu option values that the player can enter.
    final static String EXIT_RESPONSE = "X";
    final static String SCORE_CARD_RESPONSE = "S";
    final static String DISPLAY_DICE_RESPONSE = "D";
    final static String END_TURN_RESPONSE = "0";

    // Display message Strings.
    final static String REROLL_MESSAGE_1 = "Enter: " + SCORE_CARD_RESPONSE +
            " for ScoreCard; " + DISPLAY_DICE_RESPONSE + " for Dice; " +
            EXIT_RESPONSE + " to Exit";
    final static String REROLL_MESSAGE_2 = "Or: A series of numbers to " +
            "re-roll dice as follows:";
    final static String REROLL_MESSAGE_3 = "\t\tYou may re-roll any of the " +
            "dice by entering the die #s without spaces.";
    final static String REROLL_MESSAGE_4 = "\t\tFor example, to re-roll dice " +
            "#1, #3 & #4, enter 134 or enter " + END_TURN_RESPONSE + " for none.";
    final static String REROLL_MESSAGE_5 = "\t\tYou have %d roll(s) left this turn.";
    final static String REROLL_MESSAGE_6 = "Which of the dice would you like " +
            "to roll again? ";

    final static String CATEGORY_MESSAGE_1 = "Enter: 1-" +
            NUMBER_OF_CATEGORIES_TO_COMPLETE_GAME + " for category; " +
            SCORE_CARD_RESPONSE + " for ScoreCard; " + DISPLAY_DICE_RESPONSE +
            " for Dice; " + EXIT_RESPONSE + " to Exit";
    final static String CATEGORY_MESSAGE_2 = "Which category would you like " +
            "to choose? ";
    //
    // End Constants
    //

    //
    // Properties
    //
    // Point values for some categories are set to a value.
    // These can be modified through the setters.
    private int fullHouseScore = 25;
    private int smallStraightScore = 30;
    private int largeStraightScore = 40;
    private int yahtzeeScore = 50;
    private int yahtzeeBonusScore = 100;


    // Stores the text to indicate the current roll number.
    private String rollLabel = "Roll #";

    // Used to store the value of each die face.
    private final int[] dice = new int[NUMBER_OF_DICE];

    // Tracker for number of times the player has rolled the dice.
    private int numberOfRolls = 0;

    // This constant is the maximum number of rolls of the dice that a player
    //    receives during each turn.
    // It is used to ensure that the player does not exceed the maximum number
    //    of rolls and in display messaging.
    private int maxNumberRolls = 3;

    // Store the welcome message text.
    private String welcomeMessage = "Welcome to YAHTZEE";

    // Store the "Press Enter to Continue" message.
    private String pressEnterMessage = "Press the Enter key to continue: ";
    //
    // End Properties
    //

    //
    // Setters and Getters
    //
    public int getFullHouseScore() {
        return fullHouseScore;
    }

    public void setFullHouseScore(int fullHouseScore) {
        if (fullHouseScore >= 0)
            this.fullHouseScore = fullHouseScore;
        else
            this.fullHouseScore = 0;
    }

    public int getSmallStraightScore() {
        return smallStraightScore;
    }

    public void setSmallStraightScore(int smallStraightScore) {
        if (smallStraightScore >= 0)
            this.smallStraightScore = smallStraightScore;
        else
            this.smallStraightScore = 0;
    }

    public int getLargeStraightScore() {
        return largeStraightScore;
    }

    public void setLargeStraightScore(int largeStraightScore) {
        if (largeStraightScore >= 0)
            this.largeStraightScore = largeStraightScore;
        else
            this.largeStraightScore = 0;
    }

    public int getYahtzeeScore() {
        return yahtzeeScore;
    }

    public void setYahtzeeScore(int yahtzeeScore) {
        if (yahtzeeScore >= 0)
            this.yahtzeeScore = yahtzeeScore;
        else
            this.yahtzeeScore = 0;
    }

    public int getYahtzeeBonusScore() {
        return yahtzeeBonusScore;
    }

    public void setYahtzeeBonusScore(int yahtzeeBonusScore) {
        if (yahtzeeBonusScore >= 0)
            this.yahtzeeBonusScore = yahtzeeBonusScore;
        else
            this.yahtzeeBonusScore = 0;
    }

    public String getRollLabel() {
        return rollLabel;
    }

    public void setRollLabel(String rollLabel) {
        this.rollLabel = rollLabel;
    }

    public int getDice(int index) {
        int retVal;

        if (index >= 0 && index < dice.length)
            retVal = dice[index];
        else
            retVal = 0;

        return retVal;
    }

    public void setDice(int index, int value) {
        if ((index >= 0 && index < dice.length) && (value > 0 && value <= MAX_NUMBER_ON_DIE))
            dice[index] = value;
    }

    public int getNumberOfRolls() {
        return numberOfRolls;
    }

    public void setNumberOfRolls(int numberOfRolls) {
        if (numberOfRolls > 0)
            this.numberOfRolls = numberOfRolls;
    }

    public int getMaxNumberRolls() {
        return maxNumberRolls;
    }

    public void setMaxNumberRolls(int maxNumberRolls) {
        if (maxNumberRolls > 0)
            this.maxNumberRolls = maxNumberRolls;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public String getPressEnterMessage() {
        return pressEnterMessage;
    }

    public void setPressEnterMessage(String pressEnterMessage) {
        this.pressEnterMessage = pressEnterMessage;
    }
    //
    // End Setters and Getters
    //

    // Constructor for Yahtzee subclass of Game class.
    public Yahtzee() {

        // Call Game class constructor passing the number of
        //    categories which is used to define the correct
        //    number of elements in the array property "scores".
        super(NUMBER_OF_CATEGORIES);
    }

    //
    // Methods
    //
    // This method displays the current turn and dice roll number.
    public void displayTurnNumber() {

        int labelLength;
        int centerValue;

        System.out.println(getBorderChar().repeat(getDisplayWidth()));
        labelLength = (getTurnLabel() + getTurnCount() + " " + getRollLabel() + numberOfRolls).length();
        centerValue = labelLength + ((getDisplayWidth()) - labelLength) / 2;
        System.out.printf("%" + centerValue + "s", getTurnLabel() + getTurnCount() + " " + getRollLabel() + numberOfRolls);
        System.out.println();
        System.out.println(getBorderChar().repeat(getDisplayWidth()));
    }

    // This method displays the values of the dice after rolling.
    public void displayDice() {

        final char UNICODE_DIE_INDEX = '\u267F';

        final String DIE_LABEL_PREFIX = "Die #";
        final String DIE_LABEL_SUFFIX = " = ";

        System.out.println();

        displayTurnNumber();

        for (int i = 0; i < NUMBER_OF_DICE; i++) {
            System.out.print(DIE_LABEL_PREFIX + (i + 1) + DIE_LABEL_SUFFIX);

            System.out.print((char) ((int) UNICODE_DIE_INDEX + dice[i]));

            System.out.println(" (" + (dice[i]) + ")");
        }

        System.out.println();
    }



    //****
    // Adding an overloaded public method named displayScoreSheet which
    // returns no value and receives no parameters.
    // This method is used to display the current scorecard.
    public void displayScoreSheet() {
        this.displayScoreSheet(System.out);
    }


    //****
    // This method displays the current scorecard.
    // Adding one parameter named outStream of datatype PrintStream.
    // And replacing every System.out inside this method with the variable outStream.
    public void displayScoreSheet(PrintStream outStream) {

        final String[] labels = new String[NUMBER_OF_CATEGORIES];

        labels[ACES_INDEX] = "Aces";
        labels[TWOS_INDEX] = "Twos";
        labels[THREES_INDEX] = "Threes";
        labels[FOURS_INDEX] = "Fours";
        labels[FIVES_INDEX] ="Fives";
        labels[SIXES_INDEX] = "Sixes";
        labels[THREE_KIND_INDEX] = "3 of a kind";
        labels[FOUR_KIND_INDEX] = "4 of a kind";
        labels[FULL_HOUSE_INDEX] = "Full House";
        labels[SMALL_STRAIGHT_INDEX] = "Sm. Straight";
        labels[LARGE_STRAIGHT_INDEX] = "Lg. Straight";
        labels[YAHTZEE_INDEX] = "YAHTZEE";
        labels[CHANCE_INDEX] = "Chance";
        labels[YAHTZEE_BONUS_INDEX] = "YAHTZEE BONUS";

        // If the player achieves a score of 63 or greater in the upper section,
        //    then a bonus is awarded.
        final int BONUS_THRESHOLD = 63;

        // This is the upper section bonus.
        final int BONUS_SCORE = 35;

        final String UPPER_SECTION_LABEL = "UPPER SECTION";
        final String LOWER_SECTION_LABEL = "LOWER SECTION";
        final String UPPER_SECTION_SUBTOTAL_LABEL = "TOTAL SCORE";
        final String UPPER_SECTION_BONUS_LABEL = "BONUS if >= 63";
        final String UPPER_SECTION_TOTAL_LABEL = "TOTAL of Upper Section";
        final String LOWER_SECTION_TOTAL_LABEL = "TOTAL of Lower Section";
        final String GRAND_TOTAL_LABEL = "GRAND TOTAL";

        final String OPTION_SUFFIX_ONE_DIGIT = ")  ";
        final String OPTION_SUFFIX_TWO_DIGIT = ") ";

        final String EQUALS_LABEL = " = ";

        // Assign the upper scorecard section score to variable upperScoreTotal.
        int upperScoreTotal = calculateUpperScore();

        // Assign the lower scorecard section score to variable lowerScoreTotal.
        int lowerScoreTotal = calculateLowerScore();

        outStream.println();
        outStream.println(UPPER_SECTION_LABEL);

        // Options 1 (Aces) through 6 (sixes).
        for (int i = 0; i <= UPPER_CATEGORY_UPPER_BOUND_INDEX; i++) {
            if (getScore(i) == SCORE_NO_VALUE)
                outStream.println((i+1) + OPTION_SUFFIX_ONE_DIGIT + labels[i]);
            else
                outStream.println((i+1) + OPTION_SUFFIX_ONE_DIGIT + labels[i] + EQUALS_LABEL + getScore(i));
        }

        // Upper section score subtotal.
        if (upperScoreTotal > 0)
            outStream.println(UPPER_SECTION_SUBTOTAL_LABEL + EQUALS_LABEL + upperScoreTotal);
        else
            outStream.println(UPPER_SECTION_SUBTOTAL_LABEL);

        // Upper section score bonus.
        if (upperScoreTotal >= BONUS_THRESHOLD)
            outStream.println(UPPER_SECTION_BONUS_LABEL + EQUALS_LABEL + BONUS_SCORE);
        else
            outStream.println(UPPER_SECTION_BONUS_LABEL);

        // Upper section score total.
        if (upperScoreTotal > 0)
            if (upperScoreTotal >= BONUS_THRESHOLD)
                outStream.println(UPPER_SECTION_TOTAL_LABEL + EQUALS_LABEL + (upperScoreTotal + BONUS_SCORE));
            else
                outStream.println(UPPER_SECTION_TOTAL_LABEL + EQUALS_LABEL + upperScoreTotal);
        else
            outStream.println(UPPER_SECTION_TOTAL_LABEL);

        outStream.println();
        outStream.println(LOWER_SECTION_LABEL);

        // Options 7 (Three-of-a-kind) through 13 (Chance) plus Yahtzee Bonus.
        for (int i = (UPPER_CATEGORY_UPPER_BOUND_INDEX + 1); i <= LOWER_CATEGORY_UPPER_BOUND_INDEX; i++) {

            // If this is NOT the iteration of the for-loop that is to display
            //    Yahtzee Bonus, then display the category score directly from
            //    the array property "scores".
            if (i != YAHTZEE_BONUS_INDEX) {

                // If the Option number is one digit (1-9)
                //    then use two spaces (OPTION_SUFFIX_ONE_DIGIT).
                if (i < 9)
                    outStream.println((i+1) + OPTION_SUFFIX_ONE_DIGIT + labels[i]);

                    // If the Option number is two digits (10 or greater)
                    //    then use one space (OPTION_SUFFIX_TWO_DIGIT).
                else
                    outStream.println((i+1) + OPTION_SUFFIX_TWO_DIGIT + labels[i]);

                // If the player has scored this then display the number of points.
                if (getScore(i) != SCORE_NO_VALUE)
                    outStream.println(EQUALS_LABEL + getScore(i));

                    // Otherwise, do not display an equals sign or score.
                else
                    outStream.println();
            }

            // If this is the iteration of the for-loop that is to display
            //    Yahtzee Bonus then calculate the score differently.
            // The score for Yahtzee Bonus is not stored directly in the
            //    array property "scores". The count of Yahtzee Bonuses
            //    achieved are stored in "scores". Therefore to calculate
            //    the score, the value for Yahtzee Bonus that is stored
            //    in "scores" must be multiplied by the number of points
            //    awarded per additional Yahtzee which is the CONSTANT
            //    YAHTZEE_BONUS_SCORE.
            else {
                if (getScore(i) != SCORE_NO_VALUE)
                    outStream.println(labels[i] + EQUALS_LABEL + (getScore(i) * getYahtzeeBonusScore()));
                else
                    outStream.println(labels[i]);
            }
        }

        // Lower section score total.
        if (lowerScoreTotal > 0)
            outStream.println(LOWER_SECTION_TOTAL_LABEL + EQUALS_LABEL + lowerScoreTotal);
        else
            outStream.println(LOWER_SECTION_TOTAL_LABEL);

        // Upper section score total.
        if (upperScoreTotal > 0)
            if (upperScoreTotal >= BONUS_THRESHOLD)
                outStream.println(UPPER_SECTION_TOTAL_LABEL + EQUALS_LABEL + (upperScoreTotal + BONUS_SCORE));
            else
                outStream.println(UPPER_SECTION_TOTAL_LABEL + EQUALS_LABEL + upperScoreTotal);
        else
            outStream.println(UPPER_SECTION_TOTAL_LABEL);

        // Grand total.
        if (upperScoreTotal + lowerScoreTotal > 0)
            if (upperScoreTotal >= BONUS_THRESHOLD)
                outStream.println(GRAND_TOTAL_LABEL + EQUALS_LABEL + (upperScoreTotal + lowerScoreTotal + BONUS_SCORE));
            else
                outStream.println(GRAND_TOTAL_LABEL + EQUALS_LABEL + (upperScoreTotal + lowerScoreTotal));
        else
            outStream.println(GRAND_TOTAL_LABEL);

        outStream.println();
    }

    // This method is called after the user enters a valid category to score and
    //    updates the appropriate category score in the array property "scores".
    public void calculateTurnScore(int scoreOption) {

        // Convert the Option number (Categories 1-13) to 0-12 for zero-based indexing.
        int scoreOption2Index = scoreOption - 1;

        // The player chooses from the upper section; categories 1 (Aces) through 6 (Sixes).
        if (scoreOption2Index <= UPPER_CATEGORY_UPPER_BOUND_INDEX)
            setScore(scoreOption2Index, calculateUpperSectionCategory(scoreOption));

            // The player chooses from the lower section; categories 7 (Three-of-a-kind)
            //    through 13 (Chance).
        else {

            switch (scoreOption2Index) {

                // Option 7 = Three-of-a-kind
                case THREE_KIND_INDEX:
                    setScore(THREE_KIND_INDEX,
                            calculateNOfKind(calculateLowerSectionCategory(), 3));

                    break;

                // Option 8 = Four-of-a-kind
                case FOUR_KIND_INDEX:
                    setScore(FOUR_KIND_INDEX,
                            calculateNOfKind(calculateLowerSectionCategory(), 4));

                    break;

                // Option 9 = Full House
                case FULL_HOUSE_INDEX:
                    setScore(FULL_HOUSE_INDEX,
                            calculateFullHouse(calculateLowerSectionCategory()));

                    break;

                // Option 10 = Small Straight
                case SMALL_STRAIGHT_INDEX:
                    setScore(SMALL_STRAIGHT_INDEX,
                            calculateNStraight(calculateLowerSectionCategory(),
                                    NUMBER_IN_SMALL_STRAIGHT));

                    break;

                // Option 11 = Large Straight
                case LARGE_STRAIGHT_INDEX:
                    setScore(LARGE_STRAIGHT_INDEX,
                            calculateNStraight(calculateLowerSectionCategory(),
                                    NUMBER_IN_LARGE_STRAIGHT));

                    break;

                // Option 12 = Yahtzee
                case YAHTZEE_INDEX:
                    int checkYahtzee = calculateYahtzee();

                    if (getScore(YAHTZEE_INDEX) == SCORE_NO_VALUE)
                        setScore(YAHTZEE_INDEX, checkYahtzee);

                    else if (getScore(YAHTZEE_INDEX) == getYahtzeeScore() && checkYahtzee == getYahtzeeScore()) {
                        if (getScore(YAHTZEE_BONUS_INDEX) == SCORE_NO_VALUE)
                            setScore(YAHTZEE_BONUS_INDEX, 1);
                        else
                            setScore(YAHTZEE_BONUS_INDEX, getScore(YAHTZEE_BONUS_INDEX) + 1);
                    }

                    break;

                // Option 13 = Chance
                case CHANCE_INDEX:
                    setScore(CHANCE_INDEX, calculateChance());

                    break;
            }
        }
    }

    // This method is called if the player chooses from the upper section
    //    categories 1 (Aces) through 6 (Sixes). It does the calculation
    //    for these six categories and then passes back the value to be
    //    used to update the array property "scores".
    public int calculateUpperSectionCategory(int dieNumber) {

        int score = 0;

        for (int i = 0; i < NUMBER_OF_DICE; i++) {
            if (dice[i] == dieNumber)
                score += dice[i];
        }

        return score;
    }

    // This method is called for lower section categories 7 (Three-of-a-kind)
    //    through 11 (Large straight). It creates a 2-D array that is used to
    //    track how many of the five rolled dice have a certain die face number.
    // In doing so, this 2-D array simplifies the code necessary to determine
    //    if the criteria of a player chosen category have been met or not.
    // This 2-D array is passed back to the calling code.
    public int[][] calculateLowerSectionCategory() {

        int[] tempDice = new int[NUMBER_OF_DICE];
        System.arraycopy(dice, 0, tempDice, 0, dice.length);
        Arrays.sort(tempDice);

        int[][] dieCount = new int[1][DIE_COUNT_COLUMN_SIZE];
        dieCount[dieCount.length-1][DIE_NUMBER_INDEX] = tempDice[0];
        dieCount[dieCount.length-1][DIE_COUNT_INDEX] = 1;

        for (int i = 1; i < tempDice.length; i++) {
            if (tempDice[i] == dieCount[dieCount.length-1][DIE_NUMBER_INDEX]) {
                dieCount[dieCount.length-1][DIE_COUNT_INDEX]++;
            }
            else {
                int[][] tempDieCount = new int[dieCount.length + 1][DIE_COUNT_COLUMN_SIZE];
                System.arraycopy(dieCount, 0, tempDieCount, 0, dieCount.length);

                tempDieCount[tempDieCount.length-1][DIE_NUMBER_INDEX] = tempDice[i];
                tempDieCount[tempDieCount.length-1][DIE_COUNT_INDEX] = 1;

                dieCount = tempDieCount;
            }
        }

        return dieCount;
    }

    // This method is called if the player chooses from the lower section
    //    categories 7 (Three-of-a-kind) or 8 (Four-of-a-kind). It does the
    //    calculation for these two categories. The score value is then
    //    passed back to be used to update the array property "scores".
    public int calculateNOfKind(int[][] dieCount, int nKind) {

        int score = 0;
        boolean isNKind = false;

        for (int i = 0; i < dieCount.length; i++) {
            if (dieCount[i][DIE_COUNT_INDEX] >= nKind)
                isNKind = true;
        }

        if (isNKind)
            for (int i = 0; i < NUMBER_OF_DICE; i++)
                score += dice[i];

        return score;
    }

    // This method is called if the player chooses from the lower section
    //    category 9 (Full House). It does the calculation for this category
    //    and the score value is passed back to be used to update the array
    //    variable "scores".
    public int calculateFullHouse(int[][] dieCount) {

        int score = 0;

        if (dieCount.length == 2 &&
                ((dieCount[0][DIE_COUNT_INDEX] == FULL_HOUSE_NUMBER_IN_GROUP_1 &&
                        dieCount[1][DIE_COUNT_INDEX] == FULL_HOUSE_NUMBER_IN_GROUP_2)
                        ||
                        (dieCount[1][DIE_COUNT_INDEX] == FULL_HOUSE_NUMBER_IN_GROUP_1 &&
                                dieCount[0][DIE_COUNT_INDEX] == FULL_HOUSE_NUMBER_IN_GROUP_2)))
            score = getFullHouseScore();

        return score;
    }

    // This method is called if the player chooses from the lower section
    //    categories 10 (Small Straight) or 11 (Large Straight). It does the
    //    calculation for these two categories. The score value is then
    //    passed back to be used to update the array property "scores".
    public int calculateNStraight(int[][] dieCount, int nStraight) {

        int score = 0;
        int n = 1;

        for (int i = 0; i < dieCount.length-1; i++) {
            if (n < nStraight) {
                if (dieCount[i][DIE_NUMBER_INDEX] == dieCount[i+1][DIE_NUMBER_INDEX]-1)
                    n++;
                else
                    n = 1;
            }
        }

        if (n >= nStraight) {
            switch (nStraight) {
                case NUMBER_IN_SMALL_STRAIGHT:
                    score = getSmallStraightScore();
                    break;

                case NUMBER_IN_LARGE_STRAIGHT:
                    score = getLargeStraightScore();
                    break;
            }
        }

        return score;
    }

    // This method is called if the player chooses from the lower section
    //    category 12 (Yahtzee). It does the calculation for this category
    //    and the score value is passed back to be used to update the array
    //    variable "scores".
    public int calculateYahtzee() {

        int score = 0;
        boolean isYahtzee = true;

        for (int i = 0; i < NUMBER_OF_DICE - 1; i++) {
            if (dice[i] != dice[i+1]) {
                isYahtzee = false;
            }
        }

        if (isYahtzee)
            score = getYahtzeeScore();

        return score;
    }

    // This method is called if the player chooses from the lower section
    //    category 13 (Chance). It does the calculation for this category
    //    and the score value is passed back to be used to update the array
    //    variable "scores".
    public int calculateChance() {

        int score = 0;

        for (int i = 0; i < NUMBER_OF_DICE; i++) {
            score += dice[i];
        }

        return score;
    }

    // This method calculates the upper scorecard section subtotal.
    public int calculateUpperScore() {

        int score = 0;

        for (int i = 0; i <= UPPER_CATEGORY_UPPER_BOUND_INDEX; i++)
            if (getScore(i) != SCORE_NO_VALUE)
                score += getScore(i);

        return score;
    }

    // This method calculates the lower scorecard section subtotal.
    public int calculateLowerScore() {

        int score = 0;

        for (int i = UPPER_CATEGORY_UPPER_BOUND_INDEX+1; i <= LOWER_CATEGORY_UPPER_BOUND_INDEX; i++) {
            if (i != YAHTZEE_BONUS_INDEX) {
                if (getScore(i) != SCORE_NO_VALUE)
                    score += getScore(i);
            }
            else {
                if (getScore(i) != SCORE_NO_VALUE)
                    score += (getScore(i) * getYahtzeeBonusScore());
            }
        }

        return score;
    }

    // This method determines if the criteria of a player chosen category
    //    has been used or not.
    // A category can only be used once with the exception of Yahtzee. There
    //    can be multiple times that a Yahtzee is scored provided that the
    //    first time the Yahtzee category is selected, that the configuration
    //    of the dice meets the criteria for a Yahtzee.
    // In other words, if the Yahtzee category is "zeroed out", then no more
    //    Yahtzees can be scored even if one is rolled.
    public boolean isCategoryUsed(int scoreOption) {

        boolean used = false;

        if ((scoreOption-1) != YAHTZEE_INDEX) {
            if (getScore(scoreOption-1) != SCORE_NO_VALUE)
                used = true;
        }
        else {
            if (getScore(YAHTZEE_INDEX) == getYahtzeeScore() &&
                    calculateYahtzee() != getYahtzeeScore())
                used = true;
            else if (getScore(YAHTZEE_INDEX) != getYahtzeeScore() &&
                    getScore(YAHTZEE_INDEX) != SCORE_NO_VALUE)
                used = true;
        }

        return used;
    }

    // This method determines if all of the categories have been used which
    //    signals the end of the current game.
    public boolean isGameOver() {

        boolean gameOver = true;

        for (int i = 0; i < NUMBER_OF_CATEGORIES_TO_COMPLETE_GAME; i++)
            if (getScore(i) == SCORE_NO_VALUE)
                gameOver = false;

        return gameOver;
    }

    // This method Displays a generic error message.
    public void displayErrorMessage() {

        System.out.println();

        System.out.println(getBorderChar().repeat(getInvalidInputMessage().length()));
        System.out.println(getInvalidInputMessage());
        System.out.println(getBorderChar().repeat(getInvalidInputMessage().length()));
    }

}

public class YahtzeeFP {

    public static void main(String[] args) {

        //****
        // Creating a new instance of class Yahtzee and assign it to the variable
        // named GameOfYahtzee of datatype Yahtzee.
        Yahtzee GameOfYahtzee = new Yahtzee();


        // This constant is used to transform the ASCII (character) value that
        //    represents the characters '1' through '5' into the integer
        //    representation 1-5 and then shifting by one for zero-based
        //    indexing into the range 0-4.
        final int ASCII_DICE_INDEX = 49;

        final String OUTPUT_FILE_NAME = "output.txt";
        final String OUTPUT_FILE_ERROR_MESSAGE = "Error opening file: ";

        // Variable dice2Reroll collects the users response during a turn.
        // Valid responses are:
        //    * EXIT_RESPONSE to exit the program.
        //    * SCORE_CARD_RESPONSE to redisplay the current scorecard.
        //    * DISPLAY_DICE_RESPONSE to redisplay the current numbers on the
        //         faces of the five dice.
        //    * END_TURN_RESPONSE to forgo re-rolling of the dice and end the turn.
        //    * Any String of integers using 1-5 non-repeating. Spaces are
        //         stripped out.
        //    *    For example: 123 re-rolls dice 1, 2 and 3
        String dice2Reroll;

        // Variable scoreOptionInput collects the user's response as the
        //    category to score.
        // Valid responses are:
        //    * EXIT_RESPONSE to exit the program.
        //    * SCORE_CARD_RESPONSE to redisplay the current scorecard.
        //    * DISPLAY_DICE_RESPONSE to redisplay the current numbers on
        //         the faces of the five dice.
        //    * Any integer from 1-13 represented as a String.
        //    *    This is the scorecard category the user wishes to choose.
        //    *     1  = Aces
        //    *     2  = Twos
        //    *     3  = Threes
        //    *     4  = Fours
        //    *     5  = Fives
        //    *     6  = Sixes
        //    *     7  = Three of a kind
        //    *     8  = Four of a kind
        //    *     9  = Full House
        //    *     10 = Small Straight
        //    *     11 = Large Straight
        //    *     12 = Yahtzee
        //    *     13 = Chance
        String scoreOptionInput;

        // If during the input validation, it is determined that variable
        //    scoreOptionInput contains only numbers, then variable
        //    scoreOptionInput is cast into an datatype int and stored in
        //    variable scoreOption.
        // Variable scoreOption is then tested for validity by being in the
        //    range 1-13.
        int scoreOption = 0;

        Scanner input = new Scanner(System.in);

        System.out.println();

        //****
        // Invoking the public method getWelcomeMessage on the variable GameOfYahtzee
        // passing no arguments and return value using the println method.
        // To print the welcome message.
        System.out.println(GameOfYahtzee.getWelcomeMessage());


        // This is the main do-while loop.
        // Execution exits this loop when the game is over; having been ended
        //    by the user entering EXIT_RESPONSE to exit or the user completing
        //    the game by scoring all categories.
        do {
            System.out.println();


            //****
            // Invoking the public method getPressEnterMessage on the  variable GameOfYahtzee
            // passing no arguments and return value using the print method.
            // To print the press enter message, so that the user press enter to start the game.
            System.out.print(GameOfYahtzee.getPressEnterMessage());


            input.nextLine();


            //****
            // Invoking the method getTurnCount and setTurnCount on variable GameOfYahtzee.
            // to keep tracking the number of turns the user uses.
            GameOfYahtzee.setTurnCount(GameOfYahtzee.getTurnCount() + 1);


            // Roll the dice.
            for (int i = 0; i < Yahtzee.NUMBER_OF_DICE; i++) {


                //****
                // Invoking the public method setDice on variable GameOfYahtzee
                // using two arguments, the first argument variable i and the second
                // argument the getRandomInt method invoking on the variable GameOfYahtzee,
                // to roll the dice randomly.
                GameOfYahtzee.setDice(i , GameOfYahtzee.getRandomInt());

            }


            //****
            // Invoking the public  method setNumberOfRolls on variable GameOfYahtzee passing the argument of 1,
            // to set number of rolls per turn.
            GameOfYahtzee.setNumberOfRolls(1);

            // Invoking the public method displayDice on variable GameOfYahtzee passing no arguments,
            // to display the values of the dice after rolling.
            GameOfYahtzee.displayDice();

            // Invoking the public method setTurnOver on variable GameOfYahtzee passing the argument of false,
            // to set the turn flag variable for the next turn.
            GameOfYahtzee.setTurnOver(false);


            // This is the "turn" loop.
            // Execution exits this loop when the user's turn has ended;
            //    choosing to forgo re-rolls of the dice by entering
            //    END_TURN_RESPONSE or by consuming the maximum number of
            //    rolls of the dice.
            do {

                // Give the user the menu commands and instructions.
                System.out.println();
                System.out.println(Yahtzee.REROLL_MESSAGE_1);
                System.out.println();

                System.out.println(Yahtzee.REROLL_MESSAGE_2);
                System.out.println(Yahtzee.REROLL_MESSAGE_3);
                System.out.println(Yahtzee.REROLL_MESSAGE_4);

                System.out.println();
                System.out.printf(Yahtzee.REROLL_MESSAGE_5,
                        (GameOfYahtzee.getMaxNumberRolls() - GameOfYahtzee.getNumberOfRolls()));
                System.out.println();

                System.out.println();
                System.out.print(Yahtzee.REROLL_MESSAGE_6);

                // Collect the user's input response of which command to execute
                //    or of which dice to re-roll.
                dice2Reroll = input.nextLine().trim();

                // The user's response from the menu prompt, which is stored in
                //    variable dice2Reroll, is processed here.
                // Variable dice2Reroll is forced to upper case so that the user
                //    can validly enter either upper or lower case values.
                switch (dice2Reroll.toUpperCase()) {


                    //****
                    // Case when the user end the game, then
                    // exit the turn and exit the game.
                    case Yahtzee.EXIT_RESPONSE:
                        GameOfYahtzee.setTurnOver(true);
                        GameOfYahtzee.setGameExit(true);
                        break;

                    // Case when the user want to see the current scorecard, then
                    // displaying the score sheet.
                    case Yahtzee.SCORE_CARD_RESPONSE:
                        GameOfYahtzee.displayScoreSheet();
                        break;

                    // Case when user wants to see the current dice face values, then
                    // displaying the dice face value.
                    case Yahtzee.DISPLAY_DICE_RESPONSE:
                        GameOfYahtzee.displayDice();
                        break;

                    // Case when the user wants to end the turn, then
                    // exist the turn.
                    case Yahtzee.END_TURN_RESPONSE:
                        GameOfYahtzee.setTurnOver(true);
                        break;

                    // Case when The user entered a space or pressed enter, then
                    // displaying the error message.
                    case "":
                        GameOfYahtzee.displayErrorMessage();
                        break;


                    // In all other cases, test for a valid String of integers
                    //    representing dice to re-roll.
                    default:

                        // Remove any spaces in the String. Particularly ones
                        //    between characters.
                        // For example: 1 2 3 instead of 123
                        dice2Reroll = dice2Reroll.replace(" ", "");

                        // Save off the contents of variable dice2Reroll to variable
                        //    checkDice2Reroll in order for checkDice2Reroll to be
                        //    modified during the validation process.
                        String checkDice2Reroll = dice2Reroll;

                        // Replace the numbers 1 through CONSTANT NUMBER_OF_DICE
                        //    in String checkDice2Reroll with a space.
                        // If anything but empty space is left in String
                        //    checkDice2Reroll after this loop has completed,
                        //    then there has been invalid input from the user.
                        for (int i = 1; i <= Yahtzee.NUMBER_OF_DICE; i++) {
                            checkDice2Reroll = checkDice2Reroll.replaceFirst(String.valueOf(i), " ");
                        }

                        // When checkDice2Reroll is blank, the user entered
                        //    valid dice to reroll.
                        // Conversely, when checkDice2Reroll is not blank, the
                        //    user entered invalid dice to reroll.
                        if (checkDice2Reroll.isBlank()) {

                            // If execution makes it here, the input is a valid
                            //    set of integers in String format representing
                            //    dice to re-roll.
                            // Iterate for each character in the String variable
                            //    dice2Reroll (each of which is an integer), and
                            //    for each of these characters re-roll that die.
                            for (int i = 0; i < dice2Reroll.length(); i++) {
                                GameOfYahtzee.setDice(((int)dice2Reroll.charAt(i)) -
                                        ASCII_DICE_INDEX, GameOfYahtzee.getRandomInt());
                            }


                            //****
                            // Invoking the getNumberOfRolls and the setNumberOfRolls on the variable
                            // GameOfYahtzee to keep tracking the number of rolls the user uses.
                            GameOfYahtzee.setNumberOfRolls(GameOfYahtzee.getNumberOfRolls() + 1);

                            // Invoking the method displayDice on the variable GameOfYahtzee to
                            // display the values of the dice after rolling.
                            GameOfYahtzee.displayDice();



                            //****
                            // Adding an if statement that checks the value of property numberOfRolls
                            // for equality to the value of property maxNumberRolls by using the instance variable
                            // GameOfYahtzee and the appropriate getters, to check if the roll of dice reach the
                            // maximum number of rolls which is "3", then end the players turn,
                            // so they can choose the category they want.
                            if (GameOfYahtzee.getNumberOfRolls() == GameOfYahtzee.getMaxNumberRolls())
                                GameOfYahtzee.setTurnOver(true);


                        }
                        // The user entered invalid input when prompted for which
                        //    dice to reroll. So display an error message.
                        else {
                            GameOfYahtzee.displayErrorMessage();
                        }

                } // This is the closing curly brace for the switch statement.

            } while (!GameOfYahtzee.isTurnOver());

            // If in the previous menu the user responded with EXIT_RESPONSE
            //    to exit the game, then skip this code block.
            // If the user is continuing to play the game, then provide the
            //    menu to score the turn.
            if (!GameOfYahtzee.isGameExit()) {


                //****
                // Invoking the public method displayScoreSheet on variable GameOfYahtzee, passing
                // no argument. To display the current scorecard.
                GameOfYahtzee.displayScoreSheet();


                // Flag variable that indicates whether the user's input
                //    is valid or invalid.
                boolean isValidEntry;

                // Flag variable that indicates whether the user's entry
                //    a category to score.
                boolean categoryPicked;

                // Variable continuePrompting is used as the loop control
                //    variable for the following do-while loop.
                boolean continuePrompting;

                // This is the "category" loop.
                // Execution exits this loop when the enters a valid category
                //    to score or chooses to exit the game.
                do {

                    // Start with the assumption that input will be valid and
                    //    set isValidEntry to false if otherwise.
                    isValidEntry = true;

                    // Flag variable categoryPicked is false until the user
                    //    selects a valid category to score.
                    categoryPicked = false;

                    // Display messaging prompting the player for a category.
                    System.out.println();
                    System.out.println(Yahtzee.CATEGORY_MESSAGE_1);
                    System.out.println();

                    System.out.print(Yahtzee.CATEGORY_MESSAGE_2);

                    // Collect the user's input response of which command to
                    //    execute or of which category to score.
                    scoreOptionInput = input.nextLine().trim();

                    // Set/reset the numeric equivalent of variable scoreOptionInput.
                    scoreOption = 0;


                    // The user's response from the menu prompt, which is stored
                    //    in variable scoreOptionInput, is processed here.
                    // Variable scoreOptionInput is forced to upper case so that
                    //    the user can validly enter either upper or lower case values.
                    switch (scoreOptionInput.toUpperCase()) {


                        //****
                        // Case when the user end the game, then exit the game.
                        case Yahtzee.EXIT_RESPONSE:
                            GameOfYahtzee.setGameExit(true);
                            break;

                        // Case when the user want to see the current scorecard, then
                        // displaying the score sheet.
                        case Yahtzee.SCORE_CARD_RESPONSE:
                            GameOfYahtzee.displayScoreSheet();
                            break;

                        // Case when user wants to see the current dice face values, then
                        // displaying the dice face values.
                        case Yahtzee.DISPLAY_DICE_RESPONSE:
                            GameOfYahtzee.displayDice();
                            break;

                        // Case when The user entered a space or pressed enter, then
                        // displaying the error message.
                        case "":
                            GameOfYahtzee.displayErrorMessage();
                            break;


                        // In all other cases, test for a valid String of integers
                        //    representing a menu category number.
                        default:

                            // Iterate for each character in the String variable
                            //    scoreOptionInput (each of which is an integer),
                            //    and for each of these characters, check if it is a digit.
                            for (int x = 0; x < scoreOptionInput.length(); x++)
                                if (!(Character.isDigit(scoreOptionInput.charAt(x)))) {
                                    isValidEntry = false;
                                    GameOfYahtzee.displayErrorMessage();
                                }

                            // If execution makes it here, the input is a valid
                            //    set of integers in String format potentially
                            //    representing a valid menu category number.
                            if (isValidEntry) {

                                // Cast variable scoreOptionInput into an integer
                                //    data type and store in variable scoreOption.
                                scoreOption = Integer.parseInt(scoreOptionInput);

                                // Test if the numeric menu category is invalid which is not
                                //    between 1-13 inclusive.
                                // The valid menu categories are 1-13 inclusive.
                                if (scoreOption < 1 ||
                                        scoreOption > Yahtzee.NUMBER_OF_CATEGORIES_TO_COMPLETE_GAME) {
                                    isValidEntry = false;
                                    GameOfYahtzee.displayErrorMessage();
                                }
                            }

                            // The user entered a valid category to score however the
                            //     category was already used by the player.
                            // Categories can only be used once.
                            if (isValidEntry && GameOfYahtzee.isCategoryUsed(scoreOption)) {
                                isValidEntry = false;
                                GameOfYahtzee.displayErrorMessage();
                            }

                            // If the user's input passes all checks then calculate
                            //    the new score display the current score sheet and
                            //    determine if the game is over.
                            if (isValidEntry) {
                                categoryPicked = true;
                                GameOfYahtzee.calculateTurnScore(scoreOption);
                                GameOfYahtzee.displayScoreSheet();
                                GameOfYahtzee.setGameComplete(GameOfYahtzee.isGameOver());
                            }
                    } // This is the closing curly brace for the switch statement.

                    // This do-while continues to execute when variable continuePrompting is true.
                    // Variable continuePrompting is only true when the two parts are both true.
                    //    1) The first part examines variable gameExit. When gameExit is false,
                    //       the user did not enter EXIT_RESPONSE to exit the game. Therefore,
                    //       to continue looping the user must not have indicated that they
                    //       wanted to exit the game.
                    //    2) The second part checks the validity of the user input value and
                    //       determines if a category was picked. If the user input is invalid,
                    //       the loop continues OR if the user did not pick an unused category,
                    //       the loop continues.
                    continuePrompting = !GameOfYahtzee.isGameExit() && !categoryPicked;

                } while (continuePrompting);

            } // This is the closing curly brace for the if-statement.

        } while (!GameOfYahtzee.isGameExit() && !GameOfYahtzee.isGameComplete());


        //****
        // Invoking the public method displayScoreSheet on variable GameOfYahtzee, passing
        // no argument. To display the current score sheet.
        GameOfYahtzee.displayScoreSheet();


        //****
        // Creating a new instance of class File, passing the OUTPUT_FILE_NAME constant to the File constructor.
        // And assign it to the variable outputFile of datatype File.
        File outputFile = new File(OUTPUT_FILE_NAME);


        //****
        // Adding a try-catch block to test for errors and to catch them and execute code to handle it, if an error occurs,
        // by creating on the try block a new instance of class PrintStream, passing the outputFile to the PrintStream constructor, and
        // assign it to the variable outputStream of datatype PrintStream, and invoking the public method displayScoreSheet
        // on variable GameOfYahtzee, passing the variable outputStream as an argument.
        // And using the class Exception for the exception type and using the variable ex on it, in the catch block header.
        // And displaying the error message to the console by using the println method on the catch block.
        try {
            PrintStream outputStream = new PrintStream(outputFile);
            GameOfYahtzee.displayScoreSheet(outputStream);
        }catch (Exception ex)
            {
                System.out.println(OUTPUT_FILE_ERROR_MESSAGE + outputFile.getName());
        }

    }
}
