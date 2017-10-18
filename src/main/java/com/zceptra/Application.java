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
	public CommandLineRunner demo(AccountRepository accountRepository, StatementProfileRepository stmtProfileRepository) {
		return (args) -> {
			// save a couple of customers
			accountRepository.save(new Account("commerzbank", "", "commerzbank"));
			accountRepository.save(new Account("comdirect", "", "Transfer"));
			accountRepository.save(new Account("eat out", "", "restaurant;dinner"));
			accountRepository.save(new Account("wallet", "", "withdrawal"));
			accountRepository.save(new Account("other income", "", "Bundesagentur"));
			accountRepository.save(new Account("service charges", "", "KlassikDepot;Lohnsteuerhilfe"));
			accountRepository.save(new Account("utilities", "", "Erdgas; Rundfunk"));
			
			StatementProfile profile_1 = new StatementProfile();
			profile_1.setColumnSeparator(";");
			profile_1.setRowSeparator("\n");
			profile_1.setDateHeader("Buchungstag");
			profile_1.setAmountHeader("Betrag");
			profile_1.setTextHeader("Buchungstext");
			
			stmtProfileRepository.save(profile_1);
			
			StatementProfile profile_2 = new StatementProfile();
			profile_2.setColumnSeparator(",");
			profile_2.setRowSeparator("\n");
			profile_2.setDateHeader("date");
			profile_2.setAmountHeader("amount");
			profile_2.setTextHeader("text");
			
			stmtProfileRepository.save(profile_2);
			
		};
	}	
}
