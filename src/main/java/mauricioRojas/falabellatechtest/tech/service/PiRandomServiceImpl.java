package mauricioRojas.falabellatechtest.tech.service;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

@Service
public class PiRandomServiceImpl implements PiRandomService{

    public String piRandom (int random_limit) throws InterruptedException, ExecutionException {
        int digits = getRandom(random_limit);
        BigDecimal result = computePi(digits);
        return "PiCalc: " + result;
    }
    
    private int getRandom(int input) {
        
        int min = input/2;
        int random_number = (int) (Math.random() * (input - min +1) + min);
        return random_number;
    }


	/** constants used in pi computation */
	private static final BigDecimal FOUR =
	BigDecimal.valueOf(4);

	/** rounding mode to use during pi computation */
	private static final int roundingMode = 
	BigDecimal.ROUND_HALF_EVEN;

	private static BigDecimal computePi(int digits) {
		int scale = digits + 5;
		BigDecimal arctan1_5 = arctan(5, scale);
		BigDecimal arctan1_239 = arctan(239, scale);
		BigDecimal pi = arctan1_5.multiply(FOUR).subtract(
			arctan1_239).multiply(FOUR);
		return pi.setScale(digits, 
						   BigDecimal.ROUND_HALF_UP);
	}
	private static BigDecimal arctan(int inverseX, int scale) 
	{
		BigDecimal result, numer, term;
		BigDecimal invX = BigDecimal.valueOf(inverseX);
		BigDecimal invX2 = 
			BigDecimal.valueOf(inverseX * inverseX);

		numer = BigDecimal.ONE.divide(invX,scale, roundingMode);

		result = numer;
		int i = 1;
		do {
			numer = 
				numer.divide(invX2, scale, roundingMode);
			int denom = 2 * i + 1;
			term = 
				numer.divide(BigDecimal.valueOf(denom),scale, roundingMode);
			if ((i % 2) != 0) {
				result = result.subtract(term);
			} else {
				result = result.add(term);
			}
			i++;
		} while (term.compareTo(BigDecimal.ZERO) != 0);
		return result;
	}
}


