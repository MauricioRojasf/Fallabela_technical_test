package mauricioRojas.falabellatechtest;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"mauricioRojas.falabellatechtest","mauricioRojas.falabellatechtest.configuration","mauricioRojas.falabellatechtest.tech.service","mauricioRojas.falabella.tech.api"})
public class FallabellaTechtestApplication {

	public static void main(String[] args) throws Exception {
		new SpringApplication(FallabellaTechtestApplication.class).run(args);
	}
	
	class ExitException extends RuntimeException implements ExitCodeGenerator {
		private static final long serialVersionUID = 1L;

		@Override
		public	int getExitCode(){
			return 10;
		}
	}

}
