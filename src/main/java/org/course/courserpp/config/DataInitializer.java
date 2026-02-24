package org.course.courserpp.config;

import org.course.courserpp.model.ApplicationForCash;
import org.course.courserpp.model.Card;
import org.course.courserpp.model.CashWithdrawal;
import org.course.courserpp.model.MoneyTransfer;
import org.course.courserpp.model.ReplenishmentOperation;
import org.course.courserpp.model.Score;
import org.course.courserpp.model.User;
import org.course.courserpp.repository.ApplicationForCashRepository;
import org.course.courserpp.repository.CardRepository;
import org.course.courserpp.repository.CashWithdrawalRepository;
import org.course.courserpp.repository.MoneyTransferRepository;
import org.course.courserpp.repository.ReplenishmentOperationRepository;
import org.course.courserpp.repository.ScoreRepository;
import org.course.courserpp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final ScoreRepository scoreRepository;
    private final ApplicationForCashRepository applicationForCashRepository;
    private final CashWithdrawalRepository cashWithdrawalRepository;
    private final MoneyTransferRepository moneyTransferRepository;
    private final ReplenishmentOperationRepository replenishmentOperationRepository;

    public DataInitializer(UserRepository userRepository,
                           CardRepository cardRepository,
                           ScoreRepository scoreRepository,
                           ApplicationForCashRepository applicationForCashRepository,
                           CashWithdrawalRepository cashWithdrawalRepository,
                           MoneyTransferRepository moneyTransferRepository,
                           ReplenishmentOperationRepository replenishmentOperationRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.scoreRepository = scoreRepository;
        this.applicationForCashRepository = applicationForCashRepository;
        this.cashWithdrawalRepository = cashWithdrawalRepository;
        this.moneyTransferRepository = moneyTransferRepository;
        this.replenishmentOperationRepository = replenishmentOperationRepository;
    }

    @Override
    public void run(String... args) {
        boolean isDbEmpty = cardRepository.count() == 0
                && scoreRepository.count() == 0
                && applicationForCashRepository.count() == 0
                && cashWithdrawalRepository.count() == 0
                && moneyTransferRepository.count() == 0
                && replenishmentOperationRepository.count() == 0;

        if (!isDbEmpty) {
            return;
        }

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return;
        }

        User user = users.get(0);

        Card card1 = new Card();
        card1.setNumber("4276123456789010");
        card1.setBalance(50000L);
        card1.setCVC("123");
        card1.setUser(user);

        Card card2 = new Card();
        card2.setNumber("4276987654321012");
        card2.setBalance(120000L);
        card2.setCVC("456");
        card2.setUser(user);

        cardRepository.saveAll(List.of(card1, card2));

        Score score1 = new Score();
        score1.setNumber("40817810000000000001");
        score1.setBalance("75000");
        score1.setCurrency(810);
        score1.setUser(user);

        Score score2 = new Score();
        score2.setNumber("40817840000000000002");
        score2.setBalance("1500");
        score2.setCurrency(840);
        score2.setUser(user);

        scoreRepository.saveAll(List.of(score1, score2));

        ApplicationForCash app1 = new ApplicationForCash();
        app1.setDelay(30L);
        app1.setWithdrawalAmount(10000L);

        ApplicationForCash app2 = new ApplicationForCash();
        app2.setDelay(60L);
        app2.setWithdrawalAmount(25000L);

        applicationForCashRepository.saveAll(List.of(app1, app2));

        CashWithdrawal cw1 = new CashWithdrawal();
        cw1.setWithdrawalAmount(5000L);

        CashWithdrawal cw2 = new CashWithdrawal();
        cw2.setWithdrawalAmount(15000L);

        cashWithdrawalRepository.saveAll(List.of(cw1, cw2));

        MoneyTransfer mt1 = new MoneyTransfer();
        mt1.setSum(30000L);

        MoneyTransfer mt2 = new MoneyTransfer();
        mt2.setSum(7500L);

        moneyTransferRepository.saveAll(List.of(mt1, mt2));

        ReplenishmentOperation ro1 = new ReplenishmentOperation();
        ro1.setSum(100000L);

        ReplenishmentOperation ro2 = new ReplenishmentOperation();
        ro2.setSum(50000L);

        replenishmentOperationRepository.saveAll(List.of(ro1, ro2));
    }
}
