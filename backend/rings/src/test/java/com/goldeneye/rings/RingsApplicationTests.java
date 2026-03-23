package com.goldeneye.rings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import org.springframework.boot.SpringApplication;

@DisplayName("RingsApplication Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class RingsApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	void main_invokesSpringApplicationRun() {
		try (MockedStatic<SpringApplication> mockedSpring = mockStatic(SpringApplication.class)) {
			mockedSpring.when(() -> SpringApplication.run(RingsApplication.class, new String[]{}))
				.thenReturn(null);

			RingsApplication.main(new String[]{});

			mockedSpring.verify(() -> SpringApplication.run(RingsApplication.class, new String[]{}));
		}
	}
}
