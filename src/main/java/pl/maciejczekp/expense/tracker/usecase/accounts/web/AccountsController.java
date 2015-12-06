package pl.maciejczekp.expense.tracker.usecase.accounts.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.maciejczekp.expense.tracker.web.iface.ApplicationResponse;
import pl.maciejczekp.expense.tracker.model.Account;
import pl.maciejczekp.expense.tracker.usecase.accounts.ShowAccountUseCase;

import java.security.Principal;

@Controller
@RequestMapping(value = "/accounts/")
public class AccountsController {

    @Autowired
    private ShowAccountUseCase showAccountUseCase;

    @RequestMapping("/current/")
    public ResponseEntity<ApplicationResponse<Account>> showAccount(Principal principal) {
        Account account = showAccountUseCase.execute(new ShowAccountUseCase.ShowAccountRequest(principal.getName()));
        return ResponseEntity.ok(
                ApplicationResponse.success(account)
        );
    }

}
