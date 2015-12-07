package pl.maciejczekp.expense.tracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.maciejczekp.expense.tracker.model.Account;
import pl.maciejczekp.expense.tracker.usecase.accounts.AddAccountEntryUseCase;
import pl.maciejczekp.expense.tracker.usecase.accounts.ShowAccountUseCase;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/accounts/")
public class AccountsController {

    @Autowired
    private ShowAccountUseCase showAccountUseCase;

    @Autowired
    private AddAccountEntryUseCase addAccountEntryUseCase;

    @RequestMapping("/current/")
    public ResponseEntity<ApplicationResponse<Account>> showAccount(Principal principal) {
        Account account = showAccountUseCase.execute(new ShowAccountUseCase.ShowAccountRequest(principal.getName()));
        return ResponseEntity.ok(
                ApplicationResponse.success(account)
        );
    }

    @RequestMapping(value = "entry/", method = RequestMethod.POST)
    public ResponseEntity<ApplicationResponse<Void>> addAccountEntry(Principal principal, @Valid @RequestBody AddAccountEntryUseCase.AddAccountEntryRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ApplicationResponse.error(
                    bindingResult.getFieldErrors().stream().map(FieldError::toString).collect(Collectors.joining("<br />"))
            ));
        }

        request.setUserEmail(principal.getName());
        addAccountEntryUseCase.execute(request);
        return ResponseEntity.ok(ApplicationResponse.success("Entry added"));
    }

}
