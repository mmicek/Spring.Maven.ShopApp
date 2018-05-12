package shopApp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import View.LoginFrame;
import View.MainFrame;

@Configuration
@ComponentScan("View")
@ComponentScan("Model")
public class ConfigFile {
	
	@Bean
	public MainFrame frame() {
		return MainFrame.getMainFrame();
	}
	
}
