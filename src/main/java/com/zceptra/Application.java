package com.zceptra;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.zceptra.entities.Account;
import com.zceptra.entities.Category;
import com.zceptra.entities.StatementProfile;
import com.zceptra.repositories.AccountRepository;
import com.zceptra.repositories.CategoryRepository;
import com.zceptra.repositories.StatementProfileRepository;

@SpringBootApplication
public class Application {
	
	//private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(CategoryRepository categoryRepository, AccountRepository accountRepository, StatementProfileRepository stmtProfileRepository) {
		return (args) -> {
			
/*			//Setup the categories
			Category liquidCash = new Category("Liquid Cash", "Bank Accounts, Wallet etc.");
			categoryRepository.save(liquidCash);
			Category income = new Category("Income", "Salary and other income");
			categoryRepository.save(income);
			Category expenses = new Category("Expenses", "Expenses");
			categoryRepository.save(expenses);

			// save a couple of accounts
			accountRepository.save(new Account(liquidCash, "commerzbank", "", "commerzbank"));
			accountRepository.save(new Account(liquidCash, "comdirect", "", "Transfer"));
			accountRepository.save(new Account(expenses, "eat out", "", "restaurant;dinner"));
			accountRepository.save(new Account(liquidCash, "wallet", "", "withdrawal"));
			accountRepository.save(new Account(income, "other income", "", "Bundesagentur"));
			accountRepository.save(new Account(expenses, "service charges", "", "KlassikDepot;Lohnsteuerhilfe"));
			accountRepository.save(new Account(expenses, "utilities", "", "Erdgas; Rundfunk"));
			
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
			
			stmtProfileRepository.save(profile_2);*/
			
		};
	}	
}
