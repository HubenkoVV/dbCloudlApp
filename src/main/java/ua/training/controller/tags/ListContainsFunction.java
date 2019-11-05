package ua.training.controller.tags;

import ua.training.model.entities.Periodical;

import java.util.List;

public class ListContainsFunction {
    public static boolean containsPeriodical(List<Periodical> periodicals, Periodical periodical) {
        return periodicals != null && periodicals.contains(periodical);
    }
}
