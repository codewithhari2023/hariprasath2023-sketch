package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Model.Borrow;

import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Request.PenaltyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.List;

@Service
public class PenaltyService {

    @Autowired
    private EmailService emailService; // Assuming you have an EmailService class

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public BigDecimal calculatePenaltyAmount(PenaltyRequest borrow) {
        LocalDate borrowDate = LocalDate.parse(borrow.getIssueDate(), dateFormatter);
        LocalDate returnDate = LocalDate.parse(borrow.getReturnDate(), dateFormatter);

        LocalDate currentDate = LocalDate.now();
        long daysOverdue = ChronoUnit.DAYS.between(currentDate, returnDate);
        BigDecimal penaltyPerDay = new BigDecimal("200"); // Example penalty amount per day
        BigDecimal penaltyAmount = penaltyPerDay.multiply(BigDecimal.valueOf(daysOverdue));
        // Send email notification
        String subject = "Penalty Notification";
        String message = "Dear User,\n\n"
                + "You have incurred a penalty of $" + penaltyAmount + " for overdue return.\n\n"
                + "Thank you.";
        emailService.sendSimpleEmail(borrow.getEmail(), subject, message);

        return penaltyAmount;
    }


}
