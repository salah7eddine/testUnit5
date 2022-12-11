package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
class DemoApplicationTests {

	Calculator underTest = new Calculator();

	@Test
	void itShouldAddTwoNumbers() {
		// given
		int numOne = 11;
		int numTwo = 22;

		//When
		int result = underTest.add(numOne, numTwo);

		// then
		assertThat(result).isEqualTo(33);
	}

	class Calculator {
		int add(int a, int b) {return  a + b;}
	}

}
