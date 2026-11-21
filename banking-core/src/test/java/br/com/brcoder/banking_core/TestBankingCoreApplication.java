package br.com.brcoder.banking_core;

import org.springframework.boot.SpringApplication;

public class TestBankingCoreApplication {

	public static void main(String[] args) {
		SpringApplication.from(BankingCoreApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
