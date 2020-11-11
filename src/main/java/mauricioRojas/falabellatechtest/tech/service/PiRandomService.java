
package mauricioRojas.falabellatechtest.tech.service;

import java.util.concurrent.ExecutionException;


public interface PiRandomService{

	public String piRandom(int random_limit) throws InterruptedException, ExecutionException;
	
}