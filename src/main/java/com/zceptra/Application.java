package com.zceptra;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.zceptra.entities.Account;
import com.zceptra.entities.StatementProfile;
import com.zceptra.repositories.AccountRepository;
import com.zceptra.repositories.StatementProfileRepository;

@SpringBootApplication
public class Application {
	
	//private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(AccountRepository accountRepository, StatementProfileRepository stmtRepository) {
		return (args) -> {
			// save a couple of customers
			accountRepository.save(new Account("commerzbank", "", "commerzbank"));
			accountRepository.save(new Account("comdirect", "", "Transfer"));
			accountRepository.save(new Account("wallet", "", "withdrawal"));
			accountRepository.save(new Account("other income", "", "Bundesagentur"));
			accountRepository.save(new Account("service charges", "", "KlassikDepot;Lohnsteuerhilfe"));
			accountRepository.save(new Account("utilities", "", "Erdgas; Rundfunk"));
			
			StatementProfile profile = new StatementProfile();
			profile.setColumnSeparator(";");
			profile.setRowSeparator("\n");
			profile.setDateHeader("Buchungstag");
			profile.setAmountHeader("Betrag");
			profile.setTextHeader("Buchungstext");
			
			stmtRepository.save(profile);
		};
	}	
}
