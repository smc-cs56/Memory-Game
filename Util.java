import java.util.*;

public class Util // no need to be abstract. it's never extended
{
	private Util()
	{

	}

	 // Purpose: get a random number from min to max
	 //
	 // Param: int - min and max
	 //
	 // Return: a random number btwn min and max
	public static int randomNumber(int min, int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt(max - min + 1) + min;

	    return randomNum;
	}


	// Purpose: creates a string representation of nCardNumber. If 2 <= nCardNumber <= 10,
	// no need to enter switch statement
	//
	// Param: Integer - the card number (1-13)
	//
	// Return: string representation of nCardNumber
	public static String convertCardNumber(Integer nCardNumber)
	{
		String szReturn = nCardNumber.toString();

		switch (nCardNumber)
		{
			case 1:
				szReturn = "A";
				break;

			case 11:
				szReturn = "J";
				break;

			case 12:
				szReturn = "Q";
				break;

			case 13:
				szReturn = "K";
				break;
		}

		return szReturn;
	}
	
	// Purpose: creates a string representation of nCardSuit. 
	// Card is either Spade, Heart, Diamond, Club
	// 
	// Param: Integer - Card suit (1-4)
	//
	// Return: string representation of nCardSuit
	public static String convertCardSuit(Integer nCardSuit)
	{
		String szReturn = "";

		switch (nCardSuit)
		{
			case 1:
				szReturn = "Spade";
				break;

			case 2:
				szReturn = "Heart";
				break;

			case 3:
				szReturn = "Diamond";
				break;

			case 4:
				szReturn = "Club";
				break;
		}

		return szReturn;
	}
}