package application;

import java.util.ArrayList;
import java.util.List;

public class RuleBook {
    private List<Rule> rules;

    public RuleBook() {
        rules = new ArrayList<>();
        loadRules();
    }

    private void loadRules() {
        rules.add(new Rule("Client Information Must Match Client"));
        rules.add(new Rule("Debits and Credits on the Balance Sheet Must be Equal"));
        // Add more rules as needed
    }

    public List<Rule> getRules() {
        return rules;
    }
}

