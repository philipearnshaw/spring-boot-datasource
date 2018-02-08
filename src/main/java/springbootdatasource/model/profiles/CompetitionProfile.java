package springbootdatasource.model.profiles;

public class CompetitionProfile {
    public interface View {}
    public interface SummaryView extends View {}
    public interface DetailView extends SummaryView {}
}
