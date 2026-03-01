package sanchez.bankingapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bankingapi")
public class BankingController {

    public BankingController() {
    }

    @GetMapping("/health")
    public String getHealth() {
        return "OK";
    }

}
