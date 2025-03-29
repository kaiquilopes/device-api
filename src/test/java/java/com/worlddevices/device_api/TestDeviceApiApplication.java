package java.com.worlddevices.device_api;

import org.springframework.boot.SpringApplication;

public class TestDeviceApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(DeviceApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
